import java.net.URI;
import java.net.URISyntaxException;

public class crawlerQueue {

    public static URI fetchNextURI() throws URISyntaxException {
        return new URI("http://www.google.dk");
    }

    public static void removeURI(URI uriToDelete) {

    }
}
