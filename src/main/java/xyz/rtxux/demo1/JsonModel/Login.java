package xyz.rtxux.demo1.JsonModel;

public class Login {
    private int status;
    private int uid;
    private String token;

    public Login(int status, int uid, String token) {
        this.status = status;
        this.uid = uid;
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

