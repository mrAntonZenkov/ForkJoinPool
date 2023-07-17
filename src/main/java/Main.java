
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String url = "https://skillbox.by/";
    private static final String path = "links/linksList.txt";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<String> result = new ForkJoinPool().invoke(new LinkExtractor(url));
        System.out.println(result.size());
        try {
            Files.write(Paths.get(path), getList(result), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Время парсинга : " + (finish - start));

    }

    public static List<String> getList(List<String> list) {
        List<String> newList = new ArrayList<>();
        int i = 0;
        for (String link : list) {
            for (int j = 0; j < link.length(); j++) {
                if (link.charAt(j) == '/') {
                    i++;
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int a = 0; a < (i - 3); a++) {
                sb.append('\t');
            }
            newList.add(sb + link);
            i = 0;
        }
        System.out.println(newList.size());
        return newList;
    }
}

