import crawlercommons.robots.BaseRobotRules;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class main {

    static List<URL> visited = new ArrayList<>();
    static crawlerCache crawlerCache = new crawlerCache();
    static URLFrontier urlFrontier = new URLFrontier();
    static indexer index = new indexer();

    public static void main(String[] args) {
        // Initialize queue with seed URLs
        try {
            URL url = new URL("http://www.dr.dk");
            urlFrontier.add(url);
            visited.add(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        while (index.getCount() < 1000) {
            System.out.println("indexed: " + index.getCount());
            System.out.println("in frontier: " + urlFrontier.getBackQueueCount());
            crawl();
        }
        System.out.println("hello");
    }

    private static void crawl() {
        // Fetch next URI and delete from queue
        URL nextURL = urlFrontier.next();

        // Download this page
        String thisPage = null;
        try {
            thisPage = utilities.downloadText(nextURL);
            index.savePage(nextURL, thisPage);
            System.out.println(thisPage);
        } catch (Exception e) {
            System.out.println("Download error on " + nextURL);
            return;
        }

        // Extract outgoing links
        Document doc = Jsoup.parse(thisPage);
        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.absUrl("href");
            try {
                URL urlLink = new URL(linkHref);
                BaseRobotRules robotRules = crawlerCache.getRobotTxt(urlLink);
                // Check if obey robots.txt
                // Check if already in frontier
                if (robotRules != null && robotRules.isAllowed(urlLink.toString()) && !visited.contains(urlLink)) {
                    // Add to frontier
                    urlFrontier.add(urlLink);
                }
            } catch (MalformedURLException e) {
                System.out.println("Malformed url: " + linkHref);
                // do nothing
            }
        }

        // Extract text


        // Run near-duplicate check
        // If not duplicate, pass to indexer (LATER)

//        try {
//            index.savePage(new URI("http://www.google.dk"), "testtestestestestestsetse");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }
}
