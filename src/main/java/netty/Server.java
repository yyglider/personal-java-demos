package netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.discard.DiscardServerHandler;
import netty.echo.EchoServerHandler;
import netty.echo.LoggingServerHandler;
import netty.pojo.TimeDecoder;
import netty.time.TimeServerHandler;

/**
 * Created by yaoyuan on 2018/5/18.
 */
public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //创建BOSS线程组　服务端用于接收接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用来处理已经被接收的连接，一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动 NIO 服务的辅助启动类
            //用于启动NIO服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //Netty通过工厂类，利用反射创建NioServerSocketChannel对象
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingServerHandler())
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    })
                    //TCP/IP 的服务端，设置socket的参数选项比如tcpNoDelay 和 keepAlive
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            // 等待服务器socket关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 10086;
        new Server(port).run();
    }


}
