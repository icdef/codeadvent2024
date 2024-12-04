package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainPart2 {

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
        for (int i = 1; i < crossword.size()-1; i++) {
            String line = crossword.get(i).getFirst();
            for (int j = 1; j < line.length()-1; j++) {
                if (line.charAt(j) == 'A') {
                    if ((diagCheckLeftRightDown(line, j, crossword, i) || diagCheckRightLeftUp(line, j, crossword, i))
                    && (diagCheckRightLeftDown(line,j,crossword,i) || diagCheckLeftRightUp(line,j,crossword,i))) {
                        sum++;
                    }
                }
            }
        }
        System.out.println(sum);

    }
    private static boolean diagCheckLeftRightDown(String line, int j, List<List<String>> crossword, int i) {
        char m = crossword.get(i - 1).getFirst().charAt(j - 1);
        char a = line.charAt(j);
        char s = crossword.get(i + 1).getFirst().charAt(j + 1);
        String mas = "" + m + a + s;
        return mas.equals("MAS");
    }
    private static boolean diagCheckRightLeftUp(String line, int j, List<List<String>> crossword, int i) {
        char m = crossword.get(i + 1).getFirst().charAt(j + 1);
        char a = line.charAt(j);
        char s = crossword.get(i - 1).getFirst().charAt(j - 1);
        String mas = "" + m + a + s;
        return mas.equals("MAS");
    }
    private static boolean diagCheckRightLeftDown(String line, int j, List<List<String>> crossword, int i) {
        char m = crossword.get(i - 1).getFirst().charAt(j + 1);
        char a = line.charAt(j);
        char s = crossword.get(i + 1).getFirst().charAt(j - 1);
        String mas = "" + m + a + s;
        return mas.equals("MAS");
    }
    private static boolean diagCheckLeftRightUp(String line, int j, List<List<String>> crossword, int i) {
        char m = crossword.get(i + 1).getFirst().charAt(j - 1);
        char a = line.charAt(j);
        char s = crossword.get(i - 1).getFirst().charAt(j + 1);
        String mas = "" + m + a + s;
        return mas.equals("MAS");
    }
}
