package net.hlinfo.opt;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * @author 呐喊
 * @desc HTTP/HTTPS请求，包含GET、POST方法
 * @HomePage http://www.hlinfo.cc
 */
public class HttpRequest {
	/**
	 * 返回请求http状态码
	 */
	public int statusCode = 200;
	public String charset="utf-8";
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param http get请求
	 * @param url
	 * @author 呐喊
	 * @return
	 */
    public String httpget(String url) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);  
            //System.out.println("URI:" + httpget.getURI());
            httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
               // System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println("response status:"+response.getStatusLine());  
                this.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == 200) {  
                    // 打印响应内容长度    
                    //System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                   // System.out.println("Response content: " + EntityUtils.toString(entity));
                	return EntityUtils.toString(entity);
                }else{
                	return "http请求失败,response status:"+response.getStatusLine();
                }
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;
    } 
    
    /**
     * @param http post请求
     * @param url  url地址
     * @param map  参数,Map对象
     * @author 呐喊
     * @return
     */
    public String httpPost(String url,Map<String,String> map){
    	boolean noNeedResponse=false;
    	CloseableHttpClient httpclient = HttpClients.createDefault(); 
        HttpPost httppost = new HttpPost(url);
        try {
        	httppost.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
          //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httppost.setEntity(entity);  
            }  
            // 执行post请求
            CloseableHttpResponse response = httpclient.execute(httppost);
            url = URLDecoder.decode(url, "utf-8");
            // 获取响应实体    
            HttpEntity httpentity = response.getEntity();
            // 打印响应状态    
            System.out.println("response status:"+response.getStatusLine()); 
            this.setStatusCode(response.getStatusLine().getStatusCode());
            
            if (response.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    //读取服务器返回的数据
                    str = EntityUtils.toString(httpentity);
                    /*if (noNeedResponse) {
                        return null;
                    }*/
                    return str;
                } catch (Exception e) {
                    System.out.println("post请求提交失败:" + url);
                }
            }
        } catch (IOException e) {
        	System.out.println("post请求提交失败:" + url);
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } 
        return null;
    }
    
    /**
     * @param Https post请求
     * @param url
     * @param map 参数，Map
     * @author 呐喊
     * @return
     */
    public String httpsPost(String url,Map<String,String> map){ 
    	String paramsCharset="utf-8"; 
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,paramsCharset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            this.setStatusCode(response.getStatusLine().getStatusCode());
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        } finally {  
        	
        }   
        return result;  
    }  
    
    /**
     * @param HTTPS GET 请求
     * @param url
     * @author 呐喊
     * @return
     */
    public String  httpsget(String url){
    	String charset="utf-8"; 
        HttpClient httpClient = null;
        HttpGet httpget = null;
        
        try {
			httpClient = new SSLClient();
			httpget = new HttpGet(url);
			httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0");
			//System.out.println("URI:" + httpget.getURI());  
            // 执行get请求.    
			HttpResponse response = httpClient.execute(httpget);
			// 获取响应实体    
            HttpEntity httpentity = response.getEntity();
            // 打印响应状态    
            System.out.println("response status:"+response.getStatusLine());
            this.setStatusCode(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {  
                // 打印响应内容    
                //System.out.println("Response content: " + EntityUtils.toString(httpentity);  
                // 响应内容  
            	return EntityUtils.toString(httpentity);
            }else{
            	return "http请求失败,response status:"+response.getStatusLine();
            }  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			  // 关闭连接,释放资源    
             
		}
        
    	return null;
    }

}
