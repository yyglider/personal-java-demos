package nio;// $Id$

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class MultiPortEcho
{
  private int ports[];
  private ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );

  public MultiPortEcho( int ports[] ) throws IOException {
    this.ports = ports;

    go();
  }

  private void go() throws IOException {
    Selector selector = Selector.open();

    // Open a listener on each port, and register each one with the selector
    for (int i=0; i < ports.length; ++i) {
      //对于每一个端口，我们打开一个 ServerSocketChannel
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.configureBlocking( false );
      //绑定到给定的端口
      ServerSocket ss = ssc.socket();
      InetSocketAddress address = new InetSocketAddress( ports[i] );
      ss.bind( address );
      //将新打开的 ServerSocketChannel注册到 Selector上
      SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT );

      System.out.println( "Going to listen on "+ports[i] );
    }

    //内部循环
    while (true) {
      int num = selector.select(); //阻塞，直到至少有一个已注册的事件发生

      //返回发生了事件的 SelectionKey 对象的一个集合
      Set selectedKeys = selector.selectedKeys();

      Iterator it = selectedKeys.iterator();
      while (it.hasNext()) {
        SelectionKey key = (SelectionKey)it.next();

        //监听新连接
        if ((key.readyOps() & SelectionKey.OP_ACCEPT)
          == SelectionKey.OP_ACCEPT) {
          // 因为我们知道这个服务器套接字上有一个传入连接在等待，所以可以安全地接受它；
          // 也就是说，不用担心 accept() 操作会阻塞
          ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
          SocketChannel sc = ssc.accept();
          sc.configureBlocking( false );

          // Add the new connection to the selector
          SelectionKey newKey = sc.register( selector, SelectionKey.OP_READ );
          it.remove();

          System.out.println( "Got connection from "+sc );
        }
        //接受新连接
        else if ((key.readyOps() & SelectionKey.OP_READ)
          == SelectionKey.OP_READ) {
          // Read the data
          SocketChannel sc = (SocketChannel)key.channel();

          // Echo data
          int bytesEchoed = 0;
          while (true) {
            echoBuffer.clear();

            int r = sc.read( echoBuffer );

            if (r<=0) {
              break;
            }

            echoBuffer.flip();

            sc.write( echoBuffer );
            bytesEchoed += r;
          }

          System.out.println( "Echoed "+bytesEchoed+" from "+sc );

          it.remove();
        }

      }

//System.out.println( "going to clear" );
//      selectedKeys.clear();
//System.out.println( "cleared" );
    }
  }

  static public void main( String args[] ) throws Exception {

    int ports[] = new int[]{1010,1011,1012,1013};

    new MultiPortEcho( ports );
  }
}
