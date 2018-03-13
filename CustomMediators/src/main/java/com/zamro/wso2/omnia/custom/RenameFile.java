package com.zamro.wso2.omnia.custom;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.zamro.wso2.omnia.custom.utils.FileUtils;

public class RenameFile extends AbstractMediator {

	
	@Override
	public boolean mediate(MessageContext context) {
		
		String filePath = (String) context.getProperty(FileUtils.FILE_PATH);
		String fileName = (String) context.getProperty(FileUtils.FILE_NAME);
		String newFileName = (String) context.getProperty(FileUtils.NEW_FILE_NAME);
		
		
		FileUtils.renameFile(filePath, fileName, newFileName);
		
		return true;
	}

}
