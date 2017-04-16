package Omega;

public class MessageDispatcher {
    private static MessageDispatcher ourInstance = new MessageDispatcher();

    public static MessageDispatcher getInstance() {
        return ourInstance;
    }

    private MessageDispatcher() {
    }

    public void dispatchMessage(Player sender, Player receiver, String msg) {
        this.dispatchMessage(sender, receiver, msg,null);
    }

    public void dispatchMessage(Player sender, Player receiver, String msg, Object extra) {
        Message m;
        if(extra == null){
            m = new Message(sender, receiver, msg);
        } else {
            m = new Message(sender, receiver, msg, extra);
        }
        deliverMessage(receiver, m);
    }

    private void deliverMessage(Player receive, Message msg) {
        if(!receive.handleMessage(msg)){
            System.out.println("Player can`t handle message");
        }


    }
}
