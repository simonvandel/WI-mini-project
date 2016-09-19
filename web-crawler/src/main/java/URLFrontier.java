import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class URLFrontier {

    private int FRONT_QUEUES = 2;

    // urls grouped by content priority
    private List<Queue<URL>> frontQueues = new ArrayList<>(FRONT_QUEUES);

    // urls grouped by hostname
    private Map<String, Queue<URL>> backQueues = new HashMap<>();

    // TODO: part of backQueues
    // priority queue of host entries prioritized by next access time
    private Queue<HostEntry> hostEntries = new PriorityQueue<>(new Comparator<HostEntry>() {
        public int compare(HostEntry host1, HostEntry host2) {
            return host1.earliestAccess.compareTo(host2.earliestAccess);
        }
    });

    public int getBackQueueCount() {
        return backQueues.size();
    }

    //private Prioritizer prioritizer = new Prioritizer(FRONT_QUEUES);

    private Random rand = new Random();

    public URLFrontier () {
        // init the front queues
        for(int i = 0; i < FRONT_QUEUES; i++)
            frontQueues.add(new LinkedList<>());
    }

    public void add (URL url) {
        //int priority = prioritizer.priority(url);
        int priority = 0; // TODO: front queue priority

        System.out.println("adding url: " + url + " with priority " + priority);

        frontQueues.get(priority).add(url);

        updateBackQueue();
    }

    private URL fetchFromFrontQueue () {
        // fetch from a front queue
        // int r = rand.nextInt(100);

        // TODO: make random biased queue
        int index = 0;

        if (frontQueues.get(index).size() <= 0) throw new NoSuchElementException("shit");

        return frontQueues.get(index).remove();
    }

    private void updateBackQueue () {
        URL url = fetchFromFrontQueue();

        // System.out.println("fetching url: " + url + " from front queue");

        // add to back queue
        // TODO: a.b.com should be b.com
        String hostname = url.getHost();

        // backQueues.putIfAbsent(hostname, new LinkedList<>()).add(url);

        if (!backQueues.containsKey(hostname)) {
            backQueues.put(hostname, new LinkedList<>());
            hostEntries.add(new HostEntry(hostname, LocalDateTime.now()));
        }

        backQueues.get(hostname).add(url);
    }

    public URL next () {
        // fetch from back queue (time priority)
        HostEntry nextHost = hostEntries.remove();

        while (LocalDateTime.now().isBefore(nextHost.earliestAccess)) {
            System.out.println("waiting time for host " + nextHost.hostname + " has not elapsed, waiting...");
            try {
                Thread.sleep(1000); // TODO: calc
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt(); // TODO: I have no idea what this is
            }
        }

        // System.out.println("next hostname " + nextHost.hostname);

        // update next access time and re-add it to priority queue if the are more urls for this host
        if (backQueues.get(nextHost.hostname).size() > 1) {
            nextHost.earliestAccess = LocalDateTime.now().plusSeconds(5);
            hostEntries.add(nextHost);
        }

        return backQueues.get(nextHost.hostname).remove();
    }
    
}
