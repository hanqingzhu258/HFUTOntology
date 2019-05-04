package dataMining.LDAProcess;

import dataHandler.FileHandler;
import dataMining.ICTCLAS.NlpirMethod;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;

import static org.junit.Assert.*;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.LDAProcess
 * @Author: hanqing zhu
 * @Date: 16:03 2019/5/4
 * @Description:
 */
public class PartOfSpeechFiltrationTest {

    public static Logger logger= LoggerFactory.getLogger(PartOfSpeechFiltrationTest.class);

    @Test
    public void filterPartOfSpeech() throws Exception {

        BufferedReader reader= FileHandler.getBufferedReader("dataCollection/result/filterTermsResult/stopWordsFilterTermsSet.txt");
        String testText=reader.readLine();
        String result;
        result= NlpirMethod.NLPIR_ParagraphProcess(testText,1);
        logger.info(result);
    }

}