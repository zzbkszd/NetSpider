package xinou.youquer.search;

import xinou.youquer.search.collector.TextListCollector;
import xinou.youquer.search.collector.ZoikhenCollector;
import xinou.youquer.search.downloader.TextAt1024;

import java.util.Scanner;

/**
 * 1024爬虫
 * Created by shizhida on 15/10/11.
 */
public class Sprider {

    public static void main(String[] args) {
        String path = "/Users/shizhida/Documents/images/";
        Processer<Task> processer = new Processer<>();
        Processer<Collector> collector = new Processer<>();
        new Thread(processer).start();
        new Thread(collector).start();
        String url = "http://g.e-hentai.org/g/366410/b1b6f90340/" +
                "";
        for(int i=0;i<1;i++)
            collector.push(new ZoikhenCollector(url+"?p="+i,processer,path));
//        Scanner scanner = new Scanner(System.in);
//        while(true){
//            String url = scanner.nextLine();
//            collector.push(new ZoikhenCollector(url, processer));
//        }
    }






}
