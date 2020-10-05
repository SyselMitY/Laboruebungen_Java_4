package schwimmbad;

import java.util.LinkedList;
import java.util.List;

public class Schwimmbad {
    public static final int FIRST_TICKET_NUMBER = 1;
    private final int maxLiegen;
    private int nextTicketNumber;
    private final List<Badegast> badegäste;

    public Schwimmbad(int maxLiegen) {
        this.maxLiegen = maxLiegen;
        badegäste = new LinkedList<>();
        this.nextTicketNumber = FIRST_TICKET_NUMBER;
    }

    public synchronized int enterSchwimmbad(Badegast gast) {
        while (badegäste.size() >= maxLiegen) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        badegäste.add(gast);
        System.out.printf("%s betritt Schwimmbad mit Ticket %d ---> %d anwesend\n", gast.getGastName(), nextTicketNumber, getVisitorCount());
        return nextTicketNumber++;
    }

    public synchronized void leaveSchwimmbad(Badegast badegast) {
        badegäste.remove(badegast);
        System.out.printf("%s verlässt Schwimmbad ---> %d anwesend\n", badegast.getGastName(), getVisitorCount());
        this.notifyAll();
    }


    public synchronized int getVisitorCount() {
        return badegäste.size();
    }

}
