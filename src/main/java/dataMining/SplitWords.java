package dataMining;

import dataHandler.FileHandler;
import dataMining.ICTCLAS.NlpirMethod;
import dataMining.newWordFinder.NewWordFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.fileHandler.GeneralFileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining
 * @Author: hanqing zhu
 * @Date: 16:51 2019/5/3
 * @Description: 论文：基于LDA的领域本体概念获取方法研究--王红
 * 思路的实验实现
 */
public class SplitWords {

    public static Logger logger = LoggerFactory.getLogger(SplitWords.class);

    public static void main(String[] args) {

        /*1.基于NLPIR的自适应分词:
         *    首先使用“新词发现”功能实现领域词汇自动识别；
         *   将识别词汇加入到用户分词词典中；
         *    遍历文集依据词典进行分词操作，形成领域术语集
         *    ：dataCollection/result/splitWordResult/domainTermSet.txt。
         */
        splitWords();

    }

    /**
     * @Author: hanqing zhu
     * @Date: 17:02 2019/5/3
     * @Return:
     * @Description: 1.基于NLPIR的自适应分词:
     * 首先使用“新词发现”功能实现领域词汇自动识别；
     * 将识别词汇加入到用户分词词典中；
     * 遍历文集依据词典进行分词操作，形成领域术语集。
     */
    public static void splitWords() {

        //新词发现结果
        String result=null;
        result=getNewWordsFinderResult(false);

        //保存为用户自定义词典
        result2UserDict(result);

        //导入自定义词典
        NlpirMethod.NLPIR_ImportUserDict("dataCollection/dics/HFUTDictory.txt",false);

        //遍历文集依据词典进行分词操作，形成领域术语集，保存在不同的文件中
        String path = "dataCollection/datasource/";
        List<String> fileNames = GeneralFileHandler.getAllFileName(path);
        int count = 0;
        for (String fileName : fileNames) {
            String resultPath = "dataCollection/result/splitWordResult/HFUTResult" + (count++) + ".txt";
            NlpirMethod.NLPIR_FileProcess(path + fileName, resultPath, 0);
        }

        //将批量分词过的多个文本结果合并到一个文件中
        combineResult();
    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:54 2019/5/4
     * @Return:
     *
     * @Description: 新词发现处理过程
     */
    public static String getNewWordsFinderResult(boolean showPartOfSpeech){
        //初始化新词发现工具
        NewWordFinder.init("");
        //启动新词发现功能
        NewWordFinder.batchStart();
        //添加新词训练的文件，可反复添加
        String path = "dataCollection/datasource/";
        List<String> fileNames = GeneralFileHandler.getAllFileName(path);
        for (String fileName : fileNames) {
            NewWordFinder.batchAddFile(path + fileName);
        }
        /*NewWordFinder.batchAddFile(path+"HFUT0.txt");*/
        //添加文件或者训练内容结束
        NewWordFinder.batchComplete();
        //输出新词识别结果
        String result = NewWordFinder.batchGetResult(showPartOfSpeech);
        logger.info("识别结果：{}", result);
        //新词识别结果导入到用户词典
        /*int newCount = NewWordFinder.result2UserDict();
        logger.info("新词数 ：{}", newCount);*/
        /*result2UserDict(result);*/
        NewWordFinder.exit();
        return result;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:41 2019/5/4
     * @Return:
     * 
     * @Description: 将新词发现结果添加至用户自定义词典
     */
    public static void result2UserDict(String content){
        if (content==null){
            System.out.println("用户词典添加文本不能为空！");
            return;
        }
        String [] newWords=content.split("#");
        /*String dicPath="dataCollection/dics/HFUTDictory.txt";
        try {
            BufferedWriter writer = FileHandler.getBufferedWriter(dicPath);
            for (String newWord : newWords) {
                writer.write(newWord+"\r");
            }
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        for (String newWord:newWords){
            NlpirMethod.NLPIR_AddUserWord(newWord);
        }
        NlpirMethod.NLPIR_SaveTheUsrDic();
    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:59 2019/5/4
     * @Return:
     * 
     * @Description: 将批量分词过的多个文本结果合并到一个文件中
     */
    public static void combineResult(){
        String resultPath = "dataCollection/result/splitWordResult/";
        List<String> resultFileNames = GeneralFileHandler.getAllFileName(resultPath);
        List<String> terms = new ArrayList<String>();

        //将上述文件合并为一个文件，一个文件一行
        String combinePath="dataCollection/result/splitWordResult/resultCombination.txt";

        try {
            BufferedWriter totalWriter=FileHandler.getBufferedWriter(combinePath);
            //将每个结果文件按空格分隔后添加到总的list：terms中
            for (String resultFileName : resultFileNames) {
                BufferedReader reader = FileHandler.getBufferedReader(resultPath + resultFileName);
                String content=reader.readLine();
                if (content!=null){
                    String [] a=content.split(" ");
                    for (String a1:a){
                        terms.add(a1);
                    }
                }
                reader.close();
                //删除处理过的文件
                File file=new File(resultPath+resultFileName);
                if (file.exists()){
                    file.delete();
                }
                totalWriter.write(content+"\r");
            }
            //将terms写入总文件：一个词汇一行
            BufferedWriter writer=FileHandler.getBufferedWriter(resultPath+"domainTermSet.txt");
            for (String term:terms){
                writer.write(term+"\r");
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
