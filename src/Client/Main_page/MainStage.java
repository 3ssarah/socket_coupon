package Client.Main_page;

import Client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainStage extends Stage {

    private static MainController controller=null;
    private Client client;

    public MainStage(Client client, MainClient mainClient){
        super();
        this.client=client;
        setTitle("Main Page");
        setResizable(false);

        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("./MainFrame.fxml"));
            Parent main= loader.load();

            controller=loader.<MainController>getController();
            controller.setClient(client);
            controller.setMainClient(mainClient);
            System.out.println("controller setting");
           // controller.recvStoreList();


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
    public Client getClient(){
        return this.client;
    }
}
