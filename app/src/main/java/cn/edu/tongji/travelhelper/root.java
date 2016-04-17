package cn.edu.tongji.travelhelper;


import java.io.Serializable;

public class root implements Serializable{
    private String resultcode;
    private String reason;
    private result result;
    private String error_code;

    public root(){

    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public cn.edu.tongji.travelhelper.result getResult() {
        return result;
    }

    public void setResult(cn.edu.tongji.travelhelper.result result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
