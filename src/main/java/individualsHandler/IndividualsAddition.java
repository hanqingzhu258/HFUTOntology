package individualsHandler;

import classesHandler.ClassHierarchy;
import dataHandler.Crawler;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.algebra.op.OpN;
import org.jsoup.nodes.Document;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import propertiesHandler.SpecialPropertyHandler;
import tools.data.IndividualSelectorData;
import tools.enums.NSEnum;

import java.util.*;

/**
 * @projectName: HFUTOntology
 * @packageName: individualsHandler
 * @Author: hanqing zhu
 * @Date: 10:21 2019/4/24
 * @Description:
 */
public class IndividualsAddition {

    /**
     * @Author: hanqing zhu
     * @Date: 10:32 2019/4/24
     * @Return:
     * @Description: 创建标签为pre+label+suf的个体，所属类别为classLabel对应的类
     */
    public static Individual createIndividual(OntModel model, String classLabel, String label, String pre, String suf) {
        /**
         * 获取个体对应的类
         */
        OntClass ontClass = ClassHierarchy.getClassByLabel(classLabel, model);
        /**
         * 创建个体
         */
        String uri = pre + UUID.randomUUID().toString() + suf;
        Individual individual = model.createIndividual(uri, ontClass);

        individual.setLabel(label, NSEnum.LANGUAGE.getNs());
        return individual;
    }

    /**
     * 根据所属类别标签和选择器直接来增添个体
     */
    /**
     * @Author: hanqing zhu
     * @Date: 10:29 2019/4/24
     * @Return:
     * @Description: 根据所属“类别及选择器”集合，进行网页解析并向model中增添相应个体
     */
    public static void addCollections(OntModel model, List<IndividualSelectorData> isds, Document document) {

        /**
         * 遍历类标签及其对应的选择器
         */
        /*for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String classLabel = (String) entry.getKey();
            String selectors = (String) entry.getValue();
            addCollection(model, classLabel, selectors, document);
        }*/
        for (IndividualSelectorData isd:isds){
            String classLabel = isd.getClassLabelSelector();
            String selectors = isd.getLabelSelector();
            addCollection(model, classLabel, selectors, document);
        }

    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:29 2019/4/24
     * @Return:
     * @Description: 根据所属“类别及选择器”，进行单个个体的添加
     */
    public static void addCollection(OntModel model, String classLabel, String selector, Document document) {
        /**
         * 根据选择器和网页
         * 选择该类下的个体标签集合
         */
        List<String> labels = Crawler.getSpecifiedContentInText(selector, document);
        for (String label : labels) {
            /**
             * 指明创建个体的前缀和后缀，已确定完整名称
             */
            createIndividual(model, classLabel, label, NSEnum.HFUT.getNs(), "");
        }

    }

    /**
     * 上述中的类标签也需要通过选择器来获取
     */
    /**
     * @Author: hanqing zhu
     * @Date: 20:36 2019/4/25
     * @Return:
     * @Description: 增加学校现任领导,注意有的领导身兼多职
     */
    public static List<Individual> addUniCurrentLeaders(OntModel model, List<IndividualSelectorData> isds, Document document) {
        List<Individual> individuals = new ArrayList<Individual>();
        for (IndividualSelectorData isd:isds ) {
            /**
             * 获取对应的姓名列表和职位列表
             */
            List<String> ranks = Crawler.getSpecifiedContentInText(isd.getClassLabelSelector(),document);
            List<String> names = Crawler.getSpecifiedContentInText(isd.getLabelSelector(), document);
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                String rank = ranks.get(i);
                Individual individual;
                individual=createMulClassIndividuals(model,rank,"、",name,NSEnum.HFUT.getNs(),"");
                individuals.add(individual);
            }
        }
        return individuals;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 21:54 2019/4/25
     * @Return:
     * @Description: 增加学校历任领导
     */
    public static List<Individual> addUniPastLeaders(OntModel model, List<IndividualSelectorData> data, Document document) {
        List<Individual> individuals = new ArrayList<Individual>();
        for (IndividualSelectorData isd : data) {
            List<String> names = Crawler.getSpecifiedContentInText(isd.getLabelSelector(), document);
            List<String> classLabels = Crawler.getSpecifiedContentInText(isd.getClassLabelSelector(), document);
            List<List<String>> props = new ArrayList<List<String>>();
            for (String prop : isd.getPropertyLabels()) {
                List<String> prop1 = Crawler.getSpecifiedContentInText(prop, document);
                props.add(prop1);
            }
            List<String> propertyLabels = new ArrayList<String>();
            for (int i = 0; i < props.size(); i++) {
                propertyLabels.add(props.get(i).get(0));
            }
            for (int i = 1; i < names.size(); i++) {

                Individual individual;

                individual=createMulClassIndividuals(model,classLabels.get(i),"、",names.get(i),NSEnum.HFUT.getNs(),"");

                for (int j = 0; j < propertyLabels.size(); j++) {
                    addDatatypePropertyToIndividual(individual,model,propertyLabels.get(j),props.get(j).get(i));
                }

                individuals.add(individual);
            }
        }
        return individuals;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:17 2019/4/26
     * @Return:
     * 
     * @Description: 类标签中包含多个现有类，并以split字符隔开，创建符合此类条件的个体Individual
     */
    public static Individual createMulClassIndividuals(OntModel model,String classLabel,String split,String label,String pre,String suf){
        Individual individual=null;
        if (classLabel.contains(split)){
            String [] classes=classLabel.split(split);
            individual=createIndividual(model,classes[0],label,pre,suf);
            for (int i=1;i<classes.length;i++){
                IndividualsHandler.putIndividualsToClass(model,classes[i],Arrays.asList(individual));
            }
        }else{
            individual=createIndividual(model,classLabel,label,pre,suf);
        }
        return individual;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:28 2019/4/26
     * @Return:
     * 
     * @Description: 向个体增加属性名为propertyLabel，属性值为propertyValue(字符串literal类型)的属性
     */
    public static void addDatatypePropertyToIndividual(Individual individual,OntModel model,String propertyLabel,String propertyValue){
        DatatypeProperty property=SpecialPropertyHandler.getDatatypePropertyByLabel(propertyLabel,model);
        individual.setPropertyValue(property,model.createLiteral(propertyValue));
    }
}


