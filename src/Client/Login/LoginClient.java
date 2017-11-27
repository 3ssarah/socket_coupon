package Client.Login;

import Client.Client;
import Client.UserData;
import Client.Register.RegisterStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginClient extends Application {

    private Client client;
    private Stage primaryStage;
    private StackPane rootLayout;

    /** Stage **/
    private LoginStage loginStage;
    private RegisterStage registerStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        client= new Client(null,null, new Socket("127.0.0.1",12345));

        //initRootLayout();
        initLoginStage();
    }


    /**try connect to server and Login**/
    public void tryLogin(UserData data){
        BufferedReader br= null;

        try{
            br= new BufferedReader(new InputStreamReader(this.getClient().getLoginSock().getInputStream()));


            sendData(data.getID());
            sendData(data.getPwd());

            //receive msg from server
            String check= null;
            check=br.readLine();
            System.out.println(check);

            //Login complete
            if(check.equals("0")){
                System.out.println("login complete");
                this.getClient().getData().setID(data.getID());
                this.getClient().getData().setPwd(data.getPwd());
                this.getClient().loginComplete=true;
                //여기서 현재화면 죽이고

                //close Client.Login socket
                this.getClient().getLoginSock().close();

                //새로운 화면 띄우기
            }
            else if(check.equals("-1"))System.out.println("Unless get check!");
            else System.out.println(check.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    /** Send data to Server **/
    public void sendData(String str){
        PrintWriter pw = null;

        try{
            if(this.getClient().getLoginSock().isConnected())System.out.println("연결되어있음");

            pw = new PrintWriter(this.getClient().getLoginSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /** Initializing Stage **/
    public void initRootLayout() {

        try {
            //상위레이아웃을 가져온다
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((LoginClient.class.getResource("view/RootLayout.fxml")));
            Parent root= loader.load();

            //show scene contain root layout
            Scene s = new Scene(root);
            primaryStage.setScene(s);
            primaryStage.show();

            //컨트롤러를 이용할 수 있게 한다
            LoginController controller= loader.getController();
            controller.setLoginClient(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initLoginStage(){
        loginStage= new LoginStage(this);
    }
    /** return Each Stage**/
    public LoginStage getLoginStage(){
        return loginStage;
    }
    public RegisterStage getRegisterStage(){
        return registerStage;
    }

    /**return Client(include sockets)**/
    public Client getClient(){
        return this.client;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {

        launch();

    }

}



