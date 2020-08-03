package TableView;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import TableView.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import TableView.CustomerBean;

public class CustomerTableViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<CustomerBean> TVRecords;

    @FXML
    private DatePicker DPpad;

    @FXML
    private CheckBox chkCow;

    @FXML
    private CheckBox chkBuffalo;
    
    ObservableList<CustomerBean> list;
    ResultSet tabel;
    void getRecordsfromTable(PreparedStatement pst) throws SQLException
    {
    	
    	list=FXCollections.observableArrayList();
    	
    	tabel=pst.executeQuery();
    	while(tabel.next())
    	{
    		String name=tabel.getString("cname");
    		float cqty=tabel.getFloat("cqty");
    		float bqty=tabel.getFloat("bqty");
    		float cprice=tabel.getFloat("cprice");
    		float bprice=tabel.getFloat("bprice");
    		String address=tabel.getString("address");
    		String date=tabel.getString("dos");
    		String mobile=tabel.getString("mobile");
    		CustomerBean obj=new CustomerBean(date, name, mobile, cqty, cprice, bqty, bprice, address);
    		list.add(obj);
    		TVRecords.setItems(list);
    	}
    }
    @FXML
    void doDateFetch(ActionEvent event) throws SQLException 
    {
    	java.sql.PreparedStatement pst=con.prepareStatement("select * from customer where dos=?");
    	pst.setString(1, DPpad.getValue()+"");
    	getRecordsfromTable((PreparedStatement) pst);
    }

    @FXML
    void doFetchbyMilk(ActionEvent event) throws SQLException 
    {
    	if(chkCow.isSelected())
    	{
    		java.sql.PreparedStatement pst=con.prepareStatement("select * from customer where cqty>0");
    		getRecordsfromTable((PreparedStatement) pst);
    	}
    	else
    	{
    		if(chkBuffalo.isSelected())
    		{
    			java.sql.PreparedStatement pst=con.prepareStatement("select * from customer where bqty>0");
        		getRecordsfromTable((PreparedStatement) pst);
    		}
    	}
    }

    @FXML
    void doShowAll(ActionEvent event) throws SQLException 
    {
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from customer");
    	getRecordsfromTable(pst);
    }
    
    void addColumns()
    {
    	TableColumn<CustomerBean, String> date=new TableColumn<CustomerBean, String>("Date");
     	date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	TableColumn<CustomerBean, String> name=new TableColumn<CustomerBean, String>("Name");
     	name.setCellValueFactory(new PropertyValueFactory<>("name"));
     	
     	TableColumn<CustomerBean, String> mobile=new TableColumn<CustomerBean, String>("Mobile");
     	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
     	
     	TableColumn<CustomerBean, Float> cqty=new TableColumn<CustomerBean, Float>("CQty");
     	cqty.setCellValueFactory(new PropertyValueFactory<>("cqty"));
     	
     	TableColumn<CustomerBean, Float> cprice=new TableColumn<CustomerBean, Float>("CPrice");
     	cprice.setCellValueFactory(new PropertyValueFactory<>("cprice"));
     	
     	TableColumn<CustomerBean, Float> bqty=new TableColumn<CustomerBean, Float>("BQty");
     	bqty.setCellValueFactory(new PropertyValueFactory<>("bqty"));
     	
     	TableColumn<CustomerBean, Float> bprice=new TableColumn<CustomerBean, Float>("Bprice");
     	bprice.setCellValueFactory(new PropertyValueFactory<>("bprice"));
     	
     	TableColumn<CustomerBean, String> address=new TableColumn<CustomerBean, String>("Address");
     	address.setCellValueFactory(new PropertyValueFactory<>("address"));
     	
     	TVRecords.getColumns().clear();
     	TVRecords.getColumns().addAll(date, name, mobile, cqty, cprice, bqty, bprice, address);
    }

	public void writeExcel() throws Exception {
        Writer writer = null;
        try {
        	FileChooser chooser=new FileChooser();
	    	
        	chooser.setTitle("Select Path:");
        	
        	chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*")
                    
                );
        	 File file=chooser.showSaveDialog(null);
        	String filePath=file.getAbsolutePath();
        	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
        	{
        		filePath=filePath+".csv";
        	}
        	 file = new File(filePath);
        	 
        	 
        	 
            writer = new BufferedWriter(new FileWriter(file));
            String text="date,name,mobile,cqty,cprice,bqty,bprice,address\n";
            writer.write(text);
            for (CustomerBean p : list)
            {
				text = p.getDate()+ "," + p.getName()+ "," + p.getMobile()+ "," + p.getCqty()+"," + p.getCprice()+ "," + p.getBqty()+ "," + p.getBprice()+"," + p.getAddress()+"\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
           
            writer.flush();
             writer.close();
        }
    }
    Connection con;
    @FXML
    void initialize() {
    	con=DBConnection.doConnect();
    	addColumns();

    }
}
