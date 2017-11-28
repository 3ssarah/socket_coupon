package Client.Main_page;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController {
    private static MainController controller;
    private static DialogController dialogCon;
    /**tab: stores*/
    @FXML private ListView<String> listView;

    /**tab: my_page*/

    /**tab: setting*/
    @FXML private Button modifyBtn, addEventBtn, addMenuBtn, seeMenuBtn;


    /**Main application (MainClient)참조*/
    private MainClient mainClient;

    /****************************/
    private BufferedReader br=null;
    private PrintWriter pw= null;
    ObservableList<String> storelist= FXCollections.observableArrayList();
    private ArrayList<String> clientList=null;

    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}
    public MainController(){
        controller=this;
    }
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        /** First tab: stores_tab Event Handler **/
        listView.setItems(storelist);
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


                    }
                }
        );

    }


    /** Second tab: my_tab Event Handler **/

    /** Third tab: setting_tab Event Handler **/

    /** First tab: stores_tab Event Handler functions **/

    public void modifyStore(ActionEvent  event){


        try{
            mainClient.sendData("1");//send signal add store
            Stage dialog= new Stage();
            dialog.initOwner(mainClient.getPrimaryStage());
            dialog.setTitle("Add Store");

            FXMLLoader loader= new FXMLLoader(getClass().getResource("StoreDialogFrame.fxml"));
            Parent parent= loader.load();

            dialogCon= loader.<DialogController>getController();
            dialogCon.setMainClient(mainClient);

            Scene s = new Scene(parent);
            dialog.setScene(s);
            dialog.setResizable(false);
            dialog.show();


        }catch(Exception e){
            e.printStackTrace();
        }



    }

    /** add store*/
    public void addStore(String name, String category, String location, String phone){
        //send store basic information
        pw.println(name);
        pw.println(category);
        pw.println(location);
        pw.println(phone);
    }

    /** recv Store list*/
    public void recvStoreList(){
        String temp="0";
        ArrayList<String> templist= new ArrayList<String>();
        while(true){
            temp=recvData();
            if(temp.equals("-1"))break;
            templist.add(temp);
        }
        String name= "["+templist.get(3)+"]["+templist.get(2)+"] "+templist.get(0);
        System.out.println(name);
        storelist.add(name);
        templist.clear();
    }
    /**recv Client list*/
    public void recvClientList() {
        this.clientList = new ArrayList<String>();
        String temp = "0";

        while (true) {
            temp = recvData();
            if (temp.equals("-1")) break;
            this.clientList.add(temp);
        }
    }
        public void F5(){
            sendData("0");
            recvStoreList();
            recvClientList();

            //기존의 화면 지우고 다시 불러와야 함
    }





    /** send Data */
    public void sendData(String str){
        this.pw.println(str);
    }
    /** receive Data */
    public String recvData(){
        String result = null;
        try{
            result = br.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}
