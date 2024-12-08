package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day8.txt");
        Scanner scanner = new Scanner(inputFile);
        List<String> field = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine());
        }
        Map<Character, List<Coord>> map = new HashMap<>();
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length(); j++) {
                char c = field.get(i).charAt(j);
                if (c != '.') {
                    map.computeIfAbsent(c, k -> new ArrayList<>());
                    List<Coord> coords = map.get(c);
                    coords.add(new Coord(j, i));

                }
            }
        }

        for (Map.Entry<Character, List<Coord>> entry : map.entrySet()) {
            List<Coord> coords = entry.getValue();
            for (int i = 0; i < coords.size(); i++) {
                Coord left = coords.get(i);
                System.out.println("For each: " + left);
                for (int j = i + 1; j < coords.size(); j++) {
                    Coord right = coords.get(j);
                    System.out.println("Combine with : " + right);
                }

            }
        }


        for (Map.Entry<Character, List<Coord>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(System.out::println);
        }

    }


    private record Coord(int x, int y) {
    }
}
