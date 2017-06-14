package nio.buffer;// $Id$

import java.net.*;
import java.nio.*;
import java.nio.channels.*;


/**
 * 分散和聚集
 * 个分散的读取就像一个常规通道读取，只不过它是将数据读到一个缓冲区数组中而不是读到单个缓冲区中。
 * 同样地，一个聚集写入是向缓冲区数组而不是向单个缓冲区写入数据。
 */
public class UseScatterGather {
    static private final int firstHeaderLength = 2;
    static private final int secondHeaderLength = 4;
    static private final int bodyLength = 6;
    static private final int default_port = 1919;

    static public void main(String args[]) throws Exception {


        int port = default_port;

        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        ssc.socket().bind(address);

        int messageLength =
                firstHeaderLength + secondHeaderLength + bodyLength;

        ByteBuffer buffers[] = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(firstHeaderLength);
        buffers[1] = ByteBuffer.allocate(secondHeaderLength);
        buffers[2] = ByteBuffer.allocate(bodyLength);

        SocketChannel sc = ssc.accept();

        while (true) {

            // Scatter-read into buffers
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = sc.read(buffers);
                bytesRead += r;

                System.out.println("have read: " + r);
                System.out.println("buffer length : " + buffers.length);
                for (int i = 0; i < buffers.length; ++i) {
                    ByteBuffer bb = buffers[i];
                    System.out.println("buffer number : " + i + " , buffer pos:  " + bb.position() + ", buffer limit:  " + bb.limit());
                }
            }

            // Process message here

            // Flip buffers
            System.out.println("begin flip ...");
            for (int i = 0; i < buffers.length; ++i) {
                ByteBuffer bb = buffers[i];
                bb.flip();
            }
            System.out.println("flip done ...");

            // Scatter-write back out, 聚集写入
            System.out.println("scatter write ...");
            long bytesWritten = 0;
            while (bytesWritten < messageLength) {
                long r = sc.write(buffers);
                bytesWritten += r;
            }
            System.out.println("scatter write done...");

            // Clear buffers
            for (int i = 0; i < buffers.length; ++i) {
                ByteBuffer bb = buffers[i];
                bb.clear();
            }
            System.out.println("bytes read : "+bytesRead + " , bytes write : " + bytesWritten + " , message length : " + messageLength);
        }
    }
}
