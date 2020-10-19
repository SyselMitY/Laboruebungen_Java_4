package streamingone;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamingTools {
    private StreamingTools() {
    }

    public static Collection<Integer> a_quadratzahlen(int bisInklusive) {
        if (bisInklusive < 2)
            throw new IllegalArgumentException("bisInklusive has to be at least 2");

        return IntStream.rangeClosed(1, bisInklusive)
                .filter(integer -> integer % 2 == 1)
                .map(integer -> integer * integer)
                .boxed()
                .collect(Collectors.toList());
    }

    public static double b_summe(int bisInklusive) {
        if (bisInklusive < 2)
            throw new IllegalArgumentException("bisInklusive has to be at least 2");

        return IntStream.rangeClosed(1, bisInklusive)
                .mapToDouble(value -> 1.0 / ((value + 1) * (value + 2)))
                .sum();
    }

    public static Collection<Integer> c_lotto() {
        Random random = new Random();

        return IntStream.generate(() -> random.nextInt(45) + 1)
                .distinct()
                .limit(6)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
    }

    public static Collection<Integer> d_quadratzahlen(int[] arr) {
        if (arr.length < 1)
            throw new IllegalArgumentException("Array must not be empty");

        return Arrays.stream(arr)
                .distinct()
                .filter(value -> value % 2 == 1)
                .sorted()
                .map(operand -> operand * operand)
                .boxed()
                .collect(Collectors.toList());
    }

    public static long e_faktorial(int i) {
        if (i < 2)
            throw new IllegalArgumentException("i has to be at least 2");

        return LongStream.rangeClosed(1, i)
                .reduce((left, right) -> left * right)
                .orElse(0);
    }

    public static long f_oneCounter(int bisInklusive) {
        if (bisInklusive < 2)
            throw new IllegalArgumentException("bisInklusive has to be at least 2");

        return IntStream.rangeClosed(1, bisInklusive)
                .mapToObj(Integer::toString)
                .reduce("", (left, right) -> left + right)
                .chars()
                .filter(value -> value == '1')
                .count();
    }

    public static List<Long> g_zahlenfolge() {
        return LongStream.iterate(1, i -> i * (i + 1))
                .limit(6)
                .boxed()
                .collect(Collectors.toList());
    }

    public static BigInteger h_fakultaetLaenge(int laenge) {
        if (laenge < 2)
            throw new IllegalArgumentException("laenge has to be at least 2");
        return Stream.generate(new FaktSupplier())
                .filter(fakt -> fakt.toString().length() > laenge)
                .findFirst()
                .orElseThrow();
    }

    public static long i_fakultaetLaengeBig(BigInteger size) {
        if (size.compareTo(BigInteger.valueOf(0)) < 1)
            throw new IllegalArgumentException("Size must not be negative");
        return Stream.generate(new FaktSupplierAdvanced())
                .filter(fakt -> fakt.getValue().compareTo(size) > 0)
                .findFirst()
                .map(AbstractMap.SimpleImmutableEntry::getKey)
                .orElseThrow();
    }

    public static int j_fibonacciSupplier(int length) {
        if (length < 2)
            throw new IllegalArgumentException("length has to be at least 2");
        return Stream.generate(new FiboSupplier())
                .filter(fibo -> fibo.getValue().toString().length() >= length)
                .findFirst()
                .map(AbstractMap.SimpleImmutableEntry::getKey)
                .orElseThrow();
    }
}
