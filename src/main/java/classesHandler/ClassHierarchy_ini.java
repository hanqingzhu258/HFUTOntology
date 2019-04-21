package classesHandler;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import tools.enums.NSEnum;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: classesHandler
 * @Author: hanqing zhu
 * @Date: 19:54 2019/4/20
 * @Description:
 */
public class ClassHierarchy_ini {

    /**
     * @Author: hanqing zhu
     * @Date: 20:03 2019/4/20
     * @Return:
     *
     * @Description: 创建本体的类结构：class hierarchy
     */
    public static void createClassHierarchy(OntModel model){
        /**
         *创建最顶层类：HFUT
         */
        OntClass HFUT= ClassCreation.createOntClass("HFUT",NSEnum.HFUT.getNs(),"",model,null);
        /**
         *创建顶层概念类：专业、人物、学科、学校、教学、机构、科研、课程
         */
        List<String> labels= Arrays.asList("专业","人物","学科","学校","教学","机构","科研","课程");
        ClassCreation.createClassesInBatches(labels,NSEnum.HFUT.getNs(),NSEnum.GENERAL.getNs(),model,HFUT);
        /**
         *创建“人物”类层级：其他人物、学生、
         *      教师{院士（中国工程院院士）、长江学者、其他人才、硕士生导师、博士生导师、教授、副教授、讲师、学院教师（管理学院教师）}、校友、
         *      领导{党委领导（党委书记、党委代理书记、党委副书记、纪委书记）、学校领导（学校历任领导、学校现任领导）、学院领导（管理学院领导）、
         *      行政领导（主任委员、代理校长、副主任委员、副校长、副院长、巡视员、总会计师、校长、院长、革委会主任、革委会副主任）}
         */
        OntClass person=getClassByLable("人物",model,HFUT);
        List<String> personLabels=Arrays.asList("其他人物","学生","教师","校友","领导");
        ClassCreation.createClassesInBatches(personLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,person);
            //“教师”层级
        OntClass teacher=getClassByLable("教师",model,person);
        List<String> teacherLabels=Arrays.asList("院士","长江学者","其他人才","硕士生导师","博士生导师","教授","副教授","讲师","学院教师");
        ClassCreation.createClassesInBatches(teacherLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,teacher);
                //“学院教师”层级
        OntClass teacher_school=getClassByLable("学院教师",model,teacher);
        List<String> tsLabels=Arrays.asList("管理学院教师");
        ClassCreation.createClassesInBatches(tsLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,teacher_school);
                //“院士”层级
        OntClass academician=getClassByLable("院士",model,teacher);
        List<String> acLabels=Arrays.asList("中国工程院院士");
        ClassCreation.createClassesInBatches(acLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,teacher_school);
            //“领导”层级
        OntClass leader=getClassByLable("领导",model,person);
        List<String> leaderLabels=Arrays.asList("党委领导","学校领导","学院领导","行政领导");
        ClassCreation.createClassesInBatches(leaderLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,leader);
                //“党委领导”层级
        OntClass partyCommitteeLeader=getClassByLable("党委领导",model,leader);
        List<String> pclLabels=Arrays.asList("党委书记","党委代理书记","党委副书记","纪委书记");
        ClassCreation.createClassesInBatches(pclLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,partyCommitteeLeader);
                //“学校领导”层级
        OntClass uniLeader=getClassByLable("学校领导",model,leader);
        List<String> ulLabels=Arrays.asList("学校历任领导","学校现任领导");
        ClassCreation.createClassesInBatches(ulLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,uniLeader);
                //“学院领导”层级
        OntClass schLeader=getClassByLable("学院领导",model,leader);
        List<String> schlLabels=Arrays.asList("管理学院领导");
        ClassCreation.createClassesInBatches(schlLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,schLeader);
                //“行政领导”层级
        OntClass exeLeader=getClassByLable("行政领导",model,leader);
        List<String> exeLLebels=Arrays.asList("主任委员","代理校长","副主任委员","副校长","副院长","巡视员","总会计师","校长","院长","革委会主任","革委会副主任");
        ClassCreation.createClassesInBatches(exeLLebels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,exeLeader);
        /**
         *创建“学科”类层级：一级学科、二级学科、学科门类
         */
        OntClass discipline=getClassByLable("学科",model,HFUT);
        List<String> disLabels=Arrays.asList("一级学科","二级学科","学科门类");
        ClassCreation.createClassesInBatches(disLabels,NSEnum.HFUT.getNs(),NSEnum.PERSON.getNs(),model,discipline);


    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:45 2019/4/21
     * @Return:
     *
     * @Description: 输出模型中的所有类
     */
    public static void printAllClasses(OntModel model){
        for(ExtendedIterator<OntClass> iterator = model.listClasses(); iterator.hasNext();){
            OntClass ontClass=iterator.next();
            System.out.println(ontClass.getURI()+":"+ontClass.getLabel(NSEnum.LANGUAGE.getNs())+":"+ontClass.getRDFType());
        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:06 2019/4/21
     * @Return:
     *
     * @Description: 根据标签和父类来获取对应类
     */
    public static OntClass getClassByLable(String label,OntModel model,OntClass parent){
        OntClass findedClass=null;
        for (Iterator<OntClass> iterator = parent.listSubClasses(true); iterator.hasNext();){
            findedClass=iterator.next();
            if (findedClass.getLabel(NSEnum.LANGUAGE.getNs()).equals(label)){
                break;
            }
        }
        return findedClass;
    }

}
