package name.yumao.douyu.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import name.teemo.api.vo.live.douyu.DouyuApiServersVo;
import name.yumao.douyu.mina.LoginMinaThread;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpVideoDownloader {
	public void download(JTextField inNum,JButton butnSure,String url,String filePath){
       HttpClient client = new DefaultHttpClient();  
       HttpGet httpGet = new HttpGet(url);  
        try {  
			//进行录像操作
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
    			//启动弹幕获取进程 将弹幕写成ass文件
    			try{
    				Integer.parseInt(inNum.getText());
    			}catch (NumberFormatException numEx) {
    				// 进行房间参数的号码转换
    				inNum.setText(HttpClientFromDouyu.QueryDouyuRoomNum(inNum.getText()));
    			}
    			// 获取登入服务器列表
    			List<DouyuApiServersVo> loginServerList = HttpClientFromDouyu.QueryLoginServer(inNum.getText());
    			// 拉起弹幕获取进程
    			LoginMinaThread loginMina = new LoginMinaThread(filePath.substring(0,filePath.indexOf("."))+".ass",loginServerList,inNum,butnSure);
    			Thread loginMinaThread = new Thread(loginMina);
    			loginMinaThread.start();
    			//进行录像文件的储存操作
	            HttpEntity entity = response.getEntity();  
	            InputStream is = entity.getContent();  
	            File file = new File(filePath);  
	            FileOutputStream fileout = new FileOutputStream(file);  
	            //缓存200KB
	            byte[] buffer=new byte[204800];  
	            int ch = 0;  
	            while ((ch = is.read(buffer)) != -1) {  
	                fileout.write(buffer,0,ch);  
	            }  
	            is.close();  
	            fileout.flush();  
	            fileout.close();  
            }
            httpGet.abort();
        }catch (Exception e){  
            e.printStackTrace();  
        }
	}
}
