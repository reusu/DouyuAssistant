package name.yumao.douyu.mina;

import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.swing.Donate;
import name.yumao.douyu.swing.ToolTipInterface;
import name.yumao.douyu.utils.ClassUtils;
import name.yumao.douyu.utils.ServerUtils;
import name.yumao.douyu.utils.SttDecoder;
import name.yumao.douyu.utils.SttEncoder;
import name.yumao.douyu.vo.ContentServerVo;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class LoginMinaHandler implements IoHandler {
	private static Logger logger = Logger.getLogger(LoginMinaHandler.class);
	private ToolTipInterface tti =null;
	private String username="";
	private String nickname="";
	private String gid = "";
	private List<ContentServerVo> contentServerList = null;
	private String filepath;
	private JButton butnSure;
	private JTextField inNum;
	private SttDecoder sttDecoder;
	private Properties properties;
	@SuppressWarnings("unused")
	private SttEncoder sttEncoder;
	public LoginMinaHandler(String filepath ,JTextField inNum,JButton butnSure,Properties properties){
		this.inNum = inNum;
		this.butnSure = butnSure;
		this.sttDecoder = new SttDecoder();
		this.filepath = filepath;
		this.properties = properties;
		this.sttEncoder = new SttEncoder();
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable arg1) throws Exception {
	}
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String msgStr = (String)message;
//				logger.info("LoginServer:" + msgStr);
		//信息处理模块
		sttDecoder.Parse(msgStr);
		if(sttDecoder.GetItem("type").equals("loginres")){
			//获取匿名登陆用户名
			username = sttDecoder.GetItem("username");
			nickname = sttDecoder.GetItem("nickname");
			//登陆信息获得之后开始继续登陆操作
			Thread.sleep(1000);
			KeepLive keeplive = new KeepLive(session);
			Thread keepliveThread = new Thread(keeplive);
			keepliveThread.start();
			//刷新等级信息
			Thread.sleep(3000);
//					sttEncoder.Clear();
//					sttEncoder.AddItem("type","qtlq");
//					session.write(HexUtils.setStringHeader("b1020000"+HexUtils.Bytes2HexStringLower(sttEncoder.GetResualt().getBytes("UTF-8"))+"00"));
		}
		if(sttDecoder.GetItem("type").equals("msgrepeaterlist")){
			//获取弹幕服务器列表
			contentServerList = ServerUtils.QueryContentServerList(sttDecoder.GetItem("list"));
		}
		if(sttDecoder.GetItem("type").equals("error")){
			//错误信息获取
			if(tti == null){
				tti = ClassUtils.getInstance().getTTI();
			}
			tti.setToolTip("服务器返回错误代码:"+sttDecoder.GetItem("code"));
			logger.info("服务器返回错误代码:"+sttDecoder.GetItem("code"));
		}
		if(sttDecoder.GetItem("type").equals("setmsggroup")){
			//获取弹幕分组
			gid = sttDecoder.GetItem("gid");
			if(tti == null){
				tti = ClassUtils.getInstance().getTTI();
			}
			tti.setToolTip("获取的登录用户名为: "+nickname);
			logger.info("获取的登录用户名为: "+nickname);
			Thread.sleep(100);
			tti.setToolTip("登陆的房间编号为: "+inNum.getText()+" 床");
			logger.info("登陆的房间编号为: "+inNum.getText()+" 床");
			Thread.sleep(100);
			tti.setToolTip("加入的讨论群组为: "+gid+" 组");
			logger.info("加入的讨论群组为: "+gid+" 组");
			Thread.sleep(100);
			if(filepath!=null&&(!filepath.equals(""))){
				tti.setToolTip("启动录制进程...");
			}else{
			//启动弹幕获取进程
				tti.setToolTip("启动弹幕获取进程...");
			}
			ContentMinaThread contentMina = new ContentMinaThread(filepath,contentServerList,inNum,butnSure,username,gid,tti,session);
			Thread contentMinaThead = new Thread(contentMina);
			contentMinaThead.start();
			tti.setToolTip("斗鱼TV小助手启动完毕!");
			//-----------------------开始附加程序加载项目-----------------------//
//					//赠送鱼丸测试
//					sttEncoder.Clear();
//					sttEncoder.AddItem("type","donatereq");
//					sttEncoder.AddItem("mg","0");
//					sttEncoder.AddItem("ms","10000");
//					session.write(HexUtils.setStringHeader("b1020000"+HexUtils.Bytes2HexStringLower(sttEncoder.GetResualt().getBytes("UTF-8"))+"00"));
			//开启鱼丸榜
			Thread.sleep(1000);
			new Thread(){  
				public void run(){
					@SuppressWarnings("unused")
					Donate donate =new Donate(inNum.getText());
				}
			}.start();
			//开启鱼丸反馈测试
			if(Boolean.parseBoolean(properties.getProperty("Auth_Login"))){
				Thread.sleep(1000);
				DonateReqThread donateReq = new DonateReqThread(session,inNum.getText());
				Thread donateReqThread = new Thread(donateReq);
				donateReqThread.start();
			}
			//-----------------------结束附加程序加载项目-----------------------//
		}
	}
	@Override
	public void messageSent(IoSession session, Object massage) throws Exception {
//		String msg = new String(HexUtils.HexString2Bytes(massage.toString()),"utf-8");
//		logger.info("Send Login Server: " + msg.toString().substring(12));
	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		tti.setToolTip("失去与验证服务器的连接");
		logger.info("失去与验证服务器的连接");
		inNum.setEditable(true);
		butnSure.setEnabled(true);
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}
}
