package Client.Store;

import Client.Main_page.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StoreController {
    @FXML
    private Button menuBtn, eventBtn, sendBtn, backBtn;
    @FXML
    private Label addr, phone, type,store_name;
    @FXML
    private ListView commentListView;
    @FXML
    private Pane storeFrame;

    /**Controller for menu and event*/
    private static EventInfoController eventCon;
    private static MenuController menuCon;

    /**client 참조*/
    private MainClient mainClient;
    /**store 참조*/
    private Store store=null;
    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}
    public void setStore(Store store){this.store=store;}
    public StoreController(){

    }
    public void handleMenuBtn(ActionEvent event){

        //send specific signal to server
        Stage menu= new Stage();
        menu.initOwner(mainClient.getPrimaryStage());
        menu.setTitle("Menu");
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("MenuFrame.fxml"));
            Parent parent = loader.load();

            menuCon= loader.<MenuController>getController();
            menuCon.setMainClient(mainClient);
            //menuCon.setClient(mainClient.getClient());

            Scene s= new Scene(parent);
            menu.setScene(s);
            menu.setResizable(false);
            menu.show();

        }catch (Exception e){e.printStackTrace();}

    }
    public void handleEventBtn(ActionEvent event){

        //send specific signal to server
        Stage event_info= new Stage();
        event_info.initOwner(mainClient.getPrimaryStage());
        event_info.setTitle("Event");
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("Event_infoFrame.fxml"));
            Parent parent = loader.load();

            eventCon= loader.<EventInfoController>getController();
            eventCon.setClient(mainClient.getClient());

            Scene s= new Scene(parent);
            event_info.setScene(s);
            event_info.setResizable(false);
            event_info.show();

        }catch (Exception e){e.printStackTrace();}


    }
    public void handleSendBtn(ActionEvent event){

    }
    public void handleBackBtn(ActionEvent event){
        try{
            StackPane root=(StackPane)backBtn.getScene().getRoot();
            root.getChildren().remove(storeFrame);
        }catch(Exception e){e.printStackTrace();}
    }
}
