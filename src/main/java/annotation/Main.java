package main.java.annotation;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yaoyuan on 2017/2/23.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Class<?> controllerClass =  Class.forName("main.java.annotation.Controller");
        Method controllerMethod = controllerClass.getDeclaredMethod("main/java/test");
        if(controllerMethod.isAnnotationPresent(RequestMapping.class)){
            RequestMapping requestMapping = controllerMethod.getAnnotation(RequestMapping.class);
            RequestMapping.RequestMethod requestMethod = requestMapping.method();
            String requestPath = requestMapping.path();
            String method = requestMethod.name();


            if (requestPath.matches(".+\\{\\w+\\}.*")) {
                // 将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
                requestPath = replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
                System.out.println(requestPath);
            }

            String currentRequestMethod = "GET";
            String currentRequestPath = "/main/java/test/id/resource";

            Matcher requstPathMather = Pattern.compile(requestPath).matcher(currentRequestPath);
            if(method.equalsIgnoreCase(currentRequestMethod) && requstPathMather.matches()){
                System.out.println("match!");
            }
        }




    }
        /**
         * 替换固定格式的字符串（支持正则表达式）
         */
    public static String replaceAll(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
