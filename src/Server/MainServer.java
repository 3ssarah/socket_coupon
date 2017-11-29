package Server;

import Client.Client;
import Client.Store.Store;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

class StoreInfo{
    private Hashtable<String, String> storeInfo =null; //store information <name, location>
    final private String fileName_store=getClass().getResource("").getPath()+"sotre_info.txt";

    public StoreInfo(){
        storeInfo= new Hashtable<String, String>();

        try{
            hashMake();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void hashMake()throws IOException{
        BufferedReader br= new BufferedReader(new FileReader(fileName_store));
        String store_name;
        String store_location;

        while((store_name=br.readLine())!=null){
            store_location=br.readLine();
            storeInfo.put(store_name, store_location);
        }
    }
}

class MainThread extends Thread{

    ArrayList<Store>storeList=null;
    Hashtable<String, Socket>clientSock_list=null;
    ArrayList<Client> clientInfoList = null;
    BufferedReader br=null;
    PrintWriter pw= null;
    Socket sock =null;
    String name=null;
    Store myStore= null;
    private StoreInfo stores;


    public MainThread(Socket sock,StoreInfo stores,ArrayList<Store> storeList ,Hashtable<String,Socket>clientSock_list,ArrayList<Client> clientInfoList){
        this.sock=sock;
        this.stores=stores;
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
            /**save into a file*/
            String filename= getClass().getResource("").getPath()+name+storeName+".txt";
            BufferedWriter fw= new BufferedWriter(new FileWriter(filename));
            fw.write(storeCategory);
            fw.write(storeLocation);
            fw.write(storePhone);
            fw.close();

            /**make Store Object*/
            myStore= new Store(storeName,storeCategory,storePhone,storeLocation);
            this.storeList.add(myStore);
            pw.println("Store Register Complete");
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
        sendData("-1");
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
    /** Search Store by location*/
    public void sendSearchedStoreList(){
        String s_location=recvData();

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

            }
        }
    }


}
public class MainServer{

    public static void main(String[] args){
        ArrayList<Store> storeList= new ArrayList<Store>();
        Hashtable<String, Socket> clientSocket_list= new Hashtable<String, Socket>();
        ArrayList<Client> clientInfoList = new ArrayList<Client>();
        StoreInfo stores= new StoreInfo();
        ServerSocket server=null;
        Socket socket=null;

        try{
            server= new ServerSocket(23456);
            while(true){
                System.out.println("..Main Server Waiting...");
                socket=server.accept();

                new MainThread(socket,stores,storeList,clientSocket_list,clientInfoList).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}