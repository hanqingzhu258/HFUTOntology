package dataHandler;

import java.io.*;

/**
 * @projectName: HFUTOntology
 * @packageName: dataHandler
 * @Author: hanqing zhu
 * @Date: 17:03 2019/4/23
 * @Description:
 */
public class FileHandler {

    /**
     * @Author: hanqing zhu
     * @Date: 17:04 2019/4/23
     * @Return:
     *
     * @Description: 获取相应的文件输入流，一次读取一行
     */
    public static BufferedReader getBufferedReader(String fileUrl) throws Exception{
        ClassLoader classLoader = FileHandler.class.getClassLoader();
        File file = new File(classLoader.getResource(fileUrl).getFile());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 15:45 2019/4/28
     * @Return:
     *
     * @Description: 获取相应的文件输出流，一次输出一行
     */
    public static BufferedWriter getBufferedWriter(String fileUrl) throws Exception{
        File file = new File(fileUrl);
        if (file.exists()){
            file.mkdir();
        }
        if (!file.exists()){
            file.createNewFile();
        }
        BufferedWriter writer=new BufferedWriter(new FileWriter(file));
        return writer;
    }
}
