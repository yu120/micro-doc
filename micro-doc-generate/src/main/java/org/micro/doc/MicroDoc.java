package org.micro.doc;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.micro.doc.model.MicroClass;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Input Main
 *
 * @author lry
 */
@Slf4j
public class MicroDoc {

    public static String PATH = "";

    /**
     * dddd
     *
     * @param args
     * @throws Exception            aaa
     * @throws NullPointerException bbb
     */
    public static void main(String[] args) throws Exception, NullPointerException {
        String realRootPath = MicroDoc.class.getResource("/").getPath();
        realRootPath = realRootPath.substring(0, realRootPath.lastIndexOf("/target/classes"));
        System.out.println(execute(realRootPath) ? "成功" : "失败");
    }

    public static boolean execute(String realRootPath) {
        String realPath = realRootPath + "/src/main/java";
        log.info("The real path is: {}", realPath);
        PATH = realRootPath + Constants.TARGET_CLASSES;
        log.info("The json path is: {}", PATH);

        List<String> javaFiles = new ArrayList<>();
        javaFiles.add("-doclet");
        javaFiles.add(MicroDoclet.class.getName());
        // The recursive read java file
        Collection<File> listFiles = FileUtils.listFiles(new File(realPath), new String[]{Constants.JAVA_SUFFIX}, true);
        for (File file : listFiles) {
            javaFiles.add(file.getPath());
        }

        if (javaFiles.size() <= 2) {
            return false;
        }

        log.info("The java files: ", javaFiles);
        return com.sun.tools.javadoc.Main.execute(javaFiles.toArray(new String[0])) == 0;
    }

    public static List<MicroClass> readInfo() {
        List<MicroClass> microClassList = new ArrayList<>();

        try {
            List<String> jsonList = IOUtils.readLines(IOUtils.resourceToURL(
                    Constants.MICRO_DOC_JSON).openStream(), StandardCharsets.UTF_8.name());
            for (String json : jsonList) {
                microClassList.add(JSON.parseObject(json, MicroClass.class));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Not found file");
        }

        return microClassList;
    }

}
