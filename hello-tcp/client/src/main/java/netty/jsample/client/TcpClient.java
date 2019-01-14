package netty.jsample.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.jsample.util.AppConstants;

public class TcpClient {

	public void createClient() throws InterruptedException {

		NioEventLoopGroup group = new NioEventLoopGroup(1);

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.AUTO_READ, true);
		bootstrap.handler(new TcpClientInitializer());

		ChannelFuture channelFuture = bootstrap.connect(AppConstants.BIND_IP, AppConstants.BIND_PORT).sync();
		System.out.println("Tcp Client connected with server on " + AppConstants.BIND_PORT);
		channelFuture.channel().closeFuture().sync();
		group.shutdownGracefully().sync();
	}
}
