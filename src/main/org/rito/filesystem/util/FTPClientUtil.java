package org.rito.filesystem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FTPClientUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FTPClientUtil.class);

	FtpClient ftpClient;

	/**
	 * 连接FTP服务
	 *
	 * @param ip       FTP服务IP地址
	 * @param port     FTP服务端口
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @throws IOException
	 */
	public static FtpClient connectFTP(String ip, int port, String userName, String password) throws IOException {
		FtpClient ftp = null;
		try {
			SocketAddress addr = new InetSocketAddress(ip, port);
			ftp = FtpClient.create();
			ftp.connect(addr);
			// 登陆
			ftp.login(userName, password.toCharArray());
			ftp.setBinaryType();

		} catch (FtpProtocolException | IOException e) {
			LOGGER.info("连接FTP异常", e);
		}
		return ftp;
	}

	/**
	 * 获取FTP上指定文件的文件内容
	 *
	 * @param ftpFile 文件路径
	 * @param ftp
	 * @return
	 * @throws IOException
	 */
	public static List<String> download(String ftpFile, FtpClient ftp) throws IOException {
		List<String> list = new ArrayList<>();
		String str ;
		InputStream is = null;
		BufferedReader br = null;
		try {
			// 获取ftp上的文件
			is = ftp.getFileStream(ftpFile);
			// 转为字节流
			br = new BufferedReader(new InputStreamReader(is));
			while ((str = br.readLine()) != null) {
				list.add(str);
			}

			br.close();
			is.close();
		} catch (Exception e) {
			LOGGER.debug("获取FTP上指定文件的文件内容失败", e);
		} finally {
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return list;
	}
}
