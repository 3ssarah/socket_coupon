package Server;

import java.net.ServerSocket;
import java.net.Socket;

class ImgThread extends Thread{

    public void run(){

    }

}
public class ImgServer {

    public static void main(String[] args){
        ServerSocket imgServer=null;
        Socket sock= null;

        try{
            imgServer=new ServerSocket(45454);
            while(true){
                System.out.println("..Img Server waiting..");
                sock= imgServer.accept();


                new ImgThread().start();
            }
        }catch(Exception e){e.printStackTrace();}

    }
}
