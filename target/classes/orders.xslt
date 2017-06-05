<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml" indent="yes"/>

  <!--<orders>-->
    <!-- the identity template (copies your input verbatim) -->
    <xsl:template match="node() | @*">
            <orders>
            <xsl:copy>
            <xsl:apply-templates select="node() | @*" />
            </xsl:copy>
        </orders>
    </xsl:template>
  <!--</orders>-->

  <!-- special templates only for things that need them -->
  <xsl:template match="name">
    <FirstName><xsl:value-of select="." /></FirstName>
  </xsl:template>

</xsl:stylesheet>
