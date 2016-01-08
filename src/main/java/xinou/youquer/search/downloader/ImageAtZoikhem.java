package xinou.youquer.search.downloader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xinou.youquer.search.EasemobHttpUtil;
import xinou.youquer.search.Task;

import java.io.IOException;

/**
 * 下载zoikhem_lib图片的任务
 * Created by shizhida on 16/1/6.
 */
public class ImageAtZoikhem extends Task {

    String url;

    public ImageAtZoikhem(String url,String savePath){
        super(savePath);
        this.url = url;
    }

    public String scanContent() throws IOException {
        Document doc = connect(url);

        if(doc==null)
            return "";

        Elements sni = doc.getElementsByClass("sni");

        for (Element element : sni) {
            Elements href = element.getElementsByTag("img");
            for (Element element1 : href) {
                String src = element1.attr("src");
                if(!src.startsWith("http://ehgt.org/g/")){
                    System.out.println("downloading "+src);
                    return src;
//                    System.out.println(src);
                }
//                System.out.println(element1.attr("src"));
            }
        }
        return "";

    }

    public void saveImage(String url){

        byte[] image = EasemobHttpUtil.download(url);
        String name = url.substring(url.lastIndexOf('/'),url.length());
        try {
            saveFile(name,image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        new ImageAtZoikhem("http://g.e-hentai.org/s/4433c4ce5d/348233-1").execute();
    }

    @Override
    public Integer execute() {
        try {
            String imageurl = scanContent();
            saveImage(imageurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
