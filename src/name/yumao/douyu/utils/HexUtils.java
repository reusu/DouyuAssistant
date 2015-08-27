package name.yumao.douyu.utils;


import org.apache.mina.core.buffer.IoBuffer;

public class HexUtils {
//    private static byte[] decodeHex(String hex)
//    {
//        char[] chars = hex.toCharArray();
////        for (int i = 0; i < chars.length; i++)
////        {
////            Console.WriteLine(chars[i]);
////        }
////        Console.WriteLine();
//        byte[] bytes = new byte[chars.length / 2];
//        int byteCount = 0;
//        for (int i = 0; i < chars.length; i += 2)
//        {
//            byte newByte = 0x00;
//            newByte |= hexCharToByte(chars[i]);
//            newByte <<= 4;
//            newByte |= hexCharToByte(chars[i + 1]);
//            bytes[byteCount] = newByte;
//            byteCount++;
//        }
//        return bytes;
//    } 
//    private static byte hexCharToByte(char ch)
//    {
//        switch (ch)
//        {
//            case '0': return 0x00;
//            case '1': return 0x01;
//            case '2': return 0x02;
//            case '3': return 0x03;
//            case '4': return 0x04;
//            case '5': return 0x05;
//            case '6': return 0x06;
//            case '7': return 0x07;
//            case '8': return 0x08;
//            case '9': return 0x09;
//            case 'a': return 0x0A;
//            case 'b': return 0x0B;
//            case 'c': return 0x0C;
//            case 'd': return 0x0D;
//            case 'e': return 0x0E;
//            case 'f': return 0x0F;
//        }
//        return 0x00;
//    }
    
    public static IoBuffer hexString2IoBuffer(String hexString){
//    	System.out.println(hexString);
    	IoBuffer ioBuffer = IoBuffer.allocate(8);
    	ioBuffer.setAutoExpand(true);
    	ioBuffer.put(HexString2Bytes(hexString));
    	ioBuffer.flip(); 
    	return ioBuffer;
    }
    public static String ioBufferToString(Object message) throws Exception   
    {   
          if (!(message instanceof IoBuffer))   
          {   
            return "";   
          }   
          IoBuffer ioBuffer = (IoBuffer) message;   
          byte[] b = new byte [ioBuffer.limit()];   
          ioBuffer.get(b);   
          String bb = new String(b,"utf-8");
//          bb = bb.substring(bb.indexOf("type"),bb.indexOf("@Srg"));
//          StringBuffer stringBuffer = new StringBuffer();   
//          for (int i = 12; i < b.length; i++)   
//          {   
//           stringBuffer.append((char) b [i]);   
//          }   
           return bb;
//          return new String(stringBuffer.toString().getBytes());
    }  
    
    public static String ioBufferToHexString(Object message) throws Exception   
    {   
          if (!(message instanceof IoBuffer))   
          {   
            return "";   
          }   
          IoBuffer ioBuffer = (IoBuffer) message;   
          byte[] b = new byte [ioBuffer.limit()];   
          ioBuffer.get(b);   
          String bb = Bytes2HexString(b);
          return bb;
    }  
    
    private final static byte[] hex = "0123456789ABCDEF".getBytes();  
    private static int parse(char c) {  
        if (c >= 'a')  
            return (c - 'a' + 10) & 0x0f;  
        if (c >= 'A')  
            return (c - 'A' + 10) & 0x0f;  
        return (c - '0') & 0x0f;  
    }  
    // 从字节数组到十六进制字符串转换  
    public static String Bytes2HexString(byte[] b) {  
        byte[] buff = new byte[3 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[3 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[3 * i + 1] = hex[b[i] & 0x0f];  
            buff[3 * i + 2] = 45;  
        }  
        String re = new String(buff);  
        return re.replace("-", " ");  
    }  
    // 从字节数组到十六进制字符串转换  
    public static String Bytes2HexStringLower(byte[] b) {  
        byte[] buff = new byte[3 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[3 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[3 * i + 1] = hex[b[i] & 0x0f];  
            buff[3 * i + 2] = 45;  
        }  
        String re = new String(buff);  
        return re.replace("-", "").toLowerCase();  
    } 
    // 从十六进制字符串到字节数组转换  
    public static byte[] HexString2Bytes(String hexstr) {  
        hexstr = hexstr.replace(" ", "");  
        byte[] b = new byte[hexstr.length() / 2];  
        int j = 0;  
        for (int i = 0; i < b.length; i++) {  
            char c0 = hexstr.charAt(j++);  
            char c1 = hexstr.charAt(j++);  
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
        }  
        return b;  
    }  
    public static byte[] hexString2Bytes(String hexString) {
    	hexString = hexString.replace(" ", "");  
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    
    public static String setStringHeader(String hexStr){
    	String length = Integer.toHexString((hexStr.length()+8)/2)+"000000" ;
    	return length+length+hexStr;
    }
    public static int getHexStringLength(String hexStr){
    	hexStr = hexStr.replace(" ", "");
    	if(hexStr.length() < 8){
    		return hexStr.length() + 1;
    	}
    	String headerStr = hexStr.substring(0,8);
    	String hexLength = "";
    	for(int i=6;i>=0;i=i-2){
    		hexLength = hexLength + headerStr.substring(i,i+2);
    	}
    	return Integer.parseInt(hexLength,16) * 2 - 8;
    }
//    public static void main(String a[]){
//    	//String i = "BF 00 00 00 BF 00 00 00 B2 02 00 00 74 79 70 65 40 3D 6C 6F 67 69 6E 72 65 73 2F 75 73 65 72 69 64 40 3D 31 33 30 30 32 33 36 34 31 33 2F 72 6F 6F 6D 67 72 6F 75 70 40 3D 30 2F 70 67 40 3D 30 2F 73 65 73 73 69 6F 6E 69 64 40 3D 2D 31 33 34 31 32 34 30 31 38 34 2F 75 73 65 72 6E 61 6D 65 40 3D 76 69 73 69 74 6F 72 31 34 36 34 31 33 2F 6E 69 63 6B 6E 61 6D 65 40 3D 76 69 73 69 74 6F 72 31 34 36 34 31 33 2F 69 73 5F 73 69 67 6E 69 6E 65 64 40 3D 30 2F 73 69 67 6E 69 6E 5F 63 6F 75 6E 74 40 3D 30 2F 73 40 3D 31 34 30 38 30 37 31 36 37 34 2F 6C 69 76 65 5F 73 74 61 74 40 3D 30 2F 00".replace(" ", "");
//    	String i="32 30 2F 74";
//    	String i="0B 01 00 00 0B 01 00 00 B2 02 00 00 74 79 70 65 40 3D 64 6F 6E 61 74 65 72 65 73 2F 72 69 64 40 3D 35 36 30 34 30 2F 67 69 64 40 3D 30 2F 6D 67 40 3D 30 2F 6D 73 40 3D 31 30 30 2F 67 62 40 3D 30 2F 73 62 40 3D 31 30 30 2F 73 72 63 5F 73 74 72 65 6E 67 74 68 40 3D 33 35 30 30 2F 64 73 74 5F 77 65 69 67 68 74 40 3D 36 36 39 32 37 31 39 2F 72 40 3D 30 2F 73 75 69 40 3D 69 64 40 41 3D 31 32 36 34 35 33 38 40 53 6E 61 6D 65 40 41 3D 77 65 69 62 6F 5F 33 35 36 56 58 4E 78 6B 40 53 6E 69 63 6B 40 41 3D E4 B8 8D E4 BA 8C E5 BF 83 E7 9A 84 E5 8C 85 E5 AD 90 40 53 72 67 40 41 3D 31 40 53 70 67 40 41 3D 31 40 53 72 74 40 41 3D 31 34 30 34 38 39 38 30 39 38 40 53 62 67 40 41 3D 30 40 53 77 65 69 67 68 74 40 41 3D 30 40 53 73 74 72 65 6E 67 74 68 40 41 3D 33 34 30 30 40 53 63 70 73 5F 69 64 40 41 3D 30 40 53 2F 00".replace(" ", "");
//    	System.out.println(i.length());
//    	System.out.println(i.length()-16);
//    	System.out.println(getHexStringLength(i));
//    }
	public static int getHexStringLength(byte[] sizeBytes) {
		String hexStr = Bytes2HexString(sizeBytes);
    	hexStr = hexStr.replace(" ", "");
    	if(hexStr.length() < 8){
    		return hexStr.length() + 1;
    	}
    	String headerStr = hexStr.substring(0,8);
    	String hexLength = "";
    	for(int i=6;i>=0;i=i-2){
    		hexLength = hexLength + headerStr.substring(i,i+2);
    	}
    	return Integer.parseInt(hexLength,16);
	}
	public static String Bytes2HexStringWithOutSpace(byte[] b) {
        byte[] buff = new byte[3 * b.length];  
        for (int i = 0; i < b.length; i++) {  
            buff[3 * i] = hex[(b[i] >> 4) & 0x0f];  
            buff[3 * i + 1] = hex[b[i] & 0x0f];  
            buff[3 * i + 2] = 45;  
        }  
        String re = new String(buff);  
        return re.replace("-", "");  
	}
}
