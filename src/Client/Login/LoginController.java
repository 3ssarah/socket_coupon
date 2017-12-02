package Client.Login;

import Client.Client;
import Client.Main_page.MainClient;
import Client.Main_page.MainController;
import Client.Register.RegisterController;

import Client.Store.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController  {

    /**Login Frame**/
    @FXML
    private Button login, create;
    @FXML private TextField id_f, pwd_f;
    /**Alert*/
    Alert alert;


    private static LoginController controller;
    private static RegisterController con;
    private static MainController mainCon;
    // 로그인 애플리케이션 참조
    private  LoginClient loginClient;


    public LoginController(){
        controller=this;
    }
    public static LoginController getController(){
        return controller;
    }
    public void setLoginClient(LoginClient loginClient){
        this.loginClient=loginClient;
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleLogin(ActionEvent event){
        loginClient.sendData("1");//send Log-in signal
        String ID= id_f.getText();
        String pwd= pwd_f.getText();

        BufferedReader br= null;

        try{
            br= new BufferedReader(new InputStreamReader(loginClient.getClient().getLoginSock().getInputStream()));


            sendData(ID);
            sendData(pwd);

            //receive msg from server
            String check= null;
            alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(br.readLine());
            alert.setContentText(":)");
            alert.showAndWait();

            check=br.readLine();


            /** recv check data from server**/

            //Login complete
            if(check.equals("0")){
                System.out.println("login complete");
                loginClient.getClient().getData().setID(ID);
                loginClient.getClient().getData().setPwd(pwd);
                loginClient.getClient().loginComplete=true;

                //여기서 현재화면 죽이고
                Stage stage=(Stage)login.getScene().getWindow();
                stage.close();


                new MainClient(loginClient);
                new ChatClient(loginClient);





            }
            else if(check.equals("-1"))System.out.println("Unless get check!");
            else System.out.println(check.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
//        loginClient.tryLogin(data);
        //text field initialization
        id_f.setText("");
        pwd_f.setText("");
    }
    @FXML
    public void handleCreate(ActionEvent event){

        //로그인 화면에서 계정생성 화면으로 전환하기
        try{
            loginClient.sendData("0");//send Register signal

            FXMLLoader loader= new FXMLLoader(getClass().getResource("../Register/RegisterFrame.fxml"));
            Parent register=loader.load();
            con =loader.<RegisterController>getController();
            con.setLoginClient(loginClient);


//            Parent register=FXMLLoader.load(getClass().getResource("../Register/RegisterFrame.fxml"));
//            Scene s= new Scene(register);
//            Stage primary= (Stage)create.getScene().getWindow();
//            primary.setScene(s);


            StackPane root=(StackPane)create.getScene().getRoot();
            root.getChildren().add(register);


        }catch(Exception e){
            e.printStackTrace();
        }




    }

    public void sendData(String str){
        PrintWriter pw = null;

        try{

            Client tempC= loginClient.getClient();
           // if(tempC.getLoginSock().isConnected())System.out.println("연결되어있음");
            pw = new PrintWriter(tempC.getLoginSock().getOutputStream(),true);
            pw.println(str);  //send string data
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void changeStage(){

        try{
        Stage stage= new Stage();
        stage.setTitle("Main Page");

        FXMLLoader loader= new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        Parent parent= loader.load();

        mainCon= loader.<MainController>getController();
        mainCon.setMainClient(new MainClient(loginClient));

        Scene s=new Scene(parent);
        stage.setResizable(false);
        stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
//    private Parent replaceSceneContent(String fxml) throws Exception {
//        FXMLLoader loader= new FXMLLoader();
//        Parent page=(Parent)loader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory()) ;
//        mainCon =loader.<MainController>getController();
//        mainCon.setMainClient(new MainClient(loginClient));
//
//        //Parent page = (Parent) FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
//        Scene scene = loginClient.getPrimaryStage().getScene();
//
//        if (scene == null) {
//            scene = new Scene(page, 700, 450);
//            scene.getStylesheets().add(getClass().getResource("RootLayout.fxml").toExternalForm());
//            loginClient.getPrimaryStage().setScene(scene);
//        } else {
//            loginClient.getPrimaryStage().getScene().setRoot(page);
//        }
//        loginClient.getPrimaryStage().sizeToScene();
//
//        return page;
//    }




}
