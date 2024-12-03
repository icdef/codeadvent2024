package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day3.txt");
        Scanner scanner = new Scanner(inputFile);
        int sum = 0;
        boolean allowed = true;
        String text = "";
        String cleanedUpText = "";
        while (scanner.hasNextLine()) {
            text += scanner.nextLine();
        }
        while (!text.isEmpty()) {
            int allowIndex = text.indexOf("do()");
            int disallowIndex = text.indexOf("don't()");
            if (allowed && disallowIndex != -1) {
                cleanedUpText += text.substring(0, disallowIndex);
                text = text.substring(disallowIndex + 1);
                allowed = false;
            } else if (!allowed && allowIndex != -1) {
                allowed = true;
                text = text.substring(allowIndex + 1);
            } else if (allowIndex == -1 && disallowIndex == -1) {
                if (allowed) {
                    cleanedUpText += text;
                }
                text = "";
            }
        }
        System.out.println(cleanedUpText);
        while (cleanedUpText.contains("mul(")) {
            int indexStart = cleanedUpText.indexOf("mul(");
            cleanedUpText = cleanedUpText.substring(indexStart + 4);
            int indexEnd = cleanedUpText.indexOf(")");
            String mulString = cleanedUpText.substring(0, indexEnd);
            if (mulString.matches("^\\d{1,3},\\d{1,3}$")) {
                String[] split = mulString.split(",");
                int nr1 = Integer.parseInt(split[0]);
                int nr2 = Integer.parseInt(split[1]);
//                    System.out.println(nr1 + "," + nr2);
                sum += nr1 * nr2;

            }
        }
        System.out.println(sum);
    }
}
