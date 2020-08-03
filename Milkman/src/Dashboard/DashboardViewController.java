package Dashboard;

import javafx.scene.media.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class DashboardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void doOpenVariation(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Variations/VariationsView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void doopenCustomerList(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("TableView/CustomerTableView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void doopenEntryForm(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("MilkmanPackage/MilkmanView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void doopenPaymentCollection(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("PaymentCollecton/PaymentCollectionView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void doopenPaymentHistory(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("BillingHistory/BillingHistoryView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void doopenTotalBill(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("Bill/BillView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    void doopenVariationLog(MouseEvent event) throws IOException 
    {
    	playSound();
    	Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("VariationLog/VariationLogView.fxml")); 
		Scene scene = new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.show();
    }
    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
/*    void playSong()
    {
    	
    	url=getClass().getResource("bg.mp3");
		media=new Media(url.toString());
		mediaplayer=new MediaPlayer(media);
		mediaplayer.play();
    }*/
    void playSound(){
    	url=getClass().getResource("click.wav");
    	audio=new AudioClip(url.toString());
		audio.play();
    }
    @FXML
    void initialize() 
    {
    	 	
    	       
    }
}
