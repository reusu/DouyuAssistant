package name.yumao.douyu.http;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import name.yumao.douyu.http.HttpClientFromDouyu;

public class DownloaderThead implements Runnable{
	private static Logger logger = Logger.getLogger(DownloaderThead.class);	
	private JTextField inNum;
	private JButton butnSure;
	private HttpVideoDownloader httpVideoDownloader;
	private Calendar calendar;
	public DownloaderThead(JTextField inNum,JButton butnSure){
		logger.info("Sub:初始化录像进程");
		this.inNum = inNum;
		this.butnSure = butnSure;
		this.httpVideoDownloader = new HttpVideoDownloader();
	}
	@Override
	public void run() {
		while(true){
			this.calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			httpVideoDownloader.download(inNum,butnSure,HttpClientFromDouyu.QueryDouyuDownloadUrl(inNum.getText()), "record\\" + simpleDateFormat.format(calendar.getTime())+"-"+inNum.getText()+".flv");
			try{Thread.sleep(10000);}catch (Exception e) {}
		}
	}
}
