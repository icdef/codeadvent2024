package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day2.txt");
        Scanner scanner = new Scanner(inputFile);
        int sum = 0;
        boolean isIncreasing;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(" ");
            List<Integer> numbers = new java.util.ArrayList<>(Arrays.stream(split).map(Integer::parseInt).toList());
            if (numbers.get(0).equals(numbers.get(1)))
                continue;
            isIncreasing = numbers.get(0) < numbers.get(1);

            if (isIncreasing && isSafeIncreasing(numbers) == -1) {
                sum++;
            } else if (!isIncreasing && isSafeDecreasing(numbers) == -1) {
                sum++;
            }

        }
        System.out.println(sum);
    }

    private static int isSafeIncreasing(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) >= numbers.get(i + 1) || numbers.get(i + 1) - numbers.get(i) > 3)
                return i;
        }
        return -1;
    }

    private static int isSafeDecreasing(List<Integer> numbers) {
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i) <= numbers.get(i + 1) || numbers.get(i) - numbers.get(i + 1) > 3)
                return i;
        }
        return -1;
    }
}
