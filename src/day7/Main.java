package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day7.txt");
        Scanner scanner = new Scanner(inputFile);
        long sum = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(":");
            long result = Long.parseLong(split[0]);
            String operatorsString = split[1];
            String[] operatorSplit = operatorsString.strip().split(" ");
            int[] operators = new int[operatorSplit.length];
            for (int i = 0; i < operators.length; i++) {
                operators[i] = Integer.parseInt(operatorSplit[i]);
            }
            boolean possible = rec(0, operators, 0, result);
            if (possible) {
                sum += result;
            }

        }
        System.out.println(sum);
    }

    public static boolean rec(long current, int[] operators, int index, long target) {
        if (current == target)
            return true;
        if (index == operators.length)
            return false;

        boolean plus = rec(current + operators[index], operators, index + 1, target);
        boolean times = rec(current * operators[index], operators, index + 1, target);

        return plus || times;
    }
}
