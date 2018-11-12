package com.example.yeohf.loginsystem.Entity;

public class Rental {

    public String rentalid;
    public String address;
    public String price;
    public double lat;
    public double lng;
    public String desc;
    public String title;
    public String zone;
    public String storey;
    public String type;
    public String model;
    public String listingType;
    public String imagePath;
    public String chatId;
    public String userid;
    public String key;



    public Rental(){
    }

    public Rental(String id, String title, String address, String model, String listingType, String zone, String type, String storey, String price, String imagePath, String chatid, String userid, String key, double lat, double lng) {
        this.address = address;
        this.price = price;
        this.rentalid= id;
        this.title= title;
        this.zone = zone;
        this.type = type;
        this.storey = storey;
        this.lat = lat;
        this.lng = lng;
        this.chatId = chatid;
        this.imagePath = imagePath;
        this.model = model;
        this.listingType = listingType;
        this.userid=userid;
        this.key=key;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }
    public String getRentalid() {
        return rentalid;
    }

    public void setRentalid(String rentalid) {
        this.rentalid = rentalid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStorey() {
        return storey;
    }

    public void setStorey(String storey) {
        this.storey = storey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
