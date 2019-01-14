package netty.jsample.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.jsample.util.AppConstants;

public class TcpServer {

	public void createServer() throws InterruptedException {

		EventLoopGroup group = new NioEventLoopGroup(1);

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(group);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.option(ChannelOption.SO_BACKLOG, 0);
			serverBootstrap.option(ChannelOption.AUTO_CLOSE, false);
			serverBootstrap.childOption(ChannelOption.AUTO_READ, true);
			

			serverBootstrap.childHandler(new TcpServerInitializer());

			ChannelFuture channelFuture = serverBootstrap
					.bind(new InetSocketAddress(AppConstants.BIND_IP, AppConstants.BIND_PORT)).sync();
			System.out.println("Tcp Server started listening on port " + AppConstants.BIND_PORT);
			channelFuture.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
