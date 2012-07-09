<?php
/**
 * Description of GalMenu
 *
 * @author steph
 */
class GALMenu
{
	private $label ; 
	private $url   ;
	private $subMenusArray = null ; 


	/**
	 * Génère un objet menu GAL.
	 * @param string $label
	 * @param string $url
	 * @param array $tabMenus 
	 */
	public function __construct($label, $url, $tabMenus)
	{
		$this->label = $label ; 
		$this->url = $url ; 
		$this->subMenusArray = $tabMenus ;
	}
	
	public function generateHTMLMenu()
	{
		$result = "<li>\n";
		$result .= "<a href='" . $this->url . "'>" . $this->label . "</a>" ; 
		
		if($this->subMenusArray != null)
		{
			$result .= "<ol>\n";
			foreach($this->subMenusArray as $menu)
			{
				 $result.= $menu->generateHTMLMenu() ; 
			}
			$result .= "</ol>\n";
		}
		
		$result .= "</li>\n";
		
		return $result ;
	}
	
	
}

/* Génère le menu GAL à partir de la liste (array) des menus */
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
