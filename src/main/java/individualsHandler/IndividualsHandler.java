package individualsHandler;

import classesHandler.ClassHierarchy;
import dataHandler.Crawler;
import individualsHandler.schools.ManagementSchoolIndividualHandler;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.jsoup.nodes.Document;
import propertiesHandler.SpecialPropertyHandler;
import tools.data.IndividualSelectorData;
import tools.enums.NSEnum;
import tools.fileHandler.IndividualFileParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @projectName: HFUTOntology
 * @packageName: individualsHandler
 * @Author: hanqing zhu
 * @Date: 10:54 2019/4/24
 * @Description:
 */
public class IndividualsHandler {

    /**
     * @Author: hanqing zhu
     * @Date: 10:55 2019/4/24
     * @Return:
     * @Description: 实体处理
     */
    public static void handleIndividuals(OntModel model) {
        /**
         * 增加学校机构
         */
        //获取学校机构网页内容
        Document organizationWeb = Crawler.getHTML("http://www.hfut.edu.cn/5287/list.htm");
        //类别及选择器
        /*Map<String, String> organizations = IndividualFileParser.parseIndividualFile("ontologyData/organizationSelectorFile.txt");*/
        List<IndividualSelectorData> organizations = IndividualFileParser.parseIndividualFile2("dataCollection/ontologyData/organizationSelectorFile.txt");
        //增添对应实体
        IndividualsAddition.addCollections(model, organizations, organizationWeb);

        /**
         * 增加学校领导
         */
        //学校现任领导
        Document currentLeadersWeb = Crawler.getHTML("http://www.hfut.edu.cn/5296/list.htm");
        List<IndividualSelectorData> currentLeaderLabelSelector=IndividualFileParser.parseIndividualFile2("dataCollection/ontologyData/uniCurrentLeaderSelectorFile.txt");
                //增添
        List<Individual> individuals = IndividualsAddition.addUniCurrentLeaders(model, currentLeaderLabelSelector, currentLeadersWeb);
                //将上述个体增加至指定类别（“学校现任领导”）下
        putIndividualsToClass(model, "学校现任领导", individuals);
            //学校历任领导
        Document pastLeaderWeb = Crawler.getHTML("http://www.hfut.edu.cn/5297/list.htm");
        List<IndividualSelectorData> data = IndividualFileParser.parseMulProIndividualFile("dataCollection/ontologyData/uniPastLeaderSelectorFile.txt");
                //增添
        List<Individual> pastLeaders = IndividualsAddition.addUniPastLeaders(model, data, pastLeaderWeb);
                //将上述个体增加至指定类别（“学校历任领导”）下
        putIndividualsToClass(model, "学校历任领导", pastLeaders);

        /**
         * 增加管院相关个体
         */
        ManagementSchoolIndividualHandler.handleMSIndividuals(model);
    }

    /**
     * @Author: hanqing zhu
     * @Date: 23:04 2019/4/27
     * @Return:
     *
     * @Description: 判断个体是否已存在，并获取已存在的个体
     */
    public static Individual getExistIndividual(String classLabel,Map<String,String> properties,String label,OntModel model){
        Individual individual=null;
        OntClass ontClass=ClassHierarchy.getClassByLabel(classLabel,model);
        for (ExtendedIterator<Individual> iterator=model.listIndividuals(ontClass);iterator.hasNext();){
            Individual testInd=iterator.next();
            if (testInd.getLabel(NSEnum.LANGUAGE.getNs()).equals(label)){
                boolean judge=true;
                if (properties!=null) {
                    for (Map.Entry entry : properties.entrySet()) {
                        String propertyLabel = (String) entry.getKey();
                        String propertyValue = (String) entry.getValue();
                        Statement statement = individual.getProperty(
                                SpecialPropertyHandler.getDatatypePropertyByLabel(propertyLabel, model)
                        );
                        if (!statement.getLiteral().equals(propertyValue)) {
                            judge = false;
                        }
                    }
                }
                if (judge){
                    individual=testInd;
                    break;
                }
            }
        }
        return individual;
    }


    /**
     * @Author: hanqing zhu
     * @Date: 20:47 2019/4/25
     * @Return:
     * @Description: 向标签为classLabel的类里面增加个体
     */
    public static void putIndividualsToClass(OntModel model, String classLabel, List<Individual> individuals) {
        OntClass ontClass = ClassHierarchy.getClassByLabel(classLabel, model);
//        System.out.println(ontClass.getLabel(NSEnum.LANGUAGE.getNs()));
        if (ontClass == null) {
            return;
        }
//        System.out.println(ontClass.getLabel(NSEnum.LANGUAGE.getNs()));
        for (Individual individual : individuals) {
//            System.out.println(individual.getLabel(NSEnum.LANGUAGE.getNs()));
            individual.addOntClass(ontClass);
        }
//        System.out.println("-----------------------------------");
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:19 2019/4/24
     * @Return:
     * @Description: 输出所有个体
     */
    public static void printAllIndividuals(OntModel model) {
        for (ExtendedIterator<Individual> iterator = model.listIndividuals(); iterator.hasNext(); ) {
            Individual individual = iterator.next();
            printIndividualInfo(individual, model, Arrays.asList("任期", "备注"));
        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:41 2019/4/26
     * @Return:
     * @Description: 输出某一类下的全部个体
     */
    public static void printAllIndividualsOfClass(OntModel model, String classLabel) {
        OntClass ontClass = ClassHierarchy.getClassByLabel(classLabel, model);
        for (ExtendedIterator<Individual> iterator = model.listIndividuals(ontClass); iterator.hasNext(); ) {
            Individual individual = iterator.next();
            printIndividualInfo(individual, model, Arrays.asList("任期", "备注", "职责"));
        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 17:03 2019/4/26
     * @Return:
     * @Description: 输出个体的所属类别及指定属性信息
     */
    public static void printIndividualInfo(Individual individual, OntModel model, List<String> designatedProperties) {
        String out = individual.getURI() + " : " + individual.getLabel(NSEnum.LANGUAGE.getNs())
                + "; belong to class: ";
        /**
         * 输出所属类别
         */
        out += printIndividualAllBelongedClasses(individual);
        /**
         * 输出所有属性
         */
        out += printDesignatedDatatypeProperties(designatedProperties, individual, model);

        System.out.println(out);
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:54 2019/4/26
     * @Return:
     * @Description: 输出个体的指定属性名称及其值
     */
    public static String printDesignatedDatatypeProperties(List<String> propertyLabels, Individual individual, OntModel model) {
        String out = "";
        for (String propertyLabel : propertyLabels) {
            Statement statement = individual.getProperty(SpecialPropertyHandler.getDatatypePropertyByLabel(propertyLabel, model));
            if (statement == null) {
                continue;
            }
            out += model.getOntResource(statement.getPredicate().getURI()).getLabel(NSEnum.LANGUAGE.getNs());
            out += "：";
            out += statement.getLiteral().getString();
            out += " @ ";
        }
        return out;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:59 2019/4/26
     * @Return:
     * @Description: 输出某一个体所属的全部类别
     */
    public static String printIndividualAllBelongedClasses(Individual individual) {
        String out = "";
        ExtendedIterator<OntClass> classes = individual.listOntClasses(true);
        while (classes.hasNext()) {
            out += (classes.next().getLabel(NSEnum.LANGUAGE.getNs()) + " # ");
        }
        return out;
    }
}
