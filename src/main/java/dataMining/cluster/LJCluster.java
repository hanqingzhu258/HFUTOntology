package dataMining.cluster;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.cluster
 * @Author: hanqing zhu
 * @Date: 21:24 2019/5/4
 * @Description:
 */
public class LJCluster {

    public static boolean state=false;

    public static boolean init(String arg){
        state=CLJClusterLibrary.Instance.CLUS_Init(arg, null, 1);
        if(state)
            return state;
        else{
            System.out.println(CLJClusterLibrary.Instance.CLUS_GetLastErrMsg());
            return false;
        }
    }

    public static boolean setParameter(int nMaxClus, int nMaxDoc){
        if(state){
            return CLJClusterLibrary.Instance.CLUS_SetParameter(nMaxClus, nMaxDoc);
        }else{
            return false;
        }
    }

    public static boolean addContent(String sText, String sSignature){
        if(state){
            return CLJClusterLibrary.Instance.CLUS_AddContent(sText, sSignature);
        }else
            return false;
    }

    public static boolean addFile(String sFileName, String sSignature){
        if(state){
            return CLJClusterLibrary.Instance.CLUS_AddFile(sFileName, sSignature);
        }else
            return false;
    }

    public static boolean getLatestResult(String sXmlFileName){
        if(state)
            return CLJClusterLibrary.Instance.CLUS_GetLatestResult(sXmlFileName);
        else
            return false;
    }

    public static String getLatestResultE(){
        if(state)
            return CLJClusterLibrary.Instance.CLUS_GetLatestResultE();
        else
            return null;
    }

    public static void cleanData(){
        CLJClusterLibrary.Instance.CLUS_CleanData();
    }

    public static void exit(){
        CLJClusterLibrary.Instance.CLUS_Exit();
    }

    public static String getLastErrMsg(){
        return CLJClusterLibrary.Instance.CLUS_GetLastErrMsg();
    }
}