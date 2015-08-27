package name.yumao.douyu.swing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class ToolSub {
	private Date initTime;
	private File file;
	private BufferedWriter bufferedWriter;
	private Random random;
	private static Logger logger = Logger.getLogger(ToolSub.class);
	public ToolSub(String filePath) throws Exception{
		logger.info("Sub:初始化字幕转储进程");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		this.initTime = calendar.getTime();
		this.file = new File(filePath);  
		this.file.getParentFile().mkdirs();
		this.bufferedWriter = new BufferedWriter(new FileWriter(file));
		this.random = new Random();
		bufferedWriter.write("[Script Info]");
		bufferedWriter.newLine();
		bufferedWriter.write("; // 此字幕由斗鱼TV录制助手生成");
		bufferedWriter.newLine();
		bufferedWriter.write("; // 欢迎关注Teemo的腾讯微博");
		bufferedWriter.newLine();
		bufferedWriter.write("; // http://t.qq.com/reusu123");
		bufferedWriter.newLine();
		bufferedWriter.write("Title:DouyuAssiatantSub");
		bufferedWriter.newLine();
		bufferedWriter.write("Original Script:DouyuAssiatantV3.x");
		bufferedWriter.newLine();
		bufferedWriter.write("Synch Point:0");
		bufferedWriter.newLine();
		bufferedWriter.write("ScriptType:v4.00");
		bufferedWriter.newLine();
		bufferedWriter.write("Collisions:Normal");
		bufferedWriter.newLine();
		bufferedWriter.write("PlayResX:1280");
		bufferedWriter.newLine();
		bufferedWriter.write("PlayResY:720");
		bufferedWriter.newLine();
		bufferedWriter.write("Timer:100.0000");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		bufferedWriter.write("[V4+ Styles]");
		bufferedWriter.newLine();
		bufferedWriter.write("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
		bufferedWriter.newLine();
		bufferedWriter.write("Style: Default,微软雅黑,25,&H00FFFFFF,&H00000000,&H00F0AAD5,&H00000000,-1,0,0,0,100,100,0,0.00,1,1,0,2,30,30,10,134");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		bufferedWriter.write("[Events]");
		bufferedWriter.newLine();
		bufferedWriter.write("Format: Layer, Start, End, Style, Actor, MarginL, MarginR, MarginV, Effect, Text");
		bufferedWriter.flush();
	}
	public void addSubString(String str) throws Exception{
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		Long startTime = calendar.getTime().getTime() - initTime.getTime();
		startTime = startTime + 0;
		Long endTime = startTime + 10000;
		String randomNum = random.nextInt(720) + "";
		String startDate = String.format("%02d:%02d:%02d.%02d",
				(startTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60),
				(startTime % (1000 * 60 * 60)) / (1000 * 60),
				((startTime % (1000 * 60)) / 1000),
				startTime /10 %100);  
		String endDate = String.format("%02d:%02d:%02d.%02d",
				(endTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60),
				(endTime % (1000 * 60 * 60)) / (1000 * 60),
				((endTime % (1000 * 60)) / 1000),
				endTime /10 %100);  
		if(str.startsWith("="))str=" "+str;
		bufferedWriter.newLine();
		bufferedWriter.write("Dialogue: 0," + startDate + "," + endDate + ",*Default,NTP,0000,0000,0000,,{\\move(1280," + randomNum + ",0," + randomNum + ")}" + str);
		bufferedWriter.flush();
	}
}
