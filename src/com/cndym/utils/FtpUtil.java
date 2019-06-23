/**
 * 
 */
package com.cndym.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author 朱林虎
 *
 */
public class FtpUtil extends FtpUtils {
	
	/**
	 * 主动模式下载
	 * @param url
	 * @param port
	 * @param userName
	 * @param password
	 * @param remotePath
	 * @param fileName
	 * @param localPath
	 * @return
	 */
	public static boolean downFileByActive(String url, int port, String userName, String password, String remotePath, String fileName, String localPath)
	  {
	    boolean success = false;
	    FTPClient ftp = new FTPClient();
	    try
	    {
	      ftp.connect(url, port);
	      ftp.login(userName, password);
	      int reply = ftp.getReplyCode();
	      if (!FTPReply.isPositiveCompletion(reply)) {
	        ftp.disconnect();
	        boolean bool1 = success; return bool1;
	      }
	      //设置为主动模式
	      ftp.enterLocalActiveMode();
	      ftp.setFileType(2);
	      ftp.changeWorkingDirectory(remotePath);
	      FTPFile[] fs = ftp.listFiles();
	      File file = new File(localPath);
	      if (!file.exists()) {
	        file.mkdirs();
	      }
	      File localFile = new File(localPath + fileName);
	      localFile.exists();

	      for (FTPFile ff : fs) {
	        if (ff.getName().equals(fileName)) {
	          OutputStream is = new FileOutputStream(localFile);
	          ftp.retrieveFile(ff.getName(), is);
	          is.close();
	        }
	      }
	      ftp.logout();
	      success = true;
	    } catch (IOException e) {
	      e.printStackTrace();

	      if (ftp.isConnected())
	        try {
	          ftp.disconnect();
	        }
	        catch (IOException localIOException2)
	        {
	        }
	    }
	    finally
	    {
	      if (ftp.isConnected())
	        try {
	          ftp.disconnect();
	        }
	        catch (IOException localIOException3) {
	        }
	    }
	    return success;
	  }
	
	/**
	 * 被动模式下载
	 * @param url
	 * @param port
	 * @param userName
	 * @param password
	 * @param remotePath
	 * @param fileName
	 * @param localPath
	 * @return
	 */
	public static boolean downFileByPassive(String url, int port, String userName, String password, String remotePath, String fileName, String localPath)
	  {
	    boolean success = false;
	    FTPClient ftp = new FTPClient();
	    try
	    {
	      ftp.connect(url, port);
	      ftp.login(userName, password);
	      int reply = ftp.getReplyCode();
	      if (!FTPReply.isPositiveCompletion(reply)) {
	        ftp.disconnect();
	        boolean bool1 = success; return bool1;
	      }
	      //设置为被动模式
	      ftp.enterLocalPassiveMode();  
	      ftp.setFileType(2);
	      ftp.changeWorkingDirectory(remotePath);
	      FTPFile[] fs = ftp.listFiles();
	      File file = new File(localPath);
	      if (!file.exists()) {
	        file.mkdirs();
	      }
	      File localFile = new File(localPath + fileName);
	      localFile.exists();

	      for (FTPFile ff : fs) {
	        if (ff.getName().equals(fileName)) {
	          OutputStream is = new FileOutputStream(localFile);
	          ftp.retrieveFile(ff.getName(), is);
	          is.close();
	          success = true;
	        }
	      }
	      ftp.logout();
	    } catch (IOException e) {
	      e.printStackTrace();

	      if (ftp.isConnected())
	        try {
	          ftp.disconnect();
	        }
	        catch (IOException localIOException2)
	        {
	        }
	    }
	    finally
	    {
	      if (ftp.isConnected())
	        try {
	          ftp.disconnect();
	        }
	        catch (IOException localIOException3) {
	        }
	    }
	    return success;
	  }
}
