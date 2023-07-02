package com.freemarker.first_test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerFirst {

	public static void main(String[] args) {
		try {
			// step 1: Configure Freemarker
			Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_30);
			freemarkerConfig.setClassForTemplateLoading(FreemarkerFirst.class, "/templates");
			
			
			// step 2: load ftl template
			Template template = freemarkerConfig.getTemplate("employee_result.ftl");
			
			// step 3: create a data model
			Map<String, List<EmployeeDto>> dynamicModel = new HashMap<>();
			
			new EmployeeDto("Sunil", 50000, "Developer");
			new EmployeeDto("Anjali", 30000, "Human Resources");
			new EmployeeDto("Rakesh", 25000, "Trainee");
			new EmployeeDto("Piyush", 35000, "Human Resources");
			new EmployeeDto("Suman", 80000, "Trainee");
			new EmployeeDto("Kapil", 45000, "Human Resources");
			new EmployeeDto("Anil", 20000, "Trainee");
			new EmployeeDto("Laxmi", 40000, "Operations");
			new EmployeeDto("Kalpana", 55000, "Developer");
			new EmployeeDto("Dinesh", 65000, "Operaions");
			
			List<EmployeeDto> yearData1 = new ArrayList<>(Arrays.asList(
					new EmployeeDto("Sunil", 50000, "Developer"),
					new EmployeeDto("Anjali", 30000, "Human Resources"),
					new EmployeeDto("Rakesh", 25000, "Trainee"),
					new EmployeeDto("Piyush", 35000, "Human Resources")
					));
			
			List<EmployeeDto> yearData2 = new ArrayList<>(Arrays.asList(
					new EmployeeDto("Suman", 80000, "Trainee"),
					new EmployeeDto("Kapil", 45000, "Human Resources"),
					new EmployeeDto("Anil", 20000, "Trainee")
					));
			
			List<EmployeeDto> yearData3 = new ArrayList<>(Arrays.asList(
					new EmployeeDto("Laxmi", 40000, "Operations"),
					new EmployeeDto("Kalpana", 55000, "Developer"),
					new EmployeeDto("Dinesh", 65000, "Operaions")
							));
			
			dynamicModel.put("2022-23", yearData1);
			dynamicModel.put("2021-22", yearData2);
			dynamicModel.put("2020-21", yearData3);
			
			Map<String, String> constantsModel = new HashMap<>();
			
			constantsModel.put("pdf-header", "Employee Data For Years");
			
			
			Map<String, Object> combinedModel = new HashMap<>();
			
			combinedModel.putAll(dynamicModel);
			combinedModel.putAll(constantsModel);
			
			// step 4: process the ftl template with the data
			StringWriter writer = new StringWriter();
			template.process(combinedModel, writer);
			String xslFoContent = readFileContent("employee_result.xsl");
			String outputPath  = "C:\\Users\\fedev\\OneDrive\\Documents\\eclipse_workspaces\\primary_workspace\\FreemarkerTest\\pdf";
			String populatedTemplate = processTemplateWithData(template, combinedModel, outputPath);
			
			
			// step 5: generate the pdf using apache FOP
			FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			OutputStream out = new FileOutputStream(new File("output.pdf"));
			
			Fop fop = fopFactory.newFop("application/pdf", foUserAgent, out);
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer transformer = transformFactory.newTransformer();
			Source source = new StreamSource(new StringReader(populatedTemplate));
			Result result = new SAXResult(fop.getDefaultHandler());
			
			transformer.transform(source, result);
			
			out.close();
			
			
		}
		catch(IOException | TemplateException | TransformerException | FOPException e) {
			e.printStackTrace();
		}

	}
	
	
	private static String readFileContent(String filePath) {
	    try {
	        return new String(Files.readAllBytes(Paths.get("src/templates/" + filePath)));
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null; // or handle the exception according to your requirements
	    }
	}
	
	private static String processTemplateWithData(Template template, Map<String, Object> combinedModel, String outputPath) {
		try (Writer writer  = new FileWriter(outputPath)){
			template.process(combinedModel, writer);
			
		}
		catch(IOException | TemplateException e) {
			
		}
		return outputPath;
	}



}
