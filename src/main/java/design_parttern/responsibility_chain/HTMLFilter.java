package design_parttern.responsibility_chain;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public class HTMLFilter implements Filter {
    @Override
    public void doFilter(String str, FilterChain filterChain) {
        System.out.println("HTML FILTER --> " + str);
        filterChain.doFilter(str,filterChain);
    }
}