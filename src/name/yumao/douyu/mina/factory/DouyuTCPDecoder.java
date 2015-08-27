package name.yumao.douyu.mina.factory;

//import org.apache.log4j.Logger;
import name.yumao.douyu.utils.HexUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class DouyuTCPDecoder extends CumulativeProtocolDecoder{
//	private static Logger logger = Logger.getLogger(TCPDecoder.class);
	private static int maxBodyLength = 1024;
	@Override
	protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput out) throws Exception {
//		logger.info("in.remaining : "+ioBuffer.remaining());
		if(ioBuffer.remaining() >= 4){
			//有数据时，读取前4字节判断消息长度 
            byte [] sizeBytes = new byte[4]; 
            //读取4字节 
            ioBuffer.get(sizeBytes);
            //获取消息长度
            int contentLength = HexUtils.getHexStringLength(sizeBytes);
        	if(contentLength > maxBodyLength){
        		//头肯定有问题啦 所以搜索下这里的内容有木有正常头
        		//木有的话直接全丢掉 有的话将指针移到正常头
        		ioBuffer.mark();
        		byte [] tmp = new byte[ioBuffer.limit()]; 
        		ioBuffer.get(tmp);
        		ioBuffer.reset();
//        		String hexStr = HexUtils.Bytes2HexStringWithOutSpace(tmp);
//        		int index = hexStr.indexOf("0000");
//        		if(index > 0){
//        			index -= 8;
//        			//MayBe有正常头
//        			ioBuffer.position(index / 2 + 4);
//        			return true;
//        		}else{
	        		ioBuffer.position(ioBuffer.limit());
	        		return true;
//        		}
        	}
        	//如果长度不够 那么让父类将缓冲区置入
            if(contentLength > ioBuffer.remaining()){
            	//如果长度头大于最大body长度的话
            	//清空缓冲区内容获取下一步讯息
            	ioBuffer.rewind();
            	//父类接收新数据，以拼凑成完整数据 
                return false;
            } else{ 
            	byte[] bodyBytes = new byte[contentLength];  
            	ioBuffer.get(bodyBytes);
            	out.write(new String(subBytes(bodyBytes,8,bodyBytes.length-8),"UTF-8"));
            	//如果读取内容后还粘了包，就让父类再重读一次，进行下一次解析 
            	if(ioBuffer.remaining() > 0){
                    return true; 
                } 
            }
		}
		return false;
	}
    private byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }
}
