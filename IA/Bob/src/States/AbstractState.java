package States;

import Omega.Player;

import java.util.Random;

public abstract class AbstractState implements State<Player> {
	
    protected Random r = new Random();

    @Override
    public void enter(Player t) {
    	
    }

    @Override
    public void execute(Player t) {

    }

    @Override
    public void exit(Player t) {
        System.out.println("--------------------------------------");
        try {
            System.out.println("Thread: " + Thread.currentThread().getName());
            Thread.currentThread();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
