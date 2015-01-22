<?php
/*
Telechargement d'un fichier GAL
*/

if( ! isset($_GET['file'])) die("Aucun fichier &agrave; t&eacute;l&eacute;charger.") ;

$Fichier_a_telecharger = $_GET['file'] ; 
$type = "text/plain";
$cheminFichier = "$Fichier_a_telecharger" ; 

if(! file_exists($cheminFichier)) die("Fichier GAL introuvable") ; 




header("Content-disposition: attachment; filename=$Fichier_a_telecharger");
header("Content-Type: application/force-download");
header("Content-Transfer-Encoding: $type\n"); // Surtout ne pas enlever le \n
header("Content-Length: ".filesize($cheminFichier));
header("Pragma: no-cache");
header("Cache-Control: must-revalidate, post-check=0, pre-check=0, public");
header("Expires: 0");
readfile($cheminFichier);


?>