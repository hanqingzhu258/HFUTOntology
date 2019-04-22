import classesHandler.ClassCreation;
import classesHandler.ClassHierarchy;
import classesHandler.ClassHierarchy_ini;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;


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
        /**
         * 输出model中的所有的类
         */
        ClassHierarchy.printAllClasses(model);

        /*ClassHierarchy_ini.createClassHierarchy(model);
        ClassHierarchy_ini.printAllClasses(model);*/




    }

}
