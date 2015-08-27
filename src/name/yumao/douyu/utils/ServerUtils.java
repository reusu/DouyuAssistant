package name.yumao.douyu.utils;

//import java.net.URLDecoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import name.teemo.api.vo.live.douyu.DouyuApiVo;
import name.yumao.douyu.vo.ContentServerVo;
//import name.yumao.douyu.vo.DownloadUrlVo;
//import name.yumao.douyu.vo.LoginServerVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ServerUtils {
	public static DouyuApiVo QueryRoomApi(String roomApiStr) throws Exception{
		byte[] obj = HexUtils.hexString2Bytes(roomApiStr);
		byte[] obj_2 = new byte[obj.length-"abcde".length()];
		System.arraycopy(obj, 0, obj_2, 0, obj_2.length);
		obj = JzlibUtils.unZlib(obj_2);
		DouyuApiVo roomApiVo = (DouyuApiVo)ObjectAndByte.toObject(obj);
		return roomApiVo;
	}
//	public static String QueryRoomId(String roomApiStr){
//	    @SuppressWarnings("rawtypes")
//		Map<String, Class> classMap = new HashMap<String, Class>();
//	    classMap.put("data", RoomApiDataVo.class);
//	    classMap.put("servers", RoomApiServersVo.class);
//		JSONObject jsonObject = JSONObject.fromObject(roomApiStr);    
//		RoomApiVo vo = (RoomApiVo) JSONObject.toBean(jsonObject,RoomApiVo.class,classMap);
//	    return vo.getData().getRoom_id();
//	} 
//	public static List<RoomApiServersVo> QueryLoginServerList(String roomApiStr){
//	    @SuppressWarnings("rawtypes")
//		Map<String, Class> classMap = new HashMap<String, Class>();
//	    classMap.put("data", RoomApiDataVo.class);
//	    classMap.put("servers", RoomApiServersVo.class);
//		JSONObject jsonObject = JSONObject.fromObject(roomApiStr);    
//		RoomApiVo vo = (RoomApiVo) JSONObject.toBean(jsonObject,RoomApiVo.class,classMap);
//	    return vo.getData().getServers();
//	} 
	public static List<ContentServerVo> QueryContentServerList(String serverList){
		String serverListJson = "[{\"" + serverList.replaceAll("@S/", "\"},{\"").replaceAll("@A=", "\":\"").replaceAll("@S", "\",\"") + "\"}]";
		serverListJson = serverListJson.substring(0,serverListJson.indexOf(",{\"\"}")) + "]";
		List<ContentServerVo> contentServerList = new ArrayList<ContentServerVo>();
	    JSONArray jsonArray = JSONArray.fromObject(serverListJson);
	    for ( int i = 0 ; i<jsonArray.size(); i++){          
	    	JSONObject jsonObject = jsonArray.getJSONObject(i);
	    	ContentServerVo vo = (ContentServerVo) JSONObject.toBean(jsonObject,ContentServerVo.class);
	    	contentServerList.add(vo);	            
	    }	
		return contentServerList;
	}
//	public static String QueryDouyuDownloadUrl(String rtmp_addr) throws Exception {
//		JSONObject jsonObject = JSONObject.fromObject(rtmp_addr);    
//		DownloadUrlVo vo = (DownloadUrlVo) JSONObject.toBean(jsonObject,DownloadUrlVo.class);
//		return vo.getRtmp_url().replaceAll("\\/", "/") + "/" +  URLDecoder.decode(vo.getRtmp_live(),"UTF-8");
//	} 
	public static String getCpuIdMd5() {  
	    String serial = ""; 
	    try {  
	        Process process = Runtime.getRuntime().exec(  
	        new String[] { "wmic", "cpu", "get", "ProcessorId" });  
	        process.getOutputStream().close();  
	        Scanner sc = new Scanner(process.getInputStream());  
	        sc.next();  
	        serial = sc.next();  
	    } catch (IOException e) {  
	    }  
	    return MD5Util.MD5(serial).toUpperCase();
	}  
}
