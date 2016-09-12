import java.time.LocalDateTime;

public class HostEntry {

    public String hostname;
    public LocalDateTime earliestAccess;

    public HostEntry (String hostname, LocalDateTime earliestAccess) {
        this.hostname = hostname;
        this.earliestAccess = earliestAccess;
    }
}
