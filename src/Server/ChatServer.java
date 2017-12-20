package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

class ChatThread extends Thread{

    Socket socket=null;
    ArrayList<String> comments= new ArrayList<String>();
    BufferedReader br=null;
    PrintWriter pw= null;
    private final String path=getClass().getResource("").getPath();


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
        System.out.println("save file function");

        String storename= recvData();
        String comment= recvData();

        /**save into file*/
        try{
            String filename= path+storename+"_comments.txt";

            BufferedWriter fw= new BufferedWriter(new FileWriter(filename,true));
            fw.write(comment+"\n");
            System.out.println(comment);

            fw.close();
        }catch(Exception e){e.printStackTrace();}

    }
    public void loadComments(){/**signal: 2*/
        String storename= recvData();
        try {
            BufferedReader comment_load = new BufferedReader(new FileReader(path + storename + "_comments.txt"));
            String temp;
            while ((temp = comment_load.readLine()) != null) {
                this.comments.add(temp);
            }
            comment_load.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendComments(){/**signal: 1*/

           String temp=null;
            Iterator it= comments.iterator();
            while(it.hasNext()){
               temp=it.next().toString();
               System.out.println(temp);
               sendData(temp);
            }
            sendData("-1");

            System.out.println("send comments done!");



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

        System.out.println(recvData());
//        loadComments();

        while(true){
            int mode= Integer.parseInt(recvData());
            System.out.println(mode+" recved");
            switch (mode){
                case 0: //recve data and save into file
                    saveFile();
                    break;
                case 1:// send comments to client
                    sendComments();
                    break;
                case 2://load comments
                    loadComments();
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
