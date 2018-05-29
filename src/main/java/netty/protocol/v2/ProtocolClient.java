package netty.protocol.v2;

import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 说明：自定义协议客户端
 */
public class ProtocolClient {

	private String host;
	private int port;

	/**定义接收数据包的最大长度，如果发送的数据包超过此值，则抛出异常*/
	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
	private static final int LENGTH_FIELD_LENGTH = 4;
	private static final int LENGTH_FIELD_OFFSET = 6;

	/**长度调节值，在总长被定义为包含包头长度时，修正信息长度*/
	private static final int LENGTH_ADJUSTMENT = 0;

	/**跳过的字节数，根据需要我们跳过lengthFieldLength个字节，以便接收端直接接受到不含“长度属性”的内容*/
	private static final int INITIAL_BYTES_TO_STRIP = 0;


	public ProtocolClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws InterruptedException {

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
							"decoder",
							new ProtocolDecoder(MAX_FRAME_LENGTH,
									LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
									LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
					ch.pipeline().addLast("encoder", new ProtocolEncoder());
					ch.pipeline().addLast(new ProtocolClientHandler());

				}
			});

			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync(); // (5)

			while (true) {

				// 发送消息给服务器
				ProtocolMsg msg = new ProtocolMsg();
				ProtocolHeader protocolHeader = new ProtocolHeader();
				protocolHeader.setMagic((byte) 0x01);
				protocolHeader.setMsgType((byte) 0x01);
				protocolHeader.setReserve((short) 0);
				protocolHeader.setSn((short) 0);
				String body = "hello world";
				StringBuffer sb = new StringBuffer();
//				for (int i = 0; i < 2700; i++) {
//				}
				sb.append(body);
				byte[] bodyBytes = sb.toString().getBytes(
						Charset.forName("utf-8"));
				int bodySize = bodyBytes.length;
				protocolHeader.setLen(bodySize);

				msg.setProtocolHeader(protocolHeader);
				msg.setBody(sb.toString());

				f.channel().writeAndFlush(msg);
				Thread.sleep(2000);
			}
			// 等待连接关闭
			// f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		new ProtocolClient("localhost", 8082).run();
	}

}
