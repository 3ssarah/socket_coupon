package Client.Register;

import Client.Client;
import Client.Login.LoginClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController{
    //Register Frame
    @FXML
    private RadioButton radioBtn1, radioBtn2;
    @FXML private TextField id_field, pwd_field, phone_field;
    @FXML
    private Button cancelBtn, createBtn;
    @FXML private AnchorPane register;

    Alert alert;


    private LoginClient loginClient;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {


        ToggleGroup group= new ToggleGroup();
        radioBtn1.setToggleGroup(group);
        radioBtn1.setSelected(true);
        radioBtn2.setToggleGroup(group);

    }

    public void setLoginClient(LoginClient loginClient){
        this.loginClient=loginClient;
    }
    public void sendData(String str){
        PrintWriter pw = null;

        try{

            Client tempC= loginClient.getClient();
            if(tempC.getLoginSock().isConnected())System.out.println("연결되어있음");
            pw = new PrintWriter(tempC.getLoginSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void handleCancel_r(ActionEvent event){
        // /if Cancel button clicked back to LoginView
        try{

            StackPane root=(StackPane)cancelBtn.getScene().getRoot();
            root.getChildren().remove(register);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    public void handleCreateBtn_r(ActionEvent event){

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(loginClient.getClient().getLoginSock().getInputStream()));
        }catch(Exception e){e.printStackTrace();}
        sendData(id_field.getText());//send id
        sendData(pwd_field.getText());//send pwd
        sendData(phone_field.getText());//send phone

        if(radioBtn1.isSelected())
            sendData("false");
        else if(radioBtn2.isSelected())
            sendData("true");


        // change to login view

        try{
            alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Look!");
            alert.setContentText(br.readLine());
            alert.showAndWait();

            StackPane root=(StackPane)createBtn.getScene().getRoot();
            root.getChildren().remove(register);
        }catch(Exception e){
            e.printStackTrace();
        }
        //handleCancel_r(event);
    }

}