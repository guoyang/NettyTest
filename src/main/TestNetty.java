/**
 * 
 */
package main;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import server.ServerPipeLineFactory;
import client.ClientPush;

/**
 * @author gy
 *
 */
public class TestNetty {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		runServer();
		runClient();
	}
	
	public static void runServer() {
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), 
						Executors.newCachedThreadPool()));
		
		bootstrap.setPipelineFactory(new ServerPipeLineFactory());
		
		bootstrap.bind(new InetSocketAddress(8089));
	}
	
	public static void runClient() {
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				ChannelPipeline pipeline =  Channels.pipeline();
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("handler", new ClientPush());
				return pipeline;
			}
		});
		//创建无连接传输channel的辅助类(UDP),包括client和server
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(
				"localhost", 8089));
		future.getChannel().getCloseFuture().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
	}
}
