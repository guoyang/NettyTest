/**
 * 
 */
package server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * @author gy
 *
 */
public class ServerPipeLineFactory implements ChannelPipelineFactory {

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = new DefaultChannelPipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8888, Delimiters.lineDelimiter()));
		pipeline.addLast("decode", new StringDecoder());
		pipeline.addLast("encode", new StringEncoder());
		
		pipeline.addLast("handler", new ServerHandler());
		return pipeline;
	}

}
