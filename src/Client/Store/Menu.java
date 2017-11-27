package Client.Store;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Menu {
    private String product_name=null;
    private int price;
    private  double rate;
    private String type=null;
    private int day; //term of validity
    private Store store=null;

    public Menu(String product_name, Store store, int price, int day){
        this.product_name=product_name;
        this.store=store;
        this.type="Product";
        this.day=day;
        this.price=price;
    }
    public Menu(String product_name, Store store, double rate,int day){
        this.product_name=product_name;
        this.store=store;
        this.type="Discount coupon";
        this.rate=rate;
        this.day=day;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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

    public void setDay(int day) {
        this.day = day;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
