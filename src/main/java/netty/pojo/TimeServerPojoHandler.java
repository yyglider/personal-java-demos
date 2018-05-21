package netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 在这个部分被实现的协议是 TIME 协议。
 * 在不接受任何请求时他会发送一个含32位的整数的消息，并且一旦消息发送就会立即关闭连接
 * */
public class TimeServerPojoHandler extends ChannelInboundHandlerAdapter {

    /**channelActive方法将会在连接被建立并且准备进行通信时被调用*/
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}