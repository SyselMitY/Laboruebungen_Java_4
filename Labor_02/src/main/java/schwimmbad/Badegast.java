package schwimmbad;

import java.util.Random;

public class Badegast extends Thread {
    public static final int SCHWIMMBAD_HAWARA_ENTER_WAIT_TIME_LOWER_BOUND_INCLUSIVE = 500;
    public static final int SCHWIMMBAD_HAWARA_ENTER_WAIT_TIME_UPPER_BOUND_EXCLUSIVE = 5001;
    public static final int SCHWIMMBAD_HAWARA_SCHWIMM_TIME_LOWER_BOUND_INCLUSIVE = 2000;
    public static final int SCHWIMMBAD_HAWARA_SCHWIMM_TIME_UPPER_BOUND_EXCLUSIVE = 5001;
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
        sleepWithCatch(random.nextInt(SCHWIMMBAD_HAWARA_ENTER_WAIT_TIME_UPPER_BOUND_EXCLUSIVE) + SCHWIMMBAD_HAWARA_ENTER_WAIT_TIME_LOWER_BOUND_INCLUSIVE);
        ticketNummer = bad.enterSchwimmbad(this);
        sleepWithCatch(random.nextInt(SCHWIMMBAD_HAWARA_SCHWIMM_TIME_UPPER_BOUND_EXCLUSIVE)+ SCHWIMMBAD_HAWARA_SCHWIMM_TIME_LOWER_BOUND_INCLUSIVE);
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
