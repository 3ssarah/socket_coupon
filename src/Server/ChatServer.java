package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

class ChatThread extends Thread{

    Socket socket=null;
//    Hashtable<Socket,String>store=null;
    ArrayList<String> comments= null;
    BufferedReader br=null;
    PrintWriter pw= null;
    private final String path=getClass().getResource("").getPath();
    String name;

    public ChatThread(Socket socket){
        this.socket=socket;


        try{
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            pw = new PrintWriter(this.socket.getOutputStream(),true);
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    public void saveFile(){/**signal: 0*/

        String storename= recvData();
        String customer_id=recvData();
        String commnet= recvData();

        /**save into file*/
        try{
            String filename= path+storename+"_comments.txt";
            BufferedWriter fw= new BufferedWriter(new FileWriter(filename,true));
            fw.write(customer_id+","+commnet+"\n");
            System.out.println(customer_id+" "+commnet);

            fw.close();
        }catch(Exception e){e.printStackTrace();}

    }
    public void sendComments(){/**signal: 1*/
        String storename= recvData();
        try{
            BufferedReader comment_load= new BufferedReader(new FileReader(path+storename+"-comments.tx"));
            String temp;
            while((temp=comment_load.readLine())!=null){
                sendData(temp);
            }
            sendData("-1");
            comment_load.close();
        }catch(Exception e){e.printStackTrace();}




    }
    /** Send data */
    public void sendData(String str){
        pw.println(str);
    }
    /** receive data*/
    public String recvData(){
        String result = null;

        try{
            result=br.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public void run(){
        name=recvData();// get user id
        System.out.println(name+"chat server");

        while(true){
            int mode= Integer.parseInt(recvData());
            System.out.println(mode+" recved");
            switch (mode){
                case 0: //recve data and save into file
                    saveFile();
                    break;
                case 1:// send comments to client
                    sendComments();
            }
        }

    }
}
public class ChatServer {
    public static void main(String[] args){
        ServerSocket ChatServer=null;
        Socket sock = null;


        try{
            ChatServer= new ServerSocket(56565);
            while(true){
                System.out.println("..Chat Server waiting..");
                sock=ChatServer.accept();


                new ChatThread(sock).start();
            }
        }catch(Exception e){
         e.printStackTrace();
        }
    }
}
