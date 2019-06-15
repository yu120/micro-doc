package org.micro.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Micro Throws
 *
 * @author lry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroThrows implements Serializable {

    /**
     * Throws name
     */
    private String name;
    /**
     * Throws qualified name
     */
    private String qualifiedName;

    /**
     * Throws title
     */
    private String title;

}
