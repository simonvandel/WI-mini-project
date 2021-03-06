import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Michael on 26-09-2016.
 */
public class Queryer {
    public static void main(String[] args) {
        // read indices
        Path startPath = Paths.get("indices/");
        // key is token, value is list of urlHash + count
        HashMap<String, HashMap<String, ValueHolder>> termInfo = new HashMap<>();
        // key is document, value is hashMap where key is term, value is tf
        HashMap<String, HashMap<String, Integer>> tfHashMap = new HashMap<>();
        final int[] N = {0};
        try {
            Files.walk(startPath)
                    // skip the directory
                    .skip(1)
                    .forEach(path -> {
                        // key is urlHash, value is tf-idf
                        HashMap<String, ValueHolder> innerHashmap = new HashMap<>();
                        N[0]++;
                        // parse file
                        try {
                            Files.readAllLines(path)
                                    // go through each url hash
                                    .forEach(line -> {
                                        String[] split = line.split(" ");
                                        String urlHash = split[0];
                                        Integer count = Integer.parseInt(split[1]);
                                        Double tdIdf = Double.parseDouble(split[2]);
                                        innerHashmap.put(urlHash, new ValueHolder(count, tdIdf));
                                    });
                            termInfo.put(path.getFileName().toString(), innerHashmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // populate tfHashMap
        termInfo.forEach((term, innerHashmap) -> {
            innerHashmap.forEach((document, valueHolder) -> {
                tfHashMap.putIfAbsent(document, new HashMap<>());
                tfHashMap.compute(document, (x, old) -> {
                    old.putIfAbsent(term, 0);
                    old.compute(term, (y, oldy) -> oldy + valueHolder.frequencyCount);
                    return old;
                });
            });
        });

        long start = 0;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter query:");
            // read query from stdin
            String line = sc.nextLine();
            start = System.currentTimeMillis();
            List<String> query = Tokenizer.tokenize(String.join(" ", line));

            HashMap<String, Double> scores = new HashMap<>();

            for (String term : query) {

                HashMap<String, ValueHolder> urlHashMap = termInfo.get(term);
                if(urlHashMap == null) continue;
                urlHashMap.forEach((urlHash, valueHolder) -> {
                    scores.putIfAbsent(urlHash, 0.0);

                    scores.compute(urlHash, (x, oldCount) -> oldCount + valueHolder.tfidf);
                });
            }

            // normalize score
            scores.forEach((document, score) -> {
                double length = Math.sqrt(tfHashMap.get(document).values()
                        .stream().map(x -> x * x).mapToInt(Integer::intValue).sum());
                scores.compute(document, (x, oldScore) -> oldScore / length);
            });

            scores.entrySet()
                    .stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .limit(10)
                    .map(e -> {
                        try {
                            String url = Files.readAllLines(Paths.get("storedPages/" + e.getKey())).get(0);
                            return url + " " + e.getValue();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            return "error";
                        }
                    }).forEach(System.out::println);

            if (scores.size() == 0) {
                System.out.println("No results found");
            }

            System.out.println("Query took: " + (System.currentTimeMillis()-start) + "ms");
        }
    }
}
