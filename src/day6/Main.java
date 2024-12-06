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
        Guard oldGuard = new Guard();
        // get guard position
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).length; j++) {
                if (field.get(i)[j] == '^') {
                    guard.x = j;
                    guard.y = i;
                    oldGuard.y = i;
                    oldGuard.x = j;
                }
            }
        }
        boolean[][] visited = new boolean[field.size()][field.size()];
        char[][] visitedCopy = new char[field.size()][field.size()];
        boolean turned = false;
        int count = 0;
        List<Guard> newPositions = new ArrayList<>();
        while (true) {
            oldGuard.y = guard.y;
            oldGuard.x = guard.x;
            oldGuard.moveDirection = guard.moveDirection;
            guard.move();
            if (guard.x < 0 || guard.x >= field.getFirst().length ||
                    guard.y < 0 || guard.y >= field.size()) {
                if (visited[oldGuard.y][oldGuard.x]) {
                    field.get(oldGuard.y)[oldGuard.x] = '+';
                } else {
                    field.get(oldGuard.y)[oldGuard.x] = guard.getMoveChar();
                }
                drawField(field);
                break;
            }
            if (field.get(guard.y)[guard.x] == '#') {
                guard.x = oldGuard.x;
                guard.y = oldGuard.y;
                guard.changeDirection();
                turned = true;
            } else {
                if (visited[oldGuard.y][oldGuard.x]) {
                    field.get(oldGuard.y)[oldGuard.x] = '+';
                } else {
                    field.get(oldGuard.y)[oldGuard.x] = guard.getMoveChar();
//                field.get(oldGuard.y)[oldGuard.x] = 'X';
                }
                turned = false;
            }
            if (!turned) {
                field.get(guard.y)[guard.x] = guard.lookCorrectSide();
                Guard copy = new Guard(oldGuard.x, oldGuard.y, oldGuard.moveDirection);
                Guard oldGuardCopy = new Guard(oldGuard.x, oldGuard.y, oldGuard.moveDirection);
                copy.changeDirection();
                copy.move();
                while (copy.x != oldGuard.x || copy.y != oldGuard.y) {
                    if (copy.x < 0 || copy.x >= field.getFirst().length ||
                            copy.y < 0 || copy.y >= field.size())
                        break;
                    if (visitedCopy[copy.y][copy.x] == '+')
                        break;

                    if (visitedCopy[copy.y][copy.x] == '.')
                        visitedCopy[copy.y][copy.x] = copy.getMoveChar();
                    else if (visitedCopy[copy.y][copy.x] == 'r' &&
                            (copy.moveDirection.equals("down") || copy.moveDirection.equals("up")))
                        visitedCopy[copy.y][copy.x] = '+';
                    else if (visitedCopy[copy.y][copy.x] == 'l' &&
                            (copy.moveDirection.equals("down") || copy.moveDirection.equals("up")))
                        visitedCopy[copy.y][copy.x] = '+';
                    else if (visitedCopy[copy.y][copy.x] == 'u' &&
                            (copy.moveDirection.equals("right") || copy.moveDirection.equals("left")))
                        visitedCopy[copy.y][copy.x] = '+';
                    else if (visitedCopy[copy.y][copy.x] == 'd' &&
                            (copy.moveDirection.equals("right") || copy.moveDirection.equals("left")))
                        visitedCopy[copy.y][copy.x] = '+';
                    else
                        break;


                    if (field.get(copy.y)[copy.x] == '#') {
                        copy.y = oldGuardCopy.y;
                        copy.x = oldGuardCopy.x;
                        copy.changeDirection();
                    }
                    else {
                        oldGuardCopy.y = copy.y;
                        oldGuardCopy.x = copy.x;
                        oldGuardCopy.moveDirection = copy.moveDirection;
                    }
                    copy.move();

                }
                for (char[] chars : visitedCopy) {
                    Arrays.fill(chars, '.');
                }
                if (copy.x == oldGuard.x && copy.y == oldGuard.y) {
                    Guard newLocation = new Guard(guard.x, guard.y, guard.moveDirection);
                    newPositions.add(newLocation);
                    count++;

                }

            }
            visited[oldGuard.y][oldGuard.x] = true;

//            drawField(field);

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
        drawField(field);
        System.out.println(count);

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
        public char getShortMoveChar(){
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


