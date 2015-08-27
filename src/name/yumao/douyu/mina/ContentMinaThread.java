package name.yumao.douyu.mina;

import java.net.InetSocketAddress;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.mina.factory.DouyuCodecFactory;
import name.yumao.douyu.swing.ToolTipInterface;
import name.yumao.douyu.utils.ServerUtils;
import name.yumao.douyu.utils.SttEncoder;
import name.yumao.douyu.vo.ContentServerVo;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class ContentMinaThread implements Runnable{
	private static Logger logger = Logger.getLogger(ContentMinaThread.class);
	private static String Server2_Host;
	private static int Server2_Port;
	private String filepath;
	private JTextField inNum;
	private JButton butnSure;
	private String loginUser;
	private String gid;
	private ToolTipInterface tti;
	private IoSession loginSession;
	private SttEncoder sttEncoder;
	public ContentMinaThread(String filepath,List<ContentServerVo> contentServerList,JTextField inNum,JButton butnSure,String loginUser,String gid,ToolTipInterface tti,IoSession loginSession){
		//拉起进程同时随机选择登陆服务器
		ContentServerVo cntentServerVo = contentServerList.get((int)(Math.random()*contentServerList.size()));
		Server2_Host = cntentServerVo.getIp();
		Server2_Port = Integer.parseInt(cntentServerVo.getPort());
		logger.info("随机选择弹幕服务器 " + Server2_Host + ":" + Server2_Port);
		//初始化
		this.filepath = filepath;
		this.inNum = inNum;
		this.butnSure = butnSure;
		this.loginUser = loginUser;
		this.gid = gid;
		this.tti = tti;
		this.loginSession = loginSession;
		this.sttEncoder = new SttEncoder();
	}
	@Override
	public void run() {
		IoConnector connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		chain.addLast("codec", new ProtocolCodecFilter(new DouyuCodecFactory()));
		connector.setHandler(new ContentMinaHandler(filepath,inNum,butnSure,tti,loginSession));
		IoSession session = null;
		try{
			ConnectFuture future = connector.connect(new InetSocketAddress(Server2_Host,Server2_Port));
			future.awaitUninterruptibly();
			session=future.getSession();
			//发送登录信息
			sttEncoder.Clear();
			sttEncoder.AddItem("type","loginreq");
			sttEncoder.AddItem("username",loginUser);
			sttEncoder.AddItem("password","1234567890123456");
			sttEncoder.AddItem("roomid",inNum.getText());
			sttEncoder.AddItem("ct","2");
			sttEncoder.AddItem("devid",ServerUtils.getCpuIdMd5());
			session.write(sttEncoder.GetResualt());
			Thread.sleep(1000);
			//进入聊天室
			sttEncoder.Clear();
			sttEncoder.AddItem("type","joingroup");
			sttEncoder.AddItem("rid",inNum.getText());
			sttEncoder.AddItem("gid",gid);
			session.write(sttEncoder.GetResualt());
			//开启心跳包线程
			KeepLive keeplive = new KeepLive(session);
			Thread keepliveThread = new Thread(keeplive);
			keepliveThread.start();
		}catch (Exception e) {
			logger.error("弹幕服务器连接失败!",e);
		}
	}
}
