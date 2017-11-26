package Client.Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginStage extends Stage{

    private LoginController controller =null;

    public LoginStage(LoginClient client){
        super();

        setTitle("Login");
        setResizable(false);

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFrame.fxml"));
//            StackPane pane = loader.load();
            Parent pane = loader.load();

            controller = loader.getController();
            controller.setLoginClient(client);

            Scene scene = new Scene(pane);
            setScene(scene);
            show();
        }catch (Exception e){
            e.printStackTrace();
                        System.exit(0);
        }


    }

    public LoginController getController() {
        return controller;
    }
    public void setController(LoginController controller){
        this.controller=controller;
    }
}
