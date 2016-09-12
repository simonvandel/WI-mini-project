import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Michael on 12-09-2016.
 */
public class queue {
    public URI fetchNextURI() throws URISyntaxException {
        return new URI("http://www.google.dk");
    }
}
