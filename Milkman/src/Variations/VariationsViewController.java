package Variations;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class VariationsViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtVCowQ;

    @FXML
    private TextField txtVBuffaloQ;

    @FXML
    private Label lblCQ;

    @FXML
    private Label labelBQ;

    @FXML
    private DatePicker DPdate;

    @FXML
    private ListView<String> listViewCName;
    
    @FXML
    private CheckBox chkAbsent;
    
    float f3=0;
	float f4=0;
	float f1=0;
	float f2=0;
    @FXML
    void doDeleteOthers(ActionEvent event) throws SQLException 
    {
    	ObservableList<String> sname=listViewCName.getSelectionModel().getSelectedItems();
    	listViewCName.getItems().retainAll(sname);
    	PreparedStatement pst1=(PreparedStatement) con.prepareStatement("select cqty,bqty from customer where cname=?");
    	pst1.setString(1, listViewCName.getSelectionModel().getSelectedItem());
    	ResultSet table=pst1.executeQuery();
    	while(table.next())
    	{
    		f3=table.getFloat("cqty");
    		f4=table.getFloat("bqty");
    		lblCQ.setText(f3+"");
    		labelBQ.setText(f4+"");
    	}
    }

    @FXML
    void doSave(ActionEvent event) throws SQLException 
    {
    	if(f1>f3&&f2>f4)
    	{
    		f1=Float.parseFloat(txtVCowQ.getText());
        	f2=Float.parseFloat(txtVBuffaloQ.getText());
    		f1=f1-f3;
    		f3=f2-f4;
    	}
    	if(f1>f3&&f2<f4)
    	{
    		f1=Float.parseFloat(txtVCowQ.getText());
        	f2=Float.parseFloat(txtVBuffaloQ.getText());
    		f1=f1-f3;
    		f2=f2-f4;
    	}
    	if(f1<f3&&f2>f4)
    	{
    		f1=Float.parseFloat(txtVCowQ.getText());
        	f2=Float.parseFloat(txtVBuffaloQ.getText());
    		f1=f1-f3;
    		f2=f2-f4;
    	}
    	/*if(f1<f3&&f2<f4)
    	{
    		f1=Float.parseFloat(txtVCowQ.getText());
        	f2=Float.parseFloat(txtVBuffaloQ.getText());
    		f1=f1-f3;
    		f2=f2-f4;
    	}*/
    	if(chkAbsent.isSelected())
    	{
    		f1=-f3;
    		f2=-f4;
    	}
    	LocalDate ldwo =DPdate.getValue();
    	java.sql.Date jsdwo=java.sql.Date.valueOf(ldwo);
    	java.sql.PreparedStatement pst=con.prepareStatement("insert into variations values(?,?,?,?)");
    	pst.setString(1, listViewCName.getSelectionModel().getSelectedItem()+"");
    	pst.setDate(2,jsdwo);
    	pst.setFloat(3, f1);
    	pst.setFloat(4, f2);
    	pst.executeUpdate();
    }
    java.sql.Connection con=DBConnection.doConnect();
    ArrayList<String> cname;
    @FXML
    void initialize() throws SQLException 
    {
    	cname=new ArrayList<>();
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("select distinct cname from customer");
    	ResultSet tabel=pst.executeQuery();
    	while(tabel.next())
    	{
    		String name=tabel.getString("cname");
    		cname.add(name);
    	}
    	listViewCName.getItems().addAll(cname);
    	listViewCName.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
