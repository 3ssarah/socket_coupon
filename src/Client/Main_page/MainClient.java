package Client.Main_page;

import Client.Login.LoginClient;
import javafx.application.Application;
import javafx.stage.Stage;
import Client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            this.client.setLoginSock(new Socket(client.getClient().ServIP, client.getClient().MainPort));
        }catch(Exception e){e.printStackTrace();}
        //send user id
            sendData(this.client.getData().getID());
            System.out.println("Data sent");
           initMainStage();
        System.out.println("initMAinstage");



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
    /**recv data*/
    public String recvData(){
        String result = null;
        BufferedReader br=null;
        try{
            br= new BufferedReader(new InputStreamReader(this.client.getLoginSock().getInputStream()));
            result=br.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
    public void initMainStage(){
        mainStage= new MainStage(this.client,this);
    }


}
