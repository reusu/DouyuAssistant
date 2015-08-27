package name.yumao.douyu.utils;

public class BigHexStringUtils {
	private String hexStr = "";
	public void clear(){
		hexStr = "";
	}
	public void addHexStr(String pStr){
		hexStr = hexStr + pStr;
	}
	public String getHexStr(){
		return hexStr;
	}
	public boolean isFullHexStr(){
		int msgLength = HexUtils.getHexStringLength(hexStr);
		if(msgLength + 16==hexStr.replace(" ", "").length()){
			return true;
		}
		return false;
	}
	public boolean isFullHexStr(String hexStr){
		int msgLength = HexUtils.getHexStringLength(hexStr);
		if(msgLength + 16==hexStr.replace(" ", "").length()){
			return true;
		}
		return false;
	}
	public boolean hasFullHexStr(){
		int msgLength = HexUtils.getHexStringLength(hexStr);
		if(msgLength + 16 <= hexStr.replace(" ", "").length()){
			return true;
		}
		return false;
	}
}
