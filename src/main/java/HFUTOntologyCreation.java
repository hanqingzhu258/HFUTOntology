import classesHandler.ClassCreation;
import classesHandler.ClassHierarchy;
import classesHandler.ClassHierarchy_ini;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import propertiesHandler.DatatypePropertyHierarchy;
import propertiesHandler.ObjectPropertyHierarchy;


/**
 * @projectName: HFUTOntology
 * @packageName: PACKAGE_NAME
 * @Author: hanqing zhu
 * @Date: 17:13 2019/4/20
 * @Description: 本体构建主程序
 */
public class HFUTOntologyCreation {

    public static void main(String[] args) {

        /**
         * 新建模型
         */
        OntModel model= ModelFactory.createOntologyModel();
        /**
         * 创建类层次结构
         */
        ClassHierarchy.createClassHierarchy(model);

        /*ClassHierarchy_ini.createClassHierarchy(model);
        ClassHierarchy_ini.printAllClasses(model);*/

        /**
         * 创建对象属性层次结构
         */
        ObjectPropertyHierarchy.createObjectPropertyHierarchy(model);

        /**
         * 创建数据属性层次结构
         */
        DatatypePropertyHierarchy.createDataPropertyHierarchy(model);

        /**
         *输出所有资源
         */
        printAllResources(model);

    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:09 2019/4/23
     * @Return:
     *
     * @Description: 输出所有的资源
     */
    public static void printAllResources(OntModel model){
        /**
         * 输出model中的所有的类
         */
        ClassHierarchy.printAllClasses(model);
        /**
         *输出model中的所有的对象属性
         */
        ObjectPropertyHierarchy.printAllObjectProperties(model);
        /**
         *输出model中的所有的数据属性
         */
        DatatypePropertyHierarchy.printAllDatatypeProperties(model);
    }

}
