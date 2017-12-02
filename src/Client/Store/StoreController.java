package Client.Store;

import Client.Download.DownloadClient;
import Client.Download.DownloadController;
import Client.Main_page.MainClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StoreController implements Initializable{
    @FXML
    private Button  sendBtn, backBtn;
    @FXML
    private Label addr, phone, type,store_name;
    @FXML
    private ListView<String> commentListView, menuList, eventList;
    @FXML
    private Pane storeFrame;
    @FXML
    TextArea textArea;
    ObservableList<String> m_list= FXCollections.observableArrayList();
    ObservableList<String> e_list= FXCollections.observableArrayList();
    ObservableList<String> comment_list= FXCollections.observableArrayList();

    /**Controller for menu and event*/
    private static EventInfoController eventCon;
    private static MenuController menuCon;
    private static DownloadController downCon;

    /**client 참조*/
    private MainClient mainClient;
    private ChatClient chatClient;
    /**store 참조*/
    private Store store=null;

    public void setChatClient(ChatClient chatClient){this.chatClient=chatClient;}
    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}
    public void setStore(Store store){this.store=store;}
    public StoreController(){


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        menuList.setItems(m_list);
        eventList.setItems(e_list);
        menuList.getSelectionModel().selectedItemProperty().addListener((observable ,oldValue, newValue)->changeToDownload(newValue));
        eventList.getSelectionModel().selectedItemProperty().addListener((observable ,oldValue, newValue)->showEventDetails(newValue));

    }
    public void settingLabel(){
        store_name.setText(store.getStore_name());
        type.setText(store.getCategory());
        phone.setText(store.getStore_phone());
        addr.setText(store.getLocation());
    }

    public void changeToDownload(String newValue){



        Stage down= new Stage();
        down.initOwner(mainClient.getPrimaryStage());
        down.setTitle("Buying_"+newValue);

        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("../Download/template.fxml"));
            Parent parent = loader.load();
            DownloadClient downClient=new DownloadClient(mainClient);
            downCon=loader.<DownloadController>getController();
            downCon.setDownloadClient(downClient);

            Scene s= new Scene(parent);
            down.setScene(s);
            down.setResizable(false);
            down.show();

        }catch(Exception e){e.printStackTrace();}
    }

    public void showEventDetails(String newValue){

        //send specific signal to server
        Stage event_info= new Stage();
        event_info.initOwner(mainClient.getPrimaryStage());
        event_info.setTitle(store.getStore_name()+" Event: "+newValue);
        try{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("Event_information.fxml"));
            Parent parent = loader.load();

            eventCon= loader.<EventInfoController>getController();
            eventCon.setMainClient(mainClient);


            Scene s= new Scene(parent);
            event_info.setScene(s);
            event_info.setResizable(false);
            event_info.show();

        }catch (Exception e){e.printStackTrace();}
    }

    public void handleSendBtn(ActionEvent event){
        commentListView.setItems(comment_list);
        comment_list.add("["+mainClient.getClient().getData().getID()+"]"+textArea.getText());
        textArea.setText("");
    }
    public void handleBackBtn(ActionEvent event){
        try{
            StackPane root=(StackPane)backBtn.getScene().getRoot();
            root.getChildren().remove(storeFrame);
        }catch(Exception e){e.printStackTrace();}
    }


}

