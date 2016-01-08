package xinou.youquer.search.collector;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xinou.youquer.search.Collector;
import xinou.youquer.search.Processer;
import xinou.youquer.search.Url;
import xinou.youquer.search.downloader.TextAt1024;

import java.util.ArrayList;
import java.util.List;

/**
 * url列表扫描者
 * Created by shizhida on 15/10/12.
 */
public class TextListCollector extends Collector {

    public TextListCollector(String url, Processer processer) {
        super(url, processer);
    }

    @Override
    public List<String> scanList() {
        Document doc = connect(url);

        Elements els = doc.getElementsByTag("a");

        List<String> result = new ArrayList<>();

        for (Element el : els) {
            String href = el.attr("href");
            if(href.startsWith("htm_data"))
                result.add(Url.url_1024_base+href);
        }
        return result;
    }

    @Override
    public Integer execute() {
        List<String> list = scanList();
        for (String s : list) {
            processer.push(new TextAt1024(s));
        }
        return 200;
    }
}
