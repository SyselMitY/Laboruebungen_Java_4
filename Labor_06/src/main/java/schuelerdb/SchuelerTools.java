package schuelerdb;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SchuelerTools {
    private SchuelerTools() {
    }

    public static List<Schueler> readFromCSV(String filename) throws IOException {
        try (Stream<String> schueler = Files.lines(Path.of(filename))) {
            return schueler
                    .skip(1)
                    .filter(Schueler::isValidString)
                    .map(Schueler::of)
                    .collect(Collectors.toList());
        }
    }
}
