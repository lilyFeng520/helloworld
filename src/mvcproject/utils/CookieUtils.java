package mvcproject.utils;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtils {
	
	private static final String KEY = ":cookie@ybzy.com123!";   //����һ����Կ
     
	/**
	   * �������������cookie�ļ��õķ���
	 * @param username���ŵ�cookie��Ϣ����û���
	 * @param req
	 * @param resp������addCookie������response����
	 * @param sec������cookieʧЧ��ʱ�䣬��λ����
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
	 * ������������þ��Ǽ��ܣ���һ�������ַ������ܳ��ĸ��������������ģ�
	 * @param ss���ȴ������ܵ�����
	 * @return
	 */
	public static String md5Encrypt(String ss) {
		ss = ss==null?"":ss+KEY;
		char[] md5Digist = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};  //�����õ��ֵ�
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");   //���õļ����㷨��md5  sha1
			byte[] ssarr = ss.getBytes();
			md.update(ssarr);  //�����ķŵ�������MessageDigest�Ķ���ʵ����ȥ����������
			byte[] mssarr =  md.digest();   //����������������� : 0101 0111
			
			int len = mssarr.length;
			char[] str = new char[len*2];
			int k = 0; //��һ��������
			for (int i = 0; i < len; i++) {
				byte b = mssarr[i];  //0101 0111   0000 1111
				str[k++] =  md5Digist[b >>> 4 & 0xf];  //>>>���޷����������������߿ճ���λ��0��䣬4ָ������λ��Ȼ���0xf(0xfҲ���� 0000 1111)���������ø���λ
				str[k++] =  md5Digist[b & 0xf];
			}
			return new String(str);			// ���ؼ��ܺ���ַ���
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
