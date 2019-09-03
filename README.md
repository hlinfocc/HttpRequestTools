# HttpRequestTools
HTTP请求工具类，包含http的get、post请求和https的get、post请求^_^

使用方法：

	package net.hlinfo.test;
	import java.io.UnsupportedEncodingException;
	import java.util.HashMap;
	import java.util.Map;
	import net.hlinfo.opt.HttpRequest;
	public class Test {
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			HttpRequest http = new HttpRequest();
			//https get
			String rs1=http.httpsget("https://api.github.com/repos/hlinfocc/HttpRequestTools");
			System.out.println(rs1);
			
			//https post
			 Map<String,String> m = new HashMap<String,String>();  
		        m.put("login","111111");  //参数
		        m.put("password","123456");//参数
		     String httpsUrl="https://github.com/session";//https post Url
		     String rs2=http.httpsPost(httpsUrl, m);
		     System.out.println(rs2);
		     
		     
		    //http get 
		     String rs3 = http.httpget("https://account.chsi.com.cn/passport/login");
		     System.out.println(rs3);
		     
		    //http post
		     Map<String,String> httpmap = new HashMap<String,String>();  
		     httpmap.put("userName","111111");  //参数
		     httpmap.put("pwd","123456");//参数
		     String httpUrl="https://account.chsi.com.cn/passport/login";//http post Url
		     String rs4 = http.httpPost(httpUrl, httpmap);
		     System.out.println(rs4);
		}
	}

	
