package Client.Main_page;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import Client.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController  /*implements Initializable*/  {

   private static MainController controller;
    private static DialogController dialogCon;
    private ArrayList<String> clientList=null;

    /**tab: stores*/
    @FXML private ListView<String> listViewBox;
    @FXML private Button searchBtn;
    ObservableList<String> storelist= FXCollections.observableArrayList();
    /**tab: my_page*/

    /**tab: setting*/
    @FXML private Button modifyBtn, addEventBtn, addMenuBtn, seeMenuBtn;


    /**Client 참조*/
    private Client client;
    private MainClient mainClient;

    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}
    public void setClient(Client client){this.client=client;}
    public MainController(){
        System.out.println("MainController COnstructor");
        controller=this;

    }

    @FXML public void initialize(URL location, ResourceBundle resources) {

        System.out.println("initialize--");
        /** First tab: stores_tab Event Handler **/

        recvStoreList();
        recvClientList();

        listViewBox.setItems(storelist);


    }
    /** First tab: stores_tab Event Handler **/

    public void handleSearch(ActionEvent event){
        System.out.println("handleSearch Function");
        recvStoreList();
        //recvClientList();

        listViewBox.setItems(storelist);
    }


    /** Second tab: my_tab Event Handler **/

    /** Third tab: setting_tab Event Handler **/

    /** First tab: stores_tab Event Handler functions **/

    public void modifyStore(ActionEvent  event){


        try{
            sendData("1");//send signal add store
            Stage dialog= new Stage();
            dialog.initOwner(mainClient.getPrimaryStage());
            dialog.setTitle("Add Store");

            FXMLLoader loader= new FXMLLoader(getClass().getResource("StoreDialogFrame.fxml"));
            Parent parent= loader.load();

            dialogCon= loader.<DialogController>getController();
            dialogCon.setClient(client);

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
        sendData(name);
       sendData(category);
        sendData(location);
        sendData(phone);
    }

    /** recv Store list__signal:0*/
    public void recvStoreList() {
        sendData("0");
        String temp = "0";

        ArrayList<String> templist = new ArrayList<String>();
        while ((temp=recvData()).equals("-1")!=true) {

            for(int i=0; i<3; i++){
                temp = recvData();
                if (temp.equals("-1")) break;
                templist.add(i,temp);
            }
            if(temp.equals("-1"))break;


        String name = "[" + templist.get(2) + "][" + templist.get(1) + "] " + templist.get(0);
        System.out.println(name);
        storelist.add(name);
        //templist.clear();
        System.out.println("templist cleared");
        }
        templist.clear();
    }
    /**recv Client list __signal:0*/
    public void recvClientList() {
        sendData("4");
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

        PrintWriter pw= null;
        try{
            pw= new PrintWriter(client.getLoginSock().getOutputStream(),true);
            pw.println(str);
        }catch(Exception e){e.printStackTrace();}
    }
    /** receive Data */
    public String recvData(){
        String result = null;
        BufferedReader br=null;
        try{
            br= new BufferedReader(new InputStreamReader(client.getLoginSock().getInputStream()));
            result=br.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}
