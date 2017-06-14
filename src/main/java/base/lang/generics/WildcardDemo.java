package base.lang.generics;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yyglider on 2017/4/18.
 *
 *
 *
 <? extends T>， 该集合存放的元素必须是T或T的子类
 集合写(add)： 因为不能确定集合实例化时用的是T或T的子类，所以没有办法写。
 例如：List<? extends Number> foo = new ArrayList<Number/Integer/Double>()，
 你不能add Number，因为也可能是Integer或Double的List， 同理也不能add Integer或Double，即，extends T， 不能集合add。
 集合读(get)： 只能读出T类型的数据。

 <? super T>， 该集合存放的元素必须是T或T的父类
 集合写(add)： 可以add T或T的子类。
 集合读(get)： 不能确定从集合里读出的是哪个类型（可能是T也可能是T的父类，或者Object），所以没有办法使用get。
 例如：List<? super Integer> foo3 = new ArrayList<Integer/Number/Object>(); 只能保证get出来是Object。



 */
public class WildcardDemo {
    public static void main(String[] args) {

    }

    public void test1() {
        List<? extends A> childofa = new LinkedList<>();
        B b = new B();
        A a = new A();
//        childofa.add(a);
//        childofa.add(b);
        A ta = childofa.get(0);
    }

    public void test2() {
        List<? super B> superOfb = new LinkedList<>();
        B b = new B();
        A a = new A();
//        superOfb.add(a);
        superOfb.add(b);
//        A ta = superOfb.get(0);
//        B tb = superOfb.get(0);
    }
}

    class A {
        @Override
        public String toString() {
            return "A";
        }
    }

    class B extends A {

        @Override
        public String toString() {
            return "B";
        }
    }
