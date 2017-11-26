package Client.Login;

import Client.Register.RegisterController;
import Client.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  {

   //Client.Login Frame
    @FXML private Button login, create;
    @FXML private TextField id_f, pwd_f;


    // 로그인 애플리케이션 참조
    private LoginClient loginClient;

    private RegisterController con;


    public LoginController(){}
    public void setLoginClient(LoginClient loginClient){
        this.loginClient=loginClient;
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    public void handleLogin(ActionEvent event){
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
            FXMLLoader loader= new FXMLLoader(getClass().getResource("../Register/RegisterFrame.fxml"));
            Parent register=loader.load();

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



}
