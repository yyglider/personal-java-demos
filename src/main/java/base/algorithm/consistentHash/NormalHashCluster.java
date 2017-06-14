package base.algorithm.consistentHash;

import java.util.stream.IntStream;

/**
 * 当一个请求资源，请求某个集群时，通过对请求资源进行hash得到的值，然后对存储集群的节点数取模来得到，该请求资源，应该存储到哪一个存储节点。
 * Created by 006564 on 2017/5/2.
 */
public class NormalHashCluster extends Cluster {
    public NormalHashCluster() {
        super();
    }
    @Override
    public void addNode(Node node) {
        this.nodes.add(node);
    }
    @Override
    public void removeNode(Node node) {
        this.nodes.removeIf(o -> o.getIp().equals(node.getIp()) ||
                o.getDomain().equals(node.getDomain()));
    }
    @Override
    public Node getNode(String key) {
        long hash = key.hashCode();
        long index =  hash % nodes.size();
        return nodes.get((int)index);
    }

    public static void main(String[] args) {

        Cluster cluster = new NormalHashCluster();
        cluster.addNode(new Node("c1.yywang.info", "192.168.0.1"));
        cluster.addNode(new Node("c2.yywang.info", "192.168.0.2"));
        cluster.addNode(new Node("c3.yywang.info", "192.168.0.3"));
        cluster.addNode(new Node("c4.yywang.info", "192.168.0.4"));

        int DATA_COUNT = 10000;
        String PRE_KEY = "node_key";

        IntStream.range(0, DATA_COUNT)
                .forEach(index -> {
                    Node node = cluster.getNode(PRE_KEY + index); //按照key值找到需要存放的node
                    node.put(PRE_KEY + index, "Test Data");
                    System.out.println(PRE_KEY + index+":"+"Test Data"); //将该key，value值放到该node.data中

                });

        System.out.println("数据分布情况：");
        cluster.nodes.forEach(node -> {
            System.out.println("IP:" + node.getIp() + ",数据量:" + node.getData().size());
        });

        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.getNode(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }
}
