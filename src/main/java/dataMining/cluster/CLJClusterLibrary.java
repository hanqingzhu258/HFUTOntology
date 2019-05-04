package dataMining.cluster;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface CLJClusterLibrary extends Library {

    CLJClusterLibrary Instance=(CLJClusterLibrary) Native.loadLibrary("dataCollection/dlls/LJCluster",CLJClusterLibrary.class);

    public boolean CLUS_Init(String sDefaultPath,String sLicenseCode,int encode);

    public boolean CLUS_SetParameter(int nMaxClus, int nMaxDoc);

    public boolean CLUS_AddContent(String sText, String sSignature);

    public boolean CLUS_AddFile(String sFileName,String sSignature);

    public boolean CLUS_GetLatestResult(String sXmlFileName);

    public String CLUS_GetLatestResultE();

    public void CLUS_CleanData();

    public void CLUS_Exit();

    public String CLUS_GetLastErrMsg();
}