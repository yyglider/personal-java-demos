package main.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yaoyuan on 2017/2/23.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String path();

    RequestMethod method();

    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE,
    }
}