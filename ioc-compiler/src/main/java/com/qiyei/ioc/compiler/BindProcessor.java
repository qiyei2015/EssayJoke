package com.qiyei.ioc.compiler;

import com.google.auto.service.AutoService;
import com.qiyei.ioc.annotation.Bind;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @author Created by qiyei2015 on 2018/4/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Bind
 */
@AutoService(Processor.class)
public class BindProcessor extends AbstractProcessor{

    /**
     *
     */
    private Filer mFiler;
    /**
     *
     * 类为TypeElement，变量为VariableElement，方法为ExecuteableElement
     */
    private Elements mElementsUtils;
    /**
     *
     */
    private Messager mMessager;

    private static final ClassName sClassName = ClassName.get("com.qiyei.ioc.api", "Test");

    /**
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mFiler = processingEnv.getFiler();
        mElementsUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> typeSet = new LinkedHashSet<>();

        typeSet.add(Bind.class.getCanonicalName());

        return typeSet;
    }

    /**
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     *
     * 1.
     * 2.
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (!annotations.isEmpty()){
            //
            Set<? extends Element> bindElement = roundEnv.getElementsAnnotatedWith(Bind.class);

            try {
                generateCode(bindElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    /**
     *
     * @param elements
     */
    private void generateCode(Set<? extends Element> elements) throws IOException{

        for (Element element : elements){

//            //
            TypeElement typeElement = (TypeElement) element;

            //
            String className = typeElement.getQualifiedName().toString();

            System.out.println("className:" + className);


            PackageElement packageElement = mElementsUtils.getPackageOf(typeElement);
            String packageName = packageElement.getQualifiedName().toString();
            System.out.println("packageName:" + packageName);

            className = getClassName(typeElement,packageName);


            Bind bindAnnotation = typeElement.getAnnotation(Bind.class);
            int value = bindAnnotation.value();
            System.out.println("value:" + value);

            MethodSpec.Builder methodBuilder = MethodSpec
                    .methodBuilder("sayHello")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(TypeName.INT,"n")
                    .returns(TypeName.INT);

            //$L表示字面量 $T表示类型
            methodBuilder.addStatement("return $L",value);

            //
            TypeSpec type = TypeSpec
                    .classBuilder(className + "$$" + value)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodBuilder.build())
                    .build();

            //
            JavaFile javaFile = JavaFile.builder(packageName,type).build();
            //
            javaFile.writeTo(mFiler);

        }
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
