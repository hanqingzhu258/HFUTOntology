package dataMining.ICTCLAS;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.ICTCLAS
 * @Author: hanqing zhu
 * @Date: 16:18 2019/5/3
 * @Description:
 */
public class NlpirMethodTest {

    public static Logger logger = LoggerFactory.getLogger(NlpirMethodTest.class);

    @Before
    @Test
    public void NLPIR_Init() throws Exception {
        boolean flag = NlpirMethod.NLPIR_Init("", 1, "");
        Assert.assertEquals(flag, true);
    }

    @Test
    public void NLPIR_ParagraphProcess() throws Exception {

        String sInput = "东方网12月4日消息：2009年10月21日,辽宁省阜新市委\n" +
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

        String result = null;
        result = NlpirMethod.NLPIR_ParagraphProcess(sInput, 0);
        Assert.assertNotNull(result);
        logger.info(result);
    }

    @Test
    public void NLPIR_FileProcess() throws Exception {

        NlpirMethod.NLPIR_FileProcess("dataCollection/datasource/HFUT0.txt",
                "dataCollection/result/testText.txt", 0);

    }

    @Test
    public void NLPIR_GetKeyWords() throws Exception {
    }

    @Test
    public void NLPIR_GetFileKeyWords() throws Exception {
    }

    @Test
    public void NLPIR_GetNewWords() throws Exception {
    }

    @Test
    public void NLPIR_GetFileNewWords() throws Exception {

        String result = null;
        result = NlpirMethod.NLPIR_GetFileNewWords("dataCollection/datasource/HFUT0.txt", 10, false);
        Assert.assertNotNull(result);
        logger.info(result);

    }

    @Test
    public void NLPIR_AddUserWord() throws Exception {

        int flag;
        flag = NlpirMethod.NLPIR_AddUserWord("合肥工业大学 ");
        logger.info(flag + "");
        flag = NlpirMethod.NLPIR_AddUserWord("合肥工业大学 ");
        logger.info(flag + "");
    }

    @Test
    public void NLPIR_SaveTheUsrDic() throws Exception {

        NLPIR_AddUserWord();
        NlpirMethod.NLPIR_SaveTheUsrDic();

    }

    @Test
    public void NLPIR_DelUsrWord() throws Exception {

//        NlpirMethod.NLPIR_DelUsrWord("合肥工业大学");
        String text="合肥工业大学也大学爸爸的精神病健康才八点上课就不山大看就看";
        String result=NlpirMethod.NLPIR_ParagraphProcess(text,1);
        logger.info(result);
    }

    @Test
    public void NLPIR_ImportUserDict() throws Exception {
        int wordsCount = 0;
        wordsCount = NlpirMethod.NLPIR_ImportUserDict("dataCollection/dics/HFUTDictory.txt", false);
        logger.info("wordsCount:{}", wordsCount);
    }

    @Test
    public void NLPIR_ImportKeyBlackList() throws Exception {
    }

    @Test
    public void NLPIR_FingerPrint() throws Exception {
    }

    @Test
    public void NLPIR_GetWordPOS() throws Exception {
    }

    @Test
    public void NLPIR_IsWord() throws Exception {
    }

    @Test
    public void NLPIR_WordFreqStat() throws Exception {
    }

    @Test
    public void NLPIR_FileWordFreqStat() throws Exception {
    }

    @Test
    public void NLPIR_GetEngWordOrign() throws Exception {
    }

    @Test
    public void NLPIR_GetLastErrorMsg() throws Exception {
    }

    @Test
    public void NLPIR_Exit() throws Exception {
    }

}