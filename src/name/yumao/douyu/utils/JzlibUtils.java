package name.yumao.douyu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

public class JzlibUtils {
	  public static byte[] unZlib(byte[] bytes) throws IOException {
	      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
	      ZInputStream zis = new ZInputStream(bais);
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      byte[] tmp = new byte[1024];
	      int len = -1;
	      while ((len = zis.read(tmp, 0, 1024)) != -1) {
	          baos.write(tmp, 0, len);
	      }
	      tmp = baos.toByteArray();
	      baos.close();
	      bais.close();
	      return tmp;
	  }
	  public static byte[] enZlib(byte[] bytes) throws IOException{  
	        byte[] data = null;  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            ZOutputStream zOut = new ZOutputStream(out,JZlib.Z_BEST_COMPRESSION);
            DataOutputStream objOut = new DataOutputStream(zOut);  
            objOut.write(bytes);  
            objOut.flush();  
            zOut.close();  
            data = out.toByteArray();  
            out.close();  
	        return data;  
	  }
}
