<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
  	<menu>
  	
  	<xsl:attribute name="price"><xsl:value-of select="sum(/breakfast_menu/food/price)"/></xsl:attribute>
    <xsl:attribute name="calories"><xsl:value-of select="sum(/breakfast_menu/food/calories)"/></xsl:attribute>
    
	<xsl:for-each select="/breakfast_menu/food">

	    <ingredient><xsl:value-of select="name"/></ingredient>

	</xsl:for-each>
	</menu>
  </xsl:template>
  
</xsl:stylesheet>