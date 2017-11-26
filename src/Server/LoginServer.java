package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

class MemberInfo{

    private Hashtable<String,String > info= null; //Member information<ID,PWD>
    final private String fileName=getClass().getResource("").getPath()+"member_info.txt";

    public MemberInfo(){
        info= new Hashtable<String, String>();

        try{
            hashMake();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void hashMake()throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String ID;
        String pwd;

        while ((ID = br.readLine()) != null) {
            pwd = br.readLine();
            info.put(ID, pwd);
        }
    }
    //insert new member
    public void InsertMemeber(String ID, String pwd){info.put(ID,pwd);}

    //write in file
    public void saveFile(){
        try{
            FileWriter fw= new FileWriter(fileName);

            Iterator it= info.keySet().iterator();
            while(it.hasNext()){
                String ID= (String)it.next();
                fw.write(ID+"\n");
                fw.write((String)info.get(ID)+"\n");
            }
            fw.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public int IDCheck(String id){
        if(info.containsKey(id))
            return 1; //id exists
        else return -1; // id doesn't exist
    }
    public int Check(String id, String password){
		/* ID is exists */
        if(this.IDCheck(id) == 1){
            if(password.equals(info.get(id)))
                return 1;  //password is correct
            else
                return 0;  //password is not correct
        }
        else
            return -1;
    }
}

class LoginThread extends Thread{
    private Socket sock=null;
    private Vector<Socket> vec=null;
    private MemberInfo member;

    public LoginThread(Socket sock, Vector<Socket> vec, MemberInfo member){
        this.sock=sock;
        this.vec=vec;
        this.member=member;
    }

    public void run(){
        String id = null;
        String pwd =null;
        String phoneNumber=null;

        int mode;
        BufferedReader br=null;
        PrintWriter pw= null;

        try{
            while(true){
                br= new BufferedReader(new InputStreamReader(sock.getInputStream()));
                pw= new PrintWriter(sock.getOutputStream(),true);
                mode= Integer.parseInt(br.readLine());
                //quit sing input
                if(mode ==-1) break;
                //get id and pwd from client
                id= br.readLine();
                pwd=br.readLine();

                //new member
                if(mode==0){

                    phoneNumber=br.readLine();
                    //if ID is not distinctive
                    if(member.IDCheck(id)==1)
                        pw.println("ID already exits!");
                    else{
                        //write on member file
                        member.InsertMemeber(id,pwd);
                        this.saveMemInfo(id,phoneNumber);
                        pw.println("Registration Complete!");
                    }
                }
                //Client.Login
                else if(mode==1){
                    int result= member.Check(id,pwd);
                    switch(result){
                        case 0:
                            pw.println("Wrong Password!");
                            pw.println("-1");
                            break;
                        case 1:
                            pw.println("Login is completed!");
                            pw.println("0");
                            member.saveFile();
                            vec.remove(sock);
                            return;
                        case -1:
                            pw.println("You're not member:( Join us!");
                            pw.println("1");
                            break;
                    }
                }
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(br!= null)br.close();
                if(sock!=null) sock.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void saveMemInfo(String ID, String phoneNumber){
        FileWriter fw=null;
        try{
            fw= new FileWriter(getClass().getResource("").getPath()+ID+".txt");
            fw.write(phoneNumber+"\n");
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}

public class LoginServer{

    public static void main(String[] args){

        ServerSocket serverSock= null;
        Socket sock= null;
        MemberInfo member= new MemberInfo();
        Vector<Socket> vec= new Vector<Socket>();

        try{
            serverSock= new ServerSocket(12345);
            while(true){
                System.out.println("... Server Waiting....");
                sock=serverSock.accept();
                vec.add(sock);

                new LoginThread(sock, vec,member).start();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());

        }

    }
}

