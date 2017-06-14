package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by yyglider on 2017/6/6.
 */
public class SelectorDemo {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        //向selector注册通道
        ServerSocketChannel channel1 = ServerSocketChannel.open();

        //与Selector一起使用时，Channel必须处于非阻塞模式下
        channel1.configureBlocking(false);


        ServerSocket ss = channel1.socket();
        InetSocketAddress address = new InetSocketAddress(1234);
        ss.bind(address);

        int interestSet = SelectionKey.OP_ACCEPT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        channel1.register(selector,interestSet);


        while (true){
            //返回已经准备就绪的那些通道，阻塞到至少有一个通道在你注册的事件上就绪了
            int readyChannels = selector.select();

            if(readyChannels == 0) continue;

            //访问“已选择键集（selected key set）”中的就绪通道
            Set selectedKeys = selector.selectedKeys();

            Iterator keyIter = selectedKeys.iterator();

            while (keyIter.hasNext()){
                SelectionKey key = (SelectionKey) keyIter.next();

                if(key.isAcceptable()){
                    System.out.println(" a connection was accepted by a ServerSocketChannel");
                }
                else if(key.isConnectable()){
                    System.out.println("a connection was established with a remote server");
                }
                else if(key.isReadable()){
                    System.out.println(" a channel is ready for reading");
                }
                else if (key.isWritable()){
                    System.out.println("a channel is ready for writing");
                }
                //Selector不会自己从已选择键集中移除SelectionKey实例。必须在处理完通道时自己移除。
                //下次该通道变成就绪时，Selector会再次将其放入已选择键集中
                keyIter.remove();

            }
        }
    }
}
