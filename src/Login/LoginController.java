package Login;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  {


    @FXML private Button login, create;
    @FXML private TextField login_f, pwd_f;
    // 로그인 애플리케이션 참조
    private LoginClient loginClient;

    public LoginController(){

    }
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void sendData(String str){
        PrintWriter pw = null;

        try{
            pw = new PrintWriter(loginClient.client.getLoginSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void setLoginClient(LoginClient loginClient){
        this.loginClient=loginClient;
    }
    @FXML
    public void handleLogin(ActionEvent event){
        BufferedReader br= null;
        try{
            br= new BufferedReader(new InputStreamReader(loginClient.client.getLoginSock().getInputStream()));
            sendData("1");// send mode data: log in mode
//            if (login_f.getText().equals("")) {
//                return;
//            }
            String ID= login_f.getText();
            String pwd= pwd_f.getText();
            sendData(ID);
            sendData(pwd);

            //receive msg from server
            String check=null;
            check=br.readLine();

            //Login complete
            if(check.equals("0")){
                this.loginClient.client.setID(ID);
                this.loginClient.client.setPwd(pwd);
                this.loginClient.client.loginComplete=true;
                //여기서 현재화면 죽이고

                //close Login socket
                loginClient.client.getLoginSock().close();

                //새로운 화면 띄우기
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        //text field initialization
        login_f.setText("");
        pwd_f.setText("");
    }
    @FXML
    public void handleCreate(ActionEvent event){
        String mode = "0";
        sendData(mode);  //send register signal to server

        //로그인 화면에서 계정생성 화면으로 전환하기
        try{
            Parent register= FXMLLoader.load(getClass().getResource("view/RegisterFrame.fxml"));
            Scene s= new Scene(register);
            Stage primaryStage=loginClient.getPrimaryStage();
            primaryStage.setScene(s);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
//    public void setClient(Client client){
//        this.client=client;
//    }
}
