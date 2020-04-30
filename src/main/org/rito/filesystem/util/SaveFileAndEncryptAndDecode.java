package org.rito.filesystem.util;

import org.rito.filesystem.JobService;
import org.rito.filesystem.constant.Constant;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author xigua
 * @description 保存日志、加密与解码
 * @date 2020/4/30
 **/
public class SaveFileAndEncryptAndDecode {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SaveFileAndEncryptAndDecode.class);

    /**
     * 保存下载后密文
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    public static String saveEncryptFile(InputStream inputStream, String fileName) {
        //指定一次读多少字节
        byte[] bytes = new byte[1024];

        //获得本地路径
        String filePath = JobService.getHashMap().get(Constant.ftp_Local_File_Dir) + fileName;

        OutputStream outputStream = null;
        try {
            //指定一次读多少字节
            if (inputStream == null) {
                LOGGER.error("下载失败");
                return null;
            } else {
                outputStream = new FileOutputStream(new File(filePath));
                while (inputStream.read(bytes) != -1) {
                    //以字节的形式保存在bytes里。
                    outputStream.write(bytes);
                }
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return filePath;
    }

    /**
     * 对文件进行解码
     *
     * @param filepath
     */
    public static void decode(String filepath) {
        //获得私钥路径
        String strPrivateKeyFile = JobService.getHashMap().get(Constant.private_Key_File_Path);


        String passwd = JobService.getHashMap().get(Constant.passwd);


        FileInputStream cipheredFileIs = null;
        FileInputStream privKeyIn = null;
        FileOutputStream plainTextFileIs = null;

        try {
            cipheredFileIs = new FileInputStream(new File(filepath));
            privKeyIn = new FileInputStream(new File(strPrivateKeyFile));
            //解密后的文件名
            plainTextFileIs = new FileOutputStream(new File(filepath + ".bak"));
            PgpUtils.getInstance().decryptFile(cipheredFileIs, plainTextFileIs, privKeyIn, passwd.toCharArray());
            cipheredFileIs.close();
            plainTextFileIs.close();
            privKeyIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cipheredFileIs != null) {
                    cipheredFileIs.close();
                }
                if (privKeyIn != null) {
                    privKeyIn.close();
                }
                if (plainTextFileIs != null) {
                    plainTextFileIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
