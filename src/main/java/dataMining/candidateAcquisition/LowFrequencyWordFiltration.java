package dataMining.candidateAcquisition;

import dataHandler.FileHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.LDAProcess
 * @Author: hanqing zhu
 * @Date: 16:57 2019/5/4
 * @Description:
 */
public class LowFrequencyWordFiltration {

    public static void main(String[] args) {

        /**
         * 低频词过滤:
         *      去除低频词，首先统计所有术语在领域术语集中的出现频率，再通过设定阈值将出现频率过低的术语过滤掉。
         */
        //设置域值:去除频率低于域值的词汇
        Integer threshold=50;
        filterLowFrequencyWord(threshold);

    }
    
    /**
     * @Author: hanqing zhu
     * @Date: 16:58 2019/5/4
     * @Return:
     * 
     * @Description: 低频词过滤:
     *                  去除低频词，首先统计所有术语在领域术语集中的出现频率，再通过设定阈值将出现频率过低的术语过滤掉。
     */
    public static void filterLowFrequencyWord(Integer threshold){

        try {
            String partOfSpeechTaggingFilterTermsSetPath = "dataCollection/result/filterTermsResult/partOfSpeechTaggingFilterTermsSet.txt";
            BufferedReader reader = FileHandler.getBufferedReader(partOfSpeechTaggingFilterTermsSetPath);
            String lowFrequencyWordFilterTermsSetPath="dataCollection/result/filterTermsResult/lowFrequencyWordFilterTermsSet.txt";
            BufferedWriter writer=FileHandler.getBufferedWriter(lowFrequencyWordFilterTermsSetPath);
            //统计词频
            Map<String,Integer> frequency=new TreeMap<>();
            List<Map.Entry<String,Integer>> sortedList=new ArrayList<>();
            List<String> terms=new ArrayList<String>();
            String content;
            while ((content=reader.readLine())!=null){
                if (terms.contains(content)){
                    int count=frequency.get(content) + 1;
//                    frequency.replace(content,count);
                    frequency.put(content,count);
                }else{
                    terms.add(content);
                    frequency.put(content,0);
                }
            }
            //词频排序
            sortedList.addAll(frequency.entrySet());
            Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o1.getValue()-o2.getValue();
                }
            });

            for (Map.Entry<String,Integer> entry:sortedList){
                if (entry.getValue()<threshold){
                    continue;
                }
                writer.write(entry.getKey()+"\n");
            }

            reader.close();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
