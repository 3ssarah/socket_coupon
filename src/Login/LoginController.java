package Login;

import Client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    public void setLoginClient(LoginClient loginClient){
        this.loginClient=loginClient;
    }
//    public void setClient(Client client){
//        this.client=client;
//    }
}
