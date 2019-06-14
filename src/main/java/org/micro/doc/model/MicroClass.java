package org.micro.doc.model;

import com.sun.javadoc.ClassDoc;
import com.sun.tools.javac.code.TypeTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * [Title]
 * <p>
 * [intro]
 *
 * @author [author]
 * @version [version]
 * @serial [serial]
 * @apiNote [apiNote]
 * @implNote [implNote]
 * @implSpec [implSpec]
 * @see [see]
 * @since [since]
 * deprecated [deprecated]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean isDeprecated;
    private Boolean isClass;
    private Boolean isOrdinaryClass;
    private Boolean isEnum;
    private Boolean isInterface;
    private Boolean isException;
    private Boolean isError;
    private Boolean isThrowable;
    private Boolean isAbstract;
    private Boolean isSynthetic;
    private Boolean isIncluded;

    /**
     * Class method
     */
    private List<MicroMethod> methods;

}
