package propertiesHandler;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import tools.enums.NSEnum;

import java.util.UUID;

/**
 * @projectName: HFUTOntology
 * @packageName: propertiesHandler
 * @Author: hanqing zhu
 * @Date: 9:45 2019/4/22
 * @Description:
 */
public class DatatypePropertyCreation {

    /**
     * @Author: hanqing zhu
     * @Date: 10:21 2019/4/23
     * @Return:
     * @param label 属性标签
     *        pre 标签前缀
     *        suf 标签后缀
     *        model 本体模型
     *        domain domain域类
     *        range range域类
     *        parent 父属性
     * @Description:
     */
    public static DatatypeProperty createDatatypeProperty(String label, String pre, String suf, OntModel model,
                                                        OntClass domain, OntClass range, DatatypeProperty parent){
        String pid= UUID.randomUUID().toString();
        /**
         *创建新的对象属性
         */
        DatatypeProperty datatypeProperty=model.createDatatypeProperty(pre+pid+suf);
        /**
         *设置对象属性标签
         */
        datatypeProperty.setLabel(label, NSEnum.LANGUAGE.getNs());
        /**
         *设置父属性
         */
        if (parent!=null) {
            datatypeProperty.addSuperProperty(parent);
        }
        /**
         *设置domain域
         */
        if (domain!=null) {
            datatypeProperty.setDomain(domain);
        }
        /**
         *设置range域
         */
        if (range!=null) {
            datatypeProperty.setRange(range);
        }
        return datatypeProperty;
    }

}
