package Client.Register;


import Client.Login.LoginClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterStage extends Stage {

    private RegisterController controller=null;


    public RegisterStage(LoginClient client){

        try{

            FXMLLoader loader= new FXMLLoader(getClass().getResource("RegisterFrame.fxml"));
           Parent register= loader.load();


            controller =loader.getController();
            controller.setLoginClient(client);

//            Scene s= new Scene(pane);
//            setScene(s);
//            show();

    }catch(Exception e){
            e.printStackTrace();
        }

    }

    public RegisterController getController() {
        return controller;
    }

    public void setController(RegisterController controller) {
        this.controller = controller;
    }
}
