package com.ffcs.pojo;

/**
 * Created by urbo on 2018/8/18.
 */
public class Product {
    private int id;
    private String name;
    private int price;
    private String imgSrc;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "[id: " + id + " name: " + name + " price: " + price + " imgSrc: " + imgSrc + " info: " + info + "]";
    }
}
