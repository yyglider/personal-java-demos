package netty.protocol.v1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class ServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private static final ProtocolEncoder ENCODER = new ProtocolEncoder();

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        // 添加编解码器, 由于ByteToMessageDecoder的子类无法使用@Sharable注解,
        // 这里必须给每个Handler都添加一个独立的Decoder.
        pipeline.addLast(ENCODER);
        pipeline.addLast(new ProtocolDecoder());

        // 添加逻辑控制层
        pipeline.addLast(new ServerHandler());

    }
}


