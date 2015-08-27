package name.yumao.douyu.http;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

public class ThreadHttpClient {
//	private static Logger logger = Logger.getLogger(ThreadHttpClient.class);
	private static ThreadHttpClient threadhttpclient;
	private DefaultHttpClient httpclient;
	public static void load(){
		threadhttpclient = new ThreadHttpClient();
	}
	public static ThreadHttpClient getInstance() {
		if(threadhttpclient == null){
			load();
		}
		return threadhttpclient;
	}
	public void initHttp() {
//		System.out.println("初始化HttpClient多线程处�?);
		//设置�?��连接�?
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
//		int maxTotal = Integer.parseInt(Config.getProperty("THREADSAFEHTTP_MAXTOTAL"));
		int maxTotal = 100;
		cm.setMaxTotal(maxTotal);
		this.httpclient = new DefaultHttpClient(cm);
	}	
	public void shutHttp(){
		if(null != httpclient){
//			logger.info("关闭HttpClient多线程处�?);
			httpclient.getConnectionManager().shutdown(); 
			//设置读数据超时时�?
			//httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, "10000");
			//设置连接超时时间
			//httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, "12000");
		}
	}
	public DefaultHttpClient getHttpclient() {
		if(httpclient==null){
			initHttp();
		}
		return httpclient;
	}
	public void setHttpclient(DefaultHttpClient httpclient) {
		this.httpclient = httpclient;
	}
}