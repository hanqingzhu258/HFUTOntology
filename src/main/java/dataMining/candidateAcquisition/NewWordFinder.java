package dataMining.candidateAcquisition;

import dataMining.ICTCLAS.NlpirMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.fileHandler.GeneralFileHandler;

import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.candidateAcquisition
 * @Author: hanqing zhu
 * @Date: 10:27 2019/5/5
 * @Description:
 */
public class NewWordFinder {

    public static Logger logger= LoggerFactory.getLogger(NewWordFinder.class);

    public static void main(String[] args) {
        //新词发现结果
        String result=null;
        result=getNewWordsFinderResult(false);

        //保存为用户自定义词典
        result2UserDict(result);
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
        dataMining.newWordFinder.NewWordFinder.init("");
        //启动新词发现功能
        dataMining.newWordFinder.NewWordFinder.batchStart();
        //添加新词训练的文件，可反复添加
        String path = "dataCollection/datasource/";
        List<String> fileNames = GeneralFileHandler.getAllFileName(path);
        for (String fileName : fileNames) {
            dataMining.newWordFinder.NewWordFinder.batchAddFile(path + fileName);
        }
        /*NewWordFinder.batchAddFile(path+"HFUT0.txt");*/
        //添加文件或者训练内容结束
        dataMining.newWordFinder.NewWordFinder.batchComplete();
        //输出新词识别结果
        String result = dataMining.newWordFinder.NewWordFinder.batchGetResult(showPartOfSpeech);
        logger.info("识别结果：{}", result);
        //新词识别结果导入到用户词典
        /*int newCount = NewWordFinder.result2UserDict();
        logger.info("新词数 ：{}", newCount);*/
        /*result2UserDict(result);*/
        dataMining.newWordFinder.NewWordFinder.exit();
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

}
