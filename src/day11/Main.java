package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day11.txt");
        Scanner scanner = new Scanner(inputFile);
        String stoneLine = scanner.nextLine();
        List<Long> stones = new ArrayList<>(Arrays.stream(stoneLine.split(" "))
                .mapToLong(Long::parseLong).boxed().toList());

        Map<Long, List<Long>> transformationMapAll = new HashMap<>();
        Map<Long, Long> transformationCount = new HashMap<>();

        for (int i = 0; i < 25; i++) {
            transformationCount = new HashMap<>();
            for (Long stone : stones) {
                transform(stone, transformationMapAll, transformationCount);
            }
            stones = new ArrayList<>();
            for (Map.Entry<Long, List<Long>> tr : transformationMapAll.entrySet()) {
                if (transformationCount.containsKey(tr.getKey())) {
                    for (int j = 0; j < transformationCount.get(tr.getKey()); j++) {
                        stones.addAll(transformationMapAll.get(tr.getKey()));
                    }
                }
            }
//            System.out.println(stones);

        }

        long sum = 0;
        for (Map.Entry<Long, Long> tr : transformationCount.entrySet()) {
//            System.out.println(tr);
            sum += tr.getValue() * transformationMapAll.get(tr.getKey()).size();
        }
        System.out.println(sum);
        System.out.println("---");
        for (Map.Entry<Long, List<Long>> tr : transformationMapAll.entrySet()) {
//            System.out.println(tr);
        }

//        System.out.println(stones.size());
        //        part1(stones);
    }

    private static void transform(long stone, Map<Long, List<Long>> transformationMap,
                                  Map<Long, Long> transformationCount) {

        if (transformationMap.containsKey(stone)) {
            long old = transformationCount.getOrDefault(stone, 0L);
            transformationCount.put(stone, old + 1);
        } else {
            if (stone == 0) {
                transformationMap.put(stone, Collections.singletonList(1L));
                transformationCount.put(stone, 1L);
            } else if (((long) (Math.log10(stone) + 1)) % 2 == 0) {
                long length = (long) (Math.log10(stone) + 1);
                long left = (long) (stone / Math.pow(10, length / 2));
                long right = (long) (stone % Math.pow(10, length / 2));
                List<Long> list = new ArrayList<>();
                list.add(left);
                list.add(right);
                transformationMap.put(stone, list);
                transformationCount.put(stone, 1L);
            } else {
                transformationMap.put(stone, Collections.singletonList(stone * 2024));
                transformationCount.put(stone, 1L);
            }
        }

    }
//    private static void rec1(long stone, Map<Long, List<Long>> transformationMap,
//                            Map<Long, Long> transformationCount, int depth) {
//        if (depth == 0)
//            return;
//        if (transformationMap.containsKey(stone)) {
//            long old = transformationCount.getOrDefault(stone,0L);
//            transformationCount.put(stone, old +1);
//        }
//        else {
//            if (stone == 0) {
//                transformationMap.put(stone, Collections.singletonList(1L));
//                rec1(1, transformationMap, transformationCount, depth - 1);
//                transformationCount.put(stone, 1L);
//            }
//            else if ((""+stone).length() % 2 == 0) {
//                long length = (long) (Math.log10(stone) +1);
//                long left = (long) (stone / Math.pow(10,length/2));
//                long right = (long) (stone % Math.pow(10,length/2));
//                List<Long> list = new ArrayList<>();
//                list.add(left);
//                list.add(right);
//                transformationMap.put(stone, list);
//                rec1(list.getFirst(),transformationMap,transformationCount,depth-1);
//                rec1(list.getLast(),transformationMap,transformationCount,depth-1);
//                transformationCount.put(stone, 1L);
//            }
//            else {
//                transformationMap.put(stone, Collections.singletonList(stone * 2024));
//                rec1(transformationMap.get(stone).getFirst(), transformationMap,
//                        transformationCount, depth - 1);
//                transformationCount.put(stone, 1L);
//            }
//        }
//
//    }


//    private static void blink(List<String> stones, Map<String, List<String>> transformationMap) {
//        for (int i = 0; i < stones.size(); i++) {
//            String stone = stones.get(i);
//            if (stone.equals("0")) {
//                if (!transformationMap.containsKey(stone)) {
//                    transformationMap.put(stone, "1".lines().toList());
//                }
//                stones.set(i,transformationMap.get(stone).getFirst());
//            }
//            else if (stone.length() % 2 == 0) {
//                if (!transformationMap.containsKey(stone)) {
//                    String left = stone.substring(0, stone.length() / 2);
//                    String right = stone.substring(stone.length() / 2).replaceFirst("^0+(?!$)", "");
//                    List<String> list = new ArrayList<>();
//                    list.add(left);
//                    list.add(right);
//                    transformationMap.put(stone, list);
//                }
//                List<String> list = transformationMap.get(stone);
//                stones.set(i, list.getFirst());
//                stones.add(i + 1, list.getLast());
//
//                i++;
//            }
//            else {
//                if (!transformationMap.containsKey(stone)) {
//                    transformationMap.put(stone, ("" + Long.parseLong(stone) * 2024).lines().toList());
//                }
//                stones.set(i,transformationMap.get(stone).getFirst());
//
//            }
//        }
//    }
//private static void part1(List<String> stones) {
//    for (int i = 0; i < 2; i++) {
//        blink(stones, transformationMap);
//        System.out.println(stones);
//    }
//}
}
