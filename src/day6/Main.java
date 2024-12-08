package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day6.txt");
        Scanner scanner = new Scanner(inputFile);
        List<char[]> field = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine().toCharArray());
        }
        List<char[]> copyField = copyField(field);
        Guard guard = new Guard();
        Guard oldGuard = new Guard();
        Guard startGuard = new Guard();
        // get guard position
        for (int i = 0; i < copyField.size(); i++) {
            for (int j = 0; j < copyField.get(i).length; j++) {
                if (copyField.get(i)[j] == '^') {
                    guard.x = j;
                    guard.y = i;
                    oldGuard.y = i;
                    oldGuard.x = j;
                    startGuard.x = j;
                    startGuard.y = i;
                }

            }

        }

        boolean[][] visited = new boolean[copyField.size()][];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = new boolean[copyField.get(i).length];
        }

        boolean turned;


        while (true) {
            oldGuard.y = guard.y;
            oldGuard.x = guard.x;
            oldGuard.moveDirection = guard.moveDirection;
            guard.move();
            if (guard.x < 0 || guard.x >= copyField.getFirst().length ||
                    guard.y < 0 || guard.y >= copyField.size()) {
                break;
            }
            if (copyField.get(guard.y)[guard.x] == '#') {
                guard.x = oldGuard.x;
                guard.y = oldGuard.y;
                guard.changeDirection();
                turned = true;
            } else {
                copyField.get(oldGuard.y)[oldGuard.x] = 'X';
                turned = false;
            }
            if (!turned) {
                copyField.get(oldGuard.y)[oldGuard.x] = 'X';

            }
            visited[oldGuard.y][oldGuard.x] = true;


        }
        visited[oldGuard.y][oldGuard.x] = true;
        copyField.get(oldGuard.y)[oldGuard.x] = 'X';
//        drawField(copyField);

        copyField = copyField(field);
        guard = new Guard();
        oldGuard = new Guard();
        // get guard position
        for (int i = 0; i < copyField.size(); i++) {
            for (int j = 0; j < copyField.get(i).length; j++) {
                if (copyField.get(i)[j] == '^') {
                    guard.x = j;
                    guard.y = i;
                    oldGuard.y = i;
                    oldGuard.x = j;
                    startGuard.x = j;
                    startGuard.y = i;
                }
            }
        }
        long maxSteps = (long) copyField.size() * copyField.size();
        List<Guard> blockPositions = new ArrayList<>();
        List<Guard> blockSol = new ArrayList<>();
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (visited[i][j])
                    blockPositions.add(new Guard(j, i, "up"));
            }
        }
        for (Guard block : blockPositions) {
            copyField = copyField(field);
            copyField.get(block.y)[block.x] = 'O';
//            System.out.println(block);
//            drawField(copyField);
            int steps = 0;
            int turning = 0;
            while (steps < maxSteps) {
                oldGuard.y = guard.y;
                oldGuard.x = guard.x;
                oldGuard.moveDirection = guard.moveDirection;
                guard.move();
                if (guard.x < 0 || guard.x >= copyField.getFirst().length ||
                        guard.y < 0 || guard.y >= copyField.size() || turning == 5) {
                    copyField.get(oldGuard.y)[oldGuard.x] = 'X';
                    copyField.get(startGuard.y)[startGuard.x] = '^';
                    break;
                }
                if (copyField.get(guard.y)[guard.x] == '#' || copyField.get(guard.y)[guard.x] == 'O') {
                    guard.x = oldGuard.x;
                    guard.y = oldGuard.y;
                    guard.changeDirection();
                    turning++;
                } else {
                    copyField.get(oldGuard.y)[oldGuard.x] = 'X';
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
//            drawField(copyField);
        }


        System.out.println(new HashSet<>(blockSol).size());

    }

    private static List<char[]> copyField(List<char[]> field) {
        List<char[]> copyField = new ArrayList<>();
        for (char[] chars : field) {
            copyField.add(Arrays.copyOf(chars, chars.length));
        }
        return copyField;
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
    }
}


