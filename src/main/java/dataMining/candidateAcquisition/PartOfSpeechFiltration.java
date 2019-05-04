package dataMining.candidateAcquisition;

import dataHandler.FileHandler;
import dataMining.ICTCLAS.NlpirMethod;
import tools.StringParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.LDAProcess
 * @Author: hanqing zhu
 * @Date: 16:00 2019/5/4
 * @Description:
 */
public class PartOfSpeechFiltration {

    public static void main(String[] args) {

        /**
         * 词性过滤: 利用NLPIR 分词系统中的词性标注功能，只保留术语集Sterm 中的名词、名词性短语和动词，进行词性过滤。
         * 顺带乱码过滤
         */
        filterPartOfSpeech();

    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:01 2019/5/4
     * @Return:
     * 
     * @Description: 词性过滤:
     *                  利用NLPIR 分词系统中的词性标注功能，只保留术语集Sterm 中的名词、名词性短语和动词，进行词性过滤。
     */
    public static void filterPartOfSpeech(){

        try{

            String stopWordsFilterTermsSetPath="dataCollection/result/filterTermsResult/stopWordsFilterTermsSet.txt";
            BufferedReader reader= FileHandler.getBufferedReader(stopWordsFilterTermsSetPath);
            String resultPath="dataCollection/result/filterTermsResult/partOfSpeechTaggingFilterTermsSet.txt";
            BufferedWriter writer=FileHandler.getBufferedWriter(resultPath);
            String content;
            String result;
            while((content=reader.readLine())!=null){
                //是否是乱码
                if (StringParser.isMessyCode(content)){
                    continue;
                }
                result=NlpirMethod.NLPIR_ParagraphProcess(content,1);
                if (result.contains("/n")||result.contains("/v")){
                    writer.write(content+"\r");
                }else{
//                    System.out.println(content);
                }
            }
            reader.close();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
