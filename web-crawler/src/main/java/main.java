import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class main {

    public static void main (String[] args) {
        crawl();
        System.out.println("hello");
    }

    private static void crawl() {
        crawlerCache crawlerCache = new crawlerCache();
        // Initialize queue with seed URLs

        // Fetch next URI and delete from queue
        URI nextURI = null;
        try {
            nextURI = crawlerQueue.fetchNextURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Download this page
        String thisPage = null;
        try {
            thisPage = utilities.downloadText("http://www.fiskehuset.com/");
            System.out.println(thisPage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Extract outgoing links
        Document doc = Jsoup.parse(thisPage);
        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.absUrl("href");
            try {
                URL urllink = new URL(linkHref);
                System.out.print(urlSanitizer.isURLInteresting(urllink) + " ");
                System.out.println(linkHref);
                crawlerCache.getRobotTxt(urllink);
            } catch (MalformedURLException e) {

            }


            // Check if obey robots.txt
            // Check if already in frontier
            // Add to frontier
        }

        // Extract text


            // Run near-duplicate check
                // If not duplicate, pass to indexer (LATER)
        indexer index = new indexer();
        try {
            index.savePage(new URI("http://www.google.dk"), "testtestestestestestsetse");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
