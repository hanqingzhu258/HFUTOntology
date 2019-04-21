package tools.enums;

import lombok.Getter;

/**
 * @projectName: HFUTOntology
 * @packageName: tools
 * @Author: hanqing zhu
 * @Date: 17:15 2019/4/20
 * @Description: 命名空间枚举类
 */
@Getter
public enum NSEnum /*implements CodeEnum*/{
    HFUT("http://www.kg.zxs.com/ontologies/2019/HFUT#"),
    GENERAL("_hfut"),
    SPECIALTY("_specialty"),
    PERSON("_person"),
    UNIVERSITY("_university"),
    DISCIPLINE("_discipline"),
    TEACHING("_teaching"),
    ORGANIZATION("_organization"),
    SCIENCE("_science"),
    COURSE("_course"),

    LANGUAGE("ch")
    ;

    private String ns;

    NSEnum(String ns) {
        this.ns = ns;
    }
}
