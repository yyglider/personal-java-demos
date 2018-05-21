package netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ByteToMessageDecoder 是 ChannelInboundHandler 的一个实现类，
 * 用于处理数据拆分的问题
 */
public class TimeDecoder extends ByteToMessageDecoder {
    /**.每当有新数据接收的时候，ByteToMessageDecoder 都会调用 decode() 方法来处理内部的累积缓冲*/
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 判断累积缓冲里有没有足够数据，可以往 out 对象里放任意数据时。
        // 当有更多的数据被接收了 ByteToMessageDecoder 会再一次调用 decode() 方法。
        if (in.readableBytes() < 4) {
            return;
        }
        //解码器解码消息成功
        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
