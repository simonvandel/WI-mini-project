import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class main {

    public static void main (String[] args) {
        crawl();
    }


    public static void testqueue () throws MalformedURLException {
        URLFrontier crawlerQueue = new URLFrontier();

        crawlerQueue.add(new URL("http://google.com/"));
        crawlerQueue.add(new URL("http://google.com/maps"));
        crawlerQueue.add(new URL("http://facebook.com/"));
        crawlerQueue.add(new URL("http://twitter.com/"));
        crawlerQueue.add(new URL("http://twitter.com/stuff/"));

        System.out.println(crawlerQueue.next());
        System.out.println(crawlerQueue.next());
        System.out.println(crawlerQueue.next());
        System.out.println(crawlerQueue.next());
        System.out.println(crawlerQueue.next());
    }

    private static void crawl() {
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
            thisPage = utilities.downloadText("http://www.google.dk");
            System.out.println(thisPage);
        } catch (Exception e) {
            e.printStackTrace();
        }

            // Extract text
            // Extract outgoing links
                // For each extracted link
                    // Normalize URI
                    // Check if link is good (e.g. no mailto: or ftp: or .jpg)
                    // Check if obey robots.txt
                    // Check if already in frontier
                    // Add to frontier
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
