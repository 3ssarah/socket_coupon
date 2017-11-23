package Client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public final int LoginPort=12345;
    public final int StorePort=34343;
    public final int ImgPort=45454;
    public final int commentPort=56565;
    public final String ServIP="127.0.0.1";
    public boolean loginComplete;

    private Socket loginSock=null;
    private Socket StoresSock=null;
    private Socket ImgSock=null;
    private Socket msgSock=null;

    private String ID=null;
    private String pwd=null;
    private String phone=null;
    private int balance;
    private boolean shop;

    public Client(String ID, String pwd, Socket loginSock){
        this.ID=ID;
        this.pwd=pwd;
        this.loginSock=loginSock;
    }

    public Socket getLoginSock() {
        return loginSock;
    }

    public void setLoginSock(Socket loginSock) {
        this.loginSock = loginSock;
    }

    public Socket getStoresSock() {
        return StoresSock;
    }

    public void setStoresSock(Socket storesSock) {
        StoresSock = storesSock;
    }

    public Socket getImgSock() {
        return ImgSock;
    }

    public void setImgSock(Socket imgSock) {
        ImgSock = imgSock;
    }

    public Socket getMsgSock() {
        return msgSock;
    }

    public void setMsgSock(Socket msgSock) {
        this.msgSock = msgSock;
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

    public void endClient(){
        try{
            this.loginSock.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }}
