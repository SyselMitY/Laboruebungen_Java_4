package streamingone;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StreamingToolsTest {


    public static final int LOTTO_TIP_LAENGE = 6;
    public static final int LOTTO_TIP_MAX = 45;

    @Test
    void a_quadratzahlen() {
        Collection<Integer> actual = StreamingTools.a_quadratzahlen(10);
        Collection<Integer> expected = List.of(1, 9, 25, 49, 81);
        assertEquals(expected, actual);

        actual = StreamingTools.a_quadratzahlen(20);
        expected = List.of(1, 9, 25, 49, 81, 121, 169, 225, 289, 361);
        assertEquals(expected, actual);

        assertThrows(IllegalArgumentException.class, () -> StreamingTools.a_quadratzahlen(-2));
    }

    @Test
    void b_summe() {
        double expected = 0.49019607843137253;
        double actual = StreamingTools.b_summe(100);
        assertEquals(expected, actual);

        assertThrows(IllegalArgumentException.class, () -> StreamingTools.b_summe(-2));
    }

    @Test
    void c_lotto() {
        Collection<Integer> actual = StreamingTools.c_lotto();
        long actualSize = actual
                .stream()
                .distinct()
                .count();
        assertEquals(LOTTO_TIP_LAENGE, actualSize);
        boolean isOutOfValidRange = actual.stream()
                .anyMatch(integer -> integer < 1 || integer > LOTTO_TIP_MAX);
        assertFalse(isOutOfValidRange);
    }

    @Test
    void d_quadratzahlen() {
        Collection<Integer> actual = StreamingTools.d_quadratzahlen(new int[]{700, 2, 22, 24, 3, 25, 24, 3, 25, 27, 27, 17, 201});
        Collection<Integer> expected = List.of(9, 289, 625, 729, 40401);
        assertEquals(actual, expected);

        assertThrows(IllegalArgumentException.class, () -> StreamingTools.d_quadratzahlen(new int[0]));
    }

    @Test
    void e_faktorial() {
        assertEquals(120, StreamingTools.e_faktorial(5));
        assertEquals(2432902008176640000L, StreamingTools.e_faktorial(20));
        assertThrows(IllegalArgumentException.class, () -> StreamingTools.e_faktorial(-2));
    }

    @Test
    void f_oneCounter() {
        assertEquals(12, StreamingTools.f_oneCounter(20));
        assertThrows(IllegalArgumentException.class, () -> StreamingTools.f_oneCounter(-2));
    }

    @Test
    void g_zahlenfolge() {
        List<Long> actual = StreamingTools.g_zahlenfolge();
        Collection<Long> expected = List.of(1L, 2L, 6L, 42L, 1806L, 3263442L);
        assertEquals(expected, actual);
    }

    @Test
    void h_fakultaetLaenge() {
        assertEquals(BigInteger.valueOf(120), StreamingTools.h_fakultaetLaenge(2));
        assertEquals(new BigInteger("11978571669969891796072783721689098736458938142546425857555362864628009582789845319680000000000000000"),StreamingTools.h_fakultaetLaenge(99));
        assertThrows(IllegalArgumentException.class, () -> StreamingTools.h_fakultaetLaenge(-2));
    }

    @Test
    void i_fakultaetLaengeBig() {
        assertEquals(3249, StreamingTools.i_fakultaetLaengeBig(BigInteger.TEN.pow(10000)));
        assertThrows(IllegalArgumentException.class, () -> StreamingTools.i_fakultaetLaengeBig(BigInteger.valueOf(-2)));
    }

    @Test
    void j_fibonacciSupplier() {
        assertEquals(4782,StreamingTools.j_fibonacciSupplier(1000));
        assertThrows(IllegalArgumentException.class, () -> StreamingTools.j_fibonacciSupplier(-2));
    }
}
