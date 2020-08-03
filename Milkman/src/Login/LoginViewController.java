package Login;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;
    
    @FXML
    private CheckBox chkpass;

    @FXML
    private TextField txtPass;

    @FXML
    void doLogIn(ActionEvent event) throws SQLException, IOException 
    {
    	java.sql.PreparedStatement pst=con.prepareStatement("select * from Login");
    	ResultSet table=pst.executeQuery();
    	String pass=null;
    	String User=null;
    	while(table.next())
    	{
    		User=table.getString("UserId");
    		pass=table.getString("Pass");
    	}
    	if(txtUserId.getText().equals(User) && txtPassword.getText().equals(pass))
    	{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/DashboardView.fxml")); 
    		Scene scene = new Scene(root);
    		Stage stage=new Stage();
    		stage.setScene(scene);
    		stage.show();
    		((Node)event.getSource()).getScene().getWindow().hide();
    	}
    }

    @FXML
    void doShowPass(ActionEvent event) 
    {
    	txtPass.setText(txtPassword.getText());
    	if(chkpass.isSelected())
    	{
    		txtPass.setVisible(true);
    		txtPassword.setVisible(false);
    	}
    	else
    	{
    		txtPass.setVisible(false);
    		txtPassword.setVisible(true);
    	}
    }
    java.sql.Connection con=DBConnection.doConnect();
    @FXML
    void initialize() 
    {
    	txtPass.setVisible(false);
    }
}
