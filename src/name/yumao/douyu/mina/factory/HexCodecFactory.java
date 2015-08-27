package name.yumao.douyu.mina.factory;

//import org.apache.log4j.Logger;
import name.yumao.douyu.utils.HexUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class HexCodecFactory implements ProtocolCodecFactory{
//	private static Logger logger = Logger.getLogger(HexCodecFactory.class);
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		ProtocolDecoder pd = new ProtocolDecoder(){
			@Override
			public void decode(IoSession session, IoBuffer ioBuffer,ProtocolDecoderOutput out) throws Exception {
//				System.out.println(HexUtils.ioBufferToHexString(ioBuffer));
//				System.out.println(HexUtils.ioBufferToString(ioBuffer).substring(12));
//				out.write(HexUtils.ioBufferToString(ioBuffer));
				out.write(HexUtils.ioBufferToHexString(ioBuffer));
			}
			@Override
			public void dispose(IoSession session) throws Exception {
			}
			@Override
			public void finishDecode(IoSession session, ProtocolDecoderOutput arg1) throws Exception {
			}
		};
		return pd;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        ProtocolEncoder pe = new ProtocolEncoder() {  
            @Override  
            public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {  
                out.write(HexUtils.hexString2IoBuffer(message.toString()));  
            }  
            @Override  
            public void dispose(IoSession session) throws Exception {  
            }		
        };  
        return pe;  
	}
}
