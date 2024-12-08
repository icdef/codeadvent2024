package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day8.txt");
        Scanner scanner = new Scanner(inputFile);
        List<char[]> field = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine().toCharArray());
        }
        Map<Character, List<Coord>> map = new HashMap<>();
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length; j++) {
                char c = field.get(i)[j];
                if (c != '.') {
                    map.computeIfAbsent(c, k -> new ArrayList<>());
                    List<Coord> coords = map.get(c);
                    coords.add(new Coord(j, i));
                }
            }
        }
        Set<Coord> allAntennaCoords = new HashSet<>();
        for (Map.Entry<Character, List<Coord>> entry : map.entrySet()) {
            allAntennaCoords.addAll(entry.getValue());
        }
        Set<Coord> antiNodes = new HashSet<>();
        for (Map.Entry<Character, List<Coord>> entry : map.entrySet()) {
            List<Coord> coords = entry.getValue();
            for (int i = 0; i < coords.size(); i++) {
                Coord left = coords.get(i);
                for (int j = i + 1; j < coords.size(); j++) {
                    Coord right = coords.get(j);
                    int xDiff = left.x - right.x;
                    int yDiff = left.y - right.y;
                    int x1 = right.x - xDiff;
                    int y1 = right.y - yDiff;
                    while (x1 >= 0 && x1 < field.get(i).length && y1 >= 0 && y1 < field.size()) {
                        Coord e = new Coord(x1, y1);
                        if (!allAntennaCoords.contains(e))
                            antiNodes.add(e);
                        x1 = x1 - xDiff;
                        y1 = y1 - yDiff;
                    }
                    int x2 = left.x + xDiff;
                    int y2 = left.y + yDiff;
                    while (x2 >= 0 && x2 < field.get(i).length && y2 >= 0 && y2 < field.size()) {
                        Coord e = new Coord(x2, y2);
                        if (!allAntennaCoords.contains(e))
                            antiNodes.add(e);
                        x2 = x2 + xDiff;
                        y2 = y2 + yDiff;
                    }

                }
            }
        }

        for (Coord coord : antiNodes) {
            if (field.get(coord.y)[coord.x] == '.')
                field.get(coord.y)[coord.x] = '#';
        }
        drawField(field);
        int count = 0;
        for (Map.Entry<Character, List<Coord>> entry : map.entrySet()) {
            count += entry.getValue().size();
        }
        System.out.println(antiNodes.size() + count);

    }


    private record Coord(int x, int y) {
    }

    private static void drawField(List<char[]> field) {
        for (char[] chars : field) {
            System.out.println(Arrays.toString(chars));
        }
        System.out.println("-------------------------------");
    }
}
