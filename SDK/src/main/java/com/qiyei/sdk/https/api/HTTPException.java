package com.qiyei.sdk.https.api;

public class HTTPException extends Exception{

    public int code;
    public String taskId;

    public HTTPException(int code, String taskId,String message) {
        super(message);
        this.code = code;
        this.taskId = taskId;
    }

    public HTTPException(String taskId,String message) {
        this(-1,taskId,message);
    }

    @Override
    public String toString() {
        return "HTTPException{" +
                "code=" + code +
                ", taskId='" + taskId + '\'' + super.toString() +
                '}';
    }
}
