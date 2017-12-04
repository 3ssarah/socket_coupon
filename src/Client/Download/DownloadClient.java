package Client.Download;

import Client.Client;
import Client.Login.LoginClient;
import Client.Main_page.MainClient;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class DownloadClient {

    private Client client;
    private Stage primaryStage;

    public DownloadClient(MainClient mainClient){

        this.client=mainClient.getClient();
        this.primaryStage=mainClient.getPrimaryStage();
        try{
          this.client.setImgSock(new Socket(client.ServIP, client.ImgPort));


        }catch(Exception e){e.printStackTrace();}

    }
    /* send Data */
    public void sendData(String str){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(this.client.getImgSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /** receive Data */
    public String recvData(){
        String result = null;
        BufferedReader br=null;
        try{
            br= new BufferedReader(new InputStreamReader(client.getImgSock().getInputStream()));
            result=br.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }

        return result;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Client getClient() {
        return client;
    }
}
