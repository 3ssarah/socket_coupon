package Client.Store;

import javafx.beans.property.StringProperty;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Store {
    private String store_name=null;
    private String category=null;
    private String location=null;
    private String store_phone=null;
    private String owner;
    private ArrayList<Socket> customer= null;


    private ArrayList<Menu> itemlist= new ArrayList<Menu>();
    private ArrayList<EventInfo> eventList= new ArrayList<EventInfo>();


    public Store(String store_name, String category, String store_phone, String location,String owner){
        this.store_name=store_name;
        this.category=category;
        this.store_phone=store_phone;
        this.location=location;
        this.owner=owner;
    }

    public ArrayList<EventInfo> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<EventInfo> eventList) {
        this.eventList = eventList;
    }

    public ArrayList<Menu> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<Menu> itemlist) {
        this.itemlist = itemlist;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String loaction) {
        this.location = loaction;
    }

    public void addCustomer(Socket sock){
        this.customer.add(sock);
    }
    public void deletCustomer(Socket sock){
        this.customer.remove(sock);
    }

    public Menu getItem(String itemName){
        Iterator it= itemlist.iterator();
        Menu temp=null;
        while(it.hasNext()){
             temp= (Menu)it.next();
             if(temp.getProduct_name().equals(itemName)) return temp;
        }
        return temp;
    }
    public EventInfo getEventItem(String eventName){
        Iterator it= eventList.iterator();
        EventInfo temp=null;
        while(it.hasNext()){
            temp=(EventInfo)it.next();
            if(temp.getEvnetName().equals(eventName))return temp;
        }
        return temp;
    }
}
