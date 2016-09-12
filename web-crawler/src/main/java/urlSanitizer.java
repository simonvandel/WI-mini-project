import java.net.URI;
import java.net.URISyntaxException;

public class urlSanitizer {

    public URI sanitize(String input) throws URISyntaxException {
        URI result = new URI(input);
        return result.normalize();
    }

}
