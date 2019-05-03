package tools.data;

import lombok.Data;

import java.util.List;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.ontologyData
 * @Author: hanqing zhu
 * @Date: 21:22 2019/4/25
 * @Description:
 */
@Data
public class IndividualSelectorData {

    String classLabelSelector;
    String labelSelector;
    List<String> propertyLabels;

    public IndividualSelectorData(String classLabelSelector, String labelSelector, List<String> propertyLabels) {
        this.classLabelSelector = classLabelSelector;
        this.labelSelector = labelSelector;
        this.propertyLabels = propertyLabels;
    }
}
