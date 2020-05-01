package org.rito.filesystem.constant;

/**
 * @author xigua
 * @description
 * @date 2020/4/30
 **/

public final class Constant {
    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final int FTP_SCAN_INTERVAL = 30;
    public static final String ftp_Local_File_Dir = "ftpLocalFileDir";
    public static final String private_Key_File_Path = "privateKeyFile";
    /**
     * pgp的密码
     */
    public static final String passwd = "passwd";
    public static final String ftpScanInterval = "ftpScanInterval";
    public static final String userName = "userName";
    public static final String hostName = "hostName";
    public static final String port = "port";
    /**
     * sftp密码
     */
    public static final String password = "password";
    public static final String systemType = "systemType";
    /**
     * sftp配置文件所在位置
     */
    public static final String cfgPath = "src/main/resources/config/sftp.properties";
    /**
     * 解密后的文件后缀
     */
    public static final String suffix = ".bak";
    /**
     * 需要下载的文件在sftp上的路径
     */
    public static final String sftpPath = "/";
    public static final String txt = ".txt";
}
