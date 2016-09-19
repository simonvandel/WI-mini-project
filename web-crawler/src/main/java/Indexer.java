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
        Path startPath = Paths.get("storedPages_wip/");
        try {
            // key is token, value is list of urlHash + count
            HashMap<String, HashMap<String, Integer>> tokenCount = new HashMap<>();
            Files.walk(startPath)
                    // skip the directory
                    .skip(1)
                    .map(Indexer::readHtml)
                    .map(storedPage -> {
                        List<String> tokens = Tokenizer.tokenize(storedPage.textFromHtml);
                        return new TokenizedPage(storedPage.urlHash, tokens);
                    })
                    .forEach(tokenizedPage -> {
                        String urlHash = tokenizedPage.getUrlHash();
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

            // create directory for indices
            new File("indices/").mkdir();

            // create index files
            tokenCount.forEach((token, urlCountList) -> {
                Path file = Paths.get("indices/" + token);
                List<String> content = new ArrayList<String>();
                urlCountList.forEach((urlHash, count) -> {
                    content.add(urlHash + " " + count);
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
            return new StoredPage(path.getFileName().toString(), extractText(html));
        } catch (IOException e) {
            System.out.println("Error reading textFromHtml from file:" + path);
            return null;
        }
    }
}
