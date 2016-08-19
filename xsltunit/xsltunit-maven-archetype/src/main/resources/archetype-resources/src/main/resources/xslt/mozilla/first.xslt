<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
    <list>
    	<article>
    	<xsl:attribute name="title"><xsl:value-of select="translate(/Article/Title,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/></xsl:attribute>
    	<xsl:attribute name="authors"><xsl:value-of select="count(/Article/Authors/Author)"/></xsl:attribute>
    	<xsl:apply-templates select="/Article/Authors/Author"/>
    	</article>
    </list>
  </xsl:template>

  <xsl:template match="Author">
    <author>
    <xsl:value-of select="translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
    </author>
  </xsl:template>

</xsl:stylesheet>