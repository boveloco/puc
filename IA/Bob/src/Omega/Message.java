package Omega;

public class Message {
    private Player receiver;

    private Player sender;

    private String msg;

    private Object extra;

    public Message(Player sender, Player receiver, String msg){
        this.setSender(sender);
        this.setReceiver(receiver);
        this.setMsg(msg);
        this.setExtra(null);
    }
    public Message(Player sender, Player receiver, String msg, Object extra){
        this(sender, receiver, msg);
        this.setExtra(extra);
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
