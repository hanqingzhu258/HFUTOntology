package propertiesHandler;

import classesHandler.ClassHierarchy;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import tools.enums.NSEnum;
import tools.fileHandler.PropertyFileParser;
import tools.fileHandler.TempPropertyResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @projectName: HFUTOntology
 * @packageName: propertiesHandler
 * @Author: hanqing zhu
 * @Date: 9:44 2019/4/22
 * @Description:
 */
public class ObjectPropertyHierarchy {

    public static void createObjectPropertyHierarchy(OntModel model){

        /**
         * 获取文本中的对象属性层次结构信息
         */
        String fileURL = "dataCollection/ontologyData/objectPropertyHierarchy.txt";
        List<TempPropertyResource> resources=null;
        try {
            resources= PropertyFileParser.parseFile(fileURL);
        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * 建立ObjectProperty对象和tempPropertyResource间的映射关系
         */
        Map<TempPropertyResource,ObjectProperty> map=new HashMap<TempPropertyResource, ObjectProperty>();
        /**
         *  根据解析出来的tempPropertyResource资源创建对象属性层级
         */
        createObjectPropertyHierarchyByResources(model,resources,map);


    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:40 2019/4/23
     * @Return:
     *
     * @Description: 根据解析出来的tempPropertyResource资源创建对象属性层级
     */
    public static void createObjectPropertyHierarchyByResources(OntModel model,List<TempPropertyResource> resources,
                                                                Map<TempPropertyResource,ObjectProperty> map){
        if (resources.size()==0){
            return;
        }

        ObjectProperty objectProperty;

        for (TempPropertyResource resource:resources){

            ObjectProperty parent;
            OntClass domain;
            OntClass range;

            /**
             *确认父属性
             */
            if (resource.getParent()==null){
                parent=null;
            }else{
                parent=map.get(resource.getParent());
            }
            /**
             *确认domain类
             */
            if (resource.getDomainClassLabel()!=null){
                domain= ClassHierarchy.getClassByLabel(resource.getDomainClassLabel(),model);
            }else{
                domain=null;
            }
            /**
             *确认range类
             */
            if(resource.getRangeClassLabel()!=null){
                range=ClassHierarchy.getClassByLabel(resource.getRangeClassLabel(),model);
            }else{
                range=null;
            }
            /**
             *创建新的对象属性
             */
            objectProperty=ObjectPropertyCreation.createObjectProperty(resource.getLabelText(), NSEnum.HFUT.getNs(),
                    "",model, domain,range,parent);
            /**
             *增加相应的映射关系
             */
            map.put(resource,objectProperty);

        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:08 2019/4/23
     * @Return:
     *
     * @Description: 输出所有的对象类型属性
     */
    public static void printAllObjectProperties(OntModel model){
        int count=1;
        for (ExtendedIterator<ObjectProperty> iterator = model.listObjectProperties(); iterator.hasNext();){
            ObjectProperty objectProperty=iterator.next();
            String resource=count+++"uri:"+objectProperty.getURI()+"; label:"+objectProperty.getLabel(NSEnum.LANGUAGE.getNs());
            if (objectProperty.getDomain()==null){
                resource+="; domain：null";
            }else{
                resource+=("; domain: "+objectProperty.getDomain().getURI()+objectProperty.getDomain().getLabel(NSEnum.LANGUAGE.getNs()));
            }
            if (objectProperty.getRange()==null){
                resource+="; range：null";
            }else{
                resource+=("; range: "+objectProperty.getRange().getLabel(NSEnum.LANGUAGE.getNs()));
            }
            System.out.println(resource);
        }
    }

}
