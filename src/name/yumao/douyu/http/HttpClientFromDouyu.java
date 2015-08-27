package name.yumao.douyu.http;

import java.util.List;

import name.teemo.api.vo.live.douyu.DouyuApiServersVo;
import name.yumao.douyu.utils.ServerUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpClientFromDouyu {
	public static String QueryDouyuRoomNum(String roomName){
		String roomNum = "";
		try{
			HttpGet get = new HttpGet("http://api.teemo.name/live/douyuApi.do?room="+roomName);
			get.setHeader("User-Agent", "TeemoAssistant");
			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
			    HttpEntity entity = response.getEntity(); 
			    String htmlEntity = EntityUtils.toString(entity);
			    roomNum = ServerUtils.QueryRoomApi(htmlEntity).getData().getRoom_id();
			}
			get.abort();
		}catch (Exception e) {
			return roomNum;
		}
		return roomNum;
	}
	public static List<DouyuApiServersVo> QueryLoginServer(String roomNum){
		List<DouyuApiServersVo> roomApiServersVo = null;
		try{
			HttpGet get = new HttpGet("http://api.teemo.name/live/douyuApi.do?room="+roomNum);
			get.setHeader("User-Agent", "TeemoAssistant");
			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity(); 
			    String htmlEntity = EntityUtils.toString(entity);
			    roomApiServersVo = ServerUtils.QueryRoomApi(htmlEntity).getData().getServers();
			}
			get.abort();
		}catch (Exception e) {
			return roomApiServersVo;
		}
		return roomApiServersVo;
	}
	public static String QueryDouyuDownloadUrl(String roomNum){
		String downloadUrl = "";
		try{
			HttpGet get = new HttpGet("http://api.teemo.name/live/douyuApi.do?room="+roomNum);
			get.setHeader("User-Agent", "TeemoAssistant");
			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity(); 
			    String htmlEntity = EntityUtils.toString(entity);
			    downloadUrl = ServerUtils.QueryRoomApi(htmlEntity).getData().getRtmp_url() + "/" + ServerUtils.QueryRoomApi(htmlEntity).getData().getRtmp_live();
			}
			get.abort();
		}catch (Exception e) {
			return downloadUrl;
		}
		return downloadUrl;
	}
	public static boolean isOnline(String roomNum){
		String status = "";
		try{
			HttpGet get = new HttpGet("http://www.douyu.tv/"+roomNum);
			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
			    HttpEntity entity = response.getEntity(); 
			    String htmlEntity = EntityUtils.toString(entity);
			    status = ServerUtils.QueryRoomApi(htmlEntity).getData().getShow_status();
			}
			get.abort();
		}catch (Exception e) {
			return false;
		}
		return Boolean.parseBoolean(status);
	}
	public static String showAnnounce(){
		String announce = "";
		try{
			HttpGet get = new HttpGet("http://share.teemo.name/DouyuAssistant/Announce");
			HttpResponse response = ThreadHttpClient.getInstance().getHttpclient().execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
			    HttpEntity entity = response.getEntity(); 
			    String[] ans = EntityUtils.toString(entity).split("[|]");
			    announce = ans[(int)(Math.random()*ans.length)];
			}
			get.abort();
		}catch (Exception e) {
			announce = "公告获取失败";
		}
		return announce.trim();
	}
}
