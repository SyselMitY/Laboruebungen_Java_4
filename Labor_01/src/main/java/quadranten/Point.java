package quadranten;

public class Point {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getQuadrant() {
        if (x == 0 || y == 0)
            return 0;
        return y > 0 ? (x > 0 ? 1 : 2) : (x > 0 ? 4 : 3);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
