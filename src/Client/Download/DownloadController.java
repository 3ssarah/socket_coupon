package Client.Download;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    public void setDownloadClient(DownloadClient downloadClient){this.downClient=downloadClient;}

    public void handleBuyBtn(ActionEvent event){

        String imgPath;
        if(catCheck.isSelected()){
            imgPath=catImg.getUrl();
        }else if(dogCheck.isSelected()){
            imgPath=dogImg.getUrl();
        }else
            imgPath=birthdayImg.getUrl();

        change(imgPath);

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
                //sendData and change to download jpg fxml
                downClient.sendData(commentArea.getText());
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
        downClient.sendData("");

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
