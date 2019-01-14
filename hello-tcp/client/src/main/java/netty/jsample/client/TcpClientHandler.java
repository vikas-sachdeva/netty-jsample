package netty.jsample.client;

import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import netty.jsample.util.AppConstants;

public class TcpClientHandler extends ChannelInboundHandlerAdapter {

	private Scanner scanner;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf inBuffer = (ByteBuf) msg;

		String received = inBuffer.toString(CharsetUtil.UTF_8);
		System.out.println("Message received - " + received);

		writeMessage(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("Active with server " + ctx.channel().remoteAddress());
		scanner = new Scanner(System.in);
		writeMessage(ctx);
	}

	private void writeMessage(ChannelHandlerContext ctx) {
		System.out.println("Enter message for sending to server and type 'quit' for terminating connection -");

		if (scanner.hasNext()) {
			String line = scanner.nextLine();
			ctx.writeAndFlush(Unpooled.copiedBuffer(line, CharsetUtil.UTF_8));
			if (AppConstants.TERMINATION_MSG.equals(line)) {
				ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
			}
		}

	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		System.out.println("Registered with server " + ctx.channel().remoteAddress());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("server inactive " + ctx.channel().remoteAddress());
		scanner.close();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		System.out.println("server unregistered " + ctx.channel().remoteAddress());
	}
}