package netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 这个组件实现了服务器的业务逻辑，决定了连接创建后和接收到信息后该如何处理
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("receive msg from client : " + msg);
        if ("quit".equalsIgnoreCase(msg)) {
            ctx.close();
        } else {
            //一定要加上换行符，否则无法解析
            final ChannelFuture f = ctx.channel().writeAndFlush(msg + "\t");

            //当一个写请求已经完成时通知到我们
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    assert f == future;
                    System.out.println("write back finished!");
                }
            });
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel read complete!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
