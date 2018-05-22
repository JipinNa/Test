package com.example.chioy.test;

import java.io.Serializable;

public class PersonInfo implements Serializable {
    private int id;
    private String  name;
    private String sex;
    private String age;
    private double height;
    private double weight;
    private int init = 1;
    public PersonInfo(int id, String name, String sex, String age, double height, double weight,int init){
        setId(id);
        setName(name);
        setSex(sex);
        setAge(age);
        setHeight(height);
        setWeight(weight);
        setInit(init);
    }
    public PersonInfo(String name, String sex, String age, double height, double weight,int init){
        setName(name);
        setSex(sex);
        setAge(age);
        setHeight(height);
        setWeight(weight);
        setInit(init);
    }
    public PersonInfo(int init){
        setInit(init);
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getInit() {
        return init;
    }
}
