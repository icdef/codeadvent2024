package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day6.txt");
        Scanner scanner = new Scanner(inputFile);
        List<char[]> field = new ArrayList<>();
        while (scanner.hasNextLine()) {
            field.add(scanner.nextLine().toCharArray());
        }
        Guard guard = new Guard();
        // get guard position
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length; j++) {
                if (field.get(i)[j] == '^') {
                    guard.x = j;
                    guard.y = i;
                    Guard.oldPos.y = i;
                    Guard.oldPos.x = j;
                }
            }
        }
        boolean[][] visited = new boolean[field.size()][field.size()];
        boolean turned = false;
        List<Guard> newPositions = new ArrayList<>();
        while (true) {
            guard.move(true);
            if (guard.x < 0 || guard.x >= field.getFirst().length ||
                    guard.y < 0 || guard.y >= field.size()) {
                if (visited[Guard.oldPos.y][Guard.oldPos.x]) {
                    field.get(Guard.oldPos.y)[Guard.oldPos.x] = '+';
                } else {
                    field.get(Guard.oldPos.y)[Guard.oldPos.x] = guard.getMoveChar();
                }
                drawField(field);
                break;
            }
            if (field.get(guard.y)[guard.x] == '#') {
                guard.resetMove();
                guard.changeDirection();
                turned = true;
            } else {
                if (visited[Guard.oldPos.y][Guard.oldPos.x]) {
                    field.get(Guard.oldPos.y)[Guard.oldPos.x] = '+';
                } else {
                    field.get(Guard.oldPos.y)[Guard.oldPos.x] = guard.getMoveChar();
//                field.get(Guard.oldPos.y)[Guard.oldPos.x] = 'X';
                }
                turned = false;
            }
            if (!turned) {
                field.get(guard.y)[guard.x] = guard.lookCorrectSide();
                Guard copy = new Guard(guard.x, guard.y, guard.moveDirection);
                copy.changeDirection();
                copy.move(false);
                if (copy.checkSides(field)) {
                    Guard newLocation = new Guard(guard.x, guard.y, guard.moveDirection);
                    newLocation.move(false);
                    newPositions.add(newLocation);
                }

            }
            visited[Guard.oldPos.y][Guard.oldPos.x] = true;

            drawField(field);

        }

//        for (char[] chars: field) {
//            for (char c: chars) {
//                if (c == 'X')
//                    count++;
//            }
//        }
        for (Guard g : newPositions) {
            field.get(g.y)[g.x] = 'O';
        }
//        drawField(field);
        System.out.println(newPositions.size());

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
        private static final Guard oldPos = new Guard();

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

        public void move(boolean moveOldPos) {
            if (this.moveDirection.equals("up")) {
                if (moveOldPos)
                    copyPosToOldPos();
                y -= 1;
            }
            if (this.moveDirection.equals("right")) {
                if (moveOldPos)
                    copyPosToOldPos();
                x += 1;
            }
            if (this.moveDirection.equals("down")) {
                if (moveOldPos)
                    copyPosToOldPos();
                y += 1;
            }
            if (this.moveDirection.equals("left")) {
                if (moveOldPos)
                    copyPosToOldPos();
                x -= 1;
            }
        }

        private void copyPosToOldPos() {
            oldPos.x = this.x;
            oldPos.y = this.y;
        }

        public void resetMove() {
            this.x = oldPos.x;
            this.y = oldPos.y;
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


