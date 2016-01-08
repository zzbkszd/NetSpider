package xinou.youquer.search.collector;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xinou.youquer.search.Collector;
import xinou.youquer.search.Processer;
import xinou.youquer.search.downloader.ImageAtZoikhem;
import xinou.youquer.search.downloader.TextAt1024;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shizhida on 16/1/6.
 */
public class ZoikhenCollector extends Collector {

    String savePath;
    public ZoikhenCollector(String url, Processer processer,String savePath) {
        super(url, processer);
        this.savePath = savePath;
    }

    @Override
    public List<String> scanList() {

        List<String> urls = new ArrayList<>();
        Document doc = connect(url);

        if(doc==null)
            return urls;

        Elements gdtm = doc.getElementsByClass("gdtm");

        for (Element element : gdtm) {
            Elements href = element.getElementsByTag("a");
            for (Element element1 : href) {
                urls.add(element1.attr("href"));
            }
        }
        return urls;
    }

    @Override
    public Integer execute() {
        List<String> list = scanList();
        for (String s : list) {
            processer.push(new ImageAtZoikhem(s,savePath));
        }
        return 200;
    }
}
