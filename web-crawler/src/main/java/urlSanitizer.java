import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class urlSanitizer {

    public URL sanitize(String input) throws MalformedURLException {
        URL result = new URL(input);
        // TODO sanitize url
        return result;
    }

    public static boolean isURLInteresting(URL input) {
        // 1) Check protocol
        String scheme = input.getProtocol();
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
        if(input.toString().contains(".")) {
            String extension = input.toString().substring(input.toString().lastIndexOf("."));
            if (extension == "jpg") {
                isInteresting = false;
            }
        }

        return isInteresting;
    }

}
