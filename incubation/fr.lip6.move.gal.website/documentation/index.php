<?php
/**
 * GAL Documentation PAGE 
 */
# Includes
include_once 'util.php';
include_once 'variables.php' ; 


?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252" />
<title>libDDD web site - Guarded Action Language</title>
<link rel="stylesheet" type="text/css" charset="utf-8" media="all"
	href="../files/wiki.css"/>

<link rel="stylesheet" href="3col_leftNav.css" type="text/css" />
<script type="text/javascript" src="js/sh/scripts/shCore.js"></script>
	<script type="text/javascript" src="js/sh/scripts/shBrushJScript.js"></script>
	<script type="text/javascript" src="js/sh/scripts/shBrushGAL.js"></script>
	<link type="text/css" rel="stylesheet" href="js/sh/styles/shCoreDefault.css"/>
	<script type="text/javascript">SyntaxHighlighter.all();</script>

</head>
 <body>
  	<table border="0" cellpadding="0" cellspacing="0" width="900">
		<tr>
			<td><img src="../images/spacer.gif" width="137" height="1"
				border="0" alt="" /></td>
			<td><img src="../images/spacer.gif" width="567" height="1"
				border="0" alt="" /></td>
			<td><img src="../images/spacer.gif" width="196" height="1"
				border="0" alt="" /></td>
			<td><img src="../images/spacer.gif" width="1" height="1"
				border="0" alt="" /></td>
		</tr>
		<tr>
			<td rowspan="3"><img name="Hautgauche"
				src="../images/Haut-gauche.jpg" width="137" height="151" border="0"
				id="Hautgauche" usemap="#m_HautgaucheMap" alt="" /></td>
			<td><img name="Hautcentre" src="../images/Haut-centre.jpg"
				width="567" height="40" border="0" id="Hautcentre"
				usemap="#m_HautcentreMap" alt="" /></td>
			<td rowspan="2"><img name="Hautdroit"
				src="../images/Haut-droit-move2.jpg" width="196" height="60"
				border="0" id="Hautdroit" usemap="#m_HautdroitMap" alt="" /></td>
			<td><img src="../images/spacer.gif" width="1" height="40"
				border="0" alt="" /></td>
		</tr>
		<tr>
			<td><img name="Haut2ms" src="../images/Haut2-ms.jpg" width="567"
				height="20" border="0" id="Haut2ms" alt="" /></td>
			<td><img src="../images/spacer.gif" width="1" height="20"
				border="0" alt="" /></td>
		</tr>
		<tr>
			<td colspan="2"><img name="Hautbasmeta"
				src="../images/Haut-bas-ddd.jpg" width="763" height="91" border="0"
				id="Hautbasmeta" usemap="#m_Hautbasmeta" alt="" /></td>
			<td><img src="../images/spacer.gif" width="1" height="91"
				border="0" alt="" /></td>
		</tr>
	</table>
	<map name="m_HautgaucheMap" id="m_Hautgauche2">
		<area shape="rect" coords="0,0,137,151"
			href="http://www.lip6.fr/en/production/logiciels.php" title="LIP6"
			alt="LIP6" />
	</map>
	<map name="m_HautcentreMap" id="m_Hautcentre2">
		<area shape="rect" coords="0,0,567,40" href="http://www.lip6.fr"
			alt="" />
	</map>
	<map name="m_HautdroitMap" id="m_Hautdroit2">
		<area shape="poly" coords="83,0,186,0,186,60,83,60,83,0"
			href="http://www.upmc.fr" title="UPMC" alt="UPMC" />
		<area shape="poly" coords="0,0,83,0,83,60,0,60,0,0"
			href="http://www.cnrs.fr" title="CNRS" alt="CNRS" />
	</map>
	<map name="m_Hautbasmeta" id="m_Hautbasmeta">
		<area shape="rect" coords="0,62,293,83"
			href="http://www.lip6.fr/en/recherche/team.php?id=720"
			title="Move-team" alt="Move-team" />
	</map>
	<map name="m_Hautgauche" id="m_Hautgauche">
		<area shape="rect" coords="0,0,137,151"
			href="http://www.lip6.fr/en/production/logiciels.php" title="LIP6"
			alt="LIP6" />
	</map>
	<map name="m_Hautcentre" id="m_Hautcentre">
		<area shape="rect" coords="0,0,567,40" href="http://www.lip6.fr"
			alt="" />
	</map>
	<map name="m_Hautdroit" id="m_Hautdroit">
		<area shape="poly" coords="83,0,186,0,186,60,83,60,83,0"
			href="http://www.upmc.fr" title="UPMC" alt="UPMC" />
		<area shape="poly" coords="0,0,83,0,83,60,0,60,0,0"
			href="http://www.cnrs.fr" title="CNRS" alt="CNRS" />
	</map>
	<map name="m_Hautbasmars" id="m_Hautbasmars">
		<area shape="rect" coords="0,62,293,83"
			href="http://www.lip6.fr/en/recherche/team.php?id=720"
			title="Move-team" alt="Move-team" />
		<area shape="rect" coords="449,7,554,51"
			href="http://www.lip6.fr/macao" title="Macao" alt="Macao" />
		<area shape="rect" coords="276,7,381,51"
			href="http://www.lip6.fr/framekit" title="FrameKit" alt="FrameKit" />
		<area shape="rect" coords="104,6,208,51"
			href="http://www.lip6.fr/cpn-ami" title="CPN-AMI" alt="CPN-AMI" />
	</map> 

<table width="900" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="126" bgcolor="#667F9E" valign="top"><a title="Macao"
				href="index.html"><img src="../images/gauche-ddd.jpg" alt=""
					width="126" height="58" border="0" /></a>
				<div class="sidetitle">

					<div class="sidetitle">
						<a class="sidetitle" href="index.php">About</a>
					</div>

					<div class="sidetitle">
						<a class="sidetitle" href="manual.php">Documents</a>
					</div>
					<div class="sidetitle">
						<a class="sidetitle" href="coloane.php">Time Petri nets</a>
					</div>
					<div class="sidetitle">
						<a class="sidetitle" href="gal/index.php">Guarded Action Language</a>
					</div>
					<div class="sidetitle">
						<a class="sidetitle" href="download.php">Download</a>
					</div>
					<div class="sidetitle">
						<a class="sidetitle" href="ltl_bench.php">LTL data</a>
					</div>

					<div class="sidetitle">
						<a class="sidetitle" href="bug_report.php">Bug Report</a>
					</div>
					<div class="sidetitle">
						<a class="sidetitle" href="history.php">History</a>
					</div>
				</div>

				<div class="modDate">$Date:: 2012-04-27#$</div></td>
			<td valign="top" bgcolor="#FFFFFF">
			
			<table width="774" border="0"
					cellspacing="0" cellpadding="10">
					<!-- top menu -->
					<tr>
						<td bgcolor="#BCC9D6">
						<div>
						
						<a href="http://www.lip6.fr">LIP6</a>
							&gt; <a href="http://www.lip6.fr/en/production/logiciels.php">Software</a>
							&gt; <a href="../index.html">MoVe Sofware</a> &gt; <a
							href="index_old.php">GAL</a> &gt; 
						
						
						<div style="float:right;" id="language_selection">
							<a href="?lang=fr"  style="text-decoration:none">
								<img border="0" src="images/french-flag.gif" width="17" height="11" title="French" alt="French" />
							</a> 
							&nbsp;
							<a href="?lang=en" style="text-decoration:none">
								<img border="0" src="images/England_flag.jpg" width="17" height="11" title="English" alt="English" />
							</a>
							&nbsp;&nbsp;
						</div>
						
						</div>
						</td>
					</tr>
					<tr>
						<td bgcolor="#FFFFFF">
 							<div id="content">
							  <?php include_once $GAL_CONTENT_MAIN_PAGE ; ?>
							</div>


        </td>
      </tr>
    </table></td>
  </tr>
</table>
<img src="../images/Bas.jpg" alt="Bas"/>
</body>



</html>
