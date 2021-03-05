package org.sk.jih.response;


import org.sk.jih.modal.ImageUpload;

import java.util.ArrayList;

public class ImageUploadResponse {
    private boolean status;
    private String message;
    public ArrayList<ImageUpload> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ImageUpload> getData() {
        return data;
    }

    public void setData(ArrayList<ImageUpload> data) {
        this.data = data;
    }
}
