package propertiesHandler;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.iterator.ExtendedIterator;
import tools.enums.NSEnum;

/**
 * @projectName: HFUTOntology
 * @packageName: propertiesHandler
 * @Author: hanqing zhu
 * @Date: 16:33 2019/4/23
 * @Description:
 */
public class SpecialPropertyHandler {

    public static void handleSpecialProperties(OntModel model){
        /**
         *处理特殊的对象属性
         */
        handleSpecialObjectProperties(model);
        /**
         *处理特殊的数据属性
         */
        handleSpecialDatatypeProperties(model);
    }

    /**
     * 处理对象属性相关方法----------------------------------------------------
     */

    /**
     * @Author: hanqing zhu
     * @Date: 16:38 2019/4/23
     * @Return:
     *
     * @Description: 处理特殊的对象属性
     */
    public static void handleSpecialObjectProperties(OntModel model){
        /**
         * “对应系所” inverseof “对应专业”
         */
        setObjectPropertyInverseOf("对应系所","对应专业",model);
        /**
         * “包含机构” inverseof “隶属于”
         */
        setObjectPropertyInverseOf("包含机构","隶属于",model);
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:46 2019/4/23
     * @Return:
     * 
     * @Description: 根据标签获取相应的对象属性
     */
    public static ObjectProperty getObjectPropertyByLabel(String label,OntModel model){
        ObjectProperty objectProperty=null;
        for (ExtendedIterator<ObjectProperty> iterator=model.listObjectProperties();iterator.hasNext();){
            objectProperty=iterator.next();
            if (objectProperty.getLabel(NSEnum.LANGUAGE.getNs()).equals(label)){
                break;
            }
        }
        return objectProperty;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:54 2019/4/23
     * @Return:
     * 
     * @Description: 设置对象属性间的 inverseof 关系
     */
    public static void setObjectPropertyInverseOf(String label1,String label2,OntModel model){
        ObjectProperty op1=getObjectPropertyByLabel(label1,model);
        ObjectProperty op2=getObjectPropertyByLabel(label2,model);
        op1.setInverseOf(op2);
    }

    /**
     * 处理数据属性相关方法---------------------------------------------------
     */

    /**
     * @Author: hanqing zhu
     * @Date: 16:38 2019/4/23
     * @Return:
     *
     * @Description: 处理特殊的数据属性
     */
    public static void handleSpecialDatatypeProperties(OntModel model){

    }

    /**
     * @Author: hanqing zhu
     * @Date: 16:46 2019/4/23
     * @Return:
     *
     * @Description: 根据标签获取相应的数据属性
     */
    public static DatatypeProperty getDatatypePropertyByLabel(String label, OntModel model){
        DatatypeProperty datatypeProperty=null;
        for (ExtendedIterator<DatatypeProperty> iterator=model.listDatatypeProperties();iterator.hasNext();){
            datatypeProperty=iterator.next();
            if (datatypeProperty.getLabel(NSEnum.LANGUAGE.getNs()).equals(label)){
                break;
            }
        }
        return datatypeProperty;
    }

}
