package org.micro.doc.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.micro.doc.MicroDoc;

/**
 * Micro Doc Plugin
 *
 * @goal micro-doc
 */
public class MicroDocPlugin extends AbstractMojo {

    /**
     * The project path.
     *
     * @parameter expression="${projectPath}"
     */
    private String projectPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("1===>" + projectPath);
        MicroDoc.execute(projectPath);
        System.out.println("2===>" + projectPath);
    }

}

