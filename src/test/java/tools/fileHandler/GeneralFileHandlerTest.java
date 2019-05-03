package tools.fileHandler;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.fileHandler
 * @Author: hanqing zhu
 * @Date: 17:17 2019/5/3
 * @Description:
 */
public class GeneralFileHandlerTest {

    public static Logger logger= LoggerFactory.getLogger(GeneralFileHandler.class);

    @Test
    public void getAllFileName() throws Exception {
        String path="dataCollection/datasource/";
        List<String> fileNames=GeneralFileHandler.getAllFileName(path);
        for (String fileName:fileNames){
            logger.info(fileName);
        }
    }

}