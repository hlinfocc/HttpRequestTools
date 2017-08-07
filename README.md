# HttpRequestTools
HTTP请求工具类，包含http的get、post请求和https的get、post请求^_^

使用方法：


	HttpRequest http = new HttpRequest();
	//https get
	String rs1=http.httpsget("https://github.com/hlinfocc/HttpRequestTools/blob/master/README.md");
	System.out.println(rs1);
		
	//https post
	Map<String,String> m = new HashMap<String,String>();  
	m.put("userName","111111");  //参数
	m.put("pwd","123456");//参数
	String httpsUrl="";//https post Url
	String rs2=http.httpsPost(httpsUrl, m);
	System.out.println(rs2);
	     
	//http get 
	String rs3 = http.httpget("http://you Url");
	System.out.println(rs3);
	     
	//http post
	Map<String,String> httpmap = new HashMap<String,String>();  
	httpmap.put("userName","111111");  //参数
	httpmap.put("pwd","123456");//参数
	String httpUrl="";//http post Url
	String rs4 = http.httpPost(httpUrl, httpmap);
	System.out.println(rs4);
	
请将对应的Url替换成你的Url地址！