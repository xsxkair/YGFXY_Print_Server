package com.ncd.xsx.Server;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.ncd.xsx.Define.StringDefine;
import com.ncd.xsx.Entity.Record;
import com.ncd.xsx.Tools.Sqlite3Tool;

public class MinaServer extends IoHandlerAdapter{
	
	
	private MinaHandlers minaHandlers = null;
	
	public Boolean startMinaServer() {
		IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.setHandler(this);
        TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset.forName( "GBK" ));
	    textLineCodecFactory.setDecoderMaxLineLength(10240);
	    textLineCodecFactory.setEncoderMaxLineLength(10240);
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter(textLineCodecFactory));
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
 
        try {
			acceptor.bind( new InetSocketAddress(9200));
			
			minaHandlers = new MinaHandlers();
			
			System.out.println("NCD POCT Server Start Succeed");
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("NCD POCT Server Start Failed");
			return false;
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);

		String messageStr = message.toString();
		System.out.println(messageStr);
		if(messageStr.startsWith(StringDefine.DeviceMessageStartStr))
		{
			minaHandlers.DeviceHandler(session, messageStr);
		}
		else {
			
			minaHandlers.ClientSummyHandler(session, messageStr);
		}
	}
}
