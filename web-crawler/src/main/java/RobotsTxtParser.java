import crawlercommons.robots.BaseRobotRules;
import crawlercommons.robots.BaseRobotsParser;
import crawlercommons.robots.SimpleRobotRulesParser;

/**
 * Created by simon on 9/12/16.
 */
public class RobotsTxtParser {
    private BaseRobotsParser parser = new SimpleRobotRulesParser();

    public BaseRobotRules parse(byte[] robotsTxtContent, String robotName) {
        return parser.parseContent("foo", robotsTxtContent, "text/plain", robotName);
    }
}
