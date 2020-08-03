package VariationLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import VariationLog.VariationBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class VariationLogViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> chkName;

    @FXML
    private DatePicker DPdf;

    @FXML
    private DatePicker DPdt;

    @FXML
    private TableView<VariationBean> TVrecords;

    @FXML
    void doShowAll(ActionEvent event) throws SQLException 
    {
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from variations");
    	getAllRecords(pst);
    }

    @FXML
    void doShowbyDate(ActionEvent event) throws SQLException 
    {
       	LocalDate d1=DPdf.getValue();
    	java.sql.Date jswo1=java.sql.Date.valueOf(d1);
    	LocalDate d2=DPdt.getValue();
    	java.sql.Date jswo2=java.sql.Date.valueOf(d2);
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from variations where dov between ? and ?");
    	pst.setDate(1, jswo1);
    	pst.setDate(2, jswo2);
    	getAllRecords(pst);
    }

    @FXML
    void doShowbyName(ActionEvent event) throws SQLException 
    {
    	String name=chkName.getSelectionModel().getSelectedItem();
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("select * from variations where cname=?");
    	pst.setString(1, name);
    	getAllRecords(pst);
    }
    ResultSet table;
    ObservableList<VariationBean> list;

    void getAllRecords(PreparedStatement pst) throws SQLException 
    {
    	list=FXCollections.observableArrayList();
    	table=pst.executeQuery();
    	String cname="";
    	String date="";
    	float vcq=0;
    	float vbq=0;
    	while(table.next())
    	{
    		cname=table.getString("cname");
    		date=(table.getDate("dov")+"");
    		vcq=table.getFloat("VCQ");
    		vbq=table.getFloat("VBQ");
    		VariationBean obj=new VariationBean(cname,date,vcq,vbq);
        	list.add(obj);
        	TVrecords.setItems(list);
    	}
    }

	void addColumn()
    {
    	TableColumn<VariationBean, String> name=new TableColumn<VariationBean,String>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	
    	TableColumn<VariationBean, String> date=new TableColumn<VariationBean,String>("Date");
    	date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	TableColumn<VariationBean, Float> Vcqty=new TableColumn<VariationBean,Float>("Cow Qty");
    	Vcqty.setCellValueFactory(new PropertyValueFactory<>("Vcqty"));
    	
    	TableColumn<VariationBean, Float> Vbqty=new TableColumn<VariationBean,Float>("Buffalo Qty");
    	Vbqty.setCellValueFactory(new PropertyValueFactory<>("Vbqty"));
    	
    	TVrecords.getColumns().clear();
    	TVrecords.getColumns().addAll(name,date,Vcqty,Vbqty);
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
            String text="Name,Date,Vcqty,Vbqty\n";
            writer.write(text);
            for (VariationBean p : list)
            {
				text = p.getName()+ "," + p.getDate()+ "," + p.getVcqty()+ "," + p.getVbqty()+"\n";
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
    Connection con=DBConnection.doConnect();
    @FXML
    void initialize() throws SQLException 
    {	
    	ArrayList<String> namesArray=new ArrayList<>();
		java.sql.PreparedStatement pst=con.prepareStatement("select cname from customer");
		ResultSet table=pst.executeQuery();
		
		while(table.next())
		{
			String name=table.getString("cname");
			namesArray.add(name+"");
		}
		chkName.getItems().addAll(namesArray);
    	addColumn();
    }
}
