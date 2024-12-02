package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day1.txt");
        Scanner scanner = new Scanner(inputFile);
//        part1(scanner, leftCodes, rightCodes);
        List<Integer> leftCodes = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] split = line.split(";");
            leftCodes.add(Integer.parseInt(split[0]));
            int key = Integer.parseInt(split[1]);
            int occurence = map.getOrDefault(key,0);
            occurence += 1;
            map.put(key,occurence);
        }
        int sum = 0;
        for (Integer leftCode : leftCodes) {
            sum += leftCode * map.getOrDefault(leftCode, 0);
        }
        System.out.println(sum);
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey()+" "+ entry.getValue());
//        }




    }

    private static void part1(Scanner scanner) {
        List<Integer> leftCodes = new ArrayList<>();
        List<Integer> rightCodes = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] split = line.split(";");
            leftCodes.add(Integer.parseInt(split[0]));
            rightCodes.add(Integer.parseInt(split[1]));
        }
        Collections.sort(leftCodes);
        Collections.sort(rightCodes);
        int sum = 0;
        for (int i = 0; i < leftCodes.size(); i++) {
            sum+=Math.abs(rightCodes.get(i) - leftCodes.get(i));
        }
        System.out.println(sum);
    }
}
