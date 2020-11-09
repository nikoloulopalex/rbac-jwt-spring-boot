package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "image_table")

public class Image {
  public Image() {

        super();
    }

   public Image(String name, String type, byte[] picByte) {

        this.name = name;

        this.type = type;

        this.picByte = picByte;

    }
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    //image bytes can have large lengths so we specify a value

    //which is more than the default length for picByte column
    @Column(name = "picByte", length = 1000)

    private byte[] picByte;

    public String getName() {
        return name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String getType() {

        return type;

    }


   public void setType(String type) {

        this.type = type;

    }


    public byte[] getPicByte() {

        return picByte;

    }

    public void setPicByte(byte[] picByte) {

       this.picByte = picByte;

    }


}