package dataMining;

import dataHandler.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.fileHandler.GeneralFileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining
 * @Author: hanqing zhu
 * @Date: 11:16 2019/5/4
 * @Description:
 */
public class TermsFiltration {

    public static Logger logger= LoggerFactory.getLogger(TermsFiltration.class);

    public static void main(String [] args){

        filterTerms();

    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:20 2019/5/4
     * @Return:
     * 
     * @Description: 领域术语过滤:对领域术语集Sterm 中的术语,
     *                           依次进行停用词过滤、低频词过滤、词性过滤。
     */
    public static void filterTerms(){
        //停用词过滤
        filterStopWord();
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:23 2019/5/4
     * @Return:
     * 
     * @Description: 过滤停用词
     */
    public static void filterStopWord(){

        List<String> result=new ArrayList<String>();

        String stopWordsDicPath="dataCollection/dics/stopWordsDic.txt";
        String termSetPath="dataCollection/result/splitWordResult/domainTermSet.txt";
        Set<String> stopWordsDic=new HashSet<String>();
        //加载停用词表
        stopWordsDic=loadStopWordsDic(stopWordsDicPath);
        //去停用词
        try{
            BufferedReader reader=FileHandler.getBufferedReader(termSetPath);
            String term;
            while((term=reader.readLine())!=null){
                if (stopWordsDic.contains(term)){
                    continue;
                }else{
                    result.add(term);
                }
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //保存结果
        String resultPath="dataCollection/result/filterTermsResult/stopWordsFilterTermsSet.txt";
        GeneralFileHandler.writeListToFile(resultPath,result);
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:40 2019/5/4
     * @Return:
     * 
     * @Description: 加载停用词表
     */
    public static Set<String> loadStopWordsDic(String path){
        Set<String> stopWordsDic=new HashSet<String>();
        try {
            BufferedReader reader = FileHandler.getBufferedReader(path);
            String stopWord;
            while ((stopWord=reader.readLine())!=null){
                stopWordsDic.add(stopWord);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return stopWordsDic;
    }
}
