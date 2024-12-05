package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day5.txt");
        Scanner scanner = new Scanner(inputFile);
        Map<String, Set<String>> map = new HashMap<>();
        int sum = 0;
        int sum2= 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // order reading
            if (line.contains("|")) {
                String[] split = line.split("\\|");
                map.putIfAbsent(split[0], new HashSet<>());
                Set<String> set = map.get(split[0]);
                set.add(split[1]);
            }
            else {
                if (line.isBlank())
                    continue;
                String[] split = line.split(",");
                if (isLegalOrder(split, map)){
                    sum += Integer.parseInt(split[split.length/2]);
                }
                else {
                    Arrays.sort(split,(o1, o2) -> {
                        if (map.get(o1) != null && map.get(o1).contains(o2)) {
                            return -1;
                        }
                        if (map.get(o2) != null && map.get(o2).contains(o1))
                            return 1;
                        return 0;
                    });
                    sum2+= Integer.parseInt(split[split.length/2]);
                }
            }
        }
        System.out.println(sum2);
    }

    private static boolean isLegalOrder(String[] split, Map<String, Set<String>> map) {
        Set<String> visited = new HashSet<>();
        for (String num : split) {
            visited.add(num);
            if (isIllegalSuccessor(map, num, visited))
                return false;
        }
        return true;
    }

    private static boolean isIllegalSuccessor(Map<String, Set<String>> map, String num, Set<String> visited) {
        Set<String> successor = map.get(num);
        return successor != null && visited.stream().anyMatch(successor::contains);

    }
}
