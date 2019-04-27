package dataHandler;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tools.data.StackList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @projectName: HFUTOntology
 * @packageName: dataHandler
 * @Author: hanqing zhu
 * @Date: 17:14 2019/4/23
 * @Description:
 */
public class Crawler {

    /*public static void main(String[] args) {
        String aimUrl = "http://www.hfut.edu.cn/5287/list.htm";
        Document document= get(aimUrl);
        List<String> contents=getSpecifiedContentInText("#jxst_po > ul > li > p > a",document);
        System.out.println(contents.toString());
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
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpget);
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
            contents.add(eleContent);
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


}
