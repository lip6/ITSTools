<?php
	class GALMenu 
	{
		private $label ; 
		private $url   ;
		private $subMenusArray = null ; 
		
		
		function GALMenu(string $label, string $url, GALMenu $tabMenus)
		{
			$this->label = $label ; 
			$this->url = $url ; 
			$this->subMenusArray = $tabMenus ;
		}
		
	}

?>