package Client.Main_page;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ItemDialogController {
    @FXML
    Button OKBtn , CBtn;
    @FXML
    TextField item_f, price_f;
    Alert alert;
    /**For EventDialog*/
    @FXML Button OKBtn_e, CBtn_e;
    @FXML TextField eventName,startDate,endDate;
    @FXML
    TextArea contents;

    private MainClient mainClient;
    public void setMainClient(MainClient client){this.mainClient=client;}

    /**MenuDialog handler*/
    public void handleOk(ActionEvent event){
        mainClient.sendData(item_f.getText());
        mainClient.sendData(price_f.getText());
        try{
//            alert= new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Look!");
//            alert.setHeaderText(mainClient.recvData());
//            alert.setContentText(":)");
//            alert.showAndWait();
            popAlert();


        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage= (Stage)OKBtn.getScene().getWindow();
        stage.close();
    }
    public void handleCBtn(ActionEvent event){
        Stage stage=(Stage)CBtn.getScene().getWindow();
        stage.close();

    }

    /**EventDialog handler*/
    public void handleEventOk(ActionEvent event){

        mainClient.sendData(eventName.getText());
        mainClient.sendData(startDate.getText());
        mainClient.sendData(endDate.getText());
        mainClient.sendData(contents.getText());

        try{
            popAlert();

        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage= (Stage)OKBtn_e.getScene().getWindow();
        stage.close();
    }


    public void handleEventCBtn(ActionEvent event){
        Stage stage= (Stage)CBtn_e.getScene().getWindow();
        stage.close();
    }
    public void popAlert() throws Exception{
        alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Look!");
        alert.setHeaderText(mainClient.recvData());
        alert.setContentText(":)");
        alert.showAndWait();
    }
}
