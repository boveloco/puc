package States;

import Omega.Player;

public class AbstractState<T> implements State<T> {

    @Override
    public void enter(Player<T> t) {

    }

    @Override
    public void execute(Player<T> t) {

    }

    @Override
    public void exit(Player<T> t) {
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
