import java.net.URI;
import java.net.URISyntaxException;

public class urlSanitizer {

    public URI sanitize(String input) throws URISyntaxException {
        URI result = new URI(input);
        return result.normalize();
    }

    public boolean isURIInteresting (URI input) {
        // 1) Check protocol.
        String scheme = input.getScheme();
        boolean isInteresting = false;
        switch (scheme) {
            case "http":
                isInteresting = true;
                break;
            case "https":
                isInteresting = true;
                break;
        }
        // 2) Check extension
        // TODO check extention to remove e.g. .jpg

        return isInteresting;
    }

}
