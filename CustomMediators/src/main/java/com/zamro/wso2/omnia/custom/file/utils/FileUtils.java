package com.zamro.wso2.omnia.custom.file.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FileUtils {

	public static final String FILE_PATH = "FILE_PATH";
	public static final String FILE_NAME = "FILE_NAME";
	public static final String NEW_FILE_NAME = "NEW_FILE_NAME";

	public static final String SFTP_FILE_PATH = "SFTP_FILE_PATH";
	public static final String SFTP_FILE_NAME = "SFTP_FILE_NAME";
	public static final String SFTP_HOST = "SFTP_HOST";
	public static final String SFTP_USER = "SFTP_USER";
	public static final String SFTP_PASSWORD = "SFTP_PASSWORD";
	public static final String SFTP_PORT = "SFTP_PORT";

	public static File getFile(String filePath) {

		File file = new File(filePath);
		File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		return file;

	}

	public static void renameFile(String filePath, String fileName,
			String newFileName) {

		File file = getFile(filePath + File.separator + fileName);
		file.renameTo(new File(filePath + File.separator + newFileName));

	}

	public static void uploadFileToSFTP(String localFilePath,
			String localFileName, String sftpServer, String sftpUser,
			String sftpPassword, int sftpPort, String sftpDirectory,
			String sftpFileName) throws SocketException, IOException,
			SftpException, JSchException {

		File localFile = getFile(localFilePath + File.separator + localFileName);

		InputStream inputStream = new FileInputStream(localFile);

		JSch jsch = new JSch();
		Session session = jsch.getSession(sftpUser, sftpServer, sftpPort);
		session.setPassword(sftpPassword);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		Channel channel = session.openChannel("sftp");
		ChannelSftp sftp = (ChannelSftp) channel;
		sftp.connect();
		sftp.cd(sftpDirectory);
		sftp.put(inputStream, sftpFileName);
		channel.disconnect();
		inputStream.close();

		return;

	}

	public static void delete(String filePath, String fileName) {

		File localFile = getFile(filePath + File.separator + fileName);
		localFile.delete();
	}

}
