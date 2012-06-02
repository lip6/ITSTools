<?php
/**
 * GAL Documentation PAGE 
 */
include_once 'util.php';

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- Copyright 2005 Macromedia, Inc. All rights reserved. -->
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<title>Documentation GAL</title>
<link rel="stylesheet" type="text/css" charset="utf-8" media="all"
	href="../files/wiki.css"/>
 <link rel="stylesheet" href="3col_leftNav.css" type="text/css" />

	
<script type="text/javascript" src="js/sh/scripts/shCore.js"></script>
	<script type="text/javascript" src="js/sh/scripts/shBrushJScript.js"></script>
	<script type="text/javascript" src="js/sh/scripts/shBrushGAL.js"></script>
	<link type="text/css" rel="stylesheet" href="js/sh/styles/shCoreDefault.css"/>
	<script type="text/javascript">SyntaxHighlighter.all();</script>
	
<style type="text/css">
<!--
.Style1 {font-size: 150%}
-->
</style>
</head>
<body>
<div id="masthead">
  <h1 id="siteName">GAL</h1>
  
  <h2 id="pageName">Documentation</h2>
  <br />
  <div id="breadCrumb"> <a href="..">GAL Accueil </a> &gt; Documentation</div>
</div>

<div id="content">
  <?php include_once "content.php" ; ?>
</div>
<!--end content -->

<div id="navBar">
 <?php
 	include_once "contentLeft.php" ; 
 ?>
 
 	<div id="headlines">
	 <?php
		include "contentRight.php" ; 
	 ?>
	</div>

  </div>
<!--end navBar div -->

<!--end headlines -->

<div id="siteInfo">
<?php
	include_once "siteInfo.php";
?>
</div>
<br />
</body>
</html>
