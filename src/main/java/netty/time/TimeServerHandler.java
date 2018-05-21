package netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 在这个部分被实现的协议是 TIME 协议。
 * 在不接受任何请求时他会发送一个含32位的整数的消息，并且一旦消息发送就会立即关闭连接
 * */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**channelActive方法将会在连接被建立并且准备进行通信时被调用*/
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        //分配一个包含这个消息的新的缓冲。因为我们需要写入一个32位的整数，因此我们需要一个至少有4个字节的 ByteBuf
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        //ByteBuf之所以没有flip(),因为有两个指针，一个对应读操作一个对应写操作。
        //当你向 ByteBuf 里写入数据的时候写指针的索引就会增加，同时读指针的索引没有变化。
        //读指针索引和写指针索引分别代表了消息的开始和结束。
        final ChannelFuture f = ctx.writeAndFlush(time);
        //当一个写请求已经完成时通知到我们
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });

        //或者直接调用或者直接调用f.aener(ChannelFutureListener.CLOSE)
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}