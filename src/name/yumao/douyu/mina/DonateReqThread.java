package name.yumao.douyu.mina;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import name.yumao.douyu.utils.HexUtils;
import name.yumao.douyu.utils.SQLiteReadBDS;
import name.yumao.douyu.utils.SQLiteUtils;
import name.yumao.douyu.utils.SQLiteWriteBDS;
import name.yumao.douyu.utils.SttEncoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

public class DonateReqThread implements Runnable {
	private static Logger logger = Logger.getLogger(DonateReqThread.class);
    private IoSession session; 
    private SttEncoder sttEncoder;
    private Calendar calendar;
    private Properties properties;
    private HashMap<String,String> donateresForLast5Sec;
    private static String endStr[] = {"!","~",".","……","'","+","-","*"," ",""};
    public DonateReqThread(IoSession session,String inNum) { 
        this.session = session; 
        this.sttEncoder = new SttEncoder();
		this.calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			InputStream inputStream=new BufferedInputStream(new FileInputStream("conf"+File.separator+"donate.properties"));
			this.properties = new Properties();
			properties.load(inputStream);
			String dabPath = "logs"+File.separator+"dab"+File.separator+simpleDateFormat.format(calendar.getTime())+"-"+inNum+".dab";
			SQLiteReadBDS.setSqliteUrl(dabPath);
			SQLiteWriteBDS.setSqliteUrl(dabPath);
			SQLiteUtils.initDB();
		}catch(Exception e){
			logger.info("加载数据库驱动失败");
		}
    } 
	@SuppressWarnings("rawtypes")
	public void run() { 
		if(Boolean.parseBoolean(properties.getProperty("DonateReq_Switch"))){
			int waitTimeInt = Integer.parseInt(properties.getProperty("DonateReq_WaitTime"));
			String waitTime = "-" + waitTimeInt/1000 + "";
			try {
				String baseStr = new String(properties.getProperty("DonateReq_String").getBytes("ISO8859-1"),"UTF-8");
				sttEncoder.Clear();
				sttEncoder.AddItem("type","chatmessage");
				sttEncoder.AddItem("receiver","0");
				sttEncoder.AddItem("content","[TeemoBot]"+new String(properties.getProperty("DonateReq_Welcome").getBytes("ISO8859-1"),"UTF-8"));
				sttEncoder.AddItem("scope","");
				session.write(HexUtils.setStringHeader("b1020000"+HexUtils.Bytes2HexStringLower(sttEncoder.GetResualt().getBytes("UTF-8"))+"00"));
				Thread.sleep(1000);
				while(true){
			    	donateresForLast5Sec = SQLiteUtils.selectDonateresForLast5Sec(waitTime);
			    	Iterator iter = donateresForLast5Sec.entrySet().iterator();
			    	while (iter.hasNext()) {
						Thread.sleep(5000);
			    		Map.Entry entry = (Map.Entry) iter.next();
			    		String key = (String)entry.getKey();
			    		String val = (String)entry.getValue();
						sttEncoder.Clear();
						sttEncoder.AddItem("type","chatmessage");
						sttEncoder.AddItem("receiver","0");
						sttEncoder.AddItem("content","[TeemoBot]"+ baseStr.replace("#name#", key).replace("#num#", val) + endStr[(int)(Math.random()*10)]);
						sttEncoder.AddItem("scope","");
						session.write(HexUtils.setStringHeader("b1020000"+HexUtils.Bytes2HexStringLower(sttEncoder.GetResualt().getBytes("UTF-8"))+"00"));
			    	}
					Thread.sleep(waitTimeInt);
				}
			} catch (Exception e) {
				logger.info(e.toString());
			}
	    }
    }
}
