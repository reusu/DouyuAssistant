package name.yumao.douyu.mina;

import java.util.Date;
import name.yumao.douyu.utils.HexUtils;
import name.yumao.douyu.utils.SttEncoder;

import org.apache.mina.core.session.IoSession;

public class KeepLive implements Runnable { 
    private IoSession session; 
    private SttEncoder sttEncoder;
    public KeepLive(IoSession session) { 
    	this.sttEncoder = new SttEncoder();
        this.session = session; 
    } 
    public void run() { 
    	while(true){
	    	sttEncoder.Clear();
	    	sttEncoder.AddItem("type","keeplive");
			sttEncoder.AddItem("tick",new Date().getTime()/1000L%100L + "");
	    	session.write(HexUtils.setStringHeader("b1020000"+HexUtils.Bytes2HexStringLower(sttEncoder.GetResualt().getBytes())+"00"));
	    	try {
	    		Thread.sleep(45000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	} 
    }
}