package com.freemarker.first_test;

import freemarker.template.Configuration;

public class FreemarkerFirst {

	public static void main(String[] args) {
		try {
			// step 1: Configure Freemarker
			Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_30);
			freemarkerConfig.setClassForTemplateLoading(FreemarkerFirst.class, "/templates");
			
			
			
		}
		catch(Exception e) {
			
		}

	}

}
