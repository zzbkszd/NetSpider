package xinou.youquer.search;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class EasemobHttpUtil {
	public static void main(String[] args) {

    }

	private static HttpClient getHttpClient(){
		return new DefaultHttpClient();
	}

	public static byte[] download(String url){
		CloseableHttpClient httpClient;
        httpClient = (CloseableHttpClient) getHttpClient();
        // 创建httppost
		HttpGet httpdelete = new HttpGet(url);
        try {  
        	System.out.println("executing request " + httpdelete.getURI());  
            CloseableHttpResponse response = httpClient.execute(httpdelete);
            String ret ="";
            System.out.println(response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode()!=200)
            	return null;
            try {  
                HttpEntity entity = response.getEntity();
                InputStream input = entity.getContent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1000];
                int len;
                while((len = input.read(buf) )>0){
                	baos.write(Arrays.copyOfRange(buf, 0, len));
                }
                return baos.toByteArray();
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpClient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } 
        return null;
	}
}
