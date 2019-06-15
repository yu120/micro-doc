package org.micro.doc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.tools.javadoc.Main;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
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
public class MicroDocMain {

    public static String PATH = "";

    /**
     * dddd
     *
     * @param args
     * @throws Exception aaaaa
     * @throws NullPointerException bbb
     */
    public static void main(String[] args) throws Exception, NullPointerException {
        String realRootPath = MicroDocMain.class.getResource("/").getPath();
        realRootPath = realRootPath.substring(0, realRootPath.lastIndexOf("/target/classes"));
        execute(realRootPath);
    }

    public static void execute(String realRootPath) {
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
            return;
        }

        log.info("The java files: ", javaFiles);
        Main.execute(javaFiles.toArray(new String[0]));
    }

    public static JSONArray readInfoJSON() {
        JSONArray jsonArray = new JSONArray();

        try {
            List<String> jsons = IOUtils.readLines(IOUtils.resourceToURL(Constants.INFO_JSON).openStream(), StandardCharsets.UTF_8.name());
            for (String json : jsons) {
                jsonArray.add(JSON.parseObject(json));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

}
