import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class crawlerQueue {

    public crawlerQueue() {

    }

    public crawlerQueue(List<URI> seed) {

    }

    public static URI fetchNextURI() throws URISyntaxException {
        return new URI("http://www.google.dk");
    }

    public static void removeURI(URI uriToDelete) {

    }
}
