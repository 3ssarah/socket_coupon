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
    final private String fileName_store=getClass().getResource("").getPath()+"store_info.txt";

//    public Hashtable<String, String> getStoreInfo(){
//        return this.storeInfo;
//    }
    public String getFileName_store(){return fileName_store;}
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
    public void InsertStore(String name, String location){
        storeInfo.put(name, location);    }

    public  void saveFile(){
        try{
            FileWriter fw= new FileWriter(fileName_store);
            Iterator it= storeInfo.keySet().iterator();
            while(it.hasNext()){
                String name=(String)it.next();
                fw.write(name+"\n");
                fw.write((String)storeInfo.get(name)+"\n");
            }
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
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

    /** For Searching specific Store Variables*/
    String s_name= new String();
    String s_location= new String();
    String s_category= new String();
    String s_owner= new String();
    String s_phone= new String();


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
            pw.flush();
            /**recv Store information */
            String storeName=br.readLine();
            String storeCategory= br.readLine();
            String storeLocation= br.readLine();
            String storePhone= br.readLine();

            stores.InsertStore(storeName, storeLocation);
            pw.println("Store Register Complete");

            /**save into a file*/
            String filename= getClass().getResource("").getPath()+storeName+".txt";
            BufferedWriter fw= new BufferedWriter(new FileWriter(filename));
            fw.write(name+"\n");
            fw.write(storeCategory+"\n");
            fw.write(storeLocation+"\n");
            fw.write(storePhone+"\n");
            fw.close();

            /**make Store Object*/
            myStore= new Store(storeName,storeCategory,storePhone,storeLocation,name);
            System.out.println("add store in to sotre list");
            this.storeList.add(myStore);
            System.out.println("add store in to sotre list1");
            //pw.println("Store Register Complete");

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
            System.out.println(temp.getStore_name());
            sendData(temp.getStore_name());
//            sendData("["+temp.getLocation()+"]");
//            sendData("["+temp.getCategory()+"]");
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

        BufferedReader tempbr= null;

        try{
            tempbr= new BufferedReader(new FileReader(getClass().getResource("").getPath()+this.s_name+".txt"));
            this.s_owner=tempbr.readLine();
            this.s_category=tempbr.readLine();
            this.s_location=tempbr.readLine();
            this.s_phone=tempbr.readLine();

            pw.println(this.s_name);
            pw.println(this.s_category);
            pw.println(this.s_phone);
            pw.println(this.s_location);
            pw.println(this.s_owner);

        }catch(Exception e){e.printStackTrace();}
    }


    public void run(){

        name= recvData();
        this.clientSock_list.put(name,sock);
        System.out.println(name);


//        sendStoreList();
//        sendClientSocketList();


        while(true){
            int situation = Integer.parseInt(recvData());
            System.out.println(situation+"recved");
            switch (situation){
                case -2: //terminate store
                    deleteStore();
                    exitStore();
                    break;
                case -1: //exit client
                    this.clientSock_list.remove(this.name);
                    break;
                case 0: //refresh
                    System.out.println("sendStoreList and function");
                    sendStoreList();
                    //sendClientSocketList();
                    break;
                case 1:
                    addStore();
                    stores.saveFile();
                    break;
                case 2:
                    exitStore();
                    break;
                case 3:
                  enterStore();
                  break;
                case 4:
                    System.out.println("only sendCliemtSocketList");
                    sendClientSocketList();
                    break;
                case 5: //search specific store
                    System.out.println("send searched store data");
                    this.s_name=recvData();
                    sendSearchedStoreList();

//                    pw.println(this.s_name);
//                    pw.println(this.s_category);
//                    pw.println(this.s_phone);
//                    pw.println(this.s_location);
//                    pw.println(this.s_owner);
                    this.s_phone=null;
                    this.s_location=null;
                    this.s_phone=null;
                    this.s_category=null;
                    this.s_owner=null;
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

            /**Load store information to MainServer*/
            System.out.println("loadStorestoServer function!");
            String fileName= MainServer.class.getResource("").getPath();
            try {
                BufferedReader fr = new BufferedReader(new FileReader(stores.getFileName_store()));
                String store_name,owner,store_location, store_phone,store_category;

                while((store_name=fr.readLine())!=null){

                    fr.readLine();//location of store

                    BufferedReader tempBr= new BufferedReader(new FileReader(fileName+store_name+".txt"));
                    owner=tempBr.readLine();
                    store_category=tempBr.readLine();
                    store_location=tempBr.readLine();
                    store_phone=tempBr.readLine();

                    System.out.println(store_name+" "+store_category+" "+store_location+" "+store_phone+" "+owner);
                    Store tempStore= new Store(store_name,store_category,store_phone,store_location,owner);
                    storeList.add(tempStore);
                    System.out.println("store list added");

                    tempBr.close();
                }
                fr.close();

            }catch(Exception e){
                e.printStackTrace();
            }
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