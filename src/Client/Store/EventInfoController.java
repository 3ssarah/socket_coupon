package Client.Store;

import Client.Client;
import Client.Main_page.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class EventInfoController {
    /**Client 참조*/
    private MainClient mainClient;
    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}

    @FXML
    Button OKBtn;
    @FXML
    TextField eventName,startDate, endDate;
    @FXML
    TextArea contents;
    public void handleEventOk(ActionEvent event){

//        //mainClient.sendData("3");//send sign to server:3 add event information
//        mainClient.sendData(mainClient.getClient().getData().getStoreNAme());//send store name
//
//        String[] arr= new String[4];
//
//        arr[0]=eventName.getText();
//        arr[1]=startDate.getText();
//        arr[2]=endDate.getText();
//        arr[3]=contents.getText();
//
//        for(int i=0; i<4; i++){
//            mainClient.sendData(arr[i]);
//        }
        Stage stage= (Stage)OKBtn.getScene().getWindow();
        stage.close();
    }

}
