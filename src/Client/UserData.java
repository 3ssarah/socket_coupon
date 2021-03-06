package Client;

import Client.Store.Store;

public class UserData {


    private String ID=null;
    private String pwd=null;
    private String phone=null;
    private int balance = 0;
    private boolean shop;
    private Store store=null;
    private String storeNAme=null;

    public UserData(String ID, String pwd, String phone, boolean shop){
        this.ID=ID;
        this.pwd=pwd;
        this.phone=phone;
        this.shop=shop;

    }

    public UserData(String ID, String pwd){
        this.ID=ID;
        this.pwd=pwd;
    }

    public String getStoreNAme() {
        return storeNAme;
    }

    public void setStoreNAme(String storeNAme) {
        this.storeNAme = storeNAme;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isShop() {
        return shop;
    }

    public void setShop(boolean shop) {
        this.shop = shop;
    }
}
