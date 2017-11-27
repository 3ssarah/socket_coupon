package Client.Main_page;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    private static MainController controller;

    /**Main application (MainClient)참조*/
    private MainClient mainClient;
    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}
    public MainController(){
        controller=this;
    }
    @FXML
    public void initialize(URL location, ResourceBundle resources) {


    }
    /** First tab: stores_tab Event Handler **/

    /** Second tab: my_tab Event Handler **/

    /** Third tab: setting_tab Event Handler **/

}
