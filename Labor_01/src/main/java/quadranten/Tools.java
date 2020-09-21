package quadranten;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Tools {

    public static final int QUADRANT_ENTRY_SIZE = Integer.SIZE + Double.SIZE * 2;

    public static void fixPoints(InputStream inputStream, OutputStream outputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream);
             DataOutputStream dos = new DataOutputStream(outputStream)) {
            while (dis.available() >= QUADRANT_ENTRY_SIZE) {
                int quadrant = dis.readInt();
                double x = dis.readDouble();
                double y = dis.readDouble();

                int calculatedQuadrant = getQuadrant(x, y);
                System.out.printf("X:%f Y:%f Q:%b\n", x, y, calculatedQuadrant==quadrant);
                dos.writeInt(calculatedQuadrant);
                dos.writeDouble(x);
                dos.writeDouble(y);
            }
        }
    }

    private static int getQuadrant(double x, double y) {
        if (x == 0 || y == 0)
            return 0;
        return y > 0 ? (x > 0 ? 1 : 2) : (x > 0 ? 4 : 3);
    }

    public static void main(String[] args) {
        try (FileOutputStream outputStream = new FileOutputStream(".\\Labor_01\\src\\main\\resources\\punkte_korr.dat")) {
            fixPoints(Tools.class.getResourceAsStream("/punkte.dat"), outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Point> getPointsFromQuadrant(InputStream stream, int quadrant) throws IOException {
        Collection<Point> points = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(stream)) {
                while (dis.available() >= QUADRANT_ENTRY_SIZE) {
                    int readQuadrant = dis.readInt();
                    double x = dis.readDouble();
                    double y = dis.readDouble();

                    Point point = new Point(x, y);
                    if(point.getQuadrant() == quadrant) {
                        points.add(point);
                    }
                }
            }
        return points;
        }
}
