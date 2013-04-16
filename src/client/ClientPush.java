/**
 * 
 */
package client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author gy
 *
 */
public class ClientPush extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
//		super.messageReceived(ctx, e);
		String receive = (String) e.getMessage();
		
		System.out.println(receive);
	}
	
}
