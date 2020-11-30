package decorator;

public class JumpCounter extends FilterCounter {

    private final int jump;

    public JumpCounter(Counter in, int jump) {
        super(in);
        if (jump < 0)
            throw new IllegalArgumentException("Jump must not be negative");
        this.jump = jump;
    }

    @Override
    public Counter tick() {
        for (int i = 0; i < jump; i++) {
            super.tick();
        }
        return this;
    }
}
