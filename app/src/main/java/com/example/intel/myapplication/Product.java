package com.example.intel.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Product implements Parcelable {
    String id;
    String name;
    String email;
    ArrayList<String> phoneno;

    public Product(){

    }

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        phoneno = in.createStringArrayList();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public ArrayList<String> getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(ArrayList<String> phoneno) {
        this.phoneno = phoneno;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeStringList(phoneno);
    }
}
