package tools.enums.fileHandler;

import lombok.Data;

/**
 * @projectName: HFUTOntology
 * @packageName: tests
 * @Author: hanqing zhu
 * @Date: 17:11 2019/4/21
 * @Description:
 */
@Data
public class TempResource {

    public TempResource(int line, int deep, String labelText, TempResource parent) {
        this.line = line;
        this.deep = deep;
        this.labelText = labelText;
        this.parent = parent;
    }

    private int line;
    private int deep;
    private String labelText;
    private TempResource parent;

}
