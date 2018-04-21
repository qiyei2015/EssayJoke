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
import javax.tools.Diagnostic;

/**
 * @author Created by qiyei2015 on 2018/4/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Bind注解处理器
 */
@AutoService(Processor.class)
public class BindProcessor extends AbstractProcessor{

    /**
     * java源文件操作相关类，主要用于生成java源文件
     */
    private Filer mFiler;
    /**
     * 注解类型工具类，主要用于后续生成java源文件使用
     * 类为TypeElement，变量为VariableElement，方法为ExecuteableElement
     */
    private Elements mElementsUtils;
    /**
     * 日志打印，类似于log,可用于输出错误信息
     */
    private Messager mMessager;

    private static final ClassName sClassName = ClassName.get("com.qiyei.ioc.api", "Test");

    /**
     * 初始化，主要用于初始化各个变量
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
     * 支持的注解类型
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> typeSet = new LinkedHashSet<>();

        typeSet.add(Bind.class.getCanonicalName());

        return typeSet;
    }

    /**
     * 支持的版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     *
     * 1.搜集信息
     * 2.生成java源文件
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (!annotations.isEmpty()){
            //获取Bind注解类型的元素，这里是类类型TypeElement
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

            //由于是在类上注解，那么获取TypeElement
            TypeElement typeElement = (TypeElement) element;

            //获取全限定类名
            String className = typeElement.getQualifiedName().toString();

            mMessager.printMessage(Diagnostic.Kind.WARNING,"className:" + className);

            //获取包路径
            PackageElement packageElement = mElementsUtils.getPackageOf(typeElement);
            String packageName = packageElement.getQualifiedName().toString();

            mMessager.printMessage(Diagnostic.Kind.WARNING,"packageName:" + packageName);

            //获取用于生成的类名
            className = getClassName(typeElement,packageName);

            //获取注解值
            Bind bindAnnotation = typeElement.getAnnotation(Bind.class);
            int value = bindAnnotation.value();
            System.out.println("value:" + value);

            //生成方法
            MethodSpec.Builder methodBuilder = MethodSpec
                    .methodBuilder("sayHello")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(TypeName.INT,"n")
                    .returns(TypeName.INT);

            //$L表示字面量 $T表示类型
            methodBuilder.addStatement("return $L",value);

            //生成的类
            TypeSpec type = TypeSpec
                    .classBuilder(className + "$$" + value)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodBuilder.build())
                    .build();

            //创建javaFile文件对象
            JavaFile javaFile = JavaFile.builder(packageName,type).build();
            //写入源文件
            javaFile.writeTo(mFiler);
        }
    }

    /**
     * 根据type和package获取类名
     * @param type
     * @param packageName
     * @return
     */
    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
