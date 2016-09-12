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
        try {
            URI nextURI = crawlerQueue.fetchNextURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Download this page

        // Parse page
    }
}
