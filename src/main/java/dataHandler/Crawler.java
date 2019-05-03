package dataHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: HFUTOntology
 * @packageName: dataHandler
 * @Author: hanqing zhu
 * @Date: 17:14 2019/4/23
 * @Description:
 */
public class Crawler {

    /*public static void main(String[] args) {

        getHighAnonymousProxy();

    }*/


    /**
     * @Author: hanqing zhu
     * @Date: 22:23 2019/4/23
     * @Return:
     *
     * @Description: 转换下载的网页内容，解决网页中文乱码
     */
    public static String readResponse(HttpEntity entity, String charset){
        StringBuffer res = new StringBuffer();
        BufferedReader reader = null;
        try{
            if (entity == null){
                return null;
            }
            else{
                reader = new BufferedReader(new InputStreamReader(entity.getContent(),charset));
                String line;
                while ( (line = reader.readLine()) != null){
                    line = line + "\n";
                    res.append(line);
                }
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            try{
                if (reader != null){
                    reader.close();
                }

            }
            catch(Exception e){
                e.toString();
            }
        }
        return res.toString();
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:14 2019/4/27
     * @Return:
     *
     * @Description: 获取url对应的内容
     */
    public static String get(String url){
        String result = "";
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            /**
             * 设置请求头和代理信息
             */
//            HttpHost proxy = getHighAnonymousProxy();
            RequestConfig requestConfig = RequestConfig.custom()
//                    .setProxy(proxy)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpGet.setConfig(requestConfig);
            //设置请求头消息
            httpGet.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/62.0.3202.94 " +
                            "Safari/537.36");


            CloseableHttpResponse response = httpclient.execute(httpGet);
            try{
                if (response != null && response.getStatusLine().getStatusCode()
                        == HttpStatus.SC_OK ){
                    /*System.out.println(response.getStatusLine());*/
                    HttpEntity entity = response.getEntity();
                    /*System.out.println(entity.getContentEncoding());*/
                    result = readResponse(entity, "UTF-8");
                }
            }
            finally{
                httpclient.close();
                response.close();
            }

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 下载解析HTML页面
     */
    /**
     * @Author: hanqing zhu
     * @Date: 21:12 2019/4/23
     * @Return:
     *
     * @Description: 根据url获取网页内容
     */
    public static Document getHTML(String url){

        return Jsoup.parse(get(url));
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:06 2019/4/27
     * @Return:
     *
     * @Description: 根据选择器获取页面中相应的数据
     */
    public static List<String> getSpecifiedContentInText(String selector, Document document){
        List<String> contents=new ArrayList<String>();
        Elements elements=document.select(selector);
        for (Element element:elements){
            String eleContent=element.text();
            if (eleContent!=null){
                contents.add(eleContent);
            }
        }
        return contents;
    }

    /**
     * 下载解析json数据
     */
    public static String getJson(String url){
        return get(url);
    }

    /**
     * 下载解析其它格式数据
     */
    public static String getOtherFormatData(String url){
        String result=null;

        return result;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:50 2019/5/2
     * @Return:
     *
     * @Description: 获取代理ip：
     *               参考链接：https://www.cnblogs.com/zhangyinhua/p/8038867.html
     */
    public static HttpHost getHighAnonymousProxy(){
        HttpHost host=null;
        Map<String,String> proxies=new HashMap<String, String>();
        Document document=getHTML("https://www.xicidaili.com/");
        List<String> ips=getSpecifiedContentInText("#ip_list > tbody > tr:nth-child(22)+tr > td:nth-child(2)",document);
        List<String> ports=getSpecifiedContentInText("#ip_list > tbody > tr:nth-child(22)+tr > td:nth-child(3)",document);
        for (int i=0;i<ips.size();i++){
//            System.out.println(ips.get(i)+":"+ports.get(i));
            proxies.put(ips.get(i),ports.get(i));
        }
        int flag=(int) (Math.random()*ips.size());
        System.out.println(flag);
        int count=0;
        for (Map.Entry entry:proxies.entrySet()){
            count++;
            if (count==flag){
                host=new HttpHost((String) entry.getKey(),Integer.parseInt((String) entry.getValue()));
//                System.out.println(entry.getKey()+":"+entry.getValue());
                break;
            }
        }
//        System.out.println(host.toHostString());
        return host;
    }


}
