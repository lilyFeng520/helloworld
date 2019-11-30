package mvcproject.utils;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtils {
	
	private static final String KEY = ":cookie@ybzy.com123!";   //这是一个密钥
     
	/**
	   * 命令浏览器创建cookie文件用的方法
	 * @param username：放到cookie信息里的用户名
	 * @param req
	 * @param resp：调用addCookie方法的response对象
	 * @param sec：设置cookie失效的时间，单位是秒
	 * @throws UnsupportedEncodingException 
	 */
	public static  void createCookie(String username,HttpServletRequest req,HttpServletResponse resp,int sec) throws UnsupportedEncodingException  {
		Cookie userCookie = new Cookie("userKey", URLEncoder.encode(username,"UTF-8"));
		Cookie ssidCookie = new Cookie("ssid", URLEncoder.encode(md5Encrypt(username), "UTF-8"));  
        userCookie.setMaxAge(sec);
        ssidCookie.setMaxAge(sec);
        resp.addCookie(userCookie);	
        resp.addCookie(ssidCookie);
		
	}
    
	
	/**
	 * 这个方法的作用就是加密，把一个明文字符串加密成哪个都看不懂的密文，
	 * @param ss：等待被加密的明文
	 * @return
	 */
	public static String md5Encrypt(String ss) {
		ss = ss==null?"":ss+KEY;
		char[] md5Digist = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};  //加密用的字典
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");   //常用的加密算法：md5  sha1
			byte[] ssarr = ss.getBytes();
			md.update(ssarr);  //把明文放到加密类MessageDigest的对象实例里去，更新数据
			byte[] mssarr =  md.digest();   //这里就是真正加密了 : 0101 0111
			
			int len = mssarr.length;
			char[] str = new char[len*2];
			int k = 0; //当一个计数器
			for (int i = 0; i < len; i++) {
				byte b = mssarr[i];  //0101 0111   0000 1111
				str[k++] =  md5Digist[b >>> 4 & 0xf];  //>>>是无符号右移运算符，左边空出的位以0填充，4指右移四位，然后跟0xf(0xf也就是 0000 1111)做与运算获得高四位
				str[k++] =  md5Digist[b & 0xf];
			}
			return new String(str);			// 返回加密后的字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
