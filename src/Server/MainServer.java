package Server;

import Client.Client;
import Client.Store.Store;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

class MainThread extends Thread{

    ArrayList<Store>storeList=null;
    Hashtable<String, Socket>clientSock_list=null;
    ArrayList<Client> clientInfoList = null;
    BufferedReader br=null;
    PrintWriter pw= null;
    Socket sock =null;
    String name=null;
    Store myStore= null;

    /**Search Client Variables*/
    String s_ID= new String();
    String s_phone= new String();
    String s_shop=new String();

    public MainThread(Socket sock,ArrayList<Store> storeList,Hashtable<String,Socket>clientSock_list,ArrayList<Client> clientInfoList){
        this.sock=sock;
        this.clientSock_list=clientSock_list;
        this.storeList=storeList;
        this.clientInfoList = clientInfoList;

        try{
            br = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            pw = new PrintWriter(this.sock.getOutputStream(),true);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
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
    /** Add Store */
    public void addStore(){
        try{
            /**recv Store information */
            String storeName=br.readLine();
            String storeCategory= br.readLine();
            String storeLocation= br.readLine();
            String storePhone= br.readLine();
            /**make Store Object*/
            myStore= new Store(storeName,storeCategory,storePhone,storeLocation);
            this.storeList.add(myStore);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**Enter Store*/
    public void enterStore(){
        Iterator it = this.storeList.iterator();
        try{
            String storeName= br.readLine(); //recv Store name from Main_page
            while(it.hasNext()){
                Store temp= (Store)it.next();
                if(temp.getStore_name().equals(storeName)){
                    this.myStore=temp;
                    this.myStore.addCustomer(sock);
                    return;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteStore(){
        this.storeList.remove(myStore);
    }
    /** exit store*/
    public void exitStore(){
        myStore.deletCustomer(sock);

    }
    /**Send store list */
    public void sendStoreList(){
        Iterator it= this.storeList.iterator();
        while(it.hasNext()){
            Store temp= (Store)it.next();
            sendData(temp.getStore_name());
            sendData(temp.getLocation());
            sendData(temp.getCategory());
        }
    }
    /** Send client List*/
    public void sendClientSocketList(){
        Iterator it= this.clientSock_list.keySet().iterator();

        while(it.hasNext()){
            String ID=(String)it.next();
            sendData(ID);
            System.out.println(ID);
        }
        sendData("-1");
    }
    /**Load Client info*/

    public void loadClient(){
        BufferedReader br=null;
        try{
            br= new BufferedReader(new FileReader(this.s_ID+".txt"));
            this.s_phone=br.readLine();
            this.s_shop=br.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        name= recvData();
        this.clientSock_list.put(name,sock);
        System.out.println(name);

        sendStoreList();
        sendClientSocketList();

        while(true){
            int situation = Integer.parseInt(recvData());

            switch (situation){
                case -2: //terminate store
                    deleteStore();
                    exitStore();
                    break;
                case -1: //exit client
                    this.clientSock_list.remove(this.name);
                    break;
                case 0: //refresh
                    sendStoreList();
                    sendClientSocketList();
                    break;
                case 1:
                    addStore();
                    break;
                case 2:
                    exitStore();
                    break;
                case 3:
                  enterStore();
                  break;
                case 4://search other client
                    this.s_ID=recvData();
                    loadClient();

                    pw.println(s_phone);
                    pw.println(s_shop);
                    this.s_ID=null;
                    this.s_shop=null;
                    this.s_shop=null;
                    break;
            }
        }
    }


}
public class MainServer{

    public static void main(String[] args){
        ArrayList<Store> storeList= new ArrayList<Store>();
        Hashtable<String, Socket> clientSocket_list= new Hashtable<String, Socket>();
        ArrayList<Client> clientInfoList = new ArrayList<Client>();
        ServerSocket server=null;
        Socket socket=null;

        try{
            server= new ServerSocket(23456);
            while(true){
                System.out.println("..Main Server Waiting...");
                socket=server.accept();

                new MainThread(socket,storeList,clientSocket_list,clientInfoList).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}