package Client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public final int LoginPort=12345;
    public final int StorePort=34343;
    public final int ImgPort=45454;
    public final int commentPort=56565;
    public final String ServIP="127.0.0.1";
    public boolean loginComplete=false;

    private Socket loginSock=null;
    private Socket StoresSock=null;
    private Socket ImgSock=null;
    private Socket msgSock=null;

    private UserData data=new UserData(null,null);

    public Client(String ID, String pwd, Socket loginSock){
        this.data.setID(ID);
        this.data.setPwd(pwd);
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

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public void endLoginSock(){
        try{
            this.loginSock.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

    }}

