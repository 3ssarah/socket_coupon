package Client.Download;

import Client.Client;
import Client.Login.LoginClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class DownloadClient {

    private Client client;

    public DownloadClient(LoginClient loginClient){

        this.client=loginClient.getClient();

        try{
          this.client.setImgSock(new Socket(client.ServIP, client.ImgPort));
          sendData("IMG socket setting done!");

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
}
