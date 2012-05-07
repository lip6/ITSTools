<?php
include_once 'GalMenuClass.php';

$LISTE_MENUS = array(new GALMenu("Introduction", "#introduction", null),
					 new GALMenu("Installation", "#installation", null),
					 new GALMenu("Aperçu", "#apercu", null),
					 new GALMenu("Le langage GAL", "#le-langage-gal", array(
						new GALMenu("Qu'est-ce que le GAL", "#questceque-gal", null),
						new GALMenu("A quoi sert-il ?", "#a-quoi-sert", null),
						new GALMenu("Concepts du GAL", "#concepts", array(
							new GALMenu("Fichiers GAL", "#fichiers-gal", null),
							new GALMenu("Système GAL", "#systeme-gal", null),
							new GALMenu("Déclaration de variables", "#decl-variables", null),
							new GALMenu("Déclaration de tableaux", "#decl-tableaux", null),
							new GALMenu("Déclaration de listes", "#decl-listes", null),
							new GALMenu("Transition", "#transition", null),
							new GALMenu("Action", "action", null),
							new GALMenu("Transient", "#transient", null),
							
						))
					 )),
					 new GALMenu("Le côté obscur du GAL", "#cote-obscur", array(
						 new GALMenu("Un système GAL", "#un-systeme-gal", null),
						 new GALMenu("Structures de stockage", "#structure", array(
							 new GALMenu("Variables", "#struct-variables", null),
							 new GALMenu("Tableaux", "#struct-tabl", null),
							 new GALMenu("Listes", "#struct-list", null),
							 new GALMenu("Transient", "#struct-transient",null)
						 )),
						 new GALMenu("Affectations", "#affectations", null),
						 new GALMenu("Transitions", "#transitions", null),
						 new GALMenu("Les actions", "#actions", null),
						 new GALMenu("Les expressions", "#expressions", array(
							 new GALMenu("Arithmétiques", "#expr-arithmetiques", null),
							 new GALMenu("Booléennes", "#expr-bool", null),
							 new GALMenu("Diagramme complet", "#diagramme", null)
						 ))
					 ))
					 ) ;

function generateMenu($listeGALMenus) {
	$result = "<ol>\n" ; 
	foreach($listeGALMenus as $menu)
	{
		$result .= $menu->generateHTMLMenu() ; 
	}
	$result .= "</ol>\n" ; 
	return $result ; 
	
}


?>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        
		echo generateMenu($LISTE_MENUS);
        ?>
    </body>
</html>
