package cn.edu.tongji.travelhelper;


import java.io.Serializable;

public class result implements Serializable{
    private info info;
    private list list;

    public result(){

    }

    public cn.edu.tongji.travelhelper.info getInfo() {
        return info;
    }

    public void setInfo(cn.edu.tongji.travelhelper.info info) {
        this.info = info;
    }

    public cn.edu.tongji.travelhelper.list getList() {
        return list;
    }

    public void setList(cn.edu.tongji.travelhelper.list list) {
        this.list = list;
    }
}
