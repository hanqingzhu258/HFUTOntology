package dataMining;

import dataMining.ICTCLAS.NlpirMethod;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining
 * @Author: hanqing zhu
 * @Date: 10:26 2019/5/4
 * @Description:
 */
public class SplitWordsTest {

    public static Logger logger= LoggerFactory.getLogger(SplitWordsTest.class);

    @Test
    public void splitWords() throws Exception {

        NlpirMethod.NLPIR_FileProcess("dataCollection/result/testText.txt",
                "dataCollection/result/testResult0.txt",1);


        //新词发现结果
        String result=null;
        result= SplitWords.getNewWordsFinderResult(false);

        //保存为用户自定义词典
        SplitWords.result2UserDict(result);

        //导入自定义词典
        NlpirMethod.NLPIR_ImportUserDict("dataCollection/dics/HFUTDictory.txt",false);


        //检测用户词典是否生效
        NlpirMethod.NLPIR_FileProcess("dataCollection/result/testText.txt",
                "dataCollection/result/testResult1.txt",1);


    }


}