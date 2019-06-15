package org.micro.doc.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Micro Class Model
 *
 * @author lry
 */
@Data
@ToString
public class MicroClass implements Serializable {

    /**
     * Class Name
     */
    private String name;
    /**
     * Class qualified name
     */
    private String qualifiedName;
    /**
     * Class doc package name
     */
    private String packageName;

    /**
     * Class doc title
     */
    private String title;
    /**
     * Class doc intro
     **/
    private String intro;

    /**
     * Class doc {@author}
     */
    private String author;
    /**
     * Class doc {@version}
     */
    private String version;
    /**
     * Class doc {@serial}
     */
    private String serial;
    /**
     * Class doc {@apiNote}
     */
    private String apiNote;
    /**
     * Class doc {@implNote}
     */
    private String implNote;
    /**
     * Class doc {@implSpec}
     */
    private String implSpec;
    /**
     * Class doc {@see}
     */
    private String see;
    /**
     * Class doc {@since}
     */
    private String since;
    /**
     * Class doc {@deprecated}
     */
    private String deprecated;

    /**
     * Class doc has deprecated
     */
    private Boolean isDeprecated = false;
    private Boolean isClass = false;
    private Boolean isOrdinaryClass = false;
    private Boolean isEnum = false;
    private Boolean isInterface = false;
    private Boolean isAnnotation = false;
    private Boolean isException = false;
    private Boolean isError = false;
    private Boolean isThrowable = false;
    private Boolean isAbstract = false;
    private Boolean isSynthetic = false;
    private Boolean isIncluded = false;

    /**
     * Class method
     */
    private List<MicroMethod> methods;

}
