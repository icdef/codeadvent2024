package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    /* possible speed up using memoization
        For each block size I have to add, the index of the last place I added such a block size or smaller can be used
        as starting point.
        e.g. I need to add block size 18 and I added a block with size 17 at index 52.
        There is no reason for me to start looking from index 0, since I know that the last time the first free place
        for a even smaller block size was at index 52. So I look in my memoization array get the end index of block 17
        and use it as start index for my look-up for the size 18 block. Works here since we use fit first and not
        fit-best
     */

    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input-data/day9.txt");
        Scanner scanner = new Scanner(inputFile);
        String diskMap = "";
        while (scanner.hasNextLine()) {
            diskMap = scanner.nextLine();
        }
        List<DiskBlock> blocks = new ArrayList<>();
        String s = "";
        int id = 0;
        for (int i = 0; i < diskMap.length(); i++) {
            for (int j = 0; j < (diskMap.charAt(i) - '0'); j++) {
                if (i % 2 == 0)
                    blocks.add(new DiskBlock(id));
                else
                    blocks.add(null);
            }
            if (i % 2 == 0)
                id++;
        }
//        for (DiskFile diskFile: blocks){
//            System.out.println(diskFile);
//        }
        System.out.println("------------------");
        List<List<DiskBlock>> diskBlocks = getListsOfDiskBlocks(blocks);
        for (List<DiskBlock> list : diskBlocks) {
//            System.out.println(list);
            int space = 0;
            int startIndex = 0;
            int endIndex = 0;
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.get(i) == null) {
                    startIndex = i;
                    endIndex = i;
                    while (endIndex < blocks.size() && blocks.get(endIndex) == null) {
                        endIndex++;
                        space++;
                    }
                }
                if (space >= list.size()) {
                    break;
                } else {
                    space = 0;
                }
            }
            if (space >= list.size()) {
                for (int i = startIndex; i < startIndex + list.size(); i++) {
                    blocks.set(i, list.getFirst());
                    blocks.set(blocks.lastIndexOf(list.getFirst()), null);
                }
//                for (DiskBlock diskBlock: blocks) {
//                    if (diskBlock != null)
//                        System.out.print(diskBlock.id);
//                    else
//                        System.out.print(".");
//                }
//                System.out.println();
            }
        }
//        for (DiskBlock diskBlock : blocks) {
//            System.out.println(diskBlock);
//        }


        long sum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            DiskBlock diskBlock = blocks.get(i);
            if (diskBlock != null)
                sum += (long) i * diskBlock.id;
        }
        System.out.println(sum);

    }

    private static List<List<DiskBlock>> getListsOfDiskBlocks(List<DiskBlock> blocks) {
        List<List<DiskBlock>> fileBlocks = new ArrayList<>();
//        part1(blocks, last);
        for (int i = blocks.size() - 1; i > 0; i--) {
            List<DiskBlock> tmp = new ArrayList<>();
            DiskBlock currentBlock = blocks.get(i);
            if (currentBlock != null) {
                tmp.add(currentBlock);
                while (i > 0 && Objects.equals(blocks.get(i - 1), (currentBlock))) {
                    tmp.add(blocks.get(i - 1));
                    i--;
                }
                fileBlocks.add(tmp);
            }

        }
        return fileBlocks;
    }

    private static void part1(List<DiskBlock> blocks, int last) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) == null) {
                while (last > i) {
                    if (blocks.get(last) != null) {
                        blocks.set(i, blocks.get(last));
                        blocks.set(last, null);
                        break;
                    } else {
                        last--;
                    }
                }
            }
        }
    }

    private record DiskBlock(int id) {
    }
}
