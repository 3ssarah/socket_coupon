package Client.Main_page;

import Client.Store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import Client.Client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController {

    @FXML
    Button  OKBtn , CBtn;
    @FXML
    TextField name_f, category_f, location_f, phone_f;

    Alert alert;

    private Client client;
    private MainController mainController;
    public void setMainController(MainController mainController){this.mainController=mainController;}


    @FXML public void initialize(URL location, ResourceBundle resources){
    }
    public void setClient(Client  client){this.client=client;}
    public void sendData(String str){
        PrintWriter pw= null;
        try{
            pw= new PrintWriter(client.getLoginSock().getOutputStream(),true);
            pw.println(str);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void handleOk(ActionEvent event){


        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(client.getLoginSock().getInputStream()));
        }catch(Exception e){e.printStackTrace();}

        sendData(name_f.getText());
        sendData(category_f.getText());
        sendData(location_f.getText());
        sendData(phone_f.getText());

        mainController.getName_store().setText(name_f.getText());
        Store tempstore=new Store(name_f.getText(), category_f.getText(), phone_f.getText(), location_f.getText(), client.getData().getID());
        client.getData().setStore(tempstore);

        try{
            alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Look!");
            alert.setHeaderText(br.readLine());
            alert.setContentText(":)");
            alert.showAndWait();


        }catch(Exception e){
            e.printStackTrace();
        }
        mainController.getAddStoreBtn().setDisable(true);
        Stage stage= (Stage)OKBtn.getScene().getWindow();
        stage.close();
    }
    public void handleCBtn(ActionEvent event){
        Stage stage= (Stage)CBtn.getScene().getWindow();
        stage.close();
    }
}
