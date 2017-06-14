package base.algorithm.consistentHash;

import java.util.ArrayList;
import java.util.List;

/**
 * 在一个集群中包含多个节点，可以在一个集群中，增加、删除节点。还可以通过key获取数据存储的节点
 * Created by yyglider on 2017/5/2.
 */
public abstract class Cluster {

    protected List<Node> nodes;

    public Cluster() {
        this.nodes = new ArrayList<>();
    }

    public abstract void addNode(Node node);

    public abstract void removeNode(Node node);

    public abstract Node getNode(String key);
}