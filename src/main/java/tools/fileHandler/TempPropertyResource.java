package tools.fileHandler;

import lombok.Data;

/**
 * @projectName: HFUTOntology
 * @packageName: tools.fileHandler
 * @Author: hanqing zhu
 * @Date: 9:54 2019/4/22
 * @Description:
 */
@Data
public class TempPropertyResource {
    private int line;
    private int deep;
    private String labelText;
    private TempPropertyResource parent;
    private String domainClassLabel;
    private String rangeClassLabel;

    public TempPropertyResource(int line, int deep, String labelText, TempPropertyResource parent,
                                String domainClassLabel, String rangeClassLabel) {
        this.line = line;
        this.deep = deep;
        this.labelText = labelText;
        this.parent = parent;
        this.domainClassLabel = domainClassLabel;
        this.rangeClassLabel = rangeClassLabel;
    }
}
