/**
 * Created by simon on 9/19/16.
 */
public class PageInfo {
    String urlHash;

    public PageInfo(String urlHash, int frequencyCount) {
        this.urlHash = urlHash;
        this.frequencyCount = frequencyCount;
    }

    int frequencyCount;
}
