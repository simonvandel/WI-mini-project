import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;
import java.nio.*;
import java.security.*;

public class session1 {

    public static boolean areSimilar(String inputA, String inputB) {
        return (similarityScore(inputA, inputB) > 90);
    }

    public static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public static <T> double jaccards(List<T> inputA, List<T> inputB) {
        Double intersectionCount = (double) intersection(inputA, inputB).size();
        Double unionCount = (double) union(inputA, inputB).size();

        return intersectionCount / unionCount;
    }

    public static long hash(String input) {
        byte[] byteData = utilities.hashString(input).getBytes();
        return ByteBuffer.wrap(byteData).getLong();
    }

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }


    public static Long minimumHash(String salt, List<String> shingles) {
        return
                shingles.stream()
                        .map(shingle -> hash(shingle + salt))
                        .min(Long::compare).get();
    }

    public static List<Long> superShinglify(int n, List<Long> sketch) {
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < sketch.size(); i += n) {
            // TODO: is it okay to use toString?
            result.add(hash(sketch.subList(i, i+n).toString()));
        }

        return result;
    }

    public static Double similarityScore(String inputA, String inputB) {
        int shingleSize = 4;

        // create the shingles
        List<String> shingles1 = shinglify(shingleSize, inputA);
        List<String> shingles2 = shinglify(shingleSize, inputB);

        // for each different hashing function
        // hash all shingles
        // find and store minimum

        // create sketches
        List<Long> sketchA = new ArrayList<>();
        List<Long> sketchB = new ArrayList<>();

        for (Integer i = 0; i < 84; i++){
            sketchA.add(minimumHash(i.toString(), shingles1));
            sketchB.add(minimumHash(i.toString(), shingles2));
        }

        // System.out.println(shingles1);
        // System.out.println(shingles2);

        // System.out.println(sketchA);
        // System.out.println(sketchB);

        // create super-shingles
        List<Long> superA = superShinglify(shingleSize, sketchA);
        List<Long> superB = superShinglify(shingleSize, sketchB);


        System.out.println("Simple: " + jaccards(shingles1, shingles2));
        System.out.println("Trick1: " + jaccards(sketchA, sketchB));
        
        int identicalHashes = intersection(superA, superB).size();
        System.out.println("Super-shingle match: " + identicalHashes + " (" + (identicalHashes >= 2 ? "yes" : "no") + ")");

        // if (intersection(superA, superB).size() >= 2) {
        //     System.out.println("Matched using super-shingle trick");
        //     return 1.0;
        // }

        return 1.0;
    }

    public static List<String> shinglify(int n, String input) {
        // Split på spaces
        List<String> words = Arrays.asList(sanitize(input).split(" "));
        int length = words.size();

        List<String> result = new ArrayList<>();
        for (int i = 0; i < length - n+1; i++) {
            String item = words.get(i);
            for (int j = 1; j < n; j++) {
                item += " " + words.get(i+j);
            }
            result.add(item);
        }
        
        return result;
    }

    public static void main (String[] args) throws IOException {
        String string1 = Files.readAllLines(FileSystems.getDefault().getPath(".", "string1.txt")).get(0);
        String string2 = Files.readAllLines(FileSystems.getDefault().getPath(".", "string2.txt")).get(0);
        String string3 = Files.readAllLines(FileSystems.getDefault().getPath(".", "string3.txt")).get(0);
 
        System.out.println("String1 <-> String2\n-------------------");
        similarityScore(string1, string2);
        
        System.out.println("\nString1 <-> String3\n-------------------");
        similarityScore(string1, string3);

        //System.out.println(areSimilar(string1, string2));
        //System.out.println(similarityScore(string1, string2));
    }
}

