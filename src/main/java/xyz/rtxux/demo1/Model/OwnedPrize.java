package xyz.rtxux.demo1.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OwnedPrize {
    @Id
    @GeneratedValue
    private int id;
    private int uid;
    private int pid;
    private int amount;

    public OwnedPrize() {
    }

    public OwnedPrize(int uid, int pid, int amount) {
        this.uid = uid;
        this.pid = pid;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
