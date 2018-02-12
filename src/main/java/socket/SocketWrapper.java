package socket;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import static socket.Commons.*;

/***
 * 将Socket的输入/出流包装在内部，对外提供简单的读、写功能API
 */
public class SocketWrapper implements Closeable {
	
	private final static int PAGE_SIZE = 1024 * 1024;

	private Socket socket;
	
	private DataInputStream inputStream;
	
	private DataOutputStream outputStream;
	
	public SocketWrapper(Socket socket) throws IOException {
		this.socket = socket;
		processStreams();
	}
	
	public SocketWrapper(String host , int port) throws IOException {
		this.socket = new Socket();
		this.socket.connect(new InetSocketAddress(host , port) , 1500);
		this.socket.setKeepAlive(true);
		this.socket.setTcpNoDelay(true);
		//socket.setReceiveBufferSize(16 * 1024);
		//socket.setSendBufferSize(8 * 1024);
		processStreams();
	}

	/***
	 * 用DataInputStream包装，就不用自己再进行数据格式的解析了，可以直接发送或接收int，long，short等类型数据
	 * @throws IOException
	 */
	private void processStreams() throws IOException {
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	public void write(byte b) throws IOException {
		this.outputStream.write(b);
	}
	
	public void write(short s) throws IOException {
		this.outputStream.writeShort(s);
	}
	
	public void write(int i) throws IOException {
		this.outputStream.writeInt(i);
	}
	
	public void write(long l) throws IOException {
		this.outputStream.writeLong(l);
	}
	
	public void write(byte []bytes) throws IOException {
		this.outputStream.write(bytes);
	}
	
	public void write(byte []bytes , int length) throws IOException {
		this.outputStream.write(bytes , 0 , length);
	}
	
	public void write(String value , String charset) throws IOException {
		if(value != null) {
			write(value.getBytes(charset));
		}
	}
	
	public byte readByte() throws IOException {
		return this.inputStream.readByte();
	}
	
	public short readShort() throws IOException {
		return this.inputStream.readShort();
	}
	
	public int readInt() throws IOException {
		return this.inputStream.readInt();
	}
	
	public long readLong() throws IOException {
		return this.inputStream.readLong();
	}
	
	public void readFull(byte []bytes) throws IOException {
		this.inputStream.readFully(bytes);
	}
	
	public int read(byte []bytes) throws IOException {
		return this.inputStream.read(bytes);
	}
	
	public String read(int length , String charset) throws IOException {
		byte []bytes = new byte[length];
		read(bytes);
		return new String(bytes , charset);
	}


	public void writeFile(String path) throws IOException {
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(file);
		try {
			long fileLenth = file.length();
			// PAGE_SIZE在这里起到一个阈值的作用，当小于这个值时，就一次性将文件中的字节读取出来发送到kernel，由kernel调度发送，否则进行分批发送。
			// 实际上，也可以使用BufferdInputStream来达到缓冲效果，这个缓冲区实际上是java堆中一个大小为8192字节的byte[]数组，每次通过写操作时，如果
			// 没有发起flush、close操作，则不会将数据真正提交到Kernel中发送出去。
			if(fileLenth > PAGE_SIZE) {
				byte []bytes = new byte[PAGE_SIZE];
				int allLength = 0;
				int length = fileInputStream.read(bytes);
				//超过一定大小文件，分段传送
				while(length > 0) {
					allLength += length;
					this.write(bytes , length);
					length = fileInputStream.read(bytes);
					print(".");
				}
				println("实际发送文件长度为：" + allLength);
			}else {
				byte []bytes = new byte[(int)fileLenth];
				fileInputStream.read(bytes);
				this.write(bytes);
			}
		}finally {
			closeStream(fileInputStream);
		}
	}
	
	@Override
	public void close() {
		try {
			this.socket.close();
		}catch(Exception e) {
			/*this body code need you write*/
		}
	}
	
	public void displayStatus() throws SocketException {
	}
}
