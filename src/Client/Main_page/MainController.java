package Client.Main_page;



import Client.Store.ChatClient;
import Client.Store.EventInfoController;
import Client.Store.Store;
import Client.Store.StoreController;
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
import javafx.scene.layout.StackPane;

import Client.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController  implements Initializable  {


    @FXML private Tab setting_tab;

    /**tab: stores*/
    @FXML private ListView<String> listViewBox;
    @FXML private Button searchBtn;
    ObservableList<String> storelist= FXCollections.observableArrayList();
    @FXML Label name_store;
    /**tab: my_page*/

    /**tab: setting*/
    @FXML private Button addStoreBtn, addEventBtn, addMenuBtn, seeMenuBtn;


    /**참조하기 위한 객체*/
    private Client client;
    private MainClient mainClient;
    private static MainController controller;
    private static DialogController dialogCon;
    private static StoreController storeCon;
    private static ItemDialogController itemCon;
     private Store store1=null;
    private ArrayList<String> clientList=null;

    public void setMainClient(MainClient mainClient){this.mainClient=mainClient;}
    public void setClient(Client client){this.client=client;}
    public Label getName_store(){return this.name_store;}
    public Button getAddStoreBtn(){return this.addStoreBtn;}
    public MainController(){
        System.out.println("MainController COnstructor");
        controller=this;
    }

    @FXML public void initialize(URL location, ResourceBundle resources) {

        System.out.println("initialize--");
        listViewBox.getSelectionModel().selectedItemProperty().addListener((observable ,oldValue, newValue)->showStoreDetails(newValue));
        System.out.println("list view setting");
    }




    /**
     * First tab: stores_tab Event Handler
     *                                      **/
    public void handleSearch(ActionEvent event){

        System.out.println("handleSearch Function");
        recvStoreList();
        listViewBox.setItems(storelist);


        System.out.println("recvStore done1!!");

        //recvClientList();


    }
    public void showStoreDetails(String newValue){
        try{
            sendData("5");
            recvInformation(newValue);

            FXMLLoader loader= new FXMLLoader(getClass().getResource("../Store/StoreFrame.fxml"));
            Parent storepage= loader.load();

            ChatClient chatClient=new ChatClient(mainClient);
            storeCon= loader.<StoreController>getController();
            storeCon.setMainClient(mainClient);
            storeCon.setChatClient(chatClient);
            chatClient.sendData("2");
            chatClient.sendData(store1.getStore_name());
            storeCon.setStore(store1);
            storeCon.settingLabel();
            storeCon.setCommentsView();
            // storeCon.setCommentsView();

            System.out.println("set store and mainClient");

            StackPane root=(StackPane)listViewBox.getScene().getRoot();
            root.getChildren().add(storepage);
//                    Scene s= new Scene(parent);
//                    storepage.setScene(s);
//                    storepage.show();


        }catch(Exception e){e.printStackTrace();}

    }
    /** recv Store list__signal:0*/
    public void recvStoreList() {
        sendData("0");
        String temp = "0";

        storelist.clear();
        while ((temp=recvData()).equals("-1")!=true) {
            storelist.add(temp);
        }

    }
    /**recv specific store information*/
    public void recvInformation(String name){
        System.out.println(" recvInformation function");
        sendData(name);//send selected store name

        String tempRecv= recvData();
        String[] arr= tempRecv.split(",");

        String  t_category, t_phone,t_location, t_owner;
        for(int i=0; i<arr.length;i++){
            System.out.println(arr[i]);
        }


        this.store1 = new Store(name, arr[0], arr[1],arr[2], arr[3]);
        System.out.println("temp store!");


    }
    /** Second tab: my_tab Event Handler **/
    /**
     * Third tab: setting_tab Event Handler
     *                                         **/
    public void handleAddMenuBtn(ActionEvent event){
        try{
            sendData("2");//send signal to server2: add menu item
            Stage menuDialog = new Stage();
            menuDialog.initOwner(mainClient.getPrimaryStage());
            menuDialog.setTitle("Add Menu item");

            FXMLLoader loader= new FXMLLoader(getClass().getResource("ItemDialog.fxml"));
            Parent parent= loader.load();

            itemCon= loader.<ItemDialogController>getController();
            itemCon.setMainClient(mainClient);

            Scene s = new Scene(parent);
            menuDialog.setScene(s);
            menuDialog.setResizable(false);
            menuDialog.show();


        }catch(Exception e){
            e.printStackTrace();
        }



    }
    public void handleAddEventBtn(ActionEvent event){
        try{
            sendData("3");
            Stage eventDialog = new Stage();
            eventDialog.initOwner(mainClient.getPrimaryStage());
            eventDialog.setTitle("Add Event");

            FXMLLoader loader= new FXMLLoader(getClass().getResource("EventDialog.fxml"));
            Parent parent= loader.load();

            itemCon= loader.<ItemDialogController>getController();
            itemCon.setMainClient(mainClient);

            Scene s = new Scene(parent);
            eventDialog.setScene(s);
            eventDialog.setResizable(false);
            eventDialog.show();


        }catch(Exception e){
            e.printStackTrace();
        }


    }
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
            dialogCon.setMainController(this);

            Scene s = new Scene(parent);
            dialog.setScene(s);
            dialog.setResizable(false);
            dialog.show();


        }catch(Exception e){
            e.printStackTrace();
        }



    }
    public void checkOwner(){

        if( mainClient.getClient().getData().isShop()==true){
            setting_tab.setDisable(false);
            String flag=null;
            if((flag=mainClient.getClient().getData().getStoreNAme()).equals("null")!=true){
                name_store.setText(flag);
                addStoreBtn.setDisable(true);
            }

        }
        else setting_tab.setDisable(true);
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



