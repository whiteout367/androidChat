package kr.pe.jw.citychat;

public class Message {
    String sid;
    String rid;
    String mdt;
    String nn;
    String message;
    User sender;
    long createdAt;

    public Message(String sid, String rid, String mdt, String nn, String message) {
        this.sid = sid;
        this.rid = rid;
        this.mdt = mdt;
        this.nn = nn;
        this.message = message;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getMdt() {
        return mdt;
    }

    public void setMdt(String mdt) {
        this.mdt = mdt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
