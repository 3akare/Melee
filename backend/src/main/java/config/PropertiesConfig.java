package config;

public class PropertiesConfig {
    private String password;
    private String host;
    private String storeType;
    private String address;
    private String geminiUrl;
    private String geminiApiKey;

    public PropertiesConfig(String password, String host, String storeType, String address, String geminiUrl, String geminiApiKey) {
        this.password = password;
        this.host = host;
        this.storeType = storeType;
        this.address = address;
        this.geminiUrl = geminiUrl;
        this.geminiApiKey = geminiApiKey;
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
    public String getGeminiApiKey() {
        return geminiApiKey;
    }
    public void setGeminiApiKey(String geminiApiKey) {
        this.geminiApiKey = geminiApiKey;
    }
    public String getGeminiUrl() {
        return geminiUrl;
    }
    public void setGeminiUrl(String geminiUrl) {
        this.geminiUrl = geminiUrl;
    }
}
