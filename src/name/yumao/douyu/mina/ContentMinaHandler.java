package name.yumao.douyu.mina;

//import javax.swing.ImageIcon;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.yumao.douyu.swing.ToolSub;
import name.yumao.douyu.swing.ToolTipInterface;
import name.yumao.douyu.utils.ClassUtils;
import name.yumao.douyu.utils.SQLiteReadBDS;
import name.yumao.douyu.utils.SQLiteUtils;
import name.yumao.douyu.utils.SQLiteWriteBDS;
import name.yumao.douyu.utils.SttDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ContentMinaHandler implements IoHandler {
	private ToolSub sub = null;
	private ToolTipInterface tti = null;
	private String filepath;
	private JTextField inNum;
	private JButton butnSure;
	private IoSession loginSession;
	private SttDecoder sttDecoder;
	private SttDecoder suiDecoder;
	private Calendar calendar;
	private static Logger logger = Logger.getLogger(ContentMinaHandler.class);
	public ContentMinaHandler(String filepath,JTextField inNum,JButton butnSure,ToolTipInterface tti,IoSession loginSession){
		this.filepath = filepath;
		this.inNum = inNum;
		this.butnSure = butnSure;
		this.tti = tti;
		this.loginSession = loginSession;
		this.sttDecoder = new SttDecoder();
		this.suiDecoder = new SttDecoder();
		this.calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dabPath = "logs"+File.separator+"dab"+File.separator+simpleDateFormat.format(calendar.getTime())+"-"+inNum.getText()+".dab";
		SQLiteReadBDS.setSqliteUrl(dabPath);
		SQLiteWriteBDS.setSqliteUrl(dabPath);
		SQLiteUtils.initDB();
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable arg1)
			throws Exception {
	}
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception{
		String msgStr = (String)message;
//					logger.info("DanmuServer:" + msgStr);
		//评论解析操作
		sttDecoder.Parse(msgStr);
		//收到评论
		if(sttDecoder.GetItem("type").equals("chatmessage")){
			if(filepath!=null&&(!filepath.equals(""))){
				//进行弹幕分析 拉起字幕转储进程
				String content = sttDecoder.GetItem("content");
				sub.addSubString(content.replace("=", "-"));
			}else{
				//进行弹幕分析 拉起气泡进程
				String content = sttDecoder.GetItem("content");
				String snick = sttDecoder.GetItem("snick");
				logger.info("接收到弹幕消息\t" + snick + " : " +content);
				if(tti == null){
					tti = ClassUtils.getInstance().getTTI();;
				}
				tti.setToolTip(snick + " : " +content);
				SQLiteUtils.addContent(snick , content);
			}
		}
		//收到鱼丸
		if(sttDecoder.GetItem("type").equals("donateres")){
			String ms = sttDecoder.GetItem("ms");
			String sui = sttDecoder.GetItem("sui");
			suiDecoder.Clear();
			suiDecoder.Parse(sui);
			String snick = suiDecoder.GetItem("nick");
			logger.info("接收到礼物消息\t" + snick + " 赠送给主播" + ms + "个鱼丸");
			if(filepath!=null&&(!filepath.equals(""))){
				//进行弹幕分析 拉起字幕转储进程
				sub.addSubString(snick + " 赠送给主播" + ms + "个鱼丸".replace("=", "-"));
			}else{
				if(tti == null){
					tti = ClassUtils.getInstance().getTTI();
				}
				if(filepath!=null&&(!filepath.equals(""))){}else{
					tti.setToolTip(snick + " 赠送给主播" + ms + "个鱼丸");
					SQLiteUtils.addDonateres(snick ,ms);
				}
			}
		}
		//收到酬勤
		if(sttDecoder.GetItem("type").equals("bc_buy_deserve")){
			String sui = sttDecoder.GetItem("sui");
			String count = sttDecoder.GetItem("cnt");
			String lev = sttDecoder.GetItem("lev");
			String levStr[] = {"没有酬勤","初级酬勤","中级酬勤","高级酬勤"};
			suiDecoder.Clear();
			suiDecoder.Parse(sui);
			String snick = suiDecoder.GetItem("nick");
			logger.info("接收到酬勤消息\t" + snick + " 赠送给主播" + count + "个" + levStr[Integer.parseInt(lev)]);
			if(filepath!=null&&(!filepath.equals(""))){
				//进行弹幕分析 拉起字幕转储进程
				sub.addSubString(snick + " 赠送给主播" + count + "个" + levStr[Integer.parseInt(lev)].replace("=", "-"));
			}else{
				if(tti == null){
					tti = ClassUtils.getInstance().getTTI();
				}
				if(filepath!=null&&(!filepath.equals(""))){}else{
					tti.setToolTip(snick + " 赠送给主播" + count + "个" + levStr[Integer.parseInt(lev)]);
				}
			}
		}
	}
	@Override
	public void messageSent(IoSession session, Object massage) throws Exception {
//		String msg =new String(HexUtils.HexString2Bytes(massage.toString()),"utf-8") ;
//		logger.info("Send Danmu Server " + msg);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		tti.setToolTip("失去与弹幕服务器的连接");
		logger.info("失去与弹幕服务器的连接");
		inNum.setEditable(true);
		butnSure.setEnabled(true);
		loginSession.close(true);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		if(filepath!=null&&(!filepath.equals(""))){
			//录制模式建立连接的同时建立字幕文件
			if(sub == null){
				sub = new ToolSub(filepath);
			}
		}
		
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}
}
