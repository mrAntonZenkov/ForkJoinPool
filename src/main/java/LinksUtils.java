
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LinksUtils {
    static HashSet<String> uniqueUrl = new HashSet<>();
    static String mySite = "//skillbox.by/";


    public static List<String> parseSite(String url) {
        List<String> linksList = new ArrayList<>();
        try {
            Thread.sleep(150);
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("https://www.google.com")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .timeout(0)
                    .get();
            Elements link = doc.select("a");
            if (!link.isEmpty()) {
                synchronized (uniqueUrl) {
                    for (Element element : link) {
                        String thisUrl = element.attr("abs:href");
                        if (!thisUrl.contains("#") && !uniqueUrl.contains(thisUrl)
                                && thisUrl.contains(mySite) && thisUrl.endsWith("/")) {
                            uniqueUrl.add(thisUrl);
                            linksList.add(thisUrl);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return linksList;
    }
}
