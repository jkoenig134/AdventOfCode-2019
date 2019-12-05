import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day03 {

    /*


                ATTENTION
                This code is the literal definition of shitcode. I have no interest in improving this because it's just all over the place.
                Don't write code like this. And don't use this, it might explode.


    */

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid arguments!");
            return;
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.err.println("Given input file does not exist!");
            return;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return;
		}
        if(lines.size() != 2) {
            System.err.println("Only two lines are allowed!");
            return;
        }

        String input1 = /*"R75,D30,R83,U83,L12,D49,R71,U7,L72,U62,R66,U55,R34,D71,R55,D58,R83";*/ lines.get(0);
        String input2 = /*"R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51,U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";*/ lines.get(1);

        partOne(input1, input2);
        if(true) return;

        Pair<Integer, Integer> central = new Pair<Integer,Integer>(0, 0);

        List<Pair<Integer, Integer>> intersections = new ArrayList<>();

        int x1 = 0;
        int y1 = 0;
        for (String s1 : input1.split(",")) {
            switch (s1.charAt(0)) {
                case 'U':
                    for(int i = 0; i < Integer.parseInt(s1.substring(1)); i++) {
                        y1++;
                        check(x1, y1, input2, intersections);
                    }
                    break;
                case 'D':
                    for(int i = 0; i < Integer.parseInt(s1.substring(1)); i++) {
                        y1--;
                        check(x1, y1, input2, intersections);
                    }
                    break;
                case 'L':
                    for(int i = 0; i < Integer.parseInt(s1.substring(1)); i++) {
                        x1--;
                        check(x1, y1, input2, intersections);
                    }
                    break;
                case 'R':
                    for(int i = 0; i < Integer.parseInt(s1.substring(1)); i++) {
                        x1++;
                        check(x1, y1, input2, intersections);
                    }
                    break;
            }           
        }

        Pair<Integer, Integer> currentBest = new Pair<Integer,Integer>(Integer.MAX_VALUE, Integer.MAX_VALUE);
        int currentBestDist = Integer.MAX_VALUE;

        for(Pair<Integer, Integer> pair : intersections) {
            int dist = Math.abs(pair.left-central.left) + Math.abs(pair.right-central.right);
            if(dist < currentBestDist) {
                currentBestDist = dist;
                currentBest = pair;
            }
        }

        System.out.println("wtf?");
        System.out.println(currentBestDist+" "+currentBest.left+" & "+currentBest.right);
    }

    private static void partOne(String input1, String input2) {
        List<Pair<Integer, Integer>> points = new ArrayList<>();

        Thread thread = new Thread(() -> {
            int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;

        int currentLowestSum = -1;
        int currentSteps = 0;

        for(String rawPoint1 : input1.split(",")) {
            int a1 = Integer.parseInt(rawPoint1.substring(1));
            Pair<Integer, Integer> pair = null;
            switch (rawPoint1.charAt(0)) {
                case 'U':
                    for(int n = y1; n < a1+y1; n++) {
                        pair = new Pair<Integer,Integer>(x1, n);
                        loop2(input2, pair, points, x2, y2, currentLowestSum, currentSteps);
                    }
                    y1+=a1;
                    break;
                case 'D':
                    for(int n = y1; n > y1-a1; n--) {
                        pair = new Pair<Integer,Integer>(x1, n);
                        loop2(input2, pair, points, x2, y2, currentLowestSum, currentSteps);
                    }
                    y1-=a1;
                    break;
                case 'R':
                    for(int n = x1; n < x1+a1; n++) {
                        pair = new Pair<Integer,Integer>(n, y1);
                        loop2(input2, pair, points, x2, y2, currentLowestSum, currentSteps);
                    }
                    x1+=a1;
                    break;
                case 'L':
                    for(int n = x1; n > x1-a1; n--) {
                        pair = new Pair<Integer,Integer>(n, y1);
                        loop2(input2, pair, points, x2, y2, currentLowestSum, currentSteps);
                    }
                    x1-=a1;
                    break;
            }
        }
        System.out.println();
        System.out.println("Done, found "+points.size()+" intersections");
        System.out.println("Calculating nearest...");

        Pair<Integer, Integer> central = new Pair<>(0, 0);
        int nearest = -1;
        for(Pair<Integer, Integer> pair : points) {
            if(pair.equals(central)) continue;

            int dist = Math.abs(pair.left-central.left) + Math.abs(pair.right-central.right);
            if(dist < nearest || nearest == -1) {
                nearest = dist;
            }
        }
        System.out.println("Nearest: "+nearest);
        });
        System.out.println("Starting calculation....");
        thread.start();
    }

    private static void loop2(String input2, Pair<Integer, Integer> pair, List<Pair<Integer, Integer>> points, Integer x2, int y2, int currentLowestSum, int currentSteps) {
        currentSteps++;

        for(String rawPoint2 : input2.split(",")) {
                int a2 = Integer.parseInt(rawPoint2.substring(1));
                Pair<Integer, Integer> pair_ = null;
                switch (rawPoint2.charAt(0)) {
                case 'U':
                    for(int n = y2; n < a2+y2; n++) {
                        pair_ = new Pair<Integer,Integer>(x2, n);
                        currentSteps++;

                        if(pair != null && pair_ != null) {
                            if(pair.equals(pair_)) {
                                points.add(pair);
                                System.out.print("+");
                            }
                        }
                    }
                    y2+=a2;
                    break;
                case 'D':
                    for(int n = y2; n > y2-a2; n--) {
                        pair_ = new Pair<Integer,Integer>(x2, n);
                        currentSteps++;

                        if(pair != null && pair_ != null) {
                            if(pair.equals(pair_)) {
                                points.add(pair);
                                System.out.print("+");
                            }
                        }
                    }
                    y2-=a2;
                    break;
                case 'R':
                    for(int n = x2; n < x2+a2; n++) {
                        pair_ = new Pair<Integer,Integer>(n, y2);
                        currentSteps++;

                        if(pair != null && pair_ != null) {
                            if(pair.equals(pair_)) {
                                points.add(pair);
                                System.out.print("+");
                            }
                        }
                    }
                    x2+=a2;
                    break;
                case 'L':
                    for(int n = x2; n > x2-a2; n--) {
                        pair_ = new Pair<Integer,Integer>(n, y2);
                        currentSteps++;

                        if(pair != null && pair_ != null) {
                            if(pair.equals(pair_)) {
                                points.add(pair);
                                System.out.print("+");
                            }
                        }
                    }
                    x2-=a2;
                    break;
                }
            }
    }

    private static void check(int x1, int y1, String input2, List<Pair<Integer, Integer>> intersections) {
            int x2 = 0;
            int y2 = 0;
            for (String s2 : input2.split(",")) {
        System.out.println("-- check");
                switch (s2.charAt(0)) {
                    case 'U':
                        checkForIntersections(intersections, x1, x2, y1, y2, 0, true, Integer.parseInt(s2.substring(1)), true);
                        y2+=Integer.parseInt(s2.substring(1));
                        break;
                    case 'D':
                        checkForIntersections(intersections, x1, x2, y1, y2, 0, true, Integer.parseInt(s2.substring(1)), false);
                        y2-=Integer.parseInt(s2.substring(1));
                        break;
                    case 'L':
                        checkForIntersections(intersections, x1, x2, y1, y2, Integer.parseInt(s2.substring(1)), false, 0, true);
                        x2-=Integer.parseInt(s2.substring(1));
                        break;
                    case 'R':
                        checkForIntersections(intersections, x1, x2, y1, y2, Integer.parseInt(s2.substring(1)), true, 0, true);
                        x2+=Integer.parseInt(s2.substring(1));
                        break;
                }
            } 
    }

    private static void checkForIntersections(List<Pair<Integer, Integer>> intersections, int x1, int x2, int y1, int y2, int xAdd, boolean xPlus, int yAdd, boolean yPlus) {
        int max1 = x2+(xPlus ? xAdd : -xAdd);
        int max2 = y2 + (yPlus ? yAdd : -yAdd);
        for(; x2 <= max1; x2+=(xPlus ? 1 : -1)) {
            for(; y2 <= max2; y2+=(yPlus ? 1 : -1)) {
                System.out.println("inters check | "+x2+", "+(x2 <= x2+xAdd)+", " + (x2+xAdd) + ", "+(x2+=(xPlus ? 1 : -1))+" | "+y2+", "+(y2 <= y2 + yAdd)+", "+(y2+=(yPlus ? 1 : -1)));
                if(x1 == x2 && y1 == y2) {
                    intersections.add(new Pair<Integer,Integer>(x1, y1));
                }
            }
        }
    }

    public static class Pair<L, R> {
        public L left;
        public R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Pair<?, ?>) {
                Pair<?, ?> pair = (Pair<?, ?>) obj;
                return pair.left.equals(left) && pair.right.equals(right);
            }
            return super.equals(obj);
        }

        /**
         * @return the left
         */
        public L getLeft() {
            return left;
        }

        /**
         * @return the right
         */
        public R getRight() {
            return right;
        }
    }
}