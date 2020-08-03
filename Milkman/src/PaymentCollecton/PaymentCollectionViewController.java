package PaymentCollecton;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import PaymentCollecton.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

public class PaymentCollectionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private Label lblTotalBQ;

    @FXML
    private Label lblTotalCQ;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDatefrom;

    @FXML
    private Label lblDateto;

    @FXML
    void doRecipt(ActionEvent event) throws SQLException 
    {
    	LocalDate date=LocalDate.parse(lblDateto.getText());
    	java.sql.Date jswo=java.sql.Date.valueOf(date);
    	String name=comboName.getSelectionModel().getSelectedItem();
    	java.sql.PreparedStatement pst=con.prepareStatement("update bill set status=1 where cname=? and doe=? and status=0");
    	pst.setString(1, name);
    	pst.setDate(2, jswo);
    	pst.executeUpdate();
    }
    @FXML
    void doSelect(ActionEvent event) throws SQLException 
    {
    	
    	String cname=comboName.getSelectionModel().getSelectedItem();
    	java.sql.PreparedStatement pst=con.prepareStatement("select * from bill where cname=?");
    	pst.setString(1, cname);
    	ResultSet table1=pst.executeQuery();
    	while(table1.next())
    	{
    		lblTotalCQ.setText(table1.getString("TCqty"));
    		lblTotalBQ.setText(table1.getString("TBqty"));
    		lblDatefrom.setText(table1.getString("dos"));
    		lblDateto.setText(table1.getString("doe"));
    		lblAmount.setText(table1.getString("TotalBill"));
    	}
    }
    Connection con =DBConnection.doConnect();
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
		comboName.getItems().addAll(namesArray);
    }
}

