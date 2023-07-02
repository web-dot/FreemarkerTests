<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:fn="fn"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="fo java">

	<xsl:template match="data">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4" page-height="11.7in" page-width="8.5in">
					<fo:region-body region-name="region_body"/>
				</fo:simple-page-master>			
			</fo:layout-master-set>
			<fo:page-sequence master-reference="A4">
				<fo:flow flow-name="region_body">
					<fo:block border="2pt" padding="3mm">
						<xsl:call-template name="employee-data"/>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
	<xsl:template name="employee-data">
		<fo:block>
			<xsl:value-of select="pdf-header"></xsl:value-of>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>
