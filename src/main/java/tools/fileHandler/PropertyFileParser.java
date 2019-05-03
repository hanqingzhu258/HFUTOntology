package tools.fileHandler;

import dataHandler.FileHandler;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.fileHandler
 * @Author: hanqing zhu
 * @Date: 9:54 2019/4/22
 * @Description:
 */
public class PropertyFileParser {

    /*public static void main(String[] args) {
        String fileURL = "ontologyData/objectPropertyHierarchy.txt";
        List<TempPropertyResource> resources=null;
        try {
            resources=parseFile(fileURL);
        }catch (Exception e){
            e.printStackTrace();
        }
        printAllTempPropertyResources(resources);
    }*/

    /**
     *解析属性文档
     */
    public static List<TempPropertyResource> parseFile(String url) throws Exception {
        BufferedReader reader = FileHandler.getBufferedReader(url);
        //存放每行的信息
        List<TempPropertyResource> tempResources=new ArrayList<TempPropertyResource>();
        TempPropertyResource tr1=null;
        TempPropertyResource tr2;

        //行文本
        String lineText;
        //分隔数组
        String [] tempLabel;
        //label
        String label;
        //domainClassLabel
        String domainLabel;
        //rangeClassLabel
        String rangeLabel;
        //行标
        int line=0;
        //属性深度
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

            tempLabel=lineText.substring(i,lineText.length()).split("：");
            //获取真正的标签内容
            label=tempLabel[0];
            if (tempLabel.length==1){
                domainLabel=rangeLabel=null;
            }else{
                String temp[];
                temp=tempLabel[1].split("-");
                //获取domain域标签
                domainLabel=temp[0];
                //获取range域标签
                rangeLabel=temp[1];
            }

            //计算属性深度
            deep=count/4;
            //当前行标
            line++;
            //确定父属性
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
            tr2=new TempPropertyResource(line,deep,label,tr1,domainLabel,rangeLabel);
            tempResources.add(tr2);
        }
        return tempResources;
    }

    /**
     *输出所有的tempPropertyResource资源
     */
    public static void printAllTempPropertyResources(List<TempPropertyResource> resources){
        if (resources.size()==0){
            System.out.println("暂无资源");
            return;
        }

        for (TempPropertyResource resource:resources){
            String prS="line "+resource.getLine()+":"+resource.getLabelText()+
                    "---deep "+resource.getDeep()+"@domain:"+resource.getDomainClassLabel()+
                    " @range:"+resource.getRangeClassLabel()+"---parent:";
            if (resource.getParent()==null){
                System.out.println(prS+"null");
            }else {
                System.out.println(prS+resource.getParent().getLabelText());
            }

        }
    }

}
