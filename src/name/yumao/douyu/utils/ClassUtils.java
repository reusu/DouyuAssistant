package name.yumao.douyu.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import name.yumao.douyu.swing.ToolTipInterface;

public class ClassUtils {
	private static ClassUtils classUtils;
	public static ClassUtils getInstance() {
		if(classUtils == null)
			classUtils = new ClassUtils();
		return classUtils;
	}
	private static ToolTipInterface tti = null;
    public void javac(String writerPath){  
        //java编译器  
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();  
        //文件管理器，参数1：diagnosticListener  监听器,监听编译过程中出现的错误  
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);  
        //java文件转换到java对象，可以是多个文件  
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(writerPath);  
        //编译任务,可以编译多个文件  
        CompilationTask t = compiler.getTask(null, manager, null, null, null, it);  
        //执行任务  
        t.call();  
        try {  
            manager.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
    @SuppressWarnings({ "rawtypes", "deprecation" })
	public ToolTipInterface getExToolTip(String folder,String packPath){  
        URL[] urls = null;  
        Class clazz = null;  
        try {  

        	File file = new File(folder); 
        	URL url = file.toURL(); 
            urls = new URL[] {url};  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        }  
        //类加载器  
        URLClassLoader url = new URLClassLoader(urls);  
        try {  
            //加载到内存  
            clazz = url.loadClass(packPath);  
            //实例化对象  
            ToolTipInterface tti = (ToolTipInterface)clazz.newInstance();  
            return tti;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
    }  
    
    public ToolTipInterface getTTI(){
    	if(tti!=null){
    		return tti;
    	}else{
	    	try{
		    	ClassUtils cu = new ClassUtils();
		    	cu.javac("exTip"+File.separator+"ToolTip.java");
		    	tti = cu.getExToolTip("exTip","ToolTip");
		    	return tti;
	    	}catch (Exception e) {
	    		ClassUtils cu = new ClassUtils();
	    		tti = cu.getExToolTip("exTip","name.yumao.douyu.swing.ToolTip");
	    		return tti;
			}
    	}
    }
}
