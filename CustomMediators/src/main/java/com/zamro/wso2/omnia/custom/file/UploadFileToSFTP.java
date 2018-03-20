package com.zamro.wso2.omnia.custom.file;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.zamro.wso2.omnia.custom.file.utils.FileUtils;

public class UploadFileToSFTP extends AbstractMediator {

	Log log = LogFactory.getLog(UploadFileToSFTP.class);
	
	public boolean mediate(MessageContext context) {
		
		String localFilePath = (String) context.getProperty(FileUtils.FILE_PATH);
		String localFileName = (String) context.getProperty(FileUtils.FILE_NAME);
		
		String sftpHost = (String) context.getProperty(FileUtils.SFTP_HOST);
		String sftpUser = (String) context.getProperty(FileUtils.SFTP_USER);
		String sftpPassword = (String) context.getProperty(FileUtils.SFTP_PASSWORD);
		String sftpPort = (String) context.getProperty(FileUtils.SFTP_PORT);
		
		String sftpDirectory = (String) context.getProperty(FileUtils.SFTP_FILE_PATH);
		String sftpFileName = (String) context.getProperty(FileUtils.SFTP_FILE_NAME);
		
		log.info("Uploading files to FTP");
		log.info("Host: "+sftpHost);
		log.info("User: "+sftpUser);
		log.info("Password: xxxxxxxx");
		log.info("Port: "+sftpPort);
		log.info("Directory: "+sftpDirectory);
		log.info("File name: "+sftpFileName);
		
		try {
			FileUtils.uploadFileToSFTP(localFilePath, localFileName, sftpHost,
					sftpUser, sftpPassword, Integer.parseInt(sftpPort), sftpDirectory,
					sftpFileName);
		} catch (NumberFormatException | IOException | SftpException | JSchException e) {
			log.error("Error in uploading files to FTP", e);
		}
		return true;

	}

}
