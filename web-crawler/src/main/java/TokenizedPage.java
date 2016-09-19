import java.util.List;

/**
 * Created by simon on 9/19/16.
 */
public class TokenizedPage {
    private final String urlHash;
    private final List<String> tokens;

    public TokenizedPage(String urlHash, List<String> tokens) {
        this.urlHash = urlHash;
        this.tokens = tokens;
    }

    public String getUrlHash() {
        return urlHash;
    }

    public List<String> getTokens() {
        return tokens;
    }
}
