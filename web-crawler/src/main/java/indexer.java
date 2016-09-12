import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael on 12-09-2016.
 */
public class indexer {

    int count = 0;

    public int getCount() {
        return count;
    }

    public indexer() {

    }

    public void savePage(URI url, String content) {
        String filename = utilities.hashString(url.toString());

        List<String> lines = null;
        lines = Arrays.asList(url.toString(), content);
        new File("indices/").mkdir();
        Path file = Paths.get("indices/" + filename + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            count++;
        }
    }

    public void clearIndices() {
        new File("indices").delete();
        count = 0;
    }
}
