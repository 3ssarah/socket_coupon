package Server;

import Client.Client;
import Client.Store.EventInfo;
import Client.Store.Menu;
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
    private ArrayList<Menu> imtemList= new ArrayList<Menu>();

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
    public void saveItem(){

    }

}
class MenuInfo{
    private Hashtable<String, String>menu_info=null; // menu item information<number, menu name>

    private String fileName_m;
    public MenuInfo(String storeName){
        fileName_m=getClass().getResource("").getPath()+storeName+"_menu.txt";

    }
    public void hashMake_menu()throws IOException{
        BufferedReader br= new BufferedReader(new FileReader(fileName_m));
        String menu_name; String price;

        while((menu_name=br.readLine())!=null){
            price=(br.readLine()).toString();
            System.out.println("종류: "+br.readLine());
            menu_info.put(menu_name,price );
        }
    }
    public void Insert_item(String name,String price){
        menu_info.put(name, price);
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
    String s_name;
    String s_location;
    String s_category;
    String s_owner;
    String s_phone;


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
            String filename= getClass().getResource("").getPath();
            BufferedWriter fw= new BufferedWriter(new FileWriter(filename+storeName+".txt"));
            fw.write(name+"\n");
            fw.write(storeCategory+"\n");
            fw.write(storeLocation+"\n");
            fw.write(storePhone+"\n");
            fw.close();
            /**save into a member's file*/
            BufferedWriter mfw= new BufferedWriter(new FileWriter(filename+name+".txt",true));
            mfw.write(storeName);
            mfw.close();

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

    /**Add Menu Item*/
    public void addMenuItem(String storeName) {
        // Add menu item into store's item list
        try {
            Iterator it = this.storeList.iterator();
            Store temp=null;
            while(it.hasNext()){
                temp=(Store)it.next();
                if(temp.getStore_name().equals(storeName)) break;
            }
            String menuName= br.readLine();
            System.out.println(menuName);
           // String menuPrice= br.readLine();
           // Menu tempMenu= new Menu(menuName, temp, menuPrice);
           // temp.getItemlist().add(tempMenu);

            /**Save into file*/
            System.out.println("timeto save in file");
            String filename = getClass().getResource("").getPath() + storeName + "_menu.txt";//file name= storeName_menu.txt

            BufferedWriter fw= new BufferedWriter(new FileWriter(filename, true));

            fw.write(menuName+"\n");// save like : item name,1000
            fw.close();

        }catch(Exception e){e.printStackTrace();}
    }
    /**Add Event Item*/
    public void addEventItem(String storeName){
        try {
            System.out.println("add event function");
            Iterator it = this.storeList.iterator();
            Store temp = null;
            while (it.hasNext()) {
                temp = (Store) it.next();
                if (temp.getStore_name().equals(storeName)) break;
            }
            System.out.println("find store"+temp.getStore_name());

            String[] arr= new String[4];
            for(int i=0; i<4;i++)
            arr[i]=br.readLine();
            //EventInfo tempEvent= new EventInfo(arr[0],arr[1],arr[2],arr[3]);
           // temp.getEventList().add(tempEvent);
            System.out.println("addded");

            String filename = getClass().getResource("").getPath() + temp.getStore_name() + "_event.txt";//file name= storeName_menu.txt
            System.out.println(arr[0]+"&@"+arr[1]+"&@"+arr[2]+"&@"+arr[3]);
            BufferedWriter fw= new BufferedWriter(new FileWriter(filename, true));
            fw.write(arr[0]+"&@"+arr[1]+"&@"+arr[2]+"&@"+arr[3]+"\n"); //save like name|stat date|end date|contents
            fw.close();
        }catch(Exception e){e.printStackTrace();}
    }

    /** send Menu item to client*/
    public void loadItemlist(String storename){

        try{
            Iterator it = this.storeList.iterator();
            Store tempStore = null;
            while (it.hasNext()) {
                tempStore = (Store) it.next();
                if (tempStore.getStore_name().equals(storename)) break;
            }
            tempStore.getItemlist().clear();

            String file= getClass().getResource("").getPath()+storename+"_menu.txt";
            BufferedReader item_load= new BufferedReader(new FileReader(file));
            String temp;
            while((temp=item_load.readLine())!=null){
                tempStore.getItemlist().add(temp);
            }
        }catch(Exception e){e.printStackTrace();}
    }
    public void sendItemlist(String storename){
        String temp=null;
        Iterator it = this.storeList.iterator();
        Store tempStore = null;
        while (it.hasNext()) {
            tempStore = (Store) it.next();
            if (tempStore.getStore_name().equals(storename)) break;
        }
        Iterator iit= tempStore.getItemlist().iterator();
        while(iit.hasNext()){
            temp=iit.next().toString();
            System.out.println(temp);
            sendData(temp);
        }
        sendData("-1");
    }
    public void loadEventlist(String storename){
        try{
            Iterator it = this.storeList.iterator();
            Store tempStore = null;
            while (it.hasNext()) {
                tempStore = (Store) it.next();
                if (tempStore.getStore_name().equals(storename)) break;
            }
            tempStore.getEventList().clear();

            String file= getClass().getResource("").getPath()+storename+"_event.txt";
            BufferedReader item_load= new BufferedReader(new FileReader(file));
            String temp;
            while((temp=item_load.readLine())!=null){
                tempStore.getEventList().add(temp);
            }
        }catch(Exception e){e.printStackTrace();}
    }
    public void sendEventlist(String storename){
        String temp=null;
        Iterator it = this.storeList.iterator();
        Store tempStore = null;
        while (it.hasNext()) {
            tempStore = (Store) it.next();
            if (tempStore.getStore_name().equals(storename)) break;
        }
        Iterator iit= tempStore.getEventList().iterator();
        while(iit.hasNext()){
            temp=iit.next().toString();
            String[]arr= temp.split("&@");
            System.out.println(arr[0]);
            sendData(arr[0]);
        }
        sendData("-1");
    }


    /**Send store list */
    public void sendStoreList(){
        Iterator it= this.storeList.iterator();
        while(it.hasNext()){
            Store temp= (Store)it.next();
            System.out.println(temp.getStore_name());
            sendData(temp.getStore_name());
        }
        sendData("-1");
    }



    /** Search Store by location*/
    public void sendSearchedStoreList(){


        BufferedReader tempbr= null;

        try{
            tempbr= new BufferedReader(new FileReader(getClass().getResource("").getPath()+this.s_name+".txt"));
            this.s_owner=tempbr.readLine();
            this.s_category=tempbr.readLine();
            this.s_location=tempbr.readLine();
            this.s_phone=tempbr.readLine();

            String tempSend=s_category+","+s_phone+","+s_location+","+s_owner;
            System.out.println(tempSend);
            sendData(tempSend);

            System.out.println("send all data");

        }catch(Exception e){e.printStackTrace();}
    }


    public void run(){

        name= recvData();
        this.clientSock_list.put(name,sock);
        System.out.println(name);



        while(true){
            int situation = Integer.parseInt(recvData());
            System.out.println(situation+"recved");

            switch (situation){

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
                    this.s_name=recvData();
                    addMenuItem(s_name);
                    this.s_name=null;
                    break;
                case 3:
                    System.out.println("it's time to add Event");
                    this.s_name=recvData();
                    System.out.println(s_name);
                    addEventItem(s_name);
                    this.s_name=null;
                    break;
                case 4://load and send event
                    this.s_name=recvData();
                    loadEventlist(this.s_name);
                    sendEventlist(this.s_name);
                    this.s_name=null;
                    break;
                case 5: //search specific store
                    System.out.println("send searched store data");
                    this.s_name=recvData();
                    sendSearchedStoreList();
                    System.out.println("end of sendSearchedStoreLsit()");
                    this.s_name=null;
                    this.s_location=null;
                    this.s_phone=null;
                    this.s_category=null;
                    this.s_owner=null;
                    break;
                case 6: //load and send item
                    this.s_name=recvData();
                    loadItemlist(this.s_name);
                    sendItemlist(this.s_name);
                    this.s_name=null;
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
            String fileName=MainServer.class.getResource("").getPath();

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