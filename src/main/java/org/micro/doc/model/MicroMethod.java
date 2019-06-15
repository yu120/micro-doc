package org.micro.doc.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Micro Method Model
 *
 * @author lry
 */
@Data
@ToString
public class MicroMethod implements Serializable {

    /**
     * Method name
     */
    private String name;
    /**
     * Method qualified name
     */
    private String qualifiedName;
    /**
     * Method doc signature
     */
    private String signature;

    /**
     * Method doc title
     */
    private String title;
    /**
     * Method doc intro
     **/
    private String intro;

    /**
     * Method doc {@author}
     */
    private String author;
    /**
     * Method doc {@serialData}
     */
    private String serialData;
    /**
     * Method doc {@apiNote}
     */
    private String apiNote;
    /**
     * Method doc {@implNote}
     */
    private String implNote;
    /**
     * Method doc {@implSpec}
     */
    private String implSpec;
    /**
     * Method doc {@see}
     */
    private String see;
    /**
     * Method doc {@since}
     */
    private String since;
    /**
     * Method doc {@deprecated}
     */
    private String deprecated;

    /**
     * Method doc has deprecated
     */
    private Boolean isDeprecated;

    /**
     * 返回模型
     */
    private MicroReturn returns;
    /**
     * 参数清单
     */
    private List<MicroParameter> parameters;
    /**
     * 堆栈清单
     */
    private List<MicroThrows> throwses;

}
