package Login;

import Client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginClient extends Application {

    Client client;
    private Stage primaryStage;
    private StackPane rootLayout;

    public LoginClient() {

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        client= new Client(null,null, new Socket("127.0.0.1",12345));

        initRootLayout();
        showLoginFrame();

    }

    public void initRootLayout() {

        try {
            //상위레이아웃을 가져온다
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((LoginClient.class.getResource("view/RootLayout.fxml")));
            rootLayout =loader.load();

            //show scene contain root layout
            Scene s = new Scene(rootLayout);
            primaryStage.setScene(s);
            primaryStage.show();

            //컨트롤러를 이용할 수 있게 한다
            LoginController controller= loader.getController();
            controller.setLoginClient(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showLoginFrame(){
        try{
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(LoginClient.class.getResource("view/LoginFrame.fxml"));
            TitledPane loginFrame=loader.load();
            StackPane root=(StackPane) primaryStage.getScene().getRoot();
            root.getChildren().add(loginFrame);

            LoginController controller= loader.getController();
            controller.setLoginClient(this);

        }catch(IOException e){
            e.printStackTrace();
        }
    }




    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void main(String[] args) {

        launch();

    }

}



