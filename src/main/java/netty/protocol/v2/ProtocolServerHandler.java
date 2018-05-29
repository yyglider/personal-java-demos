package netty.protocol.v2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ProtocolServerHandler extends SimpleChannelInboundHandler<Object> {
 

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj)
			throws Exception {
        Channel incoming = ctx.channel();
		
		if(obj instanceof ProtocolMsg) {
			ProtocolMsg msg = (ProtocolMsg)obj;
			System.out.println("Client->Server:"+incoming.remoteAddress()+msg.getProtocolHeader() + "content :" + msg.getBody());
			incoming.write(obj);
		}
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
