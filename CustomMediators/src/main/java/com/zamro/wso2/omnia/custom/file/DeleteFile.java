package com.zamro.wso2.omnia.custom.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.zamro.wso2.omnia.custom.file.utils.FileUtils;

public class DeleteFile extends AbstractMediator {

	Log log = LogFactory.getLog(DeleteFile.class);

	public boolean mediate(MessageContext context) {
		
		String filePath = (String) context.getProperty(FileUtils.FILE_PATH);
		String fileName = (String) context.getProperty(FileUtils.FILE_NAME);

		log.info("Deleting file {"+fileName+"} from location: "+filePath);
		FileUtils.delete(filePath, fileName);
		log.info("File deleted");
		
		return true;
	}

}
