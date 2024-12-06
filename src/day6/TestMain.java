package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestMain {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day6.txt");
        Scanner scanner = new Scanner(inputFile);
        List<char[]> field = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine().toCharArray());
        }
        Guard guard = new Guard();
        Guard oldGuard = new Guard();
        Guard startGuard = new Guard();
        // get guard position
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length; j++) {
                if (field.get(i)[j] == '^') {
                    guard.x = j;
                    guard.y = i;
                    oldGuard.y = i;
                    oldGuard.x = j;
                    startGuard.x = j;
                    startGuard.y = i;
                }

            }

        }

        boolean[][] visited = new boolean[field.size()][];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = new boolean[field.get(i).length];
        }

        boolean turned;


        while (true) {
            oldGuard.y = guard.y;
            oldGuard.x = guard.x;
            oldGuard.moveDirection = guard.moveDirection;
            guard.move();
            if (guard.x < 0 || guard.x >= field.getFirst().length ||
                    guard.y < 0 || guard.y >= field.size()) {
                break;
            }
            if (field.get(guard.y)[guard.x] == '#') {
                guard.x = oldGuard.x;
                guard.y = oldGuard.y;
                guard.changeDirection();
                turned = true;
            } else {
                field.get(oldGuard.y)[oldGuard.x] = 'X';
                turned = false;
            }
            if (!turned) {
                field.get(oldGuard.y)[oldGuard.x] = 'X';

            }
            visited[oldGuard.y][oldGuard.x] = true;


        }
        visited[oldGuard.y][oldGuard.x] = true;
        field.get(oldGuard.y)[oldGuard.x] = 'X';
//        drawField(field);

        scanner = new Scanner(inputFile);
        field = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine().toCharArray());
        }
        guard = new Guard();
        oldGuard = new Guard();
        // get guard position
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length; j++) {
                if (field.get(i)[j] == '^') {
                    guard.x = j;
                    guard.y = i;
                    oldGuard.y = i;
                    oldGuard.x = j;
                    startGuard.x = j;
                    startGuard.y = i;
                }
            }
        }
        long maxSteps = (long) field.size() * field.size();
        List<Guard> blockPositions = new ArrayList<>();
        List<Guard> blockSol = new ArrayList<>();
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (visited[i][j])
                    blockPositions.add(new Guard(j, i, "up"));
            }
        }
        for (Guard block : blockPositions) {
            scanner = new Scanner(inputFile);
            field = new ArrayList<>();
            while (scanner.hasNextLine()) {
                field.add(scanner.nextLine().toCharArray());
            }
            field.get(block.y)[block.x] = 'O';
//            System.out.println(block);
//            drawField(field);
            int steps = 0;
            int turning = 0;
            while (steps < maxSteps) {
                oldGuard.y = guard.y;
                oldGuard.x = guard.x;
                oldGuard.moveDirection = guard.moveDirection;
                guard.move();
                if (guard.x < 0 || guard.x >= field.getFirst().length ||
                        guard.y < 0 || guard.y >= field.size() || turning == 5) {
                    field.get(oldGuard.y)[oldGuard.x] = 'X';
                    field.get(startGuard.y)[startGuard.x] = '^';
                    break;
                }
                if (field.get(guard.y)[guard.x] == '#' || field.get(guard.y)[guard.x] == 'O') {
                    guard.x = oldGuard.x;
                    guard.y = oldGuard.y;
                    guard.changeDirection();
                    turning++;
                } else {
                    field.get(oldGuard.y)[oldGuard.x] = 'X';
                    steps++;
                    turning--;

                }
                visited[oldGuard.y][oldGuard.x] = true;

            }
            if (steps >= maxSteps || turning >= 4) {
                if (!(block.x == startGuard.x && block.y == startGuard.y)) {
                    Guard blockPosition = new Guard(block.x, block.y, block.moveDirection);
                    blockSol.add(blockPosition);
                }
            }
            guard.x = startGuard.x;
            guard.y = startGuard.y;
            guard.moveDirection = "up";
            oldGuard.y = startGuard.y;
            oldGuard.x = startGuard.x;
            oldGuard.moveDirection = "up";
//            drawField(field);
        }


        int count2 = 0;
        for (char[] chars : field) {
            for (char c : chars)
                if (c == '#')
                    count2++;
        }
        field.get(startGuard.y)[startGuard.x] = '^';
        for (Guard g : blockSol) {
            field.get(g.y)[g.x] = 'O';
        }
//        drawField(field);

        System.out.println(count2);
        System.out.println(new HashSet<>(blockSol).size());
        System.out.println("----------------------");
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
//                System.out.print(visited[i][j]+",");
            }
//            System.out.println();
        }

    }

    private static void drawField(List<char[]> field) {
        for (char[] chars : field) {
            System.out.println(Arrays.toString(chars));
        }
        System.out.println("-------------------------------");
    }

    static class Guard {
        private int x = 0;
        private int y = 0;
        private String moveDirection = "up";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Guard guard = (Guard) o;
            return x == guard.x && y == guard.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Guard(int x, int y, String moveDirection) {
            this.x = x;
            this.y = y;
            this.moveDirection = moveDirection;
        }

        public Guard() {
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }


        public void move() {
            if (this.moveDirection.equals("up")) {
                y -= 1;
            }
            if (this.moveDirection.equals("right")) {
                x += 1;
            }
            if (this.moveDirection.equals("down")) {
                y += 1;
            }
            if (this.moveDirection.equals("left")) {
                x -= 1;
            }
        }

        public void changeDirection() {
            switch (this.moveDirection) {
                case "up":
                    this.moveDirection = "right";
                    break;
                case "right":
                    this.moveDirection = "down";
                    break;
                case "down":
                    this.moveDirection = "left";
                    break;
                case "left":
                    this.moveDirection = "up";
                    break;
            }
        }

        public char getMoveChar() {
            return switch (this.moveDirection) {
                case "up" -> 'u';
                case "right" -> 'r';
                case "down" -> 'd';
                case "left" -> 'l';
                default ->
                    // should not happen
                        'X';
            };
        }

        public char lookCorrectSide() {
            return switch (this.moveDirection) {
                case "up" -> 'r';
                case "right" -> 'd';
                case "down" -> 'l';
                case "left" -> 'u';
                default ->
                    // should not happen
                        'X';
            };
        }

        public char getShortMoveChar() {
            if (this.moveDirection.equals("up") || this.moveDirection.equals("down"))
                return '|';
            if (this.moveDirection.equals("right") || this.moveDirection.equals("left"))
                return '-';
            // should not happen
            return 'X';
        }

        public boolean checkSides(List<char[]> field) {
            if (field.get(this.y)[this.x] == '+')
                return true;
            return switch (this.moveDirection) {
                case "up" -> field.get(this.y)[this.x] == 'u';
                case "down" -> field.get(this.y)[this.x] == 'd';
                case "right" -> field.get(this.y)[this.x] == 'r';
                case "left" -> field.get(this.y)[this.x] == 'l';
                default ->
                    // should not happen
                        field.get(this.y)[this.x] == 'X';
            };
        }
    }
}


