package dataMining.cluster;

import dataHandler.FileHandler;

import java.io.BufferedReader;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.cluster
 * @Author: hanqing zhu
 * @Date: 0:04 2019/5/5
 * @Description:
 */
public class ClusterProcess {

    public static void main(String[] args) throws Exception {
        System.out.println("初始化开始....");
        boolean flag = LJCluster.init("");
        if (flag) {
            System.out.println("初始化成功....");
        } else {
            System.out.println("初始化失败....");
            System.out.println(LJCluster.getLastErrMsg());
            System.exit(1);
        }

        LJCluster.setParameter(50,50);

        String textPath="dataCollection/result/filterTermsResult/lowFrequencyWordFilterTermsSet.txt";
        BufferedReader reader= FileHandler.getBufferedReader(textPath);

        String content;
        int count=0;
        while ((content=reader.readLine())!=null){
            LJCluster.addContent(content,"term"+count++);
        }
        LJCluster.getLatestResult("dataCollection/result/clusterResult.txt");
        LJCluster.exit();
    }

}
