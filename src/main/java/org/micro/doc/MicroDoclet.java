package org.micro.doc;

import com.alibaba.fastjson.JSON;
import com.sun.javadoc.*;
import com.sun.source.doctree.DocTree.Kind;
import com.sun.tools.javadoc.ClassDocImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.micro.doc.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ClassDoc[] classes = root.classes();
        for (ClassDoc classDoc : classes) {
            // 构建MicroClass模型
            MicroClass microClass = buildClass(classDoc);
            for (MethodDoc methodDoc : classDoc.methods()) {
                // 只扫描public的方法
                if (!methodDoc.isPublic()) {
                    continue;
                }

                // 构建MicroMethod模型
                MicroMethod microMethod = buildMethod(methodDoc);
                // 读取可能会抛出的异常
                microMethod.setReturns(readReturn(methodDoc));
                // 读取可能会抛出的异常
                microMethod.setThrowses(readThrows(methodDoc));
                // 读取参数描述
                microMethod.setParameters(readParameters(methodDoc));
                microClass.getMethods().add(microMethod);
            }

            lines.add(JSON.toJSONString(microClass));
        }

        // 写入隐藏文件
        if (!lines.isEmpty()) {
            try {
                FileUtils.writeLines(new File(MicroDocMain.PATH), StandardCharsets.UTF_8.name(), lines, false);
            } catch (IOException e) {
                log.error("The write lines is exception", e);
            }
        }

        return true;
    }

    /**
     * 构建类模型
     *
     * @param classDoc {@link ClassDoc}
     * @return {@link MicroClass}
     */
    private static MicroClass buildClass(ClassDoc classDoc) {
        MicroClass microClass = new MicroClass();
        microClass.setName(classDoc.name());
        microClass.setQualifiedName(classDoc.qualifiedName());
        microClass.setPackageName(classDoc.toString().replace(Constants.DOT + classDoc.name(), Constants.NULL_STR));

        String commentText = classDoc.commentText();
        microClass.setTitle(commentText);
        microClass.setIntro(commentText);

        Tag[] authorTags = classDoc.tags(Kind.AUTHOR.tagName);
        if (authorTags.length > 0) {
            microClass.setAuthor(authorTags[0].text());
        }
        Tag[] versionTags = classDoc.tags(Kind.VERSION.tagName);
        if (versionTags.length > 0) {
            microClass.setVersion(versionTags[0].text());
        }
        Tag[] serialTags = classDoc.tags(Kind.SERIAL.tagName);
        if (serialTags.length > 0) {
            microClass.setSerial(serialTags[0].text());
        }
        Tag[] apiNoteTags = classDoc.tags(MicroKind.API_NOTE.tagName);
        if (apiNoteTags.length > 0) {
            microClass.setApiNote(apiNoteTags[0].text());
        }
        Tag[] implNoteTags = classDoc.tags(MicroKind.IMPL_NOTE.tagName);
        if (implNoteTags.length > 0) {
            microClass.setImplNote(implNoteTags[0].text());
        }
        Tag[] implSpecTags = classDoc.tags(MicroKind.IMPL_SPEC.tagName);
        if (implSpecTags.length > 0) {
            microClass.setImplSpec(implSpecTags[0].text());
        }
        Tag[] seeTags = classDoc.tags(Kind.SEE.tagName);
        if (seeTags.length > 0) {
            microClass.setSee(seeTags[0].text());
        }
        Tag[] sinceTags = classDoc.tags(Kind.SINCE.tagName);
        if (sinceTags.length > 0) {
            microClass.setSince(sinceTags[0].text());
        }
        Tag[] deprecatedTags = classDoc.tags(Kind.DEPRECATED.tagName);
        if (deprecatedTags.length > 0) {
            microClass.setDeprecated(deprecatedTags[0].text());
        }

        microClass.setIsDeprecated(isDeprecated(classDoc.annotations()));
        microClass.setIsClass(classDoc.isClass());
        microClass.setIsOrdinaryClass(classDoc.isOrdinaryClass());
        microClass.setIsEnum(classDoc.isEnum());
        microClass.setIsInterface(classDoc.isInterface());
        microClass.setIsException(classDoc.isException());
        microClass.setIsError(classDoc.isError());
        microClass.setIsAbstract(classDoc.isAbstract());
        microClass.setIsIncluded(classDoc.isIncluded());
        if (classDoc instanceof ClassDocImpl) {
            ClassDocImpl implClassDoc = (ClassDocImpl) classDoc;
            microClass.setIsThrowable(implClassDoc.isThrowable());
            microClass.setIsSynthetic(implClassDoc.isSynthetic());
        }

        microClass.setMethods(new ArrayList<>());
        return microClass;
    }

    /**
     * 构建方法模型
     *
     * @param methodDoc {@link MethodDoc}
     * @return {@link MicroMethod}
     */
    private static MicroMethod buildMethod(MethodDoc methodDoc) {
        MicroMethod microMethod = new MicroMethod();
        microMethod.setName(methodDoc.name());
        microMethod.setTitle(methodDoc.commentText());
        microMethod.setDeprecated(false);

        // 设置作者
        Tag[] authorMethodTags = methodDoc.tags(Kind.AUTHOR.tagName);
        if (authorMethodTags.length > 0) {
            microMethod.setAuthor(authorMethodTags[0].text());
        }
        // 设置API笔记
        Tag[] apiNoteMethodTags = methodDoc.tags(Constants.API_NOTE_TAG);
        if (apiNoteMethodTags.length > 0) {
            microMethod.setApiNote(apiNoteMethodTags[0].text());
        }
        // 设置日期
        Tag[] sinceMethodTags = methodDoc.tags(Kind.SINCE.tagName);
        if (sinceMethodTags.length > 0) {
            microMethod.setSince(sinceMethodTags[0].text());
        }

        // 校验是否被舍弃
        microMethod.setDeprecated(isDeprecated(methodDoc.annotations()));

        return microMethod;
    }

    /**
     * 读取参数列表
     *
     * @param methodDoc {@link MethodDoc}
     * @return {@link List< MicroParameter >}
     */
    private static List<MicroParameter> readParameters(MethodDoc methodDoc) {
        List<MicroParameter> parameters = new ArrayList<>();

        // 读取参数描述
        Map<String, String> paramTagMap = new HashMap<>();
        ParamTag[] paramTags = methodDoc.paramTags();
        for (ParamTag paramTag : paramTags) {
            paramTagMap.put(paramTag.parameterName(), paramTag.parameterComment());
        }

        for (Parameter parameter : methodDoc.parameters()) {
            MicroParameter microParameter = new MicroParameter();
            microParameter.setName(parameter.name());
            microParameter.setTitle(paramTagMap.get(parameter.name()));
            microParameter.setType(parameter.type().toString());
            microParameter.setTypeName(parameter.typeName());
            parameters.add(microParameter);
        }

        return parameters;
    }

    /**
     * 读取返回模型
     *
     * @param methodDoc {@link MethodDoc}
     * @return {@link MicroReturn}
     */
    private static MicroReturn readReturn(MethodDoc methodDoc) {
        MicroReturn microReturn = new MicroReturn();
        // 读取返回参数
        String returnType = methodDoc.returnType().toString();
        microReturn.setType(returnType);
        if (returnType.lastIndexOf(Constants.DOT) > 0) {
            microReturn.setTypeName(returnType.substring(returnType.lastIndexOf(Constants.DOT) + 1));
        } else {
            microReturn.setTypeName(returnType);
        }

        // 设置返回标题
        Tag[] returnMethodTags = methodDoc.tags(Kind.RETURN.tagName);
        if (returnMethodTags.length > 0) {
            microReturn.setTitle(returnMethodTags[0].text());
        }

        return microReturn;
    }

    /**
     * 读取可能抛出的异常
     *
     * @param methodDoc {@link MethodDoc}
     * @return {@link List< MicroThrows >}
     */
    private static List<MicroThrows> readThrows(MethodDoc methodDoc) {
        List<MicroThrows> microThrowsList = new ArrayList<>();
        Tag[] throwsMethodTags = methodDoc.tags(Kind.THROWS.tagName);
        for (Tag throwsMethodTag : throwsMethodTags) {
            if (throwsMethodTag instanceof ThrowsTag) {
                ThrowsTag throwsTag = (ThrowsTag) throwsMethodTag;
                MicroThrows microThrows = new MicroThrows();
                microThrows.setName(throwsTag.exceptionName());
                microThrows.setTitle(throwsTag.exceptionComment());
                ClassDoc throwsClassDoc = throwsTag.exception();
                if (throwsClassDoc != null) {
                    microThrows.setType(throwsClassDoc.toString());
                    microThrows.setTypeName(throwsClassDoc.typeName());
                }
                microThrowsList.add(microThrows);
            }
        }

        return microThrowsList;
    }

    /**
     * 校验是否被舍弃
     *
     * @param annotations {@link AnnotationDesc[]}
     * @return {@link Boolean}
     */
    private static Boolean isDeprecated(AnnotationDesc[] annotations) {
        if (annotations != null && annotations.length > 0) {
            for (AnnotationDesc annotation : annotations) {
                AnnotationTypeDoc annotationTypeDoc = annotation.annotationType();
                if (annotationTypeDoc.toString().equals(Deprecated.class.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

}