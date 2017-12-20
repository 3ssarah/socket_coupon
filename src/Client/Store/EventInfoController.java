package Client.Store;

import Client.Client;
import Client.Main_page.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class EventInfoController {
    /**Client 참조*/
    private MainClient mainClient;
    private String eventValue, storename;
    public void setStorename(String storename){this.storename=storename;}
    public void setEventName(String eventVale){this.eventValue=eventVale;}
    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}

    @FXML
    Button OKBtn;
    @FXML
    Label eventName,startDate, endDate;
    @FXML
    TextArea contents;
    public void handleEventOk(ActionEvent event){

        Stage stage= (Stage)OKBtn.getScene().getWindow();
        stage.close();
    }
    void setContents(){
  System.out.println("set Event detail");
        mainClient.sendData("7");
        mainClient.sendData(storename);
    //send Event name to server
        mainClient.sendData(eventValue);
    //setting the information
        eventName.setText(mainClient.recvData());
        startDate.setText(mainClient.recvData());
        endDate.setText(mainClient.recvData());
        contents.setText(mainClient.recvData());
  }

}
