package org.rito.filesystem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于http发送数据
 * 
 * @author yangbo
 *
 */
public class HttpClientUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

	public String post(String url, String json, int timeout) {
		try {
			// 2. 创建HttpURLConnection对象
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			/* 3. 设置请求参数等 */
			// 请求方式
			connection.setRequestMethod("POST");
			// 超时时间
			connection.setConnectTimeout(timeout);
			// 设置是否输出
			connection.setDoOutput(true);
			// 设置是否读入
			connection.setDoInput(true);
			// 设置是否使用缓存
			connection.setUseCaches(false);
			// 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
			connection.setInstanceFollowRedirects(true);
			// 设置使用标准编码格式编码参数的名-值对
			connection.setRequestProperty("Content-Type", "application/json");
			connection.addRequestProperty("Charset", "UTF-8");

			// String auth = this.esUserName + ":" + this.esPasswd;
			// 对其进行加密
			// String authBase64 = new String(Base64.encodeBase64(auth.getBytes()));
			String authBase64 = "";
			// 设置认证属性
			connection.setRequestProperty("Authorization", "Basic " + authBase64);

			// 连接
			connection.connect();
			/* 4. 处理输入输出 */
			// 写入参数到请求中
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
			out.write(json);
			out.flush();
			out.close();
			// 从连接中读取响应信息
			String msg = "";
			int code = connection.getResponseCode();
			// log.debug("code值:" + code);
			if (code == 201 || code == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
				String line;

				while ((line = reader.readLine()) != null) {
					msg += line;
				}
				reader.close();
			}
			// 5. 断开连接
			connection.disconnect();
			return msg;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
}
