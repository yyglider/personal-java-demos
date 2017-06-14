package nio.buffer;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

/**
 * 从文件中读取
 * (1) 从 FileInputStream 获取 Channel，
 * (2) 创建 Buffer，
 * (3) 将数据从 Channel 读到 Buffer 中。
 */
public class CopyFile {
    static public void main(String args[]) throws Exception {


        String infile = "D:/test_in.txt";
        String outfile = "D:/test_out.txt";

        // 从流中获取通道
        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();


        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            // 读入之前要清空
            buffer.clear();

            // position自动前进
            int r = fcin.read(buffer);

            if (r == -1) {
                break;
            }

            // position = 0; limit=读到的字节数
            buffer.flip();

            // 从 buffer 中读
            fcout.write(buffer);
        }
    }
}
