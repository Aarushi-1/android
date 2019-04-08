package com.example.aarushi.tootha;

public class Uploading {

    private String mName;
    private String mImageUrl;

    public Uploading(){

    }

    public Uploading(String name, String imageUrl){
        if(name.trim().equals("")){
            name="No Name";
        }
        mName=name;
        mImageUrl=imageUrl;
    }
    public String getName(){
        return mName;
    }
    public void setName(String name){
       mName=name;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUri(String imageUrl){
      mImageUrl=imageUrl;
    }
}
