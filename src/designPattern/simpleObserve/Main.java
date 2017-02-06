package designPattern.simpleObserve;

/**
 * Created by yaoyuan on 2016/7/21.
 */

public class Main
{
    public static void main(String[] args){
        SimpleObservable doc = new SimpleObservable ();
        SimpleObserver view = new SimpleObserver (doc);
        doc.setData(1);
        doc.setData(2);
        doc.setData(2);
        doc.setData(3);
    }
}