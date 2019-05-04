package dataMining.LDAProcess.candidateAcquisition;

import dataHandler.FileHandler;
import tools.StringParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.LDAProcess
 * @Author: hanqing zhu
 * @Date: 15:34 2019/5/4
 * @Description: 处理停用词表，去除停用词后面多余的空格
 */
public class StopWordsTableHandler {

    public static void main(String[] args) {

        try{

            String stopWordsTablePath="dataCollection/dics/stopWordsDic.txt";
            String updatePath="dataCollection/dics/updatedStopWordsDic.txt";
            BufferedReader reader= FileHandler.getBufferedReader(stopWordsTablePath);
            BufferedWriter writer=FileHandler.getBufferedWriter(updatePath);
            String update;
            String stopWord;
            while((stopWord=reader.readLine())!=null){
                update= StringParser.removeBlank(stopWord);
                writer.write(update+"\r");
            }
            reader.close();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
