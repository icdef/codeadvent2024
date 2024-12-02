package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            int indexIncrease = isSafeIncreasing(numbers);
            int indexDecrease = isSafeDecreasing(numbers);
            if (indexIncrease == -1 || indexDecrease == -1) {
                sum++;
            } else {
                List<Integer> retryIncrease = new ArrayList<>(numbers);
                retryIncrease.remove(indexIncrease);
                List<Integer> retryIncreasePlus = new ArrayList<>(numbers);
                retryIncreasePlus.remove(indexIncrease+1);
                List<Integer> retryDecrease = new ArrayList<>(numbers);
                retryDecrease.remove(indexDecrease);
                List<Integer> retryDecreasePlus = new ArrayList<>(numbers);
                retryDecreasePlus.remove(indexDecrease+1);
                if (isSafeIncreasing(retryIncrease) == -1 || isSafeIncreasing(retryIncreasePlus) == -1
                || isSafeDecreasing(retryDecrease) == -1 || isSafeDecreasing(retryDecreasePlus) == -1) {
                    sum ++;
                    }
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
