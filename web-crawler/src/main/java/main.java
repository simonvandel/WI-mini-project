import java.net.URI;
import java.net.URISyntaxException;

public class main {

    public static void main (String[] args) {
        crawl();
        System.out.println("hello");
    }

    private static void crawl() {
        // Initialize queue with seed URLs

        // Fetch next URI from queue
        URI nextURI = null;
        try {
            nextURI = crawlerQueue.fetchNextURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Download this page
            // Extract text/
            // Extract outgoing links
                // For each extracted link
                    // Normalize URI
                    // Check if link is good (e.g. no mailto: or ftp: or .jpg)
                    // Check if obey robots.txt
                    // Check if already in frontier
                    // Add to frontier
            // Run near-duplicate check
                // If not duplicate, pass to indexer (LATER)




        // Delete current URI from queue
        if (nextURI != null) {
            crawlerQueue.removeURI(nextURI);
        }
    }
}
