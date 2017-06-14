package base.design_parttern.responsibility_chain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public class FilterChain {
    private List<Filter> filters = new ArrayList<Filter>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result ;

    public FilterChain addFilter(Filter filter){
        filters.add(filter);
        return this;
    }

//    public String doFilter(String str){
//        if(null != str && !("").equals(str.trim())){
//            for(Filter f : filters){
//                str = f.doFilter(str);
//            }
//        }
//        return str;
//    }

    //这样修改才能把链条握在每一个过滤器手中，有了链条，就可以控制链条了：
    private int index = 0;
    public void doFilter(String msg , FilterChain filterChain){
        if(index >= filters.size()){
            return;
        }
        Filter f = filters.get(index);
        index ++ ;
        f.doFilter(msg, filterChain);
    }
}


