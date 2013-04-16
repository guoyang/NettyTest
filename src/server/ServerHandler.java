/**
 * 
 */
package server;

import java.net.InetAddress;
import java.util.Date;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author gy
 *
 */
public class ServerHandler extends SimpleChannelHandler {
//	private final ChannelBuffer buffer =  dynamicBuffer();
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
//		super.channelConnected(ctx, e);
		e.getChannel().write("hello~" + InetAddress.getLocalHost().getHostName() + "~~~");
		e.getChannel().write("now is " + new Date() + " ~~~");
	}

	@Override
	public void handleUpstream(ChannelHandlerContext arg0, ChannelEvent arg1)
			throws Exception {
		if(arg1 instanceof ChannelStateEvent) {
			System.out.println(arg1.toString());
		}
		super.handleUpstream(arg0, arg1);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
//		super.messageReceived(ctx, e);
		String request = (String) e.getMessage();
		
		String response = "";
		boolean isClose = false;
		if(request == null || "".equals(request)) {
			response = "please don't say nothing~~~";
		} else if("bye".equals(request)) {
			response = "bye bye";
			isClose = true;
		} else {
			response = "you say " + request;
		}
		
		ChannelFuture future = e.getChannel().write(response);
		
		if(isClose) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		System.out.println(e.getCause());
		e.getChannel().close();
	}
}
