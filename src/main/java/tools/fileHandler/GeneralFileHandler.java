package tools.fileHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.fileHandler
 * @Author: hanqing zhu
 * @Date: 17:09 2019/5/3
 * @Description:
 */
public class GeneralFileHandler {

    /**
     * @Author: hanqing zhu
     * @Date: 17:09 2019/5/3
     * @Return:
     *
     * @Description: 获取某个文件夹下的所有文件
     */
    public static List<String> getAllFileName(String path){

        List<String> fileNames=new ArrayList<String>();

        File file=new File(path);
        if (!file.isDirectory()){
            System.out.println("该路径不是文件夹");
            return null;
        }
        File [] tempList=file.listFiles();
        for (int i=0;i<tempList.length;i++){
            if (tempList[i].isFile()){
                fileNames.add(tempList[i].getName());
            }
        }

        return fileNames;
    }

}
