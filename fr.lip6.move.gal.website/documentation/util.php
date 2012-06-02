<?php
	function TableOfContents($filename, $depth)
	/*AutoTOC function written by Alex Freeman
	* Released under CC-by-sa 3.0 license
	* http://www.10stripe.com/  */
	{
	//	$filename = __FILE__;
	//read in the file
	$file = fopen($filename,"r");
	$html_string = fread($file, filesize($filename));
	fclose($file);
 
	//get the headings down to the specified depth
	$pattern = '/<h[2-'.$depth.']*[^>]*>.*?<\/h[2-'.$depth.']>/';
	$whocares = preg_match_all($pattern,$html_string,$winners);
 
	//reformat the results to be more usable
	$heads = implode("\n",$winners[0]);
	$heads = str_replace('<a name="','<a href="#',$heads);
	$heads = str_replace('</a>','',$heads);
	$heads = preg_replace('/<h([1-'.$depth.'])>/','<li class="toc$1">',$heads);
	$heads = preg_replace('/<\/h[1-'.$depth.']>/','</a></li>',$heads);
 
	//plug the results into appropriate HTML tags
	$contents = '<div id="toc"> 
	<p id="toc-header">Sommaire</p>
	<ul>
	'.$heads.'
	</ul>
	</div>';
	echo $contents;
	}
	
	
	// Lit un fichier GAL, et retourne son contenu, avec les entités HTML remplacées.
	function loadGalFile($galfile)
	{
		
		$contenuFichier = file_get_contents($galfile) or die("Impossible de lire '$galfile'");
		return "
		<span class='downloadsource'>
			<a href='downloadgalfile.php?file=$galfile'>T&eacute;l&eacute;charger le code source</a> 
		</span>
		<pre class='brush: gal;' >" . 
			htmlentities($contenuFichier, ENT_NOQUOTES, "UTF-8") .
		'</pre>' ;   	
	}
	
	function printGalFile($galfile)
	{
		echo loadGalFile($galfile);
	
	}
 	?>
