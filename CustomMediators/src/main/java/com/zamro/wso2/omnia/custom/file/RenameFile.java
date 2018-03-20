package com.zamro.wso2.omnia.custom.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.zamro.wso2.omnia.custom.file.utils.FileUtils;

public class RenameFile extends AbstractMediator {

	Log log = LogFactory.getLog(RenameFile.class);
	
	public boolean mediate(MessageContext context) {
		
		String filePath = (String) context.getProperty(FileUtils.FILE_PATH);
		String fileName = (String) context.getProperty(FileUtils.FILE_NAME);
		String newFileName = (String) context.getProperty(FileUtils.NEW_FILE_NAME);
		
		log.info("Renaming file {"+fileName+"} from location: "+filePath+ " to: " + newFileName);
		FileUtils.renameFile(filePath, fileName, newFileName);
		log.info("File renamed");
		
		return true;
	}

}
