package tools.fileHandler;

import dataHandler.FileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: tests
 * @Author: hanqing zhu
 * @Date: 16:59 2019/4/21
 * @Description:
 */
public class ClassFileParser {

    /*public static void main(String[] args) {
        String fileURL = "ontologyData/classHierarchy.txt";
        List<TempClassResource> resources=null;
        try {
            resources=parseFile(fileURL);
        }catch (Exception e){
            e.printStackTrace();
        }
        printAllTempResources(resources);
    }*/

    /**
     *解析类文档
     */
    public static List<TempClassResource> parseFile(String url) throws Exception {
        BufferedReader reader = FileHandler.getBufferedReader(url);
        //存放每行的信息
        List<TempClassResource> tempResources=new ArrayList<TempClassResource>();
        TempClassResource tr1=null;
        TempClassResource tr2;

        //行文本
        String lineText;
        //label
        String label;
        //行标
        int line=0;
        //类深度
        int deep=0;

        while((lineText=reader.readLine())!=null){
            //辅助计数
            int count=0;
            //计算空格数量，以确定类等级
            int i;
            for (i=0;i<lineText.length();i++){
                if (lineText.charAt(i)==' '){
                    count++;
                    continue;
                }
                break;
            }
            //获取真正的标签内容
            label=lineText.substring(i,lineText.length()-1);
            //计算类深度
            deep=count/4;
            //当前行标
            line++;
            //确定父类
            if (tempResources.size()==0){
                tr1=null;
            }else{
                for (int j=tempResources.size()-1;j>=0;j--){
                    if (tempResources.get(j).getDeep()==deep-1){
                        tr1=tempResources.get(j);
                        break;
                    }
                }
            }
            //创建新资源
            tr2=new TempClassResource(line,deep,label,tr1);
            tempResources.add(tr2);
        }
        return tempResources;
    }

    /**
     *输出所有的tempResource资源
     */
    public static void printAllTempResources(List<TempClassResource> resources){
        if (resources.size()==0){
            System.out.println("暂无资源");
            return;
        }

        for (TempClassResource resource:resources){
            String prS="line "+resource.getLine()+":"+resource.getLabelText()+
                    "---deep "+resource.getDeep()+"---parent:";
            if (resource.getParent()==null){
                System.out.println(prS+"null");
            }else {
                System.out.println(prS+resource.getParent().getLabelText());
            }

        }
    }
}
