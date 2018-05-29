package netty.protocol.v1;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class ProtocolClient {


    public static void main(String args[]) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientHandlerInitializer());

            // Start the connection attempt.
            Channel ch = b.connect("127.0.0.1", 8013).sync().channel();

            int version = 1;
            while (true){
                String serverName = "App-" + java.util.UUID.randomUUID().toString();
                String content = "I'm the protocol!";
                Header header = new Header(version ++, content.length(), serverName);
                Message message = new Message(header, content);
                ch.writeAndFlush(message);

                Thread.sleep(2000);
            }



        } finally {

            group.shutdownGracefully();
        }
    }
}
