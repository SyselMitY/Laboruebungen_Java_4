package synchronizedstack;

public class Simulation {
    private static final int POPPER_PUSHER_TIMES = 20;
    private static final int POPPER_DELAY = 2000;
    private static final int PUSHER_DELAY = 1000;

    public static void main(String[] args) {
        SynchronizedStack<Integer> stack = new SynchronizedStack<>(10);
        Thread pusher = new Thread(() -> {
            for (int i = 0; i < POPPER_PUSHER_TIMES; i++) {
                try {
                    stack.push(1);
                    System.out.println("Pushed new Entry, size is now " + stack.size());
                    Thread.sleep((long) (Math.random() * PUSHER_DELAY));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread popper = new Thread(() -> {
            for (int i = 0; i < POPPER_PUSHER_TIMES; i++) {
                try {
                    stack.pop();
                    System.out.println("Popped an Entry, size is now " + stack.size());
                    Thread.sleep((long) (Math.random() * POPPER_DELAY));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pusher.start();
        popper.start();
    }
}
