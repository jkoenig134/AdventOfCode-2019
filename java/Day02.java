import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.IntFunction;

public class Day02 {

    public static void main(String[] args) {
        System.out.println("Advent Of Code (https://adventofcode.com/) challenge for day 2");

        if (args.length != 1) {
            System.err.println("Invalid arguments!");
            return;
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.err.println("Given input file does not exist!");
            return;
        }

        Integer[] programOrigin;
        try {
            programOrigin = Arrays.stream(Files.readString(inputFile.toPath()).trim().split(","))
                    .map(s -> Integer.parseInt(s))
                    .toArray(size -> new Integer[size]);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int fIndex = 0;
        int sIndex = 0;

        while (true) {
            Integer[] program = Arrays.copyOf(programOrigin, programOrigin.length);

            program[1] = fIndex;
            program[2] = sIndex;

            if(fIndex == 99) {
                sIndex++;
                fIndex = 0;
            } else {
                fIndex++;
            }

            if(sIndex == 99 && fIndex == 99)
                break;

            int result = runIntcodeProgram(program);

            if(result == 19690720) {
                int value = 100 * program[1] + program[2];
                System.out.println("Value found: "+value);
                break;
            }
        }
    }

    private static int runIntcodeProgram(Integer[] program) {
        for(int index = 0; index < program.length; index++) {
            int opCode = program[index];

            switch (opCode) {
                case 1:
                case 2:
                    boolean multiply = opCode == 2;

                    int first = program[index+1];
                    int second = program[index+2];
                    int toOverwrite = program[index+3];

                    int val = multiply ? (program[first] * program[second]) : (program[first] + program[second]);
                    program[toOverwrite] = val;

                    index += 3;
                    break;
                case 99:
                    return program[0];
                default:
                    System.err.println("Invalid op code '"+opCode+"'");
                    return -1;
            }
        }

        return -1;
    }
}