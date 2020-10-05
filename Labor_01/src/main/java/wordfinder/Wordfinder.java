package wordfinder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wordfinder {
    public static final int PATTERN_LENGTH = 9;
    private final Set<String> words;

    public Wordfinder() throws IOException {
        final Path wordsPath = getWordsPath();
        try (Stream<String> inputStream = Files.lines(wordsPath)) {
            words = inputStream
                    .parallel()
                    .filter(word -> word.length() == PATTERN_LENGTH)
                    .map(String::toLowerCase)
                    .collect(Collectors.toCollection(TreeSet::new));
        }
    }

    public Set<String> find(List<int[]> patterns) {
        checkPatterns(patterns);
        Map<Integer, Character> charMap = new TreeMap<>();

        for (String word : words) {
            char[] chars = word.toCharArray();
            mapCharsToPattern(patterns.get(0), charMap, chars);

            Set<String> calculatedWords = patterns.stream()
                    .skip(1)
                    .map(pattern -> getWordFromPattern(pattern, charMap))
                    .filter(words::contains)
                    .collect(Collectors.toSet());
            calculatedWords.add(word);

            if(calculatedWords.size() == patterns.size())
                return calculatedWords;
        }
        return Set.of();
    }

    private void mapCharsToPattern(int[] pattern, Map<Integer, Character> charMap, char[] chars) {
        charMap.clear();
        for (int i = 0; i < chars.length; i++) {
            charMap.put(pattern[i], chars[i]);
        }
    }

    private String getWordFromPattern(int[] pattern, Map<Integer, Character> charMap) {
        return Arrays.stream(pattern)
                .mapToObj(charMap::get)
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    private void checkPatterns(List<int[]> patterns) {
        Objects.requireNonNull(patterns);
        if(patterns.size() < 2)
            throw new IllegalArgumentException("At least 2 patterns are required");
        for (int[] pattern : patterns) {
            if(pattern.length != 9)
                throw new IllegalArgumentException("Pattern length must be 9");
            if (Arrays.stream(pattern).anyMatch(number -> number > 9 || number < 1))
                throw new IllegalArgumentException("Pattern must only contain numbers from 1 to 9");
            if (Arrays.stream(pattern).distinct().count() != 9) {
                throw new IllegalArgumentException("Pattern must contain numbers 1 to 9 without duplicates");
            }
        }
    }


    private Path getWordsPath() throws IOException {
        try {
            return Paths.get(Wordfinder.class.getResource("/words.txt").toURI());
        } catch (URISyntaxException e) {
            throw new IOException("Error while generating URI for input file");
        }
    }


    public static void main(String[] args) {
        try {
            Wordfinder finder = new Wordfinder();
            List<int[]> patterns = new ArrayList<>();
            patterns.add(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
            patterns.add(new int[]{2, 3, 1, 4, 5, 6, 7, 8, 9});
            patterns.add(new int[]{8, 9, 3, 1, 2, 4, 5, 6, 7});

            Set<String> result = finder.find(patterns);
            System.out.println("Gefundene Wörter: ");
            result.forEach(s -> System.out.printf("%s, ",s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
