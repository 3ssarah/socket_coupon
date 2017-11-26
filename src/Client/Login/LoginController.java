package Client.Login;

import Client.Register.RegisterController;
import Client.Register.RegisterStage;
import Client.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  {

    /**Login Frame**/
   //Client.Login Frame
    @FXML private Button login, create;
    @FXML private TextField id_f, pwd_f;

    private static LoginController controller;
    private static RegisterController con;
    // 로그인 애플리케이션 참조
    private  LoginClient loginClient;

    /**Register Frame**/
    @FXML
    private RadioButton radioBtn1, radioBtn2;
    @FXML private TextField id_field, pwd_field, phone_field;
    @FXML
    private Button cancelBtn, createBtn;
    @FXML private AnchorPane register;


    public LoginController(){
        controller=this;
    }
    public static LoginController getController(){
        return controller;
    }
    public void setLoginClient(LoginClient loginClient){
        this.loginClient=loginClient;
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup group= new ToggleGroup();
        radioBtn1.setToggleGroup(group);
        radioBtn1.setSelected(true);
        radioBtn2.setToggleGroup(group);
    }

    @FXML
    public void handleLogin(ActionEvent event){
        loginClient.sendData("1");//send Log-in signal
        String ID= id_f.getText();
        String pwd= pwd_f.getText();
        UserData data= new UserData(ID,pwd);
        loginClient.tryLogin(data);
        //text field initialization
        id_f.setText("");
        pwd_f.setText("");
    }
    @FXML
    public void handleCreate(ActionEvent event){

        //로그인 화면에서 계정생성 화면으로 전환하기
        try{
            loginClient.sendData("0");//send Register signal

            FXMLLoader loader= new FXMLLoader(getClass().getResource("../Register/RegisterFrame.fxml"));
            Parent register=loader.load();
            con =loader.<RegisterController>getController();
            con.setLoginClient(loginClient);


//            Parent register=FXMLLoader.load(getClass().getResource("../Register/RegisterFrame.fxml"));
//            Scene s= new Scene(register);
//            Stage primary= (Stage)create.getScene().getWindow();
//            primary.setScene(s);
            StackPane root=(StackPane)create.getScene().getRoot();
            root.getChildren().add(register);


        }catch(Exception e){
            e.printStackTrace();
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

        String id=id_field.getText();
        String pw= pwd_field.getText();
        String phone=phone_field.getText();
        boolean shop= false;
       if(radioBtn2.isSelected())shop=true;

       UserData data= new UserData(id,pw,phone, shop);
        getController().loginClient.saveNew_member(data);

//       loginClient.sendData("0");// send register signal
//       loginClient.sendData(id);
//       loginClient.sendData(pw);
//       loginClient.sendData(phone);
//       loginClient.sendData(shop);


//        sendData(id_field.getText());//send id
//        sendData(pwd_field.getText());//send pwd
//        sendData(phone_field.getText());//send phone
//
//        if(radioBtn1.isSelected())
//            sendData("false");
//        else if(radioBtn2.isSelected())
//            sendData("true");
        // change to login view
        handleCancel_r(event);
    }




}
