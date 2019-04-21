package classesHandler;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import tools.enums.NSEnum;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @projectName: HFUTOntology
 * @packageName: tools
 * @Author: hanqing zhu
 * @Date: 9:42 2019/4/21
 * @Description: 资源创建相关方法
 */
public class ClassCreation {

    /**
     * @Author: hanqing zhu
     * @Date: 20:55 2019/4/20
     * @Return:
     * @param label 类标签（显示属性）
     * @param pre 资源前缀
     * @param suf 资源后缀
     * @param model 本体模型
     * @param parent 指定父类
     * @Description: 创建具有命名空间+随机id的新类，并将label设置为自定义值
     */
    public static OntClass createOntClass(String label, String pre,String suf, OntModel model,OntClass parent){
        /**
         *生成随机id，确保资源的唯一性以及解决名称相同的实体的问题
         */
        String classId= UUID.randomUUID().toString();
        /**
         *根据创建uri创建资源（根据后缀区分不同类）
         */
        OntClass ontClass=model.createClass(pre+classId+suf);
        /**
         *给该类设置标签label（显示属性）
         */
        ontClass.addLabel(label, NSEnum.LANGUAGE.getNs());
        /**
         *给该类设置父类
         */
        if (parent!=null){
            ontClass.addSuperClass(parent);
        }
        return ontClass;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 9:55 2019/4/21
     * @Return:
     * @param labels 类标签集合
     * @Description: 批量创建类
     */
    public static void createClassesInBatches(List<String> labels,String pre,String suf,OntModel model,OntClass parent){
        if (labels.size()==0){
            return;
        }
        for (String label:labels){
            createOntClass(label,pre,suf,model,parent);
        }
    }


}
