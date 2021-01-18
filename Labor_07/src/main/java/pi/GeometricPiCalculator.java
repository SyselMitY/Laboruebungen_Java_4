package pi;

public class GeometricPiCalculator {
    public static double calculatePi(int iterations) {
        if (iterations < 1) {
            throw new IllegalArgumentException("Iter smaller than 1");
        }
        double cur = 0.5;
        double curPfusch = 0.5;
        int n = 6;
        for (int i = 0; i < iterations; i++) {
            System.out.printf("n = %9d pi: %.6f piPfusch: %.6f\n",n,cur*n,curPfusch*n);
            n *= 2;
            cur = (Math.sqrt(0.5) * cur) / (Math.sqrt(1 + Math.sqrt(1 - cur * cur)));
            curPfusch = Math.sqrt(0.5 * (1 - Math.sqrt(1 - curPfusch * curPfusch)));
        }
        return cur*n;
    }

    public static void main(String[] args) {
        calculatePi(25);
    }
}
