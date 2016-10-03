import java.util.List;

/**
 * Created by simon on 9/19/16.
 */
public class StoredPage {
    String urlHash;
    String textFromHtml;
    final List<String> outgoingLinks;

    public StoredPage(String urlHash, String textFromHtml, List<String> outgoingLinks) {
        this.urlHash = urlHash;
        this.textFromHtml = textFromHtml;
        this.outgoingLinks = outgoingLinks;
    }
}
