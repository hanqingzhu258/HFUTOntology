package classesHandler;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import tools.enums.fileHandler.FileParser;
import tools.enums.NSEnum;
import tools.enums.fileHandler.TempResource;

import java.util.*;

/**
 * @projectName: HFUTOntology
 * @packageName: classesHandler
 * @Author: hanqing zhu
 * @Date: 19:54 2019/4/20
 * @Description:
 */
public class ClassHierarchy {

    /**
     * @Author: hanqing zhu
     * @Date: 20:03 2019/4/20
     * @Return:
     *
     * @Description: 创建本体的类结构：class hierarchy
     */
    public static void createClassHierarchy(OntModel model){
        /**
         *解析文档获取相关类层次结构资源
         */
        String fileURL="data/classHierarchy.txt";
        List<TempResource> resources=null;
        try {
            resources= FileParser.parseFile(fileURL);
            FileParser.printAllTempResources(resources);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**
         * 通过tempResource和ontClass的映射关系，
         * 解决标签可能重复带来的父类不唯一问题。
         */
        Map<TempResource,OntClass> map=new HashMap<TempResource, OntClass>();
        /**
         *正式根据资源创建模型中类的层次结构
         */
        createClassHierarchyByResources(resources,model,map);

    }
    /**
     * @Author: hanqing zhu
     * @Date: 22:26 2019/4/21
     * @Return:
     *
     * @Description: 根据解析出的文本资源，自动创建本体类结构
     */
    public static void createClassHierarchyByResources(List<TempResource> resources,OntModel model,Map<TempResource,OntClass> map){
        if (resources.size()==0){
            return;
        }
        for (TempResource resource:resources){
            //确认父类
            OntClass parent=null;
            if (resource.getParent()==null){
                parent=null;
            }else{
                parent=map.get(resource.getParent());
            }
            OntClass ontClass=ClassCreation.createOntClass(resource.getLabelText(),NSEnum.HFUT.getNs(),"",model,parent);
            map.put(resource,ontClass);
        }

    }


    /**
     * @Author: hanqing zhu
     * @Date: 10:45 2019/4/21
     * @Return:
     *
     * @Description: 输出模型中的所有类
     */
    public static void printAllClasses(OntModel model){
        int count=0;
        for(ExtendedIterator<OntClass> iterator = model.listClasses(); iterator.hasNext();){
            OntClass ontClass=iterator.next();
            System.out.println(count+++"---"+ontClass.getURI()+":"+ontClass.getLabel(NSEnum.LANGUAGE.getNs())+":"+ontClass.getRDFType());
        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 10:06 2019/4/21
     * @Return:
     *
     * @Description: 根据标签来获取对应类(假定类的标签均不同)
     */
    public static OntClass getClassByLabel(String label,OntModel model){

        OntClass findedClass=null;
        for (Iterator<OntClass> iterator = model.listClasses(); iterator.hasNext();){
            findedClass=iterator.next();
            if (findedClass.getLabel(NSEnum.LANGUAGE.getNs()).equals(label)){
                break;
            }
        }
        return findedClass;
    }


}
