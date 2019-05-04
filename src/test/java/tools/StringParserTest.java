package tools;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @projectName: HFUTOntology
 * @packageName: tools
 * @Author: hanqing zhu
 * @Date: 15:44 2019/5/4
 * @Description:
 */
public class StringParserTest {

    public static Logger logger= LoggerFactory.getLogger(StringParserTest.class);

    @Test
    public void removeBlank() throws Exception {

        String test="合工大   ";
        System.out.println(StringParser.removeBlank(test));

    }

    @Test
    public void isMessyCode(){

        String test1="氨基酸坤彩科技dsdcs22112";
        String test2="���30";
        boolean r1=StringParser.isMessyCode(test1);
        boolean r2=StringParser.isMessyCode(test2);
        logger.info("{},{}",r1,r2);


    }


}