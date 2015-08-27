package name.yumao.douyu.mina.factory;

import name.yumao.douyu.utils.HexUtils;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class DouyuTCPEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput out)throws Exception {
		out.write(HexUtils.hexString2IoBuffer(HexUtils.setStringHeader("b1020000"+HexUtils.Bytes2HexStringLower(((String)message).getBytes("UTF-8"))+"00")));
	}
}
