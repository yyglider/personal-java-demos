package netty.protocol.v1;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {

        // 将Message转换成二进制数据
        Header header = message.getHeader();

        // 这里写入的顺序就是协议的顺序.
        // 写入Header信息
        out.writeInt(header.getVersion());
        out.writeInt(message.getContent().length());
        out.writeBytes(header.getServiceName().getBytes());

        // 写入消息主体信息
        out.writeBytes(message.getContent().getBytes());
    }
}
