package com.example.iplmarket;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class item_class implements Parcelable { //아이템 정보를 저장하는 클래스 (intent해주기 위해 직렬화)
    private String item_num;
    private String content_text;
    private String image_link;
    private String info;
    private int quantity;
    private int price;
    private int total_price;    //상품개수 * 가격
    private static int cart_price = 0;


    item_class(String item_num, String content_text, String image_link, String info, int price, int quantity)
    {
        this.item_num = item_num;
        this.content_text = content_text;
        this.image_link = image_link;
        this.info = info;
        this.quantity = quantity;
        this.price = price;
        this.total_price = price*quantity;
        cart_price += total_price;
    }


    public String getNum(){
        return item_num;
    }

    public String getContent_text() {
        return content_text;
    }
    public void setContent_text(String Content_text){
        this.content_text = Content_text;
    }

    public String getImage_link() {
        return image_link;
    }

    public int getQuantity() { return quantity; }
    public void zeroQuantity(int num) { cart_price -= (price*quantity); }
    public void addQuantity(int num) { this.quantity += num; cart_price += (price*num); }
    public void decQuantity(int num) { this.quantity -= num; cart_price -= (price*num); }

    public int getPrice() { return price; }
    public void setPrice(int price) {this.price = price; }

    public int getTotalPrice() { return total_price; }
    public void setTotalPrice() { this.total_price = price*quantity; }

    public static int getCart_price(){ return cart_price;}
    public static void setCart_price(int i){cart_price = i;}
    public String getInfo(){return info;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.item_num);
        dest.writeString(this.content_text);
        dest.writeString(this.image_link);
        dest.writeString(this.info);
        dest.writeInt(this.quantity);
        dest.writeInt(this.price);
        dest.writeInt(this.total_price);
    }

    protected item_class(Parcel in) {
        this.item_num = in.readString();
        this.content_text = in.readString();
        this.image_link = in.readString();
        this.info = in.readString();
        this.quantity = in.readInt();
        this.price = in.readInt();
        this.total_price = in.readInt();
    }

    public static final Parcelable.Creator<item_class> CREATOR = new Parcelable.Creator<item_class>() {
        @Override
        public item_class createFromParcel(Parcel source) {
            return new item_class(source);
        }

        @Override
        public item_class[] newArray(int size) {
            return new item_class[size];
        }
    };
}
