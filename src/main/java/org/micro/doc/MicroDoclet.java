package org.micro.doc;

import com.alibaba.fastjson.JSON;
import com.sun.javadoc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.micro.doc.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * The Micro Doclet
 *
 * @author lry
 */
@Slf4j
public class MicroDoclet extends Doclet {

    /**
     * Micro doclet start
     *
     * @param root {@link RootDoc}
     * @return {@link Boolean}
     */
    public static boolean start(RootDoc root) {
        List<String> lines = new ArrayList<>();
        List<MicroClass> microClasses = new MicroFactory().handler(root);
        for (MicroClass microClass : microClasses) {
            lines.add(JSON.toJSONString(microClass));
        }

        System.out.println(JSON.toJSONString(microClasses));

        // 写入隐藏文件
        if (!lines.isEmpty()) {
            try {
                FileUtils.writeLines(new File(MicroDoc.PATH), StandardCharsets.UTF_8.name(), lines, false);
            } catch (IOException e) {
                log.error("The write lines is exception", e);
            }
        }

        return true;
    }

}