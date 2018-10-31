package cn.dankal.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import cn.dankal.annotations.ApiFactory;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@AutoService(Processor.class)
public class ApiFactoryProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

//         使用了@ApiFactory 这个注解的类的集合
        Set<TypeElement> elements = ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(ApiFactory.class));

        if (elements.size() > 0) {
            //Java类创建
            JavaFile.Builder javaBuilder;
            for (TypeElement element : elements) {
                ApiFactory a = element.getAnnotation(ApiFactory.class);
                TypeMirror apiClass = null;
                try {
                    a.value();
                } catch (MirroredTypeException e) {
                    apiClass = e.getTypeMirror();
                }
                if (apiClass == TypeName.VOID) {
                    mMessager.printMessage(Diagnostic.Kind.ERROR, element.getQualifiedName().toString() +
                            " @ApiFactory.apiClass can't be VOID.class.");
                    throw new NullPointerException(element.getQualifiedName().toString() +
                            " @ApiFactory.apiClass can't be VOID.class.");
                }
                String method = a.method();
                if (method.isEmpty()) {
                    mMessager.printMessage(Diagnostic.Kind.ERROR, element.getQualifiedName().toString() +
                            " @ApiFactory.method can't be null.");
                    throw new NullPointerException(element.getQualifiedName().toString() +
                            " @ApiFactory.method can't be null.");
                }
                //@ApiFactory 注解的对象是否是interface
                if (element.getKind().isInterface()) {
                    //public final class XXXFactory
                    TypeSpec.Builder classBuilder = TypeSpec.classBuilder(
                            element.getSimpleName().toString() + "Factory")
                            .addJavadoc("由APT自动生成\r\n")
                            .addModifiers(FINAL, PUBLIC);


                    //创建构造函数
                    MethodSpec constructorBuilder = MethodSpec.constructorBuilder()
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(ClassName.get("retrofit2", "Retrofit"), "retrofit")
                            .addStatement("this.retrofit = retrofit")
                            .build();

                    classBuilder.addMethod(constructorBuilder)
                            .addField(ClassName.get("retrofit2", "Retrofit"), "retrofit", Modifier.PRIVATE, Modifier.STATIC);


                    List<? extends Element> enclosedElements = element.getEnclosedElements();
                    for (Element enclosedElement : enclosedElements) {
                        if (enclosedElement.getKind().equals(ElementKind.METHOD)) {


                            classBuilder.addMethod(collectMethod(element,
                                    (ExecutableElement) enclosedElement, apiClass, method));
                        }
                    }

                    javaBuilder = JavaFile.builder("api", classBuilder.build());
                    try {
                        javaBuilder.build().writeTo(mFiler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mMessager.printMessage(Diagnostic.Kind.ERROR, "ApiFactory can only use to Interface.");
                    throw new RuntimeException("ApiFactory can only use to Interface.");
                }
            }
        }
        return true;
    }

    private MethodSpec collectMethod(TypeElement typeElement, ExecutableElement enclosedElement,
                                     TypeMirror apiClass, String method) {

        //public static Observable<BaseResponse<CarouselBean>> homeCarousel(){}
        String observablePg = "io.reactivex.Observable";
        String resultName = parest(enclosedElement, observablePg);

        //ObservableSource
        String observablePg2 = "io.reactivex";
        String observableName = "Observable";
        ClassName observableClass = ClassName.get(observablePg2, observableName);

        MethodSpec.Builder methodBuilder = MethodSpec
                .methodBuilder(enclosedElement.getSimpleName().toString())
                .addModifiers(PUBLIC, STATIC)
                .returns(TypeName.get(enclosedElement.getReturnType()));

        List<? extends VariableElement> parameters = enclosedElement.getParameters();
        StringBuilder stringBuilder = null;
        if (parameters.size() > 0) {
            stringBuilder = new StringBuilder();
            for (VariableElement parameter : parameters) {
                methodBuilder.addParameter(TypeName.get(parameter.asType()), parameter.getSimpleName().toString());
                stringBuilder.append(parameter.getSimpleName().toString()).append(",");
            }
        }

        /*return BaseApi.getRetrofit()
                .create(HomeService.class)
                .homeCarousel()*/
        methodBuilder
                .addCode("$T<$N> observable = \r\n", observableClass, resultName)
                .addCode("$T.$N()\r\n", TypeName.get(apiClass), method)
                .addCode(".create($T.class)\r\n", mElementUtils.getTypeElement(typeElement.getQualifiedName()))
                .addCode(".$L($L);\n", enclosedElement.getSimpleName().toString(),
                        stringBuilder == null ? "" : stringBuilder.toString()
                                .substring(0, stringBuilder.toString().length() - 1))
                .addCode("return observable");


        if (TypeName.get(enclosedElement.getReturnType()).toString().contains(observablePg)) {


            //Function
            String functionPg = "io.reactivex.functions";
            String functionName = "Function";
            ClassName functionClass = ClassName.get(functionPg, functionName);

            //Schedulers
            String schedulersPg = "io.reactivex.schedulers";
            String schedulersName = "Schedulers";
            ClassName schedulersClass = ClassName.get(schedulersPg, schedulersName);

            //AndroidSchedulers
            String androidSchedulersPg = "io.reactivex.android.schedulers";
            String androidSchedulersName = "AndroidSchedulers";
            ClassName androidSchedulersClass = ClassName.get(androidSchedulersPg, androidSchedulersName);

            //ExceptionHandle
            String exceptionHandlePg = "cn.dankal.basiclib.rxjava";
            String exceptionHandleName = "ExceptionHandle";
            ClassName exceptionHandleClass = ClassName.get(exceptionHandlePg, exceptionHandleName);

            //ObservableSource
            String observableSourcePg = "io.reactivex";
            String observableSourceName = "ObservableSource";
            ClassName observableSourceClass = ClassName.get(observableSourcePg, observableSourceName);

            //ExceptionHandleFuntion
            String exceptionHandleFuntionPg = "cn.dankal.basiclib.rx";
            String exceptionHandleFuntionName = "RefreshTokenHelper";
            ClassName exceptionHandleFuntionClass = ClassName.get(exceptionHandleFuntionPg, exceptionHandleFuntionName);

            CodeBlock.Builder codeBlockBuilder = CodeBlock.builder()
                    .add("\r\n.onErrorResumeNext($T.refreshTokenFunc(observable))", exceptionHandleFuntionClass)
                    .add("\r\n.subscribeOn($T.io())", schedulersClass)
                    .add("\r\n.observeOn($T.mainThread())", androidSchedulersClass)
                    .add("\r\n.unsubscribeOn($T.io())", schedulersClass);
            methodBuilder.addCode(codeBlockBuilder.build());
        }
        methodBuilder.addCode(";\r\n");
        return methodBuilder.build();
    }

    private String parest(ExecutableElement element, String observablePg) {
        //Observable<String>
        String returnType = TypeName.get(element.getReturnType()).toString();
        return returnType.substring(observablePg.length() + 1, returnType.length() - 1);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ApiFactory.class.getCanonicalName());
    }

}
