package Client.Main_page;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {

    private static MainController controller=null;

    public MainStage(MainClient client){
        super();
        setTitle("Main Page");
        setResizable(false);

        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("MainFrame.fxml"));
            Parent main= loader.load();

            controller=loader.<MainController>getController();
            controller.setMainClient(client);

            Scene s= new Scene(main);
            setScene(s);
            show();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    public MainController getController(){return controller;}
    public void setController(MainController con){controller=con;}
}
