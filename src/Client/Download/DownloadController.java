package Client.Download;

import Client.Main_page.MainClient;
import Client.Store.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ObjectInputStream;
import java.util.Optional;

public class DownloadController {

    @FXML
    TextArea commentArea;
    @FXML
    Button CBtn, buyBtn, downBtn;
    Alert alert;
    @FXML
    Image catImg, dogImg, birthdayImg;
    @FXML
    CheckBox catCheck,dogCheck,bdCheck;
    @FXML
    ImageView newImg;


    private DownloadClient downClient;
    private String[] item;
    private String saved_filename;


    public void setDownloadClient(DownloadClient downloadClient){this.downClient=downloadClient;}
    public void setItem(String[] item){this.item=item;}


    public void handleBuyBtn(ActionEvent event){



        String result= item[0]+","+item[1]+","+item[2];
        downClient.sendData(result);

        String imgPath;
        if(catCheck.isSelected()){
           // imgPath=getClass().getResource("").getPath()+"cat.jpg";
            imgPath=catImg.getUrl();
        }else if(dogCheck.isSelected()){
            imgPath=dogImg.getUrl();
        }else
           imgPath=birthdayImg.getUrl();

        downClient.sendData(imgPath);
        saved_filename=downClient.recvData();

       // change(imgPath);
        Stage stage= (Stage)buyBtn.getScene().getWindow();
        stage.close();

    }
    public void handleCBtn(ActionEvent event){
        Stage stage= (Stage)CBtn.getScene().getWindow();
        stage.close();

    }
    public void change(String img){
        try{
            alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("");// get message from server"Buying item name, price! "
            alert.setContentText("");

            Optional<ButtonType> result= alert.showAndWait();
            if(result.get()==ButtonType.OK){
                downClient.sendData("1");
                //sendData and change to download jpg fxml
               // downClient.sendData(commentArea.getText());
                downClient.sendData(img);

                Stage download= new Stage();
                download.initOwner(downClient.getPrimaryStage());
                download.setTitle("Download");

                FXMLLoader loader= new FXMLLoader(getClass().getResource("giftcard.fxml"));
                Parent parent= loader.load();

                Scene s= new Scene(parent);
                download.setScene(s);
                download.setResizable(false);
                download.show();

                Stage stage= (Stage)buyBtn.getScene().getWindow();
                stage.close();

            }else{
                Stage stage= (Stage)buyBtn.getScene().getWindow();
                stage.close();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    public void handleDownBtn(ActionEvent event){
        //send signal to Server
        downClient.sendData("2");
        change(saved_filename);

        try{
            ObjectInputStream ois= new ObjectInputStream(downClient.getClient().getImgSock().getInputStream());
            Image downed_img=(Image)ois.readObject();

            newImg.setImage(downed_img);
            downClient.getClient().getImgSock().close();
            ois.close();

            Stage stage= (Stage)downBtn.getScene().getWindow();
            stage.close();

        }catch(Exception e){
           e.printStackTrace();
        }

    }
}
