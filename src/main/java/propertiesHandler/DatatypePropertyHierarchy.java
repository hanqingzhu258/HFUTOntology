package propertiesHandler;

import classesHandler.ClassHierarchy;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import tools.enums.NSEnum;
import tools.enums.fileHandler.PropertyFileParser;
import tools.enums.fileHandler.TempPropertyResource;

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
public class DatatypePropertyHierarchy {

    public static void createDataPropertyHierarchy(OntModel model){

        /**
         * 获取文本中的对象属性层次结构信息
         */
        String fileURL = "data/dataPropertyHierarchy.txt";
        List<TempPropertyResource> resources=null;
        try {
            resources= PropertyFileParser.parseFile(fileURL);
        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * 建立ObjectProperty对象和tempPropertyResource间的映射关系
         */
        Map<TempPropertyResource,DatatypeProperty> map=new HashMap<TempPropertyResource, DatatypeProperty>();
        /**
         *  根据解析出来的tempPropertyResource资源创建对象属性层级
         */
        createDatatypePropertyHierarchyByResources(model,resources,map);


    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:40 2019/4/23
     * @Return:
     *
     * @Description: 根据解析出来的tempPropertyResource资源创建对象属性层级
     */
    public static void createDatatypePropertyHierarchyByResources(OntModel model,List<TempPropertyResource> resources,
                                                                Map<TempPropertyResource,DatatypeProperty> map){
        if (resources.size()==0){
            return;
        }

        DatatypeProperty datatypeProperty;

        for (TempPropertyResource resource:resources){

            DatatypeProperty parent;
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
            datatypeProperty= DatatypePropertyCreation.createDatatypeProperty(resource.getLabelText(), NSEnum.HFUT.getNs(),
                    "",model, domain,range,parent);
            /**
             *增加相应的映射关系
             */
            map.put(resource,datatypeProperty);
            
        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 11:08 2019/4/23
     * @Return:
     *
     * @Description: 输出所有的数据对象属性
     */
    public static void printAllDatatypeProperties(OntModel model){
        int count=1;
        for (ExtendedIterator<DatatypeProperty> iterator = model.listDatatypeProperties(); iterator.hasNext();){
            DatatypeProperty datatypeProperty=iterator.next();
            String resource=count+++"uri:"+datatypeProperty.getURI()+"; label:"+datatypeProperty.getLabel(NSEnum.LANGUAGE.getNs());
            if (datatypeProperty.getDomain()==null){
                resource+="; domain：null";
            }else{
                resource+=("; domain: "+datatypeProperty.getDomain().getLabel(NSEnum.LANGUAGE.getNs()));
            }
            if (datatypeProperty.getRange()==null){
                resource+="; range：null";
            }else{
                resource+=("; range: "+datatypeProperty.getRange().getLabel(NSEnum.LANGUAGE.getNs()));
            }
            System.out.println(resource);
        }
    }

}
