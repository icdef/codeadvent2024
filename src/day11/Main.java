package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private record CustomKey(long stone, int blink) {
    }

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day11.txt");
        Scanner scanner = new Scanner(inputFile);
        String stoneLine = scanner.nextLine();
        List<Long> stones = new ArrayList<>(Arrays.stream(stoneLine.split(" "))
                .mapToLong(Long::parseLong).boxed().toList());

        Map<Long, List<Long>> transformationMapAll = new HashMap<>();
        Map<Long, Long> transformationCount = new HashMap<>();
        Map<CustomKey, Long> stoneToAmount = new HashMap<>();
        for (Long stone : stones) {
            tt(stone, 75, stoneToAmount);
        }
        long sum2 = 0;
        for (Long stone : stones) {
            sum2 += stoneToAmount.get(new CustomKey(stone, 75));
        }
        System.out.println(sum2);



        long sum = 0;
//        for (Map.Entry<Long, Long> tr : transformationCount.entrySet()) {
////            System.out.println(tr);
//            sum += tr.getValue() * transformationMapAll.get(tr.getKey()).size();
//        }
        System.out.println(sum);

    }

    private static long tt(long stone, int blink, Map<CustomKey, Long> stoneToAmount) {
        if (blink == 0)
            return 1;
        if (stoneToAmount.containsKey(new CustomKey(stone, blink))) {
            return stoneToAmount.get(new CustomKey(stone, blink));
        }
        if (stone == 0) {
            long x = tt(1, blink - 1, stoneToAmount);
            stoneToAmount.put(new CustomKey(stone, blink), x);
            return x;
        } else if (((long) (Math.log10(stone) + 1)) % 2 == 0) {
            long length = (long) (Math.log10(stone) + 1);
            long left = (long) (stone / Math.pow(10, length / 2));
            long right = (long) (stone % Math.pow(10, length / 2));
            long x = tt(left, blink - 1, stoneToAmount);
            long y = tt(right, blink - 1, stoneToAmount);
            stoneToAmount.put(new CustomKey(stone, blink), x + y);
            return x + y;
        } else {
            long x = tt(stone * 2024, blink - 1, stoneToAmount);
            stoneToAmount.put(new CustomKey(stone, blink), x);
            return x;
        }
    }
}
