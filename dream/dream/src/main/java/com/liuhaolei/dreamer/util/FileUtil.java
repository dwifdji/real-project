package com.liuhaolei.dreamer.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	
	public static String upFileToFtp(String host, Integer port, String username, String password, String basePath,
            String filePath, String filename, InputStream input) {
		int reply = 0;
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);				//ftp服务器连接
			ftpClient.login(username, password);		//ftp登陆
			
			reply = ftpClient.getReplyCode();			//判断是否在标准时间内否建立连接
			
			if (!FTPReply.isPositiveCompletion(reply)) { 
				ftpClient.disconnect();
				return null;
	        }
			
            if (!ftpClient.changeWorkingDirectory(basePath+filePath)) {
            	//如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        if (!ftpClient.makeDirectory(tempPath)) {
                            return null;
                        } else {
                        	ftpClient.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
			 
            //设置上传文件的类型为二进制类型
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftpClient.storeFile(filename, input)) {
                return null;
            }
            input.close();
            ftpClient.logout();
            return filePath + "/" + filename;
            
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (ftpClient.isConnected()) {
                try {
                   ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
		}
		return filePath + "/" + filename;
	}
}
