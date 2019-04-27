package tools.fileHandler;

import dataHandler.FileHandler;
import tools.data.IndividualSelectorData;

import java.io.BufferedReader;
import java.util.*;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.fileHandler
 * @Author: hanqing zhu
 * @Date: 11:25 2019/4/24
 * @Description:
 */
public class IndividualFileParser {

    /**
     * @Author: hanqing zhu
     * @Date: 21:36 2019/4/25
     * @Return:
     * 
     * @Description: 解析结构为“类标签（选择器）：个体标签（选择器）”的文件
     */
    public static Map<String,String> parseIndividualFile(String fileUrl){
        Map<String,String> map=new HashMap<String, String>();
        String lineText;
        String [] texts;
        BufferedReader reader=null;
        try {
            reader = FileHandler.getBufferedReader(fileUrl);
            while ((lineText=reader.readLine())!=null){
                texts=lineText.split("：");
                map.put(texts[0],texts[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }

    public static List<IndividualSelectorData> parseIndividualFile2(String fileUrl){
        List<IndividualSelectorData> isds=new ArrayList<IndividualSelectorData>();
        String lineText;
        String [] texts;
        BufferedReader reader=null;
        try {
            reader = FileHandler.getBufferedReader(fileUrl);
            while ((lineText=reader.readLine())!=null){
                texts=lineText.split("：");
                IndividualSelectorData isd=new IndividualSelectorData(texts[0],texts[1],null);
                isds.add(isd);
//                System.out.println(isd.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(isds.size());
        return isds;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 21:36 2019/4/25
     * @Return:
     *
     * @Description: 解析结构为“类标签：个体标签@属性1%属性2%...%属性x”的文件
     */
    public static List<IndividualSelectorData> parseMulProIndividualFile(String fileUrl){
        List<IndividualSelectorData> data=new ArrayList<IndividualSelectorData>();
        String lineText;
        String lineTextL;
        String lineTextR;
        String textL[];
        String textR[];
        try {
            BufferedReader reader = FileHandler.getBufferedReader(fileUrl);
            while ((lineText = reader.readLine()) != null) {
                lineTextL = lineText.split("@")[0];
                lineTextR = lineText.split("@")[1];
                textL = lineTextL.split("：");
                textR = lineTextR.split("%");
                IndividualSelectorData isd = new IndividualSelectorData(textL[0], textL[1], Arrays.asList(textR));
                data.add(isd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
