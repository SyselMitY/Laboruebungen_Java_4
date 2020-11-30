package decorator;

public class PrintCounter extends FilterCounter {

    char append;

    public PrintCounter(Counter in,char append) {
        super(in);
        this.append = append;
    }

    @Override
    public Counter tick() {
        System.out.printf("%d%c",super.read(),append);
        super.tick();
        return this;
    }
}
