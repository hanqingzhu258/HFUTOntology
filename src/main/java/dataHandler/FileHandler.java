package dataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

}
