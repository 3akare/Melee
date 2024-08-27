package entity;

import java.util.Date;

public class Data {
    private String id;
    private String data;
    private String senderEmail;
    private Date dataTime;
    private boolean read;

    public Data(String id, String data, String senderEmail, Date dataTime, boolean read) {
        this.id = id;
        this.data = data;
        this.senderEmail = senderEmail;
        this.dataTime = dataTime;
        this.read = read;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}


