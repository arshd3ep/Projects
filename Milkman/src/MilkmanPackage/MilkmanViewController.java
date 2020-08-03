package MilkmanPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MilkmanViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgCustomer;

    @FXML
    private TextField txtImagePath;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCowQ;

    @FXML
    private TextField txtBuffaloQ;

    @FXML
    private TextField txtCowP;

    @FXML
    private TextField txtBuffaloP;

    @FXML
    private DatePicker DPdos;

    @FXML
    private ComboBox<String> comboName;
    
    FileInputStream fin=null;
    String imgpath=null;
    String path=null;

    @FXML
    void doBrowse(ActionEvent event) throws FileNotFoundException, MalformedURLException 
    {
    	FileChooser fc=new FileChooser();
    	File file=fc.showOpenDialog(new Stage());
    	
    	if(file!=null)
    	{
//    		fin=new FileInputStream(file);
    		imgpath=file.toURI().toURL().toString();
    		path=file.getAbsolutePath();
    		txtImagePath.setText(path);
    		javafx.scene.image.Image img=new javafx.scene.image.Image(imgpath);
    		imgCustomer.setImage(img);
    	}
    }
    
    @FXML
    void doFetch(ActionEvent event) throws SQLException, MalformedURLException 
    {
		java.sql.PreparedStatement pst=con.prepareStatement("select * from customer where cname=?");
		pst.setString(1, comboName.getSelectionModel().getSelectedItem());
		ResultSet table=pst.executeQuery();
		while(table.next())
		{
			txtMobile.setText(table.getString("mobile"));
			txtAddress.setText(table.getString("address"));
			txtCowQ.setText(table.getFloat("cqty")+"");
			txtCowP.setText(table.getFloat("cprice")+"");
			txtBuffaloQ.setText(table.getFloat("bqty")+"");
			txtBuffaloP.setText(table.getFloat("bprice")+"");
			String date=table.getString("dos");
			LocalDate lsdwo=LocalDate.parse(date);
			DPdos.setValue(lsdwo);
			txtImagePath.setText(table.getString("picpath"));
			File file=new File(table.getString("picpath"));
			imgpath=file.toURI().toURL().toString();
			javafx.scene.image.Image img=new javafx.scene.image.Image(imgpath);
    		imgCustomer.setImage(img);
		}
    }
    
    @FXML
    void doLeft(ActionEvent event) throws SQLException, MalformedURLException 
    {
    	java.sql.PreparedStatement pst=con.prepareStatement("Delete from customer where cname=?");
    	pst.setString(1,comboName.getSelectionModel().getSelectedItem().toString());
    	pst.executeUpdate();
    	/*String pic=table.getString("picpath");
    	File file=new File(pic);
		imgpath=file.toURI().toURL().toString();
		imgCustomer.setImage(null);*/
    	
    }
    @FXML
    void doNew(ActionEvent event) 
    {
    	comboName.getSelectionModel().clearSelection();
    	txtMobile.setText("");
    	txtAddress.setText("");
    	txtCowP.setText("");
    	txtCowQ.setText("");
    	txtBuffaloP.setText("");
    	txtBuffaloQ.setText("");
    	txtImagePath.setText("");
    	DPdos.setValue(null);
    	imgCustomer.setImage(null);
    }

    @FXML
    void doSave(ActionEvent event) throws MalformedURLException 
    {	
    	try 
    	{
			LocalDate ldwo=DPdos.getValue();
			java.sql.Date jsdwo=java.sql.Date.valueOf(ldwo);
    		java.sql.PreparedStatement pst=con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?,?)");
			pst.setString(1, comboName.getSelectionModel().getSelectedItem());
			pst.setString(2, txtMobile.getText());
			pst.setString(3, txtAddress.getText());
			pst.setFloat(4, Float.parseFloat(txtCowQ.getText()));
			pst.setFloat(5, Float.parseFloat(txtCowP.getText()));
			pst.setFloat(6, Float.parseFloat(txtBuffaloQ.getText()));
			pst.setFloat(7, Float.parseFloat(txtBuffaloP.getText()));
			pst.setDate(8, jsdwo);
			pst.setString(9, txtImagePath.getText());
			pst.executeUpdate();
			/*File file=new File(txtImagePath.getText());
			imgpath=file.toURI().toURL().toString();
			javafx.scene.image.Image img=new javafx.scene.image.Image(imgpath);
    		imgCustomer.setImage(img);*/
    		
    	} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}

    }

    @FXML
    void doUpdate(ActionEvent event) throws SQLException, MalformedURLException 
    {
    	java.sql.Date swdob=null;
		String stwdob="";
		
		LocalDate lwdob=DPdos.getValue();
		if(lwdob==null)
			{
				stwdob=DPdos.getEditor().getText();
				lwdob=LocalDate.parse(stwdob);
			}
			swdob= java.sql.Date.valueOf(lwdob);
		java.sql.PreparedStatement pst=con.prepareStatement("update customer set mobile=?, address=?, cqty=?, cprice=?, bqty=?, bprice=?, dos=?, picpath=? where cname=?");
		
		pst.setString(1, txtMobile.getText());
		pst.setString(2, txtAddress.getText());
		pst.setFloat(3, Float.parseFloat(txtCowQ.getText()));
		pst.setFloat(4, Float.parseFloat(txtCowP.getText()));
		pst.setFloat(5, Float.parseFloat(txtBuffaloQ.getText()));
		pst.setFloat(6, Float.parseFloat(txtBuffaloP.getText()));
		pst.setDate(7, swdob);
		pst.setString(8, txtImagePath.getText());
		pst.setString(9, comboName.getSelectionModel().getSelectedItem());
		
		pst.executeUpdate();
		if(txtImagePath.getText()=="")
		{
	    FileChooser fc=new FileChooser();
	    File file=fc.showOpenDialog(new Stage());
	    if(file!=null)
	    {
//	    	fin=new FileInputStream(file);
	   		imgpath=file.toURI().toURL().toString();
	   		path=file.getAbsolutePath();
	   		txtImagePath.setText(path);
	   		javafx.scene.image.Image img=new javafx.scene.image.Image(imgpath);
    		imgCustomer.setImage(img);
    	}
		}
    }
    
    void fillCombo()
    {
    	ArrayList<String> namesArray=new ArrayList<>();
    	try {
			java.sql.PreparedStatement pst=con.prepareStatement("select * from customer");
			ResultSet table=pst.executeQuery();
			String name;
			while(table.next())
			{
				name=table.getString("cname");
				namesArray.add(name+"");
			}
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	comboName.getItems().addAll(namesArray);
    }
    java.sql.Connection con=DBConnection.doConnect();
    
    @FXML
    void initialize() 
    {
    	fillCombo();
    }
}
