package config;

public class PropertiesConfig {
    private String password;
    private String host;
    private String storeType;
    private String address;

    public PropertiesConfig(String password, String host, String storeType, String address){
        this.password = password;
        this.storeType = storeType;
        this.host = host;
        this.address = address;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getStoreType() {
        return storeType;
    }
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
