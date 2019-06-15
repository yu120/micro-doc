package org.micro.doc.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Micro Return
 *
 * @author lry
 */
@Data
@ToString
public class MicroReturn implements Serializable {

    /**
     * Method return type {@return} title
     */
    private String title;
    /**
     * Method return type name
     */
    private String name;
    /**
     * Method return type qualified name
     */
    private String qualifiedName;
    /**
     * Method is return void
     */
    private Boolean isVoid = false;

}
