package individualsHandler.schools;

import dataHandler.Crawler;
import individualsHandler.IndividualsAddition;
import individualsHandler.IndividualsHandler;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.jsoup.nodes.Document;
import tools.StringParser;
import tools.data.IndividualSelectorData;
import tools.enums.NSEnum;
import tools.fileHandler.IndividualFileParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @projectName: HFUTOntology
 * @packageName: individualsHandler.schools
 * @Author: hanqing zhu
 * @Date: 11:12 2019/4/28
 * @Description: 处理管理学院有关个体
 */
public class ManagementSchoolIndividualHandler {

    public static void handleMSIndividuals(OntModel model){
        /**
         * 增加管院领导
         */
        Document msLeadersWeb = Crawler.getHTML("http://som.hfut.edu.cn/glxy/xygk/xyld/index.htm");
        List<IndividualSelectorData> msLeaderLabelSelector = IndividualFileParser.parseIndividualFile2("data/managementSchoolLeaderSelector.txt");
        List<Individual> msLeaders = addMSLeaders(model, msLeaderLabelSelector, msLeadersWeb);
        IndividualsHandler.putIndividualsToClass(model, "管理学院领导", msLeaders);

        /**
         *  增加管理学院教师
         */
        Map<String, List<String>> MSteachersInfo =
                StringParser.parseMSData(
                        Crawler.getJson("http://som.hfut.edu.cn/wgp/generalForms/getJsmlDataFormsData.do?ids=1001")
                );
        addMSTeachers(model,MSteachersInfo);
//        putIndividualsToClass(model,"管理学院教师",msTeachers);
        //教师和系所之间不是类与个体的关系，而是通过对象属性联系在一起的关系
        /*Map<String, List<String>> MSteachersInfo2 =
                StringParser.parseMSData(
                        Crawler.getJson("http://som.hfut.edu.cn/wgp/generalForms/getJsmlDataFormsData.do?ids=1002")
                );
        IndividualsAddition.addMSTeachers(model,MSteachersInfo2);*/
        Map<String, List<String>> MSteachersInfo3 =
                StringParser.parseMSData(
                        Crawler.getJson("http://som.hfut.edu.cn/wgp/generalForms/getJsmlDataFormsData.do?ids=1003")
                );
        addMSTeachers(model,MSteachersInfo3);
        Map<String, List<String>> MSteachersInfo4 =
                StringParser.parseMSData(
                        Crawler.getJson("http://som.hfut.edu.cn/wgp/generalForms/getJsmlDataFormsData.do?ids=1004")
                );
        addMSTeachers(model,MSteachersInfo4);

        /**
         * 增加管理学院系所
         */
        List<Individual> msDepartment=IndividualsAddition.addSomeIndividuals(
                model, Arrays.asList("信息管理系","电子商务系","会计系","工商管理系","物流管理系"),
                "系所");
        IndividualsHandler.putIndividualsToClass(model,"管理学院机构",msDepartment);
    }

    /**
     * @Author: hanqing zhu
     * @Date: 9:55 2019/4/27
     * @Return:
     *
     * @Description: 增添管理学院领导
     */
    public static List<Individual> addMSLeaders(OntModel model,List<IndividualSelectorData> data,Document document){
        List<Individual> individuals=new ArrayList<Individual>();
        for (IndividualSelectorData isd:data){
            List<String> cat_names=Crawler.getSpecifiedContentInText(isd.getClassLabelSelector(),document);
            List<String> responsibilities=Crawler.getSpecifiedContentInText(isd.getLabelSelector(),document);
            for (int i=0;i<cat_names.size();i++){
                String [] cat_name=cat_names.get(i).split("：");
                String classLabel=cat_name[0];
                String label=cat_name[1];
                Individual individual=IndividualsAddition.createMulClassIndividuals(model,classLabel,"、",label, NSEnum.HFUT.getNs(),"");
                IndividualsAddition.addDatatypePropertyToIndividual(individual,model,"职责",responsibilities.get(i));
                individuals.add(individual);
            }

        }
        return individuals;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 22:22 2019/4/27
     * @Return:
     *
     * @Description: 增加管理学院教师
     */
    public static List<Individual> addMSTeachers(OntModel model,Map<String,List<String>> msts) {
        List<Individual> individuals=new ArrayList<Individual>();
        for (Map.Entry entry : msts.entrySet()) {
            String classLabel= StringParser.removeBrackets((String) entry.getKey());
            for (String label:(List<String>)entry.getValue()){
                Individual individual=IndividualsHandler.getExistIndividual("管理学院教师",null,label,model);
                if (individual==null){
                    individual=IndividualsAddition.createIndividual(model,classLabel,label,NSEnum.HFUT.getNs(),"");
                    IndividualsHandler.putIndividualsToClass(model,"管理学院教师",Arrays.asList(individual));
                }else{
                    IndividualsHandler.putIndividualsToClass(model,classLabel,Arrays.asList(individual));
                }
                individuals.add(individual);
            }
        }
//        System.out.println("------------------------------------------------------");
        return individuals;
    }
}
