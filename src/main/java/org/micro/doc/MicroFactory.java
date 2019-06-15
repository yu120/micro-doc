package org.micro.doc;

import com.sun.javadoc.*;
import com.sun.source.doctree.DocTree.Kind;
import com.sun.tools.javadoc.ClassDocImpl;
import lombok.extern.slf4j.Slf4j;
import org.micro.doc.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Micro Factory
 *
 * @author lry
 */
@Slf4j
public class MicroFactory {

    /**
     * Micro handler
     *
     * @param root {@link RootDoc}
     * @return {@link Boolean}
     */
    public List<MicroClass> handler(RootDoc root) {
        List<MicroClass> microClasses = new ArrayList<>();
        ClassDoc[] classes = root.classes();
        for (ClassDoc classDoc : classes) {
            // Step 1: 构建MicroClass模型
            MicroClass microClass = this.buildClass(classDoc);
            microClasses.add(microClass);
        }

        return microClasses;
    }

    /**
     * 构建类模型
     *
     * @param classDoc {@link ClassDoc}
     * @return {@link MicroClass}
     */
    private MicroClass buildClass(ClassDoc classDoc) {
        MicroClass microClass = new MicroClass();
        microClass.setName(classDoc.name());
        microClass.setQualifiedName(classDoc.qualifiedName());
        microClass.setPackageName(classDoc.toString().replace(Constants.DOT + classDoc.name(), Constants.NULL_STR));

        String commentText = classDoc.commentText();
        microClass.setTitle(this.getTitle(commentText));
        microClass.setIntro(this.getIntro(commentText));

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

        microClass.setIsDeprecated(this.isDeprecated(classDoc.annotations()));
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

        // Step 2: 获取方法
        List<MicroMethod> methods = new ArrayList<>();
        for (MethodDoc methodDoc : classDoc.methods()) {
            // 是否只扫描public的方法
            if (MicroDoc.SCAN_PUBLIC && !methodDoc.isPublic()) {
                continue;
            }

            // 构建MicroMethod模型
            MicroMethod microMethod = this.buildMethod(methodDoc);
            methods.add(microMethod);
        }
        microClass.setMethods(methods);
        return microClass;
    }

    /**
     * 构建方法模型
     *
     * @param methodDoc {@link MethodDoc}
     * @return {@link MicroMethod}
     * @serialData
     */
    private MicroMethod buildMethod(MethodDoc methodDoc) {
        MicroMethod microMethod = new MicroMethod();
        microMethod.setName(methodDoc.name());
        microMethod.setQualifiedName(methodDoc.qualifiedName());
        microMethod.setSignature(methodDoc.toString());

        String commentText = methodDoc.commentText();
        microMethod.setTitle(this.getTitle(commentText));
        microMethod.setIntro(this.getIntro(commentText));

        Tag[] authorTags = methodDoc.tags(Kind.AUTHOR.tagName);
        if (authorTags.length > 0) {
            microMethod.setAuthor(authorTags[0].text());
        }
        Tag[] apiNoteTags = methodDoc.tags(MicroKind.API_NOTE.tagName);
        if (apiNoteTags.length > 0) {
            microMethod.setApiNote(apiNoteTags[0].text());
        }
        Tag[] implNoteTags = methodDoc.tags(MicroKind.IMPL_NOTE.tagName);
        if (implNoteTags.length > 0) {
            microMethod.setImplNote(implNoteTags[0].text());
        }
        Tag[] implSpecTags = methodDoc.tags(MicroKind.IMPL_SPEC.tagName);
        if (implSpecTags.length > 0) {
            microMethod.setImplSpec(implSpecTags[0].text());
        }
        Tag[] seeTags = methodDoc.tags(Kind.SEE.tagName);
        if (seeTags.length > 0) {
            microMethod.setSee(seeTags[0].text());
        }
        Tag[] sinceTags = methodDoc.tags(Kind.SINCE.tagName);
        if (sinceTags.length > 0) {
            microMethod.setSince(sinceTags[0].text());
        }
        Tag[] deprecatedTags = methodDoc.tags(Kind.DEPRECATED.tagName);
        if (deprecatedTags.length > 0) {
            microMethod.setDeprecated(deprecatedTags[0].text());
        }

        microMethod.setIsDeprecated(this.isDeprecated(methodDoc.annotations()));
        microMethod.setIsPublic(methodDoc.isPublic());
        microMethod.setIsProtected(methodDoc.isProtected());
        microMethod.setIsPrivate(methodDoc.isPrivate());
        microMethod.setIsPackagePrivate(methodDoc.isPackagePrivate());
        microMethod.setIsStatic(methodDoc.isStatic());
        microMethod.setIsFinal(methodDoc.isFinal());

        // Step 3: 读取可能会抛出的异常
        microMethod.setReturnInfo(this.readReturn(methodDoc));
        // Step 4: 读取可能会抛出的异常
        microMethod.setThrowses(this.readThrows(methodDoc));
        // Step 5: 读取参数描述
        microMethod.setParameters(this.readParameters(methodDoc));
        return microMethod;
    }

    /**
     * 读取参数列表
     *
     * @param methodDoc {@link MethodDoc}
     * @return {@link List<MicroParameter>}
     */
    private List<MicroParameter> readParameters(MethodDoc methodDoc) {
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
    private MicroReturn readReturn(MethodDoc methodDoc) {
        MicroReturn microReturn = new MicroReturn();
        // 读取返回参数
        String returnType = methodDoc.returnType().toString();
        microReturn.setName(returnType);
        if (returnType.lastIndexOf(Constants.DOT) > 0) {
            microReturn.setQualifiedName(returnType.substring(returnType.lastIndexOf(Constants.DOT) + 1));
        } else {
            microReturn.setQualifiedName(returnType);
        }
        if ("void".equals(returnType)) {
            microReturn.setIsVoid(true);
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
     * @return {@link List<MicroThrows>}
     */
    private List<MicroThrows> readThrows(MethodDoc methodDoc) {
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
                    microThrows.setQualifiedName(throwsClassDoc.toString());
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
    private Boolean isDeprecated(AnnotationDesc[] annotations) {
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

    private String getTitle(String commentText) {
        return commentText.indexOf("\n") > 0 ? commentText.substring(0, commentText.indexOf("\n")) : commentText;
    }

    private String getIntro(String commentText) {
        return commentText.indexOf("\n") > 0 ? commentText.substring(commentText.indexOf("\n")) : commentText;
    }

}