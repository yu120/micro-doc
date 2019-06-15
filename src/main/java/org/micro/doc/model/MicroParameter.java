package org.micro.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Micro Parameter
 *
 * @author lry
 */
@Data
@ToString
public class MicroParameter implements Serializable {

    /**
     * Parameter name
     */
    private String name;
    /**
     * Parameter class title
     */
    private String title;
    /**
     * Parameter class type
     */
    private String type;
    /**
     * Parameter class type name
     */
    private String typeName;

}
