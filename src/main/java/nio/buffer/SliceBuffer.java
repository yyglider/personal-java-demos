package nio.buffer;// $Id$

import java.nio.*;

/**
 * slice() 方法根据现有的缓冲区创建一种 子缓冲区 。
 * 也就是说，它创建一个新的缓冲区，新缓冲区与原来的缓冲区的一部分共享数据。
 */
public class SliceBuffer
{
  static public void main( String args[] ) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate( 10 );

    for (int i=0; i<buffer.capacity(); ++i) {
      buffer.put( (byte)i );
    }

    // 创建一个包含槽 3 到槽 6 的子缓冲区,
    // 窗口的起始和结束位置通过设置 position 和 limit 值来指定
    buffer.position( 3 );
    buffer.limit( 7 );

    ByteBuffer slice = buffer.slice();

    //缓冲区和子缓冲区共享同一个底层数据数组
    //我们遍历子缓冲区，将每一个元素乘以 11 来改变它。例如，5 会变成 55。
    for (int i=0; i<slice.capacity(); ++i) {
      byte b = slice.get( i );
      b *= 11;
      slice.put( i, b );
    }

    buffer.position( 0 );
    buffer.limit( buffer.capacity() );

    while (buffer.remaining()>0) {
      System.out.println( buffer.get() );
    }
  }
}
