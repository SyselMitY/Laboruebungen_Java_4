package schwimmbad;

import java.util.Random;

public class Badegast extends Thread {
    private final Schwimmbad bad;
    int ticketNummer;
    private final String gastName;

    public Badegast(String name,Schwimmbad bad) {
        this.gastName = name;
        this.bad = bad;
    }

    public String getGastName() {
        return gastName;
    }

    @Override
    public void run() {
        Random random = new Random();
        sleepWithCatch(random.nextInt(5001) + 500);
        ticketNummer = bad.enterSchwimmbad(this);
        sleepWithCatch(random.nextInt(5001)+2000);
        bad.leaveSchwimmbad(this);
    }

    private void sleepWithCatch(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
