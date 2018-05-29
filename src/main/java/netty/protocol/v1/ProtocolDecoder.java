package netty.protocol.v1;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

//@ChannelHandler.Sharable
public class ProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < 8) {
            return;
        }

        // 获取协议的版本
        int version = in.readInt();
        // 获取消息长度
        int contentLength = in.readInt();
        // 获取服务名
        byte[] serviceNameByte = new byte[40];
        in.readBytes(serviceNameByte);
        String serviceName = new String(serviceNameByte);

        // 组装协议头
        Header header = new Header(version, contentLength, serviceName);

        // 读取消息内容
        byte[] content = in.readBytes(in.readableBytes()).array();

        Message message = new Message(header, new String(content));

        out.add(message);
    }
}
