package tools.enums.fileHandler;

import lombok.Data;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.enums.fileHandler
 * @Author: hanqing zhu
 * @Date: 9:54 2019/4/22
 * @Description:
 */
@Data
public class TempPropertyResource extends TempResource{

    String domainClassLabel;
    String rangeClassLabel;

    public TempPropertyResource(int line, int deep, String labelText, TempResource parent, String domainClassLabel, String rangeClassLabel) {
        super(line, deep, labelText, parent);
        this.domainClassLabel = domainClassLabel;
        this.rangeClassLabel = rangeClassLabel;
    }
}
