package com.osama.bookapi.dto;

// Generic response wrapper class
// Used to send structured API response to client

public class ResponseStructure<T> {

    private int statusCode; // HTTP status code (e.g., 200, 404)
    private String message; // Response message (success/failure info)
    private T data; // Actual data returned from API

    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}