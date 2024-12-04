package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day4.txt");
        Scanner scanner = new Scanner(inputFile);
        List<List<String>> crossword = new ArrayList<>();
        while (scanner.hasNextLine()) {
            List<String> row = new ArrayList<>();
            row.add(scanner.nextLine());
            crossword.add(row);
        }
        int sum = 0;

        for (int i = 0; i < crossword.size(); i++) {
            String line = crossword.get(i).getFirst();
            for (int j = 0; j < line.length(); j++) {
                if (i + 3 < crossword.size()) {
                    if (columnCheckDown(line, j, crossword, i)) {
                        sum++;
                    }
                }
                if (i >= 3) {
                    if (columnCheckUp(line, j, crossword, i)) {
                        sum++;
                    }
                }
            }
        }
        for (int i = 0; i < crossword.size(); i++) {
            String line = crossword.get(i).getFirst();
            for (int j = 0; j < line.length() - 3; j++) {
                if (rowCheck(line, j)) {
                    sum++;
                }
                if (i + 3 < crossword.size()) {
                    if (diagCheckRightDown(line, j, crossword, i)) {
                        sum++;
                    }
                }
                if (i >= 3) {
                    if (diagCheckRightUp(line, j, crossword, i)) {
                        sum++;
                    }
                }
            }
        }
        for (int i = 0; i < crossword.size(); i++) {
            String line = crossword.get(i).getFirst();
            for (int j = line.length() - 1; j >= 3; j--) {
                if (rowCheckReverse(line, j)) {
                   sum++;
                }
                if (i >= 3) {
                    if (diagCheckLeftUp(line, j, crossword, i)) {
                        sum++;
                    }
                }
                if (i + 3 < crossword.size()) {
                    if (diagCheckLeftDown(line, j, crossword, i)) {
                        sum++;
                    }
                }
            }
        }
        System.out.println(sum);
    }

    private static boolean diagCheckRightDown(String line, int j, List<List<String>> crossword, int i) {
        char x = line.charAt(j);
        char m = crossword.get(i + 1).getFirst().charAt(j + 1);
        char a = crossword.get(i + 2).getFirst().charAt(j + 2);
        char s = crossword.get(i + 3).getFirst().charAt(j + 3);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean diagCheckLeftDown(String line, int j, List<List<String>> crossword, int i) {
        char x = line.charAt(j);
        char m = crossword.get(i + 1).getFirst().charAt(j - 1);
        char a = crossword.get(i + 2).getFirst().charAt(j - 2);
        char s = crossword.get(i + 3).getFirst().charAt(j - 3);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean diagCheckLeftUp(String line, int j, List<List<String>> crossword, int i) {
        char x = line.charAt(j);
        char m = crossword.get(i - 1).getFirst().charAt(j - 1);
        char a = crossword.get(i - 2).getFirst().charAt(j - 2);
        char s = crossword.get(i - 3).getFirst().charAt(j - 3);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean diagCheckRightUp(String line, int j, List<List<String>> crossword, int i) {
        char x = line.charAt(j);
        char m = crossword.get(i - 1).getFirst().charAt(j + 1);
        char a = crossword.get(i - 2).getFirst().charAt(j + 2);
        char s = crossword.get(i - 3).getFirst().charAt(j + 3);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean columnCheckDown(String line, int j, List<List<String>> crossword, int i) {
        char x = line.charAt(j);
        char m = crossword.get(i + 1).getFirst().charAt(j);
        char a = crossword.get(i + 2).getFirst().charAt(j);
        char s = crossword.get(i + 3).getFirst().charAt(j);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean columnCheckUp(String line, int j, List<List<String>> crossword, int i) {
        char x = line.charAt(j);
        char m = crossword.get(i - 1).getFirst().charAt(j);
        char a = crossword.get(i - 2).getFirst().charAt(j);
        char s = crossword.get(i - 3).getFirst().charAt(j);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean rowCheck(String line, int j) {
        char x = line.charAt(j);
        char m = line.charAt(j + 1);
        char a = line.charAt(j + 2);
        char s = line.charAt(j + 3);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }

    private static boolean rowCheckReverse(String line, int j) {
        char x = line.charAt(j);
        char m = line.charAt(j - 1);
        char a = line.charAt(j - 2);
        char s = line.charAt(j - 3);
        String xmas = "" + x + m + a + s;
        return xmas.equals("XMAS");
    }
}
