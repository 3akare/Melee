package entity;

public class Data {
    private String id;
    private String data;
    private boolean read;

    public Data(String id, String data, boolean read) {
        this.id = id;
        this.data = data;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}


