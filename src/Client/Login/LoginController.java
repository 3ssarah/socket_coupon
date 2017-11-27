package Client.Login;

import Client.Client;
import Client.Main_page.MainClient;
import Client.Register.RegisterController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController  {

    /**Login Frame**/
   //Client.Login Frame
    @FXML private Button login, create;
    @FXML private TextField id_f, pwd_f;
    /**Alert*/
    Alert alert;


    private static LoginController controller;
    private static RegisterController con;
    // 로그인 애플리케이션 참조
    private  LoginClient loginClient;


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



    }

    @FXML
    public void handleLogin(ActionEvent event){
        loginClient.sendData("1");//send Log-in signal
        String ID= id_f.getText();
        String pwd= pwd_f.getText();

        BufferedReader br= null;

        try{
            br= new BufferedReader(new InputStreamReader(loginClient.getClient().getLoginSock().getInputStream()));


            sendData(ID);
            sendData(pwd);

            //receive msg from server
            String check= null;
            alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(br.readLine());
            alert.setContentText(":)");
            alert.showAndWait();

            check=br.readLine();


            /** recv check data from server**/

            //Login complete
            if(check.equals("0")){
                System.out.println("login complete");
                loginClient.getClient().getData().setID(ID);
                loginClient.getClient().getData().setPwd(pwd);
                loginClient.getClient().loginComplete=true;

            //여기서 현재화면 죽이고

               //oginClient.getClient().getLoginSock().close();
                //새로운 화면 띄우기
                new MainClient(loginClient);
                System.out.println("login complete3");
                loginClient.getPrimaryStage().hide();
            }
            else if(check.equals("-1"))System.out.println("Unless get check!");
            else System.out.println(check.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
//        loginClient.tryLogin(data);
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
//    @FXML
//    public void handleCancel_r(ActionEvent event){
//        // /if Cancel button clicked back to LoginView
//        try{
//
//            StackPane root=(StackPane)cancelBtn.getScene().getRoot();
//            root.getChildren().remove(register);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
//    @FXML
//    public void handleCreateBtn_r(ActionEvent event){
//
//        String id=id_field.getText();
//        String pw= pwd_field.getText();
//        String phone=phone_field.getText();
//        boolean shop= false;
//       if(radioBtn2.isSelected())shop=true;
//
//       UserData data= new UserData(id,pw,phone, shop);
//        getController().loginClient.saveNew_member(data);
//
////       loginClient.sendData("0");// send register signal
////       loginClient.sendData(id);
////       loginClient.sendData(pw);
////       loginClient.sendData(phone);
////       loginClient.sendData(shop);
//
//
////        sendData(id_field.getText());//send id
////        sendData(pwd_field.getText());//send pwd
////        sendData(phone_field.getText());//send phone
////
////        if(radioBtn1.isSelected())
////            sendData("false");
////        else if(radioBtn2.isSelected())
////            sendData("true");
//        // change to login view
//        handleCancel_r(event);
//    }
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




}
