package base.algorithm.consistentHash;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 一个节点包括domain(域名)，ip(IP地址)，data(节点存储数据)，节点可以存放、删除、获取数据。
 * Created by yyglider on 2017/5/2.
 */
public class Node{

    private final static int DEFAULT_MAPSIZE = 3000;

    private String domain;

    private String ip;

    private Map<String, Object> data = new HashMap<>(DEFAULT_MAPSIZE);

    public Node(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
    }

    public void put(String key, Object value) {
        data.put(key,value);
    }

    public void remove(String key){
        data.remove(key);
    }

    public Object get(String key){
        return data.get(key);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
