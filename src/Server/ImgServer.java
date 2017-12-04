package Server;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;

class ImgThread extends Thread{

    Socket socket=null;
    BufferedReader br=null;
    PrintWriter pw=null;
    int number;
    final String filePath=getClass().getResource("").getPath();

    public ImgThread(Socket socket, int number){
        this.socket=socket;
        this.number=number;
        try{
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            pw = new PrintWriter(this.socket.getOutputStream(),true);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
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
    public void makeImg(){
        try{
            String result=recvData();
            Barcode barcode= BarcodeFactory.createCode128B(result);

            String file=recvData();//템플릿이미지 받아오기
            URL url= new URL(file);
            Image background=ImageIO.read(url);
            //BufferedImage background= ImageIO.read(new File(getClass().getResource("").getPath()+"cat.jpg"));


            barcode.setDrawingText(false);
            barcode.setBarHeight(50);
            barcode.setBarWidth(70);
            BufferedImage barcode_m=BarcodeImageHandler.getImage(barcode);

            BufferedImage mergedImage= new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics=(Graphics2D)mergedImage.getGraphics();

            graphics.setBackground(Color.WHITE);
            graphics.drawImage(background,0,0,null);
            graphics.drawImage(barcode_m,0,500,null);

            Calendar cal=Calendar.getInstance();
            ImageIO.write(mergedImage,"jpg",new File(filePath+number+"_coupon.jpg"));
            sendData(getClass().getResource("").getPath()+number+"_coupon.jpg");
            this.number++;
            System.out.println("image created on server");


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void run(){



        while(true){
            int mode=Integer.parseInt(recvData());

            System.out.println(mode+" recved");
            if(mode==1){
                makeImg();
            }
            else {

            }
        }
    }

}
public class ImgServer {

    public static void main(String[] args){
        ServerSocket imgServer=null;
        Socket sock= null;
        int number=0;

        try{
            imgServer=new ServerSocket(45454);
            while(true){
                System.out.println("..Img Server waiting..");
                sock= imgServer.accept();


                new ImgThread(sock,number).start();
            }
        }catch(Exception e){e.printStackTrace();}

    }
}
