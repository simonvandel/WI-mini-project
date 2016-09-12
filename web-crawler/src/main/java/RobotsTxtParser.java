import crawlercommons.robots.BaseRobotRules;
import crawlercommons.robots.BaseRobotsParser;
import crawlercommons.robots.SimpleRobotRulesParser;

/**
 * Created by simon on 9/12/16.
 */
public class RobotsTxtParser {

    public static BaseRobotRules parse(byte[] robotsTxtContent, String robotName) {
        BaseRobotsParser parser = new SimpleRobotRulesParser();
        return parser.parseContent("foo", robotsTxtContent, "text/plain", robotName);
    }
}
