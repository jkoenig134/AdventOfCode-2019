import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid arguments!");
            return;
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.err.println("Given input file does not exist!");
            return;
        }

        List<Integer> parsedLines;
        try {
            parsedLines = Files.readAllLines(inputFile.toPath())
                    .stream()
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int fuel = parsedLines.stream()
                .map(integer -> Math.floor((integer / 3.0D) - 2.0D))
                .mapToInt(Double::intValue)
                .sum();

        System.out.println("Required fuel: " + fuel);

        fuel = parsedLines.stream()
                .mapToInt(Day01::calculateRecursive)
                .sum();

        System.out.println("Recursive fuel: " + fuel);
    }

    private static int calculateRecursive(int i) {
        int fuel = Double.valueOf(Math.floor((i / 3.0D) - 2.0D)).intValue();
        return fuel <= 0 ? 0 : fuel + calculateRecursive(fuel);
    }
}