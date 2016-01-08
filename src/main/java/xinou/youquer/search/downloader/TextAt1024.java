package xinou.youquer.search.downloader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xinou.youquer.search.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shizhida on 15/10/12.
 */
public class TextAt1024 extends Task {

    String url;

    public TextAt1024(String url){
        this.url = url;
    }

    public void scanContent() throws IOException {
        Document doc = connect(url);

        if(doc==null)
            return;

        String title = doc.getElementsByTag("title").text();

        title = title.substring(0, title.indexOf("草榴"));

        Elements eles = doc.body().getElementsByClass("tpc_content");

        StringBuilder content = new StringBuilder(title).append("\n").append(url);

        for (Element ele : eles) {

            content.append(ele.html().replaceAll("<br>","").replaceAll("&nbsp"," "));

        }

        saveFile(title,content.toString());
    }

    private void saveFile(String title,String content) throws IOException {

        String baseUri = "/Users/shizhida/Documents/ebooks/";

        baseUri+=title+".txt";

        File file = new File(baseUri);

        if(file.exists())
            return;

        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);

        out.write(title.getBytes());

        out.write(content.getBytes());

        System.out.println("保存完成-"+title);

    }

    @Override
    public Integer execute() {
        try {
            scanContent();
            return 200;
        } catch (IOException e) {
            return 404;
        }
    }
}
