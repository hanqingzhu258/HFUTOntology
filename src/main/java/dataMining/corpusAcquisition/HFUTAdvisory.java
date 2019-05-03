package dataMining.corpusAcquisition;

import com.alibaba.fastjson.JSON;
import dataHandler.Crawler;
import dataHandler.FileHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.util.Arrays;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining
 * @Author: hanqing zhu
 * @Date: 15:25 2019/5/2
 * @Description:
 */
public class HFUTAdvisory {

    public static void main(String[] args) {
        getHFUTAdvisory();
    }

    /**
     * @Author: hanqing zhu
     * @Date: 21:54 2019/5/2
     * @Return:
     * 
     * @Description: 在百度搜索获取“合肥工业大学”相关语料
     *               为接下来的LDA操作准备语料库
     *               参考文献：基于LDA的领域本体概念获取方法研究_王红
     */
    public static void getHFUTAdvisory() {
        int count = 10;
        String webURL =
                "https://www.baidu.com/s?ie=utf-8&cl=2&rtt=1&bsst=1&rsv_dl=news_b_pn&tn=news&word=%E5%90%88%E8%82%A5%E5%B7%A5%E4%B8%9A%E5%A4%A7%E5%AD%A6&x_bfe_rqs=03E80&x_bfe_tjscore=0.005230&tngroupname=organic_news&pn=";
        try {
            //获取0-20页的百度“咨询”相关搜索页面
            for (int i = 0; i < 200; i=i+count) {
                int num=0;
                //获取页面链接集合
                Document document = Crawler.getHTML(webURL + i);
                Elements elements = document.select("#content_left > div:nth-child(3) > div > h3 > a");
                for (Element element : elements) {
                    //解析链接文档内容
                    String link = element.attr("href");
                    //文档内容摘要
                    String result = null;
                    String resultContent=null;
                    //NLPIR在线演示接口
                    CloseableHttpClient httpclient = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost("http://ictclas.nlpir.org/nlpir/index/getContentByLink.do");
                    BasicNameValuePair bnvp = new BasicNameValuePair("link", link);
                    httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(bnvp), "utf-8"));
                    CloseableHttpResponse response = httpclient.execute(httpPost);
//                    System.out.println(response.toString());
                        //接口正常
                    if (response != null && response.getStatusLine().getStatusCode()
                            == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
//                        System.out.println(i);
                        result = Crawler.readResponse(entity, "UTF-8");
                        resultContent=JSON.parseObject(result).get("content").toString();
                        response.close();
                        httpclient.close();
                    }
                        //接口异常
                    else{
                        Document doc=Crawler.getHTML(link);
                        result=doc.select("meta[name='keywords']").attr("content")
                                +doc.select("meta[name='description']").attr("content");
                        resultContent=result;
                    }
//                    System.out.println("result:"+resultContent);
                    //写文件操作
                    String fileURL="dataCollection/datasource/HFUT"+(i+num++)+".txt";
                    BufferedWriter writer= FileHandler.getBufferedWriter(fileURL);
                    if (result!=null){
                        writer.write(resultContent);
                    }
                    writer.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
