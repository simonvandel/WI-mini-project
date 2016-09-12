import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import crawlercommons.robots.BaseRobotRules;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Michael on 12-09-2016.
 */
public class crawlerCache {

    private Cache<String, BaseRobotRules> robotcache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    public BaseRobotRules getRobotTxt(URL url) {
        BaseRobotRules result = robotcache.getIfPresent(url.getHost());
        if (result == null) {
            try {
                String robottxt = utilities.downloadText(new URL(url.getProtocol(), url.getHost(), "/robots.txt"));
                result = RobotsTxtParser.parse(robottxt.getBytes(), "NiceBot");
                robotcache.put(url.getHost(), result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
