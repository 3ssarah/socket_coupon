package Client.Store;

import Client.Client;
import Client.Login.LoginClient;
import Client.Main_page.MainClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    private Client client;
    public ChatClient(LoginClient loginClient){
        this.client= loginClient.getClient();

        try{
            this.client.setMsgSock(new Socket(client.ServIP, client.commentPort));
            sendData("Msg socket setting done!");

        }catch(Exception e){e.printStackTrace();}
    }

    /* send Data */
    public void sendData(String str){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(this.client.getMsgSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
