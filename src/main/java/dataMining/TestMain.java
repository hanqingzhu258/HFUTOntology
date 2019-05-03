package dataMining;

import dataMining.newWordFinder.NewWordFinder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @projectName: newWordFinder
 * @packageName: PACKAGE_NAME
 * @Author: hanqing zhu
 * @Date: 10:05 2019/5/3
 * @Description:
 */
public class TestMain {

    public static final Logger logger= LoggerFactory.getLogger(TestMain.class);

    @Test
    public void testNewWordFinder(){
        String argu="";

        int init_flag= NewWordFinder.init(argu);
        if (init_flag==0){
            System.out.println("初始化失败！");
            return;
        }else{
            System.out.println("初始化成功！");
        }

        String sInput="东方网12月4日消息：2009年10月21日,辽宁省阜新市委\n" +
                "收到举报信,举报以付玉红为首吸毒、强奸、聚众淫乱,阜新市委政法委副书记于洋等参与\n" +
                "吸毒、强奸、聚众淫乱等。对此,阜新市委高度重视,责成阜新市公安局立即成立调查组,抽\n" +
                "调精干力量展开调查。 调查期间,署名举报人上官宏祥又通过尹东方(女)向阜新市公安\n" +
                "局刑警支队提供书面举报,举报于洋等参与吸毒、强奸、聚众淫乱。11月19日,正义网发表\n" +
                "上官宏祥接受记者专访,再次实名举报于洋等参与吸毒、强奸、聚众淫乱,引起网民广泛关\n" +
                "注。对此辽宁省政法委、省公安厅高度重视。当日,责成有关领导专程赴阜新听取案件调查\n" +
                "情况。为加强对案件的督办和指导,省有关部门迅速成立工作组,赴阜新督办、指导案件调\n" +
                "查工作,并将情况上报有关部门。 经前一段调查证明,举报事实不存在,上官宏祥行为触\n" +
                "犯《刑法》第243条,涉嫌诬告陷害罪。根据《刑事诉讼法》有关规定,阜新市公安局已于\n" +
                "11月27日依法立案侦查。上官宏祥已于2009年12月1日到案,12月2日阜新市海州区人大常\n" +
                "委会已依法停止其代表资格,阜新市公安局对其进行刑事拘留,并对同案人尹东方进行监视\n" +
                "居住。现侦查工作正在进行中";
        String nativeBytes=null;
        try{
            nativeBytes=NewWordFinder.getNewWords(sInput,5,false);
//            NewWordFinder.result2UserDict();
            System.out.println(nativeBytes);
            logger.info("this is a test of slf4j");
            System.out.println(
                    NewWordFinder.getFileNewWords("dataCollection/datasource/HFUT0.txt",10,false)
            );

            NewWordFinder.exit();
        }catch (Exception e){

        }
    }

//    public static void main(String[] args) {
//        logger.info("ssssssssssssssssssssssss");
//    }


}
