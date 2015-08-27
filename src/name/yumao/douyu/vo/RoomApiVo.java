package name.yumao.douyu.vo;

import java.io.Serializable;


public class RoomApiVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12072122814258076L;
	private String error;
	private RoomApiDataVo data;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public RoomApiDataVo getData() {
		return data;
	}
	public void setData(RoomApiDataVo data) {
		this.data = data;
	}
	
}
