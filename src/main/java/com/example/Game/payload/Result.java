package com.example.Game.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Result {
    private String message;
    private boolean status;
    private Object data;
    private String exception;

    public Result(String message, boolean success,Exception error) {
        this.message = message;
        this.status = success;
        this.exception = error.getMessage();
    }

    public Result(String message, boolean success, Object data) {
        this.message = message;
        this.status = success;
        this.data = data;
    }

    public Result(String message, boolean success) {
        this.message = message;
        this.status = success;
    }


    public Result success(Object data){
        return new Result("Success",true,data);
    }

    public Result exception(Exception e){
        return new Result("exception",false,e);
    }

    public Result delete(){
        return new Result("success",true);
    }

}
