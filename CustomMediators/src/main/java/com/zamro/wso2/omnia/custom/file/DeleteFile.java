package com.zamro.wso2.omnia.custom.file;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.zamro.wso2.omnia.custom.file.utils.FileUtils;

public class DeleteFile extends AbstractMediator {

	public boolean mediate(MessageContext context) {

		String filePath = (String) context.getProperty(FileUtils.FILE_PATH);
		String fileName = (String) context.getProperty(FileUtils.FILE_NAME);
		
		FileUtils.delete(filePath,fileName);
		
		return true;
	}

}
