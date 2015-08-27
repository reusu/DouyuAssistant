package name.yumao.douyu.mina.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class DouyuCodecFactory implements ProtocolCodecFactory{
	private DouyuTCPEncoder encoder = null;
	private DouyuTCPDecoder decoder = null;
	
    public DouyuCodecFactory() {
        encoder = new DouyuTCPEncoder();
        decoder = new DouyuTCPDecoder();
    }
    
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return this.decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		 return this.encoder;
	}

}
