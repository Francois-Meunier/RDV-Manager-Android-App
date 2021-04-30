package com.example.rdv_app_meunier_nicolas;

import android.os.Parcel;
import android.os.Parcelable;

public class Moment implements Parcelable {
    long id;
    String title;
    String date;
    String time;
    String address;
    String phone;
    String contact;
    String done;
    //int photo;
    //String address;
    public Moment(String title, String date,
                  String time, String adress, String phone,String contact,String done) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.address = adress;
        this.phone = phone;
        this.contact = contact;
        this.done = done;
    }
    public Moment(long id, String title,
                  String date, String time,String adress,String phone,String contact,String done) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.address = adress;
        this.phone = phone;
        this.contact = contact;
        this.done = done;
    }
    //Pour récuperer et creer les String de la database
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getAdress() {
        return address;
    }
    public void setAdress(String adress) {
        this.address = adress;}
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getDone() {
        return done;
    }
    public void setDone(String done) {
        this.done = done;
    }
    @Override
    public int describeContents() {
        return hashCode();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(contact);
        dest.writeString(done);
    }
    public static final Parcelable.Creator<Moment> CREATOR = new Parcelable.Creator<Moment>(){
        @Override
        public Moment createFromParcel(Parcel parcel) {
            return new Moment(parcel);
        }
        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };
    public Moment(Parcel parsel){
        id=parsel.readLong();
        title=parsel.readString();
        date=parsel.readString();
        time=parsel.readString();
        address=parsel.readString();
        phone=parsel.readString();
        contact=parsel.readString();
        done=parsel.readString();
    }
}

