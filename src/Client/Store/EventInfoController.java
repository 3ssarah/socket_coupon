package Client.Store;

import Client.Client;
import Client.Main_page.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class EventInfoController {
    /**Client 참조*/
    private MainClient mainClient;
    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}

    @FXML
    Button OKBtn;
    public void handleEventOk(ActionEvent event){
        Stage stage= (Stage)OKBtn.getScene().getWindow();
        stage.close();
    }

}
