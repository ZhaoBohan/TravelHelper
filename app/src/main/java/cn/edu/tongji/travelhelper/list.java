package cn.edu.tongji.travelhelper;


import java.io.Serializable;

public class list implements Serializable{
    private item item;

    public list(){

    }

    public cn.edu.tongji.travelhelper.item getItem() {
        return item;
    }

    public void setItem(cn.edu.tongji.travelhelper.item item) {
        this.item = item;
    }
}
