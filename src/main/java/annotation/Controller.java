package main.java.annotation;

/**
 * Created by yaoyuan on 2017/2/23.
 */
public class Controller {
    @RequestMapping(path= "/main/java/test/{id}/resource",method = RequestMapping.RequestMethod.GET)
    public void test(){
        System.out.println("main/java/test");
    }
}
