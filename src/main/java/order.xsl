<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" indent="no" encoding="UTF-8" omit-xml-declaration="no"/>

	<!-- the identity template (copies your input verbatim) -->
	<xsl:template match="node() | @*">
		<xsl:copy>
			<xsl:apply-templates select="node() | @*" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
			<xsl:copy>
				<xsl:apply-templates select="node() | @*" />
			</xsl:copy>
	</xsl:template>

</xsl:stylesheet>
