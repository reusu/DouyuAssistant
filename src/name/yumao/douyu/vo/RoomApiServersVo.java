package name.yumao.douyu.vo;

import java.io.Serializable;


public class RoomApiServersVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7815663065433622423L;
	private String ip;
	private String port;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
