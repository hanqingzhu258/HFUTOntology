package dataMining.cluster;

import dataHandler.FileHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;

import static org.junit.Assert.*;

/**
 * @projectName: HFUTOntology
 * @packageName: dataMining.cluster
 * @Author: hanqing zhu
 * @Date: 21:27 2019/5/4
 * @Description:
 */
public class LJClusterTest {
    @Test
    public void init() throws Exception {

        boolean state=LJCluster.init("");
        Assert.assertEquals(true,state);

    }

    @Test
    public void setParameter() throws Exception {

        init();
        boolean state=LJCluster.setParameter(5,10);
        assertEquals(true,state);

    }

    @Test
    public void addContent() throws Exception {
    }

    @Test
    public void addFile() throws Exception {


    }

    @Test
    public void getLatestResult() throws Exception {

        System.out.println("初始化开始....");
        boolean flag = LJCluster.init("");
        if (flag) {
            System.out.println("初始化成功....");
        } else {
            System.out.println("初始化失败....");
            System.out.println(LJCluster.getLastErrMsg());
            System.exit(1);
        }

        LJCluster.setParameter(200,200);

        BufferedReader reader= FileHandler.getBufferedReader("dataCollection/result/filterTermsResult/partOfSpeechTaggingFilterTermsSet.txt");
        String content;
        int count=0;
        while ((content=reader.readLine())!=null){
            LJCluster.addContent(content,"term"+count++);
        }
        LJCluster.getLatestResult("dataCollection/result/clusterResult.txt");
        LJCluster.exit();


    }

    @Test
    public void getLatestResultE() throws Exception {
    }

    @Test
    public void cleanData() throws Exception {
    }

    @Test
    public void exit() throws Exception {
    }

    @Test
    public void getLastErrMsg() throws Exception {
    }


}