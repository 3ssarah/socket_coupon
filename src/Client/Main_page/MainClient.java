package Client.Main_page;

import Client.Login.LoginClient;
import javafx.application.Application;
import javafx.stage.Stage;
import Client.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainClient  {

    private Client client;
    private MainStage mainStage;
    private Stage primaryStage;

    public MainClient(LoginClient client){

        this.primaryStage= client.getPrimaryStage();
        this.client=client.getClient();

        try{
            client.getClient().setLoginSock(new Socket(client.getClient().ServIP, client.getClient().MainPort));
        }catch(Exception e){e.printStackTrace();}
        //send user id
            sendData(this.client.getData().getID());
            System.out.println("Data sent");
            initMainStage();


    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MainStage getMainStage() {
        return mainStage;
    }

    public void setMainStage(MainStage mainStage) {
        this.mainStage = mainStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /* send Data */
    public void sendData(String str){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(this.client.getLoginSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    public void initMainStage(){
        mainStage= new MainStage(this);
    }


}
