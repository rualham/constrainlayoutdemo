package com.android.sharedemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@ShuSheng(name = "frank", age = 18)
//@ShuSheng(age = 20)
public class AnnotationDemo {

    /* @Documented：将被标注的注解生成到javadoc中。
     @Inherited：其让被修饰的注解拥有被继承的能力。如下，
     我们有一个用@Inherited修饰的注解@InAnnotation，那么这个注解就拥有了被继承的能力。*/
    @Inherited
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface InAnnotation {
    }

    @InAnnotation
    class Base {
    }

    class Son extends Base {
    }
}


