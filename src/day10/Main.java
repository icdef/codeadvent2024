package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day10.txt");
        Scanner scanner = new Scanner(inputFile);
        List<char[]> field = new ArrayList<>();
        int sum = 0;
        List<Coord> zeros = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine().toCharArray());
        }
        for (char[] chars : field) {
            System.out.println(Arrays.toString(chars));
        }
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length; j++) {
                if (field.get(i)[j] == '0')
                    zeros.add(new Coord(j, i));
            }
        }
//        part 1
//        for (Coord coord : zeros) {
//            Set<Coord> nines = new HashSet<>();
//            rec(field, new Coord(coord.x, coord.y), nines);
//            sum += nines.size();
//        }
        for (Coord coord : zeros) {
            sum += rec2(field, new Coord(coord.x, coord.y));
        }
        System.out.println(sum);


    }

    private static void rec(List<char[]> field, Coord currentPos, Set<Coord> nines) {
        char c = field.get(currentPos.y)[currentPos.x];
        if (field.get(currentPos.y)[currentPos.x] == '9') {
            nines.add(new Coord(currentPos.x, currentPos.y));
            return;
        }

        boolean isRightPossible = currentPos.x + 1 < field.get(currentPos.y).length &&
                field.get(currentPos.y)[currentPos.x + 1] - field.get(currentPos.y)[currentPos.x] == 1;
        boolean isLeftPossible = currentPos.x - 1 >= 0 &&
                field.get(currentPos.y)[currentPos.x - 1] - field.get(currentPos.y)[currentPos.x] == 1;
        boolean isUpPossible = currentPos.y + 1 < field.size() &&
                field.get(currentPos.y + 1)[currentPos.x] - field.get(currentPos.y)[currentPos.x] == 1;
        boolean isDownPossible = currentPos.y - 1 >= 0 &&
                field.get(currentPos.y - 1)[currentPos.x] - field.get(currentPos.y)[currentPos.x] == 1;
        if (!isRightPossible && !isLeftPossible && !isDownPossible && !isUpPossible) {
            return;
        }
        // go right
        if (isRightPossible) {
            rec(field, new Coord(currentPos.x + 1, currentPos.y), nines);
        }
        // go left
        if (isLeftPossible) {
            rec(field, new Coord(currentPos.x - 1, currentPos.y), nines);
        }
        // go down
        if (isUpPossible) {
            rec(field, new Coord(currentPos.x, currentPos.y + 1), nines);
        }
        // go up
        if (isDownPossible) {
            rec(field, new Coord(currentPos.x, currentPos.y - 1), nines);
        }
    }

    private static int rec2(List<char[]> field, Coord currentPos) {
        char c = field.get(currentPos.y)[currentPos.x];
        int right = 0;
        int left = 0;
        int down = 0;
        int up = 0;
        if (field.get(currentPos.y)[currentPos.x] == '9') {
            return 1;
        }

        boolean isRightPossible = currentPos.x + 1 < field.get(currentPos.y).length &&
                field.get(currentPos.y)[currentPos.x + 1] - field.get(currentPos.y)[currentPos.x] == 1;
        boolean isLeftPossible = currentPos.x - 1 >= 0 &&
                field.get(currentPos.y)[currentPos.x - 1] - field.get(currentPos.y)[currentPos.x] == 1;
        boolean isUpPossible = currentPos.y + 1 < field.size() &&
                field.get(currentPos.y + 1)[currentPos.x] - field.get(currentPos.y)[currentPos.x] == 1;
        boolean isDownPossible = currentPos.y - 1 >= 0 &&
                field.get(currentPos.y - 1)[currentPos.x] - field.get(currentPos.y)[currentPos.x] == 1;
        if (!isRightPossible && !isLeftPossible && !isDownPossible && !isUpPossible) {
            return 0;
        }
        // go right
        if (isRightPossible) {
            right = rec2(field, new Coord(currentPos.x + 1, currentPos.y));
        }
        // go left
        if (isLeftPossible) {
            left = rec2(field, new Coord(currentPos.x - 1, currentPos.y));
        }
        // go down
        if (isUpPossible) {
            down = rec2(field, new Coord(currentPos.x, currentPos.y + 1));
        }
        // go up
        if (isDownPossible) {
            up = rec2(field, new Coord(currentPos.x, currentPos.y - 1));
        }
        return left + right + down + up;
    }


    private record Coord(int x, int y) {
    }
}
