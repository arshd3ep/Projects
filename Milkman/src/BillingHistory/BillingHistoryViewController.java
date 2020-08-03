package BillingHistory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import BillingHistory.BillingHistoryBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class BillingHistoryViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private CheckBox chkPending;

    @FXML
    private CheckBox chkPaid;

    @FXML
    private TableView<BillingHistoryBean> TVrecords;

    @FXML
    void doShowAll(ActionEvent event) throws SQLException 
    {
    	java.sql.PreparedStatement pst=con.prepareStatement("select * from bill");
    	getAllRecords(pst);
    }

    @FXML
    void doShowbyChk(ActionEvent event) throws SQLException 
    {
    	java.sql.PreparedStatement pst=null;
    	if(chkPaid.isSelected())
    	{
        	pst=con.prepareStatement("select * from bill where status=1");
    	}
    	else
    	{
    		if(chkPending.isSelected())
    		{
    			pst=con.prepareStatement("select * from bill where status=0");
    		}
    	}
    	getAllRecords(pst);
    }

    @FXML
    void doShowbyName(ActionEvent event) throws SQLException 
    {
    	String cname=comboName.getSelectionModel().getSelectedItem();
    	java.sql.PreparedStatement pst=con.prepareStatement("select * from bill where cname=?");
    	pst.setString(1, cname);
    	getAllRecords(pst);
    }
    ObservableList<BillingHistoryBean> list;
    void getAllRecords(java.sql.PreparedStatement pst) throws SQLException
    {
    	list=FXCollections.observableArrayList();
    	table=pst.executeQuery();
    	while(table.next())
    	{
    		String name=table.getString("cname");
    		float tcqty=table.getFloat("TCqty");
    		float tbqty=table.getFloat("TBqty");
    		float total=table.getFloat("TotalBill");
    		String dos=table.getString("dos");
    		String doe=table.getString("doe");
    		int status=table.getInt("status");
			BillingHistoryBean obj=new BillingHistoryBean(name, tcqty, tbqty, total, dos, doe, status);
			list.add(obj);
			TVrecords.getItems().add(obj);
    	}
    }
    Connection con=DBConnection.doConnect();
    ResultSet table;
    void addColumns()
    {
    	TableColumn<BillingHistoryBean, String> cname=new TableColumn<BillingHistoryBean, String>("Name");
     	cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
     	
     	TableColumn<BillingHistoryBean, Float> tCqty=new TableColumn<BillingHistoryBean, Float>("TCQty");
     	tCqty.setCellValueFactory(new PropertyValueFactory<>("TCqty"));
     	
     	TableColumn<BillingHistoryBean, Float> tBqty=new TableColumn<BillingHistoryBean, Float>("TCQty");
     	tBqty.setCellValueFactory(new PropertyValueFactory<>("TBqty"));
     	
     	TableColumn<BillingHistoryBean, Float> totalBill=new TableColumn<BillingHistoryBean, Float>("Total Bill");
     	totalBill.setCellValueFactory(new PropertyValueFactory<>("TotalBill"));
     	
     	TableColumn<BillingHistoryBean, String> dos=new TableColumn<BillingHistoryBean, String>("Date of start");
     	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));
     	
     	TableColumn<BillingHistoryBean, String> doe=new TableColumn<BillingHistoryBean, String>("Date of end");
     	doe.setCellValueFactory(new PropertyValueFactory<>("doe"));
     	
     	TableColumn<BillingHistoryBean, Integer> status=new TableColumn<BillingHistoryBean, Integer>("Status");
     	status.setCellValueFactory(new PropertyValueFactory<>("status"));
     	
     	TVrecords.getColumns().clear();
     	TVrecords.getColumns().addAll(cname, tCqty, tBqty, totalBill, dos, doe, status);
    }
    @FXML
    void doExport(ActionEvent event) throws Exception 
    {
    	writeExcel();
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
            String text="Name,TCqty,TBqty,TotalBill,dos,doe,status\n";
            writer.write(text);
            for (BillingHistoryBean p : list)
            {
				text = p.getCname()+ "," + p.getDos()+ "," + p.getDoe()+ "," + p.getTCqty()+","+p.getTBqty()+","+p.getTotalBill()+","+p.getStatus()+"\n";
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
    @FXML
    void initialize() throws SQLException 
    {
    	addColumns();
    	ArrayList<String> namesArray=new ArrayList<>();
		java.sql.PreparedStatement pst=con.prepareStatement("select cname from customer");
		ResultSet table=pst.executeQuery();
		
		while(table.next())
		{
			String name=table.getString("cname");
			namesArray.add(name+"");
		}
		comboName.getItems().addAll(namesArray);
    }
}
