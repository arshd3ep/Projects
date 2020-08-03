package Bill;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import Bill.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

public class BillViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listViewNames;

    @FXML
    private DatePicker DPDos;

    @FXML
    private DatePicker DPDoe;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblDays;

    @FXML
    private Label lblCowQty;

    @FXML
    private Label lblCowPrice;

    @FXML
    private Label lblBuffaloPrice;

    @FXML
    private Label lblBuffaloQty;

    @FXML
    private Label lblMobile;

    @FXML
    private Label lblCowVariation;

    @FXML
    private Label lblBuffaloVariation;

    @FXML
    void doDoubleClick(MouseEvent event) throws SQLException 
    {
    	if(event.getClickCount()==2)
    	{
    		String name1=listViewNames.getSelectionModel().getSelectedItem();
    		listViewNames.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    		name1=listViewNames.getSelectionModel().getSelectedItem();
    		PreparedStatement pst=(PreparedStatement) con.prepareStatement("select cqty,bqty,cprice,bprice,mobile from customer where cname=?");
    		pst.setString(1, name1);
    		ResultSet table=pst.executeQuery();
    		while(table.next())
    		{
    			lblCowQty.setText(table.getFloat("cqty")+"");
    			lblBuffaloQty.setText(table.getFloat("bqty")+"");
    			lblCowPrice.setText(table.getFloat("cprice")+"");
    			lblBuffaloPrice.setText(table.getFloat("bprice")+"");
    			lblMobile.setText(table.getString("mobile"));
    		}
    	}
    }

    @FXML
    void doGetDays(ActionEvent event) 
    {
    	LocalDate ldwvs=DPDos.getValue();
    	//java.sql.Date jswos=java.sql.Date.valueOf(ldwvs);
    	LocalDate ldwve=DPDoe.getValue();
    	//java.sql.Date jswoe=java.sql.Date.valueOf(ldwve);
    	long Days=ChronoUnit.DAYS.between(ldwvs, ldwve);
    	lblDays.setText(Days+"");
    }
    
    @FXML
    void doGetVariations(ActionEvent event) throws SQLException 
    {
    	String name=listViewNames.getSelectionModel().getSelectedItem();
    	PreparedStatement pst1=(PreparedStatement) con.prepareStatement("select sum(VCQ) as sumVcq,sum(VBQ) as sumVbq from variations where cname=?");
		pst1.setString(1, name);
		ResultSet table1=pst1.executeQuery();
		while(table1.next())
		{
			lblCowVariation.setText(table1.getFloat("sumVcq")+"");
			lblBuffaloVariation.setText(table1.getFloat("sumVbq")+"");
		}
		
    }

    @FXML
    void doSave(ActionEvent event) throws SQLException 
    {
    	String name=listViewNames.getSelectionModel().getSelectedItem();
    	LocalDate date1=DPDos.getValue();
    	LocalDate date2=DPDoe.getValue();
    	java.sql.Date jswdos=java.sql.Date.valueOf(date1);
    	java.sql.Date jswdoe=java.sql.Date.valueOf(date2);
    	int days=Integer.parseInt(lblDays.getText());
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("insert into Bill values(?,?,?,?,?,?,?)");
    	pst.setString(1,name);
    	pst.setFloat(2, Float.parseFloat(lblCowQty.getText())*days+Float.parseFloat(lblCowVariation.getText()));
    	pst.setFloat(3, Float.parseFloat(lblBuffaloQty.getText())*days+Float.parseFloat(lblBuffaloVariation.getText()));
    	pst.setFloat(4, Float.parseFloat(lblTotal.getText()));
    	pst.setDate(5, jswdos);
    	pst.setDate(6, jswdoe);
    	pst.setInt(7, 0);
    	pst.executeUpdate();
    }
    
    
	
    @FXML
    void doTotal(ActionEvent event) throws SQLException 
    {
    	float cqty=0;
    	float bqty=0;
    	float cprice=0;
    	float bprice=0;
    	float vcq=0;
    	float vbq=0;
    	String name=listViewNames.getSelectionModel().getSelectedItem();
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("Select * from customer where cname=?");
    	pst.setString(1, name);
    	ResultSet tabel=pst.executeQuery();
    	while(tabel.next())
    	{
    		cqty=Float.parseFloat(tabel.getString("cqty"));
    		bqty=Float.parseFloat(tabel.getString("bqty"));
    		cprice=Float.parseFloat(tabel.getString("cprice"));
    		bprice=Float.parseFloat(tabel.getString("bprice"));
    	}
 
    	PreparedStatement pst1=(PreparedStatement) con.prepareStatement("select sum(VCQ) as sumVcq,sum(VBQ) as sumVbq from variations where cname=?");
    	pst1.setString(1, name);
    	ResultSet tabel1=pst1.executeQuery();
    	while(tabel1.next())
    	{
    		vcq=Float.parseFloat(tabel1.getString("sumVcq"));
    		vbq=Float.parseFloat(tabel1.getString("sumVbq"));
    	}
    	float total;
    	total=((cqty*cprice+bqty*bprice)*(Float.parseFloat(lblDays.getText()))-(vcq*cprice+vbq*bprice));
    	System.out.println(total);
    	
    	lblTotal.setText(total+"");
    }
    java.sql.Connection con=DBConnection.doConnect();
    ArrayList<String> cname;
    @FXML
    void initialize() throws SQLException {
    	cname=new ArrayList<>();
    	PreparedStatement pst=(PreparedStatement) con.prepareStatement("select distinct cname from customer");
    	ResultSet tabel=pst.executeQuery();
    	while(tabel.next())
    	{
    		String name=tabel.getString("cname");
    		cname.add(name);
    	}
    	listViewNames.getItems().addAll(cname);
    	listViewNames.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
}
