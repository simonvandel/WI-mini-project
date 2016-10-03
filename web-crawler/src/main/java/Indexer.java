import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by simon on 9/19/16.
 */
public class Indexer {
    public static void main(String[] args) {
        Path startPath = Paths.get("storedPages/");
        try {
            // key is token, value is list of urlHash + count
            HashMap<String, HashMap<String, Integer>> tokenCount = new HashMap<>();
            HashMap<String, List<String>> OutlinksMap = new HashMap<>();
            final int[] N = {0};
            Files.walk(startPath)
            // skip the directory
            .skip(1)
            .map(Indexer::readHtml)
            .map(storedPage -> {
                List<String> tokens = Tokenizer.tokenize(storedPage.textFromHtml);
                OutlinksMap.put(storedPage.urlHash, storedPage.outgoingLinks.stream().map(link -> utilities.hashString(link)).collect(Collectors.toList()));
                //System.out.println("test: " + storedPage.outgoingLinks.toString());
                return new TokenizedPage(storedPage.urlHash, tokens);
            })
            .forEach(tokenizedPage -> {
                String urlHash = tokenizedPage.getUrlHash();
                N[0]++;
                for (String token
                        :tokenizedPage.getTokens()) {
                    HashMap<String, Integer> innerHashTable = tokenCount.get(token);
                    if(innerHashTable == null) {
                        innerHashTable = new HashMap<>();
                        tokenCount.put(token, innerHashTable);
                    }

                    // to initialize
                    innerHashTable.putIfAbsent(urlHash, 0);

                    // find the pageInfo matching urlHash, and add 1 to the frequency
                    innerHashTable.compute(urlHash, (x, oldCount) -> oldCount + 1);
                }
            });



            int n = N[0];

           // double[] startArray = new double[n];
            HashMap<String, Double> q = new HashMap<>();
            //startArray[0] = 1.0;

            final boolean[] first = {true};

            // init q
            OutlinksMap.forEach((url, outgoingUrl) -> {
                if (first[0]) {
                    q.put(url, 1.0);
                    first[0] = false;
                } else {
                    q.put(url, 0.0);
                }
            });

            OutlinksMap.forEach((url, outgoingUrl) -> {
                double el = 0;
                for(String outlink : outgoingUrl ) {
                    if (OutlinksMap.containsKey(outlink)) {
                        el++;
                    }
                }
                double weight = 1 / el;

                for(String outlink : outgoingUrl ) {
                    
                }

                double sum = 0;
                for (int i = 0; i < n; i++) {
                    double p = outgoingUrl.
                    startArray[i] *
                }
            });

            // create directory for indices
            new File("indices/").mkdir();

            // create index files
            tokenCount.forEach((token, urlCountList) -> {
                Path file = Paths.get("indices/" + token);
                List<String> content = new ArrayList<String>();
                double df = urlCountList.size();
                double idf = Math.log10(N[0] / df);
                urlCountList.forEach((urlHash, count) -> {
                    // calc tf-idf
                    double tfLog = (count == 0) ? 0 : 1 + Math.log10(count);
                    double tfIdf = tfLog*idf;
                    content.add(urlHash + " " + count + " " + tfIdf);
                });
                try {
                    Files.write(file, content, Charset.forName("UTF-8"));
                } catch (IOException e) {
                    System.out.println("Error writing index");
                }
            });

        } catch (IOException e) {
            System.out.println("Error reading stored pages");
        }
    }

    //private static <R> R createIndexFile(List<String> tokens) {
    //}

    private static String extractText(String html) {
        return Jsoup.parse(html)
                .text();
    }

    /**
     * Parse stored page, and extract text from textFromHtml
     * @param path
     * @return
     */
    private static StoredPage readHtml(Path path){
        try {
            String html = Files.readAllLines(path).stream()
                    // skip url
                    .skip(1)
                    // join textFromHtml
                    .collect(Collectors.joining());
            return new StoredPage(path.getFileName().toString(),
                    extractText(html),
                    Jsoup.parse(html).select("a[href]").stream().map(link -> link.attr("abs:href")).filter(item -> item != null && !"".equals(item)).collect(Collectors.toList()));
        } catch (IOException e) {
            System.out.println("Error reading textFromHtml from file:" + path);
            return null;
        }
    }
}
