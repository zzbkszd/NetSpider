package xinou.youquer.search;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by shizhida on 15/10/12.
 */
public class NetWorkRole {

    /**
     * 连接服务器并获取页面html信息
     * @param url
     * @return
     */
    public Document connect (String url){

        HttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        try {

            HttpResponse response = client.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println(statusCode);

            if(statusCode!=200)
                return null;

            byte[] buf = new byte[1024];

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;

            while((len=response.getEntity().getContent().read(buf))>0){

                baos.write(buf,0,len);

            }

            Document doc = Jsoup.parse(new String(baos.toByteArray(), "GBK"));

            return doc;

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;

    }
}
