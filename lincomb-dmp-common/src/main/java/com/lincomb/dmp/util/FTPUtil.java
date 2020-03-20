package com.lincomb.dmp.util;

import org.apache.commons.net.ftp.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * FTP操作的工具类，通过此类可以完成文件到指定FTP服务器的上传和下载。
 */
public class FTPUtil {

	/**
	 * 全局配置项: ftp传输模式: passive/active
	 */
	// private static String ConfigFtpMode = null;

	/**
	 * 全局配置项: applet ftp传输模式: passive/active
	 */
	// private static String ConfigClientFtpMode = null;

	/**
	 * ftp服务器的ip地址
	 */
	private String _ftpServer;

	/**
	 * ftp端口
	 */
	private int _ftpPort;

	/**
	 * ftp服务器的有效登陆用户名
	 */
	private String _userName;

	/**
	 * ftp服务器的有效登陆用户密码
	 */
	private String _password;

	/**
	 * ftp客户端的对象实例
	 */
	private FTPClient _ftp;


	/**
	 * 构造器
	 * 
	 * @param ftpServer
	 *            FTP服务器 String
	 * @param userName
	 *            用户名 String
	 * @param password
	 *            密码 String
	 * @throws Exception
	 *             异常
	 */
	public FTPUtil(String ftpServer, int port, String userName, String password) throws Exception {
		try {
			setFTPServer(ftpServer);
			setUserName(userName);
			setPassword(password);
			this._ftpPort = port;

			// 初始化ftp客户端对象实例
			_ftp = new FTPClient();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 获取ftp服务器的ip地址
	 * 
	 * @return ftpServerIP String
	 */
	public String getFTPServer() {
		return _ftpServer;
	}

	
	public FTPClient getFTPClient() {
		return _ftp;
	}

	/**
	 * 设置ftp服务器的ip地址
	 * 
	 * @param ftpServerIP
	 *            String
	 * @throws Exception
	 */
	public void setFTPServer(String ftpServer) throws Exception {
		// 检查输入的ftp服务器地址，不允许为空。
		if (ftpServer == null || ftpServer.length() == 0) {
			throw new Exception("ftp服务器的IP地址不能够为空。");
		}

		_ftpServer = ftpServer;
	}

	/**
	 * 获取ftp服务器的用户名
	 * 
	 * @return userName String
	 */
	public String getUserName() {
		return _userName;
	}

	/**
	 * 设置ftp服务器的用户名
	 * 
	 * @param userName
	 *            String
	 * @throws Exception
	 */
	public void setUserName(String userName) throws Exception {
		// 检查输入的ftp服务器名称，不允许为空。
		if (userName == null || userName.length() == 0) {
			throw new Exception("ftp服务器的登陆用户名不能够为空。");
		}

		_userName = userName;
	}

	/**
	 * 获取ftp服务器的用户登陆密码
	 * 
	 * @return password String
	 */
	public String getPassword() {
		return _password;
	}

	/**
	 * 设置ftp服务器的用户密码
	 * 
	 * @param password
	 *            String
	 * @throws Exception
	 */
	public void setPassword(String password) throws Exception {
		// 检查输入的ftp服务器用户登陆密码，不允许为空。
		if (password == null || password.length() == 0) {
			throw new Exception("ftp服务器的登陆用户密码不能够为空。");
		}

		_password = password;
	}

	/**
	 * 上传文件到ftp服务器的指定路径
	 * 
	 * @param ftpPath
	 *            文件上传到ftp的路径
	 * @param inputStream
	 *            待上传的文件流
	 * @return isUploadSuccessed ture表示上传成功，发了社表明上传失败。
	 * @throws Exception
	 */
	public boolean UploadFile(String ftpPath, InputStream inputSteam)
			throws Exception {
		boolean isSuccessed = false;

		// 通过指定的用户名和密码登陆ftp服务器
		try {
			if (Login(_userName, _password)) {
				// 开始上传文件
				_ftp.setFileType(FTP.BINARY_FILE_TYPE);

				if (_ftp.storeFile(ftpPath, inputSteam)) {
					isSuccessed = true;
				} else {
					throw new Exception("文件上传失败！");
				}
			} else {
				// 登陆失败
				isSuccessed = false;
				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password
						+ "失败。");
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}

		return isSuccessed;
	}

	/**
	 * 在ftp服务器上面创建一个目录
	 * 
	 * @param folderName
	 *            目录名称
	 * @return boolean 创建结果true表示成功，false表示失败
	 * @throws Exception
	 */
	public boolean CreateFolder(String folderName) throws Exception {
		boolean isSuccessed = false;

		// 通过指定的用户名和密码登陆ftp服务器
		try {
			if (Login(_userName, _password)) {
				// 开始创建指定的目录到ftp服务器上
				if (_ftp.makeDirectory(folderName)) {
					isSuccessed = true;
				} else {
					throw new Exception("ftp文件夹创建失败！");
				}
			} else {
				// 登陆ftp失败
				isSuccessed = false;
				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password
						+ "失败。");
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}

		return isSuccessed;
	}

	/**
	 * 根据指定的用户名和密码登陆ftp服务器
	 * 
	 * @param username
	 *            登陆用户名
	 * @param password
	 *            登陆用户密码
	 * @return isUploadSuccessed true表示登陆成功，false表示登陆失败
	 * @throws Exception
	 */
	public boolean Login(String username, String password) throws Exception {
		boolean isSuccessed = false;

		try {
			int reply = 0;

			// 连接到指定的ftp服务器
			_ftp.connect(_ftpServer, this._ftpPort);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			_ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");

			// 检查ftp连接请求的响应码，确保本次连接请求是成功还是失败。
			reply = _ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				_ftp.disconnect();
				throw new Exception("FTP服务器拒绝了连接.");
			}

			// 根据指定的用户名和密码登陆ftp服务器
			if (!_ftp.login(_userName, _password)) {
				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password
						+ "失败。");
			}

			// set ftp mode
			_ftp.enterLocalPassiveMode();
			isSuccessed = true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return isSuccessed;
	}


	/**
	 * 释放ftp客户端
	 */
	public void ftpDispose() {
		try {
			// 注销FTP客户端
			_ftp.logout();
		} catch (Exception e) {
			// 如果FTP注销失败什么也不做，忽略掉异常。
		}

		// 如果FTP客户端的连接还存在则断开连接。
		if (_ftp.isConnected()) {
			try {
				_ftp.disconnect();
			} catch (Exception ex) {
				// 如果断开FTP连接失败什么也不做，忽略掉异常。
			}
		}
	}

	/**
	 * 从ftp下载文件
	 * 
	 * @param FtpFileName
	 *            来源文件名称
	 * @param LocaFileName
	 *            目的文件名称
	 * @return boolean true表示下载文件成功，false表示下载文件失败
	 * @throws Exception
	 */
	public boolean DownFile(String FtpFileName, String LocaFileName)
			throws Exception {
		boolean isSuccessed = false;

		try {
			if (Login(_userName, _password)) {
				File file = new File(LocaFileName);
				FileOutputStream fos = new FileOutputStream(file);

				// + by jiangyl @2008.7.6
				_ftp.setFileType(FTP.BINARY_FILE_TYPE);

				isSuccessed = _ftp.retrieveFile(FtpFileName, fos);
				fos.close();
				file = null;

				if (!isSuccessed) {
					throw new Exception("文件" + FtpFileName + "下载失败.");
				}
			} else {
				isSuccessed = false;
				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password + "失败。");
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}

		return isSuccessed;
	}
	
	/**
	 * 从服务器上读取指定的文件
	 * @param path
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public InputStream readFile(String path, String filename) throws Exception {
		InputStream ins = null;
		try {
			if (Login(_userName, _password)) {
				_ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录
				// 从服务器上读取指定的文件
				ins = _ftp.retrieveFileStream(filename);
				// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
				_ftp.getReply();
				return ins;
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}
		return ins;
	}


	/**
	 * 根据web.xml中配置，
	 * 对FTP服务器上的过期数据文件进行清理，清理的目录有：playbill、terParam、theme、ThemePolicy
	 * 
	 * @return int 表示成功删除文件的个数
	 * @throws Exception
	 */
	public int ClearExpiredFiles() throws Exception {

		int deleteNum = 0;

		try {
			if (Login(_userName, _password)) {
				deleteNum += DeleteExpiredFiles("playbill");
				deleteNum += DeleteExpiredFiles("terParam");
				deleteNum += DeleteExpiredFiles("theme");
				deleteNum += DeleteExpiredFiles("ThemePolicy");
			} else {

				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password
						+ "失败。");
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}

		return deleteNum;
	}

	/**
	 * 根据web.xml中配置， 对FTP服务器上的过期数据文件进行清理
	 * 
	 * @param FtpPathName
	 *            待清理的目录
	 * @return int 表示成功删除文件的个数
	 * @throws Exception
	 */
	private int DeleteExpiredFiles(String FtpPathName) throws Exception {
		boolean isSuccessed = false;
		int deleteNum = 0;
		Calendar dtNow = Calendar.getInstance();
		Calendar dt = null;
		FTPFile[] files = null;
		long t1 = System.currentTimeMillis();
		long t2 = -1;

		try {
			// _ftp.changeWorkingDirectory("ThemePolicy");
			// String[] names = _ftp.listNames();

			// Paged access, using a parser accessible by auto-detect:
			FTPListParseEngine engine = _ftp.initiateListParsing(FtpPathName);

			while (engine.hasNext()) {
				files = engine.getNext(25); // "page size" you want

				for (int i = 0; i < files.length; i++) {
					// System.out.println(files[i].getName()+" -- "+FastDateFormat.getInstance("yyyy-MM-dd
					// HH:mm:ss").format(files[i].getTimestamp()));
					dt = files[i].getTimestamp();
					// FTP服务器中保留最近多少个月的数据，默认为1个月
					if (dt.compareTo(dtNow) <= 0) {
						isSuccessed = _ftp.deleteFile(FtpPathName + "/"
								+ files[i].getName());

						if (isSuccessed) {
							deleteNum++;
							// System.out.println("deleteFile OK! -- " +
							// files[i].getName() + " -- " +
							// FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(dt));
						}

					}
				}// for
			}

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}

		t2 = System.currentTimeMillis();
		System.out.println("FTP服务器过期数据文件清理 -- " + FtpPathName + " 目录，"
				+ deleteNum + " 个文件已删除，用时 " + (t2 - t1) + " ms！");

		return deleteNum;
	}

	/***
	 * 字符串to输入流
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static InputStream String2inputStream2(String str)
			throws IOException {
		InputStream in_nocode = new ByteArrayInputStream(str.getBytes());
		return in_nocode;
	}
	
	/***
	 * 输入流to字符串
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param picPath
	 * @return
	 */
	public FTPFile[] getFileFromFTP(String picPath) {
		FTPFile[] list = null;
		try {
			if (Login(_userName, _password)) {
				return _ftp.listFiles(picPath);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 下载ftp目录下所有的文件
	 */
	public boolean downFile(String remotePath,String localPath) throws Exception {
		boolean success = false;
		try {
			if (Login(_userName, _password)) {
				FTPFile[] list = _ftp.listFiles(remotePath);
				for (FTPFile ff : list) {
					File localFile = new File(localPath+"/"+ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					_ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			} else {
				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password
						+ "失败。");
			}
			success = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}
		return success;
	}
	
	/**
	 * 下载ftp目录下所有的文件
	 */
	public List<String> downFileName(String remotePath,String localPath) throws Exception {
		List<String> fileNameList = new ArrayList<String>();
		try {
			if (Login(_userName, _password)) {
				FTPFile[] list = _ftp.listFiles(remotePath);
				for (FTPFile ff : list) {
					fileNameList.add(ff.getName());
					File localFile = new File(localPath + "/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					_ftp.setFileType(FTP.BINARY_FILE_TYPE);
					_ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
					_ftp.retrieveFile(remotePath + "/" + ff.getName(), is);
					is.close();
				}
			} else {
				throw new Exception("用户" + _userName + "登陆ftp服务器" + _password + "失败。");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
			ftpDispose();
		}
		
		return fileNameList;
	}
	
	/**
	 * 递归下载文件
	 * 
	 * @param ftp
	 * @param remote
	 * @param local
	 * @param validate
	 */
	public void downLoadFile(String remote, String local) {
		try {
			// 转到指定下载目录
			if (Login(_userName, _password)) {
				loadFile(remote, local);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	public void loadFile(String remote, String local) {
		try {
			_ftp.changeWorkingDirectory(remote);
			FTPFile[] files = _ftp.listFiles();
			for (FTPFile file : files) {
				if (file.isDirectory()) {
					loadFile(remote + file.getName() + "/", local + "/" + file.getName() + "/");
				} else {
					File localFile = new File(local + "/" + file.getName());
					if (!localFile.getParentFile().exists()) {
						localFile.getParentFile().mkdirs();
					}
	
					// 输出
					OutputStream is = new FileOutputStream(localFile);
					// 下载文件
					_ftp.retrieveFile(remote + file.getName(), is);
					is.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			FTPUtil ftp = new FTPUtil("222.73.156.45", 12221, "ftpadmin","lbjr123.com");
			//System.out.println(ftp.downFileName("/data/ftp/import/3/1/", "E:\\down\\"));
			
			
			ftp.downLoadFile("/data/ftp/import/", "E:\\down\\");
//			ftp.Login(ftp._userName, ftp._password);
//			
//			String imgSourcePath = "import";
//			String picPath = "/data/ftp/" + imgSourcePath + "/2/" + "1";
//			
//			System.out.println(picPath);
//			FTPFile[] list = ftp._ftp.listFiles(picPath);
//			for (FTPFile ff : list) {
//				System.out.println(ff.getName());
//			}
//			
//			System.out.println("文件或文件夹数量：" + list.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}