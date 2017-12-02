package Client.Store;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Menu {
    private int number;
    private String product_name=null;
    private int price;
    private String type="Product";
    private final  int day = 30; //term of validity ==1month
    private Store store=null;

    public Menu(String product_name, Store store, int price){
        this.number=number;
        this.product_name=product_name;
        this.store=store;
        this.type="Product";
        this.price=price;
    }

    public String getExpireDate(int day){
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd");

            Date current =new Date();

            Calendar cal= Calendar.getInstance();
            cal.setTime(current);
            cal.add(Calendar.DATE,day);

            return df.format(cal.getTime());
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDay() {
        return day;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
