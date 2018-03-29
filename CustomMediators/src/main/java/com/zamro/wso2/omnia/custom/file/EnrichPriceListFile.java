package com.zamro.wso2.omnia.custom.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

import com.zamro.wso2.omnia.custom.file.utils.FileUtils;

public class EnrichPriceListFile extends AbstractMediator {

	Log log = LogFactory.getLog(EnrichPriceListFile.class);
	
	private static final String PRICELIST_FILE_START_DATA = "<enfinity branch=\"enterprise\" build=\"1.0.0.0.15.7.6.2\""
			+ " family=\"enfinity\" major=\"6\" minor=\"1\""
			+ " xmlns=\"http://www.intershop.com/xml/ns/enfinity/7.1/bc_pricing/impex\""
			+ " xmlns:dt=\"http://www.intershop.com/xml/ns/enfinity/6.5/core/impex-dt\""
			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.intershop.com/xml/ns/enfinity/7.1/bc_pricing/impex bc_pricing.xsd\">"
			+ "\n<product-price-list id=\"PRICELIST_ID\" priceType=\"PRICE_TYPE\">"
			+ "\n<display-name>DISPLAY_NAME</display-name>"
			+ "\n<description>DESCRIPTION</description>"
			+ "\n<enabled>ENABLED</enabled>"
			+ "\n<valid-from/>"
			+ "\n<valid-to/>"
			+ "\n<priority>PRIORITY</priority>"
			+ "\n<target-groups>"
			+ "\n<customer-segments>"
			+ "\n<customer-segment id=\"CUSTOMER_SEGMENT\" repository-id=\"IMPORT_DOMAIN\"/>"
			+ "\n</customer-segments>" 
			+ "\n</target-groups>";

	private static final String PRICELIST_FILE_END_DATA = "\n</product-price-list>"
														+ "\n</enfinity>";

	private static final String PRICELIST_ID = "PRICELIST_ID";
	private static final String PRICE_TYPE = "PRICE_TYPE";
	private static final String DISPLAY_NAME = "DISPLAY_NAME";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String ENABLED = "ENABLED";
	private static final String PRIORITY = "PRIORITY";
	private static final String CUSTOMER_SEGMENT = "CUSTOMER_SEGMENT";
	private static final String IMPORT_DOMAIN = "IMPORT_DOMAIN";
	private static final String VALID_FROM = "VALID_FROM";
	private static final String VALID_TO = "VALID_TO";
	
	private static final String ENRICH_START_DATA = "ENRICH_START_DATA";
	private static final String ENRICH_END_DATA = "ENRICH_START_DATA";

	private static final String VALID_FROM_EMPTY_TAG = "<valid-from/>";
	private static final String VALID_TO_EMPTY_TAG = "<valid-to/>";
	
	private static final String VALID_FROM_START_TAG = "<valid-from>";
	private static final String VALID_TO_START_TAG = "<valid-to>";
	private static final String VALID_FROM_END_TAG = "</valid-from>";
	private static final String VALID_TO_END_TAG = "</valid-to>";
	
	public boolean mediate(MessageContext context) {

		String filePath = (String) context.getProperty(FileUtils.FILE_PATH);
		String fileName = (String) context.getProperty(FileUtils.FILE_NAME);
		String priceListID = (String) context.getProperty(PRICELIST_ID);
		String priceType = (String) context.getProperty(PRICE_TYPE);
		String displayName = (String) context.getProperty(DISPLAY_NAME);
		String description = (String) context.getProperty(DESCRIPTION);
		String enabled = (String) context.getProperty(ENABLED);
		String validFrom = (String) context.getProperty(VALID_FROM);
		String validTo = (String) context.getProperty(VALID_TO);
		String priority = (String) context.getProperty(PRIORITY);
		String customerSegment = (String) context.getProperty(CUSTOMER_SEGMENT);
		String importDomain = (String) context.getProperty(IMPORT_DOMAIN);

		String enrichStartData = (String) context
				.getProperty(ENRICH_START_DATA);
		String enrichEndData = (String) context.getProperty(ENRICH_END_DATA);

		try {

			File priceListFile = FileUtils.getFile(filePath + File.separator
					+ fileName);

			enrichFile(priceListFile, enrichStartData, enrichEndData,
					priceListID, priceType, displayName, description, enabled,validFrom,validTo,
					priority, customerSegment, importDomain);
		} catch (IOException e) {
			log.error("Error in enriching pricelist file ", e);
		}
		return true;
	}

	private void enrichFile(File priceListFile, String enrichStartData,
			String enrichEndData, String priceListID, String priceType,
			String displayName, String description, String enabled, String validFrom, String validTo,
			String priority, String customerSegment, String importDomain)
			throws IOException {

		String enrichData = null;
		Writer writer = new FileWriter(priceListFile,true);

		if (enrichStartData != null && enrichStartData.equalsIgnoreCase("true")) {
			
			log.info("Enriching price list file for Start tags ");
			
			enrichData =  EnrichPriceListFile.PRICELIST_FILE_START_DATA.replace(PRICELIST_ID, priceListID)
								 .replace(PRICE_TYPE, priceType)
								 .replace(DISPLAY_NAME, displayName)
								 .replace(DESCRIPTION, description)
								 .replace(ENABLED, enabled)
								 .replace(PRIORITY, priority)
								 .replace(CUSTOMER_SEGMENT, customerSegment)
								 .replace(IMPORT_DOMAIN, importDomain);

			if(validFrom!=null && validFrom.trim().length() > 0)
			{
				enrichData= enrichData.replace(VALID_FROM_EMPTY_TAG, VALID_FROM_START_TAG + validFrom + VALID_FROM_END_TAG);
			}
			else{
				enrichData= enrichData.replace(VALID_FROM_EMPTY_TAG, "");
			}

			if(validTo!=null && validTo.trim().length() > 0)
			{
				enrichData = enrichData.replace(VALID_TO_EMPTY_TAG, VALID_TO_START_TAG + validTo + VALID_TO_END_TAG);
			}
			else{
				enrichData= enrichData.replace(VALID_TO_EMPTY_TAG, "");
			}
			
		} else
		{
			log.info("Enriching price list file for End tags ");

			enrichData = EnrichPriceListFile.PRICELIST_FILE_END_DATA;
		}
		writer.write(enrichData);
		writer.flush();
		writer.close();

	}
}
