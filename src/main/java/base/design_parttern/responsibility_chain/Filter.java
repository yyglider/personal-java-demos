package base.design_parttern.responsibility_chain;

/**
 * Created by yaoyuan on 2017/3/31.
 */
public interface Filter {
     void doFilter(String msg , FilterChain filterChain);
}
