
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class LinkExtractor extends RecursiveTask<List<String>> {

    private String url;


    public LinkExtractor(String url) {
        this.url = url;
    }

    @Override
    public List<String> compute() {
        List<String> result = new ArrayList<>();
        List<LinkExtractor> linkExtractors = new ArrayList<>();
        List<String> linkList = new ArrayList<>();
        try {
            result.add(url);
            linkList.addAll(LinksUtils.parseSite(url));
            for (String thisUrl : linkList) {
                linkExtractors.add(new LinkExtractor(thisUrl));
            }
            for (LinkExtractor linkExtractor : linkExtractors) {
                linkExtractor.fork();
            }
            for (LinkExtractor linkExtractor : linkExtractors) {
                result.addAll(linkExtractor.join());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
