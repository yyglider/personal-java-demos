package nio;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by yyglider on 2017/6/7.
 */
public class IntgenClient {
    public static int DEFAULT_PORT  = 1919;

    public static void main(String[] args) {
        try {
            SocketAddress address = new InetSocketAddress(DEFAULT_PORT);
            SocketChannel client = SocketChannel.open(address);
            ByteBuffer buffer = ByteBuffer.allocate(4);
            IntBuffer view = buffer.asIntBuffer();

            for(int expected = 0;;expected++){
                client.read(buffer);
                int actual = view.get();
                buffer.clear();
                view.rewind();

                if(actual != expected){
                    System.out.println("expected : " + expected + ", actual : " + actual);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
