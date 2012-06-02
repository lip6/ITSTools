<style type="text/css">
<!--
.galElement {
	font-family:Monospace,"Courier New", Courier;
	font-weight:bold;
	color:red
	
}
-->
</style>

<div class="feature"> <img src="images/Documentation.png" alt="" width="120" height="73" />
    <b>Bienvenue sur la page de documentation de GAL </b>
    <p> Vous trouverez sur cette page la documentation officielle du langage GAL, toutes ses sp&eacute;cifications, ainsi que de l'aide sur l'installation et l'utilisation.</p>
    <p>&nbsp;</p>
    <p><strong>Sommaire</strong></p>
    <div id="sommaire">
	
	<?php
	TableOfContents("content.php", 5); 
	?>
	</div>
	
	</span>
</div>
  <div class="story">
  <br /><br />
    <h2><a name="introduction"></a>1. Introduction</h2>
	
    <p>&nbsp; </p>	
    <p>Ce projet s&rsquo;inscrit dans le cadre du PSTL au sein de l&rsquo;universit&eacute; Pierre et Marie Curie, au cours de l&rsquo;ann&eacute;e 2012. Le but du stage est la r&eacute;alisation d&rsquo;un plugin Eclipse qui est un &eacute;diteur pour le langage GAL. Ce plugin jouit de toute la puissance d&rsquo;&eacute;dition, d&rsquo;auto-compl&eacute;tion et surtout d&rsquo;auto-correction d&rsquo;Eclipse.</p>
    <p>Pour atteindre les objectifs, nous nous sommes servis d&rsquo;Eclipse et de Xtext ( http://www.eclipse.org/Xtext/ ), un plugin Eclipse qui permet de d&eacute;finir des grammaires pour des langages d&eacute;di&eacute;s &agrave; un domaine sp&eacute;cifique ( Domain Specific Language ) sous tout ses aspects, de mani&egrave;re tr&egrave;s compl&egrave;te; nous nous sommes aussi servis d'une plate-forme de d&eacute;veloppement  SVN ( <a href="https://srcdev.lip6.fr/svn/research/thierry/PSTL/GAL">https://srcdev.lip6.fr/svn/research/thierry/PSTL/GAL</a> ), afin de fournir un travail collaboratif entre diff&eacute;rents intervenants. Pour le d&eacute;ploiement du plugin, nous nous servons du serveur d&rsquo;int&eacute;gration continu TeamCity   ( <a href="http://www.jetbrains.com/teamcity/">http://www.jetbrains.com/teamcity/</a> )</p>
  </div>
  
<div class="story">
    <p>&nbsp;</p>
    <p><br />
    </p>
    <h2><a name="installation"></a>2. Installation</h2>
  <p><br />
  </p>
    <p>Comme mentionn&eacute;, GAL est un plugin pour l&rsquo;IDE Eclipse. Il requiert donc une installation locale d&rsquo;Eclipse sur votre ordinateur, ainsi qu&rsquo;une machine virtuelle Java install&eacute;e. </p>
  <p>Si cela n&rsquo;est pas fais,<a href="http://www.eclipse.org/downloads/" target="_blank"> t&eacute;l&eacute;chargez ici Eclipse</a>, et ici le <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html" target="_blank">JDK Java (6+)</a>. </p>
    <p>&nbsp;</p>
    <p>Pour pouvoir utiliser GAL avec votre Eclipse, fa&icirc;tes comme suit : </p>
    <p>1) Lancez Eclipse
    </p>
  <p>2) Ouvrez le menu <strong>Help</strong> ---&gt; <strong>Install new software</strong>...    </p>
    <p>3) Cliquez sur <strong>Add...</strong> </p>
    <p>4) Dans la deuxi&egrave;me zone de texte (<strong>Location</strong>), collez-y cette adresse:    </p>
    <p><strong>https://teamcity-systeme.lip6.fr/guestAuth/repository/download/bt108/.lastSuccessful/update-site</strong></p>
    <p>Puis validez. </p>
    <p>Vous verrez ensuite cette fen&ecirc;tre : </p>
  <p align="center"><img src="images/captures/Capture-Install .png" width="760" height="624" /></p>
    <p>Cochez &ldquo;<strong>GAL Xtext based feature</strong>&rdquo; et cliquez sur <strong>Next</strong>, puis encore sur <strong>Next</strong>.<br />
      Ensuite, cliquez sur &ldquo;<strong>I accept the terms of the license agreement</strong>&rdquo;, puis cliquez sur <strong>Finish</strong>.    </p>
    <p>Eclipse proc&egrave;dera au t&eacute;l&eacute;chargement du plugin sur votre ordinateur. Lorsque l&rsquo;installation sera termin&eacute;e, il vous sera demand&eacute; de red&eacute;marrer Eclipse. Acceptez, en cliquant sur <strong>restart</strong>.</p>
    <p>Au red&eacute;marrage, vous pourrez cr&eacute;er un nouveau projet GAL en proc&eacute;dant ainsi: File &rarr; New &rarr; Project &rarr; General &rarr; Project...</p>
    <p>Dans le projet, il vous suffira de cr&eacute;er un fichier avec l&rsquo;extension .gal , et ainsi vous pourrez programmer en langage GAL.<strong> FIXME (&agrave; v&eacute;rifier)</strong></p>
</div>


<div class="story">
    <p>&nbsp;</p>
    <p><br />
    </p>
    <h2><a name="apercu"></a>3. Aper&ccedil;u</h2>
    <p>Voici un petit exemple d&rsquo;un syst&egrave;me &eacute;crit en GAL </p>

	<?php 
		printGalFile("galfiles/sample-1.gal");
	?>


	
    <p>Ce code montre les &eacute;l&eacute;ments d&rsquo;un langage imp&eacute;ratif tels que les d&eacute;clarations de variables et autre affectation et des subtilit&eacute;s propres aux descriptions des syst&egrave;mes concurrents.</p>
    <p>L&rsquo;accent a &eacute;t&eacute; mis pour d&eacute;finir un langage simple, mais permettant de d&eacute;finir ais&eacute;ment des syst&egrave;mes concurrents, ici formalis&eacute;s sous forme de &ldquo;transitions&rdquo;. Elles sont assimilables aux transitions des automates ou les r&eacute;seaux de Petri.</p>
    <p>Par la suite nous d&eacute;taillerons les m&eacute;canismes du langage et ensuite nous vous d&eacute;voilerons les d&eacute;tails d&rsquo;impl&eacute;mentation du langage.</p>
    <p></p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
</div>


<div class="story">
	<h2><a name="le-langage-gal"></a>4. Le langage GAL</h2>
	<p>Cette section d&eacute;crit le langage GAL et les s&eacute;mantiques des concepts qu&rsquo;il offre.</p>
</div>

<div class="story">
	<h3><a name="what-is-gal"></a>4.1 Qu'est ce que le GAL </h3>
	<p>Le GAL est l'acronyme de <strong>Guarded Action Language,</strong>          qui est un langage  de programmation d&eacute;di&eacute; &agrave; la v&eacute;rification formelle des syst&egrave;mes concurrents. </p>
</div>

<div class="story">
	<h3><a name="utilite-gal"></a>4.2 A quoi sert-il ? </h3>
	<p>- Assembleur s&eacute;mantique</p>
	<p>- manipulation d'ensemble d'&eacute;tats &agrave; l'aide de diagrammes de decision (<a href="http://ddd.lip6.fr">ddd.lip6.fr</a>) </p>
	<p>-  utilis&eacute; pour la mod&eacute;lisation de systemes concurrents en vue de leur v&eacute;rification par model-checking <br />
	  <strong style="color:#FF0000">Not yet implemented</strong></p>
	<p>&nbsp;</p>
</div>
<div class="story">
	<h3><a name="concepts-gal"></a>4.3 Concepts du GAL </h3>
</div>


<div class="story">	
	<h4><a name="fichiers-gal" ></a>4.3.1  Fichiers GAL</h4><br />
	
	<p>Les fichiers GAL sont des fichiers simples avec l&rsquo;extension gal. Par exemple <em>foo.gal</em> est un nom de fichier gal valide.</p>
	<p>&nbsp;</p>

</div>

<div class="story">	
	<h4><a name="systeme-gal" ></a>4.3.2  Syst&egrave;me GAL</h4>
	
	
	<p>Un syst&egrave;me GAL est caract&eacute;ris&eacute; par un nom, et contient une suite d'instructions que nous d&eacute;taillerons au fil de ce document. </p>
	<p>Une fois un fichier GAL cr&eacute;&eacute; et ouvert, toutes les instructions (cf. les sections suivantes) du nouveau syst&egrave;me doivent &ecirc;tre plac&eacute;es dans un bloc structur&eacute;. On commence d'abord &agrave; &eacute;crire le mot-cl&eacute; <span class="galElement">GAL</span> (en majuscule), suivi obligatoirement du nom du syst&egrave;me. Ce nom doit commencer par une lettre de l'alphabet, et non par un chiffre ou autre symbole. Une bonne pratique est de donner des noms significatifs aux syst&egrave;mes cr&eacute;&eacute;s. </p>
	<p>Une fois le nom du syst&egrave;me &eacute;crit, on le fait suivre par une accolade ouvrante, et puis, avec toutes les instructions que le langage GAL offre. &Agrave; la fin, terminer la description du syst&egrave;me avec une accolade fermante. </p>
	<p>Voici en clair &agrave; quoi ressemble une d&eacute;claration d&rsquo;un syst&egrave;me GAL dont le nom est <em>foo</em>, et sauvegard&eacute; dans un fichier sous la d&eacute;nomination<em> foo.gal</em>:</p>
	

	<p>
	  <?php
		printGalFile("galfiles/sample-2.gal");
	?>
</p>
	<p>&nbsp;    </p>
</div>

<div class="story">	
	<h4><a name="declaration-variables" ></a>4.3.3  D&eacute;claration de variables</h4>
	
	
	
	
	<p>Un des concepts de base du GAL est la d&eacute;claration de variables. Les variables en GAL sont de type entier, il n&rsquo;y pas d&rsquo;autres types tels qu&rsquo;ils sont pr&eacute;sent dans d&rsquo;autres langages g&eacute;n&eacute;raux comme le C ou JAVA.<br />
	  La section pr&eacute;c&eacute;dente a montr&eacute; comment introduire un nouveau syst&egrave;me; dans ce paragraphe, nous exposons la fa&ccedil;on dont les variables sont d&eacute;clar&eacute;es.<br />
    </p>
    <p>L&rsquo;introduction d&rsquo;une nouvelle variable en GAL se fait avec le mot clef <span class="galElement">int</span> suivi du nom de la variable commen&ccedil;ant par une lettre -- il faut que le nom soit unique et qu&rsquo;aucune autre instruction GAL requ&eacute;rant un nom ne porte le m&ecirc;me. Le nom donn&eacute; on le fait suivre par le signe <span class="galElement">=</span>, puis d&rsquo;une valeur initiale &agrave; la variable en finissant la d&eacute;claration par le mot clef ; (point-virgule).<br />
  </p>
    <p>En GAL toute variable d&eacute;clar&eacute;e doit &ecirc;tre initialis&eacute;e.</p>
    <p>&nbsp;</p>
    <p>Ci-dessous, un exemple d&rsquo;un syst&egrave;me GAL avec deux d&eacute;clarations de variable:</p>
	
	<?php
	printGalFile('galfiles/sample-3.gal');
	?>
   <p>&nbsp;
   </p> 
</div>
	

<div class="story">	
	<h4><a name="declaration-tableaux" ></a>4.3.4  D&eacute;claration de tableaux </h4><br />
	<p>Une autre forme de d&eacute;claration est celle des tableaux, ceux-ci permettent de d&eacute;clarer plusieurs variables en une instruction et offrent ainsi un acc&egrave;s index&eacute; aux variables. Comme pour une variable simple, l&rsquo;initialisation de chacune des cases du tableau est obligatoire.<br />
	</p>
	
    <p>Le mot clef array suivi de la taille entre crochets, puis du nom du tableau et du signe <span class="galElement">=</span>, puis d&rsquo;une liste de valeurs s&eacute;par&eacute;es avec des virgules, et entour&eacute;es avec des parenth&egrave;ses en terminant l&rsquo;instruction avec ; (point-virgule), forme une d&eacute;claration d&rsquo;un tableau en GAL.</p>
    <p>Un exemple d&rsquo;un syst&egrave;me avec une d&eacute;claration d&rsquo;un tableau:
    </p>
	<?php
	printGalFile('galfiles/sample-4.gal');
	
	?>
	<p>&nbsp;</p>
</div>

<div class="story">	
	<h4><a name="declaration-listes" ></a>4.3.5  D&eacute;claration de listes </h4><br />
	<p>Les listes offrent une structure bas&eacute;e sur le principe de LIFO (Last In, First Out), contrairement aux variables et aux tableaux, l&rsquo;initialisation des listes n&rsquo;est pas obligatoire, n&eacute;anmoins on peut donner des valeurs initiales. L&rsquo;acc&egrave;s aux valeurs sauvegard&eacute;es dans les liste se fait par empilement et d&eacute;pilement du sommet de la pile (cf. actions sur les listes)<br />
	</p>
    <p>La d&eacute;claration d&rsquo;une liste se fait avec le mot clef list suivi du nom de la liste et &eacute;ventuellement, suivi d&rsquo;un signe = puis une liste de valeurs comprise entre deux parenth&egrave;ses et les valeurs s&eacute;par&eacute;es par des virgules, et l&rsquo;instruction se finit avec ; (point-virgule).</p>
    <p>Ci-dessous un exemple d&rsquo;un syst&egrave;me d&eacute;clarant deux listes, une initialis&eacute;e, l&rsquo;autre non:<br />
    </p>
	<?php
		printGalFile('galfiles/sample-5.gal');
	?>
	<p>&nbsp;</p>
</div>	
	

<div class="story">
	<h4><a name="transition" ></a>4.3.6 Transition  </h4><br />
	<p>Ce bloc permet de passer d&rsquo;un &eacute;tat &agrave; un autre &eacute;tat, en fonction d&rsquo;une garde, celle-ci est une expression bool&eacute;enne en fonction de sa valeur on change d&rsquo;&eacute;tat, en ex&eacute;cutant des instructions           (cf. actions) &eacute;crites dans le corps de la transition.<br />
	</p>
    <p>Le mot-cl&eacute; transition suivi d&rsquo;un nom commen&ccedil;ant par une lettre, puis d&rsquo;une garde entre crochet, et &eacute;ventuellement du mot-cl&eacute; lebel suivi du label entre double quotes puis du corps de la transition entre accolades.</p>
    <p>Un exemple avec deux transitions d&rsquo;un syst&egrave;me, la premi&egrave;re avec un label:<br />
    </p>
	<?php
		printGalFile('galfiles/sample-6.gal');
	?>
</div>	

<div class="story">
	<h4><a name="action" ></a>4.3.7 Actions </h4>
	<p>Les actions sont les op&eacute;rations appliqu&eacute;es sur les variables et les constantes du syst&egrave;me, consistant exclusivement &agrave; des affectations de variables, ou des actions sur des listes: pop() et push() (cf. paragraphe Actions du chapitre suivant).</p>

</div>

<div class="story">	
	<h5><a name="op-arith"></a> a) Op&eacute;rations arithm&eacute;tiques</h5>
	
	<p align="center">
		<strong>Binaires</strong>	
	</p>
	<table width="200" border="1" align="center" style="border-collapse:collapse">
      <tr>
        <th scope="col">Op&eacute;ration</th>
        <th scope="col">Op&eacute;rateur</th>
      </tr>
      <tr align="center">
        <td >OR bit &agrave; bit </td>
        <td>|</td>
      </tr>
      <tr align="center">
        <td>XOR bit &agrave; bit </td>
        <td>^</td>
      </tr>
      <tr align="center">
        <td>AND bit &agrave; bit </td>
        <td>&amp;</td>
      </tr>
      <tr align="center">
        <td>Shift gauche </td>
        <td>&lt;&lt;</td>
      </tr>
      <tr align="center">
        <td>Shift droit </td>
        <td>&gt;&gt;</td>
      </tr>
      <tr align="center">
        <td>Addition</td>
        <td>+</td>
      </tr>
      <tr align="center">
        <td>Soustraction</td>
        <td>-</td>
      </tr>
      <tr align="center">
        <td>Multiplication</td>
        <td>*</td>
      </tr>
      <tr align="center">
        <td>Modulo</td>
        <td>%</td>
      </tr>
      <tr align="center">
        <td>Division</td>
        <td>/</td>
      </tr>
	  <tr align="center">
        <td>Puissance</td>
        <td>**</td>
      </tr>
    </table>
	<p>	</p>
	
	
	
	<p align="center">
		<strong>Unaires</strong>	
	</p>
	<table width="200" border="1" align="center" style="border-collapse:collapse">
      <tr>
        <th scope="col">Op&eacute;ration</th>
        <th scope="col">Op&eacute;rateur</th>
      </tr>
      <tr align="center">
        <td >Le moins unaire </td>
        <td>-</td>
      </tr>
      <tr align="center">
        <td>Compl&eacute;ment bit &agrave; bit </td>
        <td>~</td>
      </tr>
    </table>
	<p>	</p>
</div>


<div class="story">
	<h5><a name="expr-bool"></a>b) Expressions bool&eacute;ennes</h5>
	<p>Les expressions bool&eacute;ennes sont permises seulement dans les gardes des transitions, elles sont pr&eacute;sent&eacute;es dans cette section plut&ocirc;t que dans les transition car la section suivante permet de les utiliser dans des expressions arithm&eacute;tiques (cf. Wrapper des expressions bool&eacute;ennes) et aussi pour regrouper toutes les op&eacute;rations dans une seule section.<br />
	</p>
    <p>Les expressions bool&eacute;ennes sont des expressions valant vrai ou faux, les expressions bool&eacute;ennes les plus simples en GAL sont True et False. Les op&eacute;rateurs bool&eacute;ens usuels sont pr&eacute;sents dans le GAL, en l&rsquo;occurrence, le OR ( || ), le AND ( &amp;&amp; ) et le NOT( ! ). <br />
    </p>
    <p>Un autre cas de ces expression est la comparison, celle-ci prends deux expressions enti&egrave;res et les compare avec les op&eacute;rateurs de comparaison qui sont:</p>
    <table width="200" border="1" style="border-collapse:collapse" align="center">
      <tr align="center">
        <th scope="col">Op&eacute;ration</th>
        <th scope="col">Op&eacute;rateur</th>
      </tr>
      <tr align="center">
        <td>Strictement sup&eacute;rieur </td>
        <td>&gt;</td>
      </tr>
      <tr align="center">
        <td>Strictement inf&eacute;rieur </td>
        <td>&lt;</td>
      </tr>
      <tr align="center">
        <td>Sup&eacute;rieur ou &eacute;gal </td>
        <td>&gt;=</td>
      </tr>
      <tr align="center">
        <td>Inf&eacute;rieur ou &eacute;gal </td>
        <td>&lt;=</td>
      </tr>
      <tr align="center">
        <td>Egalit&eacute;</td>
        <td>==</td>
      </tr>
      <tr align="center">
        <td>Non-&eacute;galit&eacute;</td>
        <td>!=</td>
      </tr>
    </table>
    <p>&nbsp;</p>
</div>


<div class="story">
	<h5><a name="wrapper"></a>  c) Wrapper (enveloppeur) des expressions bool&eacute;ennes </h5>
	<p> <br />
	</p>
    <p>M&eacute;langer les expressions arithm&eacute;tiques et les expressions bool&eacute;ennes dans une m&ecirc;me expression arithm&eacute;tique est possible en GAL, en entourant l&rsquo;expression bool&eacute;enne avec des parenth&egrave;ses, ceci permet de voir l&rsquo;expression bool&eacute;en comme valant 0 en cas o&ugrave; son &eacute;valuation vaut faux sinon 1. cette technique nous permet d&rsquo;obtenir une partie de la puissance des expressions du langage C et avoir une expressivit&eacute; assez similaire.<br />
    </p>
</div>

<div class="story">
	<h5><a name="op-listes"></a>d) Op&eacute;rations sur les listes </h5>
	<p>Comme dit pr&eacute;c&eacute;demment, les listes sont bas&eacute;es sur le principe LIFO, donc GAL fournit des op&eacute;rations de manipulation de cette structure, excepter la d&eacute;claration, voici les op&eacute;rations appliqu&eacute;es sur les listes:<br />
	</p>
	
    <p>.push(expression enti&egrave;re): permet d&rsquo;empiler une valeur enti&egrave;re sur la pile.</p>

    <p>.peek(): renvoie le sommet de la pile sans l&rsquo;extraire, il faut aussi faire attention l&agrave; l&rsquo;invocation de cette op&eacute;ration, que la liste ne soit pas vide.</p>
    <p>.pop(): idem que .peek(), seulement cette op&eacute;ration extrait le sommet de la pile sans le renvoyer.</p>
    <p>Ci-dessous un exemple d&rsquo;un syst&egrave;me qui offre un petit panorama de la majorit&eacute; des concepts li&eacute;s au GAL:<br />
    </p>
    <?php
		printGalFile('galfiles/sample-7.gal');
	?>
</div>	


<div class="story">	
	<h4><a name="transient" ></a>4.3.8  Transient</h4>
	<p><br />
	</p>
    <p>Transient est un mot-cl&eacute; qui en lui affectant une expression bool&eacute;enne permet en fonction de la valeur celle-ci permet d&rsquo;examiner le noeud ou l&rsquo;ignorer.</p>

    <p>Une telle expression est d&eacute;clar&eacute;e avec le mot-cl&eacute; TRANSIENT, suivi du signe d&rsquo;affectation =, puis d&rsquo;une expression bool&eacute;enne.</p>

    <p>voici un syst&egrave;me qui r&eacute;sume toutes les fonctionnalit&eacute;s du GAL:<br />
    </p>
    <?php
	printGalFile('galfiles/sample-8.gal');
	?>
</div>

<div class="story">	
	<h3><a name="fonctionnalites-editeur" ></a>4.4  Fonctionnalit&eacute;s de l'&eacute;diteur GAL</h3>
	<p>
	Le plugin Eclipse GAL hérite des fonctionnalités d'édition de l'IDE Eclipse et ceci facilite l'écriture des systèmes concurrents.	</p>
	<p><img src="images/captures/1.png" width="1030" height="702" /></p>
</div>


<div class="story">	
	<h4><a name="autocompletion" ></a>4.4.1  Auto-complétion</h4>
	<p>L&rsquo;une des fonctionnalit&eacute;s d&rsquo;Eclipse les plus connues est l'auto-completion, en appuyant simultan&eacute;ment sur CTRL + ESPACE, le GAL propose une liste d'&eacute;l&eacute;ments susceptibles de compl&eacute;ter le mot en fonction des caract&egrave;res d&eacute;j&agrave; &eacute;crits, ou sinon les &eacute;l&eacute;ments qui peuvent &ecirc;tre plac&eacute;s &agrave; l&rsquo;endroit o&ugrave; le curseur est plac&eacute;.</p>

    <p>&nbsp;</p>
    <p>Voici un exemple de d&rsquo;auto-completion propos&eacute;:<br />
    </p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&lt;&lt;Image&gt;&gt;<br />
    </p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&lt;&lt;Commentaire sur l&rsquo;image&gt;&gt;<br />
    </p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
</div>


<div class="story">
	<h2><a name="cote-obscur"></a>5. Le cot&eacute; obscur du GAL</h2>
	<p>Cette partie d&eacute;taille le fonctionnement interne du GAL, c&rsquo;est-&agrave;-dire, la repr&eacute;sentation en m&eacute;moire des programmes &eacute;crits en GAL et sur quel m&eacute;ta-mod&egrave;le il s'appuie.</p>
	<p>&nbsp;</p>
</div>

<div class="story">
	<h3><a name="un-systeme-gal"></a>5.1 Un syst&egrave;me GAL </h3>
	<p>Le chapitre pr&eacute;c&eacute;dent pr&eacute;sente le langage GAL sous tous ses aspects, d&eacute;claration un nouveau syst&egrave;me, &eacute;criture les instructions dans le corps de ce syst&egrave;me. <br />
	</p>
	<p>Un programme &eacute;crit en GAL s&rsquo;appuie sur le m&eacute;ta-mod&egrave;le dont le sch&eacute;ma UML est:<br />
  </p>
	<p>&nbsp;
	<img src="images/uml/image05.png" />
	</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>Ce diagramme illustre bien comment le m&eacute;ta-mod&egrave;le est construit, chaque concepts pr&eacute;sent&eacute; avant, &agrave; sa m&eacute;ta-classe &eacute;quivalente. Pour un nouveau programme GAL qui introduit un nouveau syst&egrave;me, une m&eacute;ta-classe System est cr&eacute;&eacute;e et qui fait office de conteneur pour les variables (m&eacute;ta-classe Variable), les listes (m&eacute;ta-classe List), les tableaux (m&eacute;ta-classe ArrayPrefix), transition (m&eacute;ta-classe Transition) et le transient (m&eacute;ta-classe Transient).</p>
	<p>Pour avoir plus d&rsquo;informations sur la mani&egrave;re dont laquelle ce mod&egrave;le est g&eacute;n&eacute;rer, il faut se r&eacute;f&eacute;rer aux sources Xtext du plugin GAL se trouvant dans le fichier fr.lip6.move.gal/src/fr/lip6/move/Gal.xtext au niveau de la r&egrave;gle Xtext System.<br />
  </p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>Il est fortement recommander de lire la documentation de Xtext pour comprendre les r&egrave;gles qui se trouvent dans le fichier Gal.xtext indiqu&eacute; ci-dessus.</p>
	<p>Toutes les m&eacute;ta-classes contiennent un champ name de type EString, ce champs contient le nom de la structure d&eacute;clar&eacute;e, &agrave; savoir, le nom d&rsquo;un syst&egrave;me GAL, d&rsquo;une variable, d&rsquo;un tableau, d&rsquo;une transition et d&rsquo;une liste.</p>
	<p>Ce diagramme est tr&egrave;s minimaliste, car il nous donne aucune informations sur les d&eacute;clarations, et les acc&egrave;s aux donn&eacute;es stock&eacute;es dans les structres pr&eacute;sent&eacute;es pr&eacute;c&eacute;demment. Par exemple la classe Transient, &agrave; part savoir qu&rsquo;elle existe, on ne connais pas son r&ocirc;le ce mod&egrave;le.<br />
  </p>
	<p></p>
	<p>&nbsp;</p>
</div>

<div class="story">
	<h3><a name="structure-stockage"></a>5.2 Structures de stockage</h3>
	<p>Cette section couvre la représentation interne des différentes structures qui permettent en GAL de stocker des données (des entiers).
	<p>&nbsp;</p>
</div>


<div class="story">
	<h4><a name="variables-impl"></a>5.2.1 Variables</h4>
	<p>Le diagramme ci-apr&egrave;s illustre, le mod&egrave;le appliqu&eacute; pour arriver &agrave; cr&eacute;er une variable:</p>
	<p><img src="images/uml/image02.png" />&nbsp;</p>
	<p>La m&eacute;ta-classe <span class="galElement">VariableRef</span> contient une r&eacute;f&eacute;rence vers une variable physique et etend la classe <span class="galElement">VarAccess</span>, celle-ci permet de r&eacute;cup&eacute;rer la r&eacute;f&eacute;rence sur la variable afin d&rsquo;y acc&eacute;der. <span class="galElement">VarAccess</span> d&eacute;rive l&rsquo;interface IntExpression, pour pouvoir utiliser les variables dans des expressions enti&egrave;res.</p>
</div>


<div class="story">
	<h4><a name="tableaux-impl"></a>5.2.2 Tableaux</h4>
	<p>Le sch&eacute;ma UML de la repr&eacute;sentation interne des tableaux:</p>
	<p><img src="images/uml/image04.png" />&nbsp;</p>
	<p>La classe InitValue sert d&rsquo;un conteneur pour les valeurs d&rsquo;initialisation (cf. Les d&eacute;clarations de tableaux). <span class="galElement">ArrayVarAccess</span> d&eacute;rivant de <span class="galElement">VarAccess</span> pour l&rsquo;utilisation de cases du tableau dans des expressions arithm&eacute;tiques, et contenant un index en forme d&rsquo;une expression enti&egrave;re pour stocker l&rsquo;index de la case &agrave; laquelle on veux y acc&eacute;der. <span class="galElement">ArrayVarAccess</span> joue le m&ecirc;me r&ocirc;le que <span class="galElement">VarAccess</span> pour les variables.<br />
  </p>
	<p>Le champ size de la meta-classe <span class="galElement">ArrayPrefix</span> contient la taille du tableau.<br />
  </p>
	<p></p>
	<p></p>
	<p></p>
	<p></p>
	<p>&nbsp;</p>
</div>


<div class="story">
	<h4><a name="listes-impl"></a>5.2.3 Listes</h4>
	<p>Les listes sont bas&eacute;es sur le principe de LIFO (Last In, Last out), leur diagramme UML est plus simple que celui des tableaux:</p>
	<p><img src="images/uml/image01.png" />&nbsp;</p>
	<p>&nbsp;</p>
	<p>Comme le sch&eacute;ma ci-dessus le montre, Une liste contient juste des valeurs d&rsquo;initialisation, celles-ci ne sont pas obl&eacute;gatoires &agrave; la d&eacute;claration de la liste (cf. Le langage GAL). </p>
    <p>Comme les listes se basent sur le principe de LIFO, GAL offre trois op&eacute;rateurs pour la manipulation de cette structure qui seront &eacute;tudi&eacute;s dans la section Actions.</p>
</div>



<div class="story">
	<h4><a name="transient-impl"></a>5.2.4 Transient</h4>
	<p>Le transient n'est qu'une méta-classe contenant une composition d'expressions booléennes, pour définir si un nœud est visitable ou non. Voici le schéma UML du transient:</p>
	<p><img src="images/uml/image00.png" />&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
</div>



<div class="story">
	<h3><a name="affectations-impl"></a>5.3 Affectations</h3>
	<p>L&rsquo;affectation, est un m&eacute;canisme qui permet de changer la valeur d&rsquo;une structure de stockage et de lui affecter une nouvelle. GAL impl&eacute;mente ce m&eacute;canisme &agrave; l&rsquo;aide de quatre m&eacute;ta-classes, que d&rsquo;ailleurs, la plupart on les &agrave; d&eacute;j&agrave; vu pr&eacute;c&eacute;demment. <br />
	</p>
	<p>Ci-dessous le diagramme de classe d&eacute;di&eacute; &agrave; l&rsquo;affectation:<br />
  </p>
	<p></p>
	<p><img src="images/uml/image03.png" />&nbsp;</p>
	<p>&nbsp;</p>
	<p>VarAccess repr&eacute;sente la partie gauche de l&rsquo;affectation, cette m&eacute;ta-classe est r&eacute;sponsable de fournir un acc&egrave;s par r&eacute;f&eacute;rence &agrave; la localisation de la variable dans le but est de mettre &agrave; jour la valeur de celle-ci.<br />
	</p>
	<p>Le cot&eacute; droit de l&rsquo;affectation, est une expression enti&egrave;re, la valeur calcul&eacute;e de celle-ci fait office de la nouvelle valeur &agrave; stocker dans la variable point&eacute;e par VarAccess.<br />
  </p>
	<p>la classe Assignment d&eacute;rive de Action, celle-ci repr&eacute;sente l&rsquo;ensemble des instructions qu&rsquo;on met dans le corps d&rsquo;une transition. Et comme dit dans le chapitre expliquant le langage GAL, le corps des transitions se constitue exclusivement d&rsquo;actions, cet h&eacute;ritage permet de donner la capacit&eacute; de mettre des affectations dans le corps des transitions.<br />
  </p>
	<p>&nbsp;</p>
	<p><strong>Remarque:</strong><br />
  </p>
	<p>Il faut bien faire la distinction entre l&rsquo;initialisation des variables &agrave; la d&eacute;claration et l&rsquo;affectation qui change la valeur d&rsquo;une variable d&eacute;j&agrave; existante. Les d&eacute;clarations se font en dehors de tout corps de transition, et les affectations &agrave; l&rsquo;int&eacute;rieur de ces corps, et essayer d&rsquo;initialiser une variable qui n&rsquo;est pas d&eacute;clarer provoque une erreur, et le plugin GAL vous le signalera.<br />
  </p>
	<p></p>
	<p>&nbsp;</p>
</div>


<div class="story">
	<h3><a name="transitions-impl"></a>5.4 Transitions</h3>
	<p>Une transition n&rsquo;est qu&rsquo;une suite d&rsquo;actions, avec un nom unique dans le syst&egrave;me et prot&eacute;ger par une garde qui doit &ecirc;tre valide pour ex&eacute;cuter la suite d&rsquo;actions. Le mod&egrave;le GAL pour une transition en UML est:<br />
	</p>
    <p><img src="images/uml/image06.png" /></p>
    <p></p>
    <p></p>
    <p>La meta-classe conteneur de la transition est Transition, qui contient une liste d&rsquo;actions de type Actions, celle-ci sera vu plutard, et une garde qui est une expression bool&eacute;enne. <br />
    </p>
    <p>Il faut observer aussi le champ label de la classe Transition qui permet de lebeler une transition.<br />
    </p>
    <p></p>
</div>



<div class="story">
	<h3><a name="actions-impl"></a>5.5 Les actions</h3>
	<p>Si les transitions sont le moteur d&rsquo;un syst&egrave;me GAL, les actions sont la m&eacute;canique de celui-ci. Les actions sont les op&eacute;rations effectu&eacute;es sur les structures de stockage du GAL, cette m&eacute;canique est illustr&eacute;e dans ce diagramme:<br />
	</p>
    <p><img src="images/uml/image07.png" /></p>
    <p>En somme, les actions permises dans un corps d&rsquo;une transition sont: <br />
    </p>
    <p> 1. Les affectations</p>
    <p> 2. Pop</p>
    <p> 3. Push</p>
    <p>Les affectations sont d&eacute;j&agrave; d&eacute;crites dans une section pr&eacute;c&eacute;dente, pour Pop et Push sont des m&eacute;ta-classes pour op&eacute;rateurs de manipulation des listes et ainsi que Peek une m&eacute;ta-classe pour l&rsquo;op&eacute;rateur peek() (cf. le paragraphe: Les op&eacute;rations sur les liste, du chapitre Le langage GAL pour avoir plus d&rsquo;informations sur l&rsquo;utilisation de ces op&eacute;rateurs).<br />
    </p>
    <p>Pop une m&eacute;ta-classe qui est r&eacute;sponsable de l&rsquo;op&eacute;rateur pop(), c&rsquo;est-&agrave;-dire depiler le sommet de la pile d&eacute;finitivement sans le renvoyer.<br />
    </p>
    <p>Push, responsable de l&rsquo;empilement de l&rsquo;expression enti&egrave;re pass&eacute;e en param&egrave;tre de l&rsquo;op&eacute;rateur push(intExpr).<br />
    </p>
    <p>Peek, dont le comportement de son op&eacute;rateur est de renvoyer le sommet de la pile sans modification de la pile, mais cette op&eacute;rateur n&rsquo;est nullement une action qui peut &ecirc;tre invoquer dans un corps d&rsquo;une transition, sauf s&rsquo;il appara&icirc;t dans une expression ent&egrave;re ou bool&eacute;enne (garde ou wrap). Peek est mis dans ce paragraphe pour completer la liste des op&eacute;rateurs sur les listes.<br />
    </p>
    <p>&nbsp;</p>
    <p></p>
</div>



<div class="story">
	<h3><a name="expressions-impl"></a>5.6 Expressions</h3>
	<p>
	
	L&rsquo;une des derni&egrave;res notions que GAL impl&eacute;mente, est l&rsquo;expression, elle est divis&eacute;e en deux parties:	</p>
</div>


<div class="story">
	<h4><a name="arithmetiques-impl"></a>5.6.1 Arithm&eacute;tiques</h4>
	<p>Les expressions arithm&eacute;tiques sont des expressions form&eacute;es avec les op&eacute;rateurs arithm&eacute;tiques usuels.<br />
	</p>
    <p>Le sch&eacute;ma des expressions que GAL impl&eacute;mente:<br />
    </p>
    <p><img src="images/uml/image08.png" /></p>
    <p></p>
    <p></p>
    <p>Le sch&eacute;ma pr&eacute;sente un pattern composite qui impl&eacute;mente les expressions arithm&eacute;tiques et dont l&rsquo;interface principale est IntExpression. Les expr&eacute;ssions arithm&eacute;tiques sont divis&eacute;es en quatres types.<br />
    </p>
    <p>Le premier, sont les constantes, repr&eacute;sent&eacute;es avec la m&eacute;ta-classe Constant, celle-ci contient un champs entier, pour sauvgarder la valeur de la constante.<br />
    </p>
    <p>Le deuxi&egrave;me, c&rsquo;est pour repr&eacute;senter, l&rsquo;operateur peek() des listes, ici, repr&eacute;sent&eacute; avec la classe Peek.<br />
    </p>
    <p>Le troisi&egrave;me c&rsquo;est les expressions arithm&eacute;tiques unaires, telles que le moins unaire ou le compl&eacute;ment bit-&agrave;-bit. Ce type est repr&eacute;sent&eacute; en GAL par deux m&eacute;ta-classes BitComplement et UnaryMinus.<br />
    </p>
    <p>Le dernier, c&rsquo;est les expressions arithm&eacute;tiques binaires, repr&eacute;sent&eacute; avec BinaryIntExpression qui contient un champs de type chaine de caract&egrave;re qui sauvegarde l&rsquo;op&eacute;rateur &agrave; appliquer sur les deux op&eacute;randes.<br />
    </p>
    <p>Voici la liste des op&eacute;rateurs binaires et unaires en GAL, du moins prioritaire au plus prioritaire:<br />
    </p>
    <p></p>
    <p>1. Or bit-&agrave;-bit (|)<br />
    </p>
    <p>2. Xor bit-&agrave;-bit (^) <br />
    </p>
    <p>3. And bit-&agrave;-bit (&amp;)<br />
    </p>
    <p>4. D&eacute;calage bit-&agrave;-bit &agrave; gauche et &agrave; droite (&lt;&lt;, &gt;&gt;)<br />
    </p>
    <p>5. Plus, Moin ( +, -)<br />
    </p>
    <p>6. Multiplication, Modulo, Division (*, %, /)<br />
    </p>
    <p>7. Complement bit-&agrave;-bit (~), Moins unaire (-)<br />
    </p>
    <p>8. Puissance (**)</p>
    <p>&nbsp;</p>
    <p><br />
    </p>
    <p>La gestion de la priorit&eacute; est d&eacute;j&agrave; g&eacute;r&eacute;e par la grammaire Xtext, mais il est hautement recommander d&rsquo;abuser des parenth&egrave;se ne serait-ce que pour la lisibilit&eacute; et la facilit&eacute; de compr&eacute;hension des expressions.<br />
    </p>
    <p>&nbsp;</p>
    <p></p>
</div>


<div class="story">
	<h5><a name="wrapper-impl"></a>5.6.1.1 Wrapper</h5>
	<p>Dans le chapitre pr&eacute;c&eacute;dent, il &eacute;tait mentionn&eacute; que le wrap sert &agrave; m&eacute;langer les expressions arithm&eacute;tiques et bool&eacute;ennes. Et en fonction de la valeur finale de l&rsquo;expression bool&eacute;ennes,  est interpret&eacute;e 0 ou 1.<br />
	</p>
    <p>Ce m&eacute;canisme est impl&eacute;ment&eacute; en GAL gr&acirc;ce &agrave; la m&eacute;ta-classe WrapBoolExpr qui h&eacute;rite d&rsquo;IntExpression pour la capacit&eacute; &agrave; l&rsquo;expression bool&eacute;enne (pr&eacute;sent&eacute;e dans le diagramme par la composition de BooleanExpression) de se transformer en un terme d&rsquo;expression arithm&eacute;tique, en l&rsquo;occurrence 0 ou 1.<br />
    </p>
    <p>&nbsp;</p>
    <p></p>
</div>

<div class="story">
	<h4><a name="expr-bool-impl"></a>5.6.2 Expressions booléennes</h4>
	<p>
	
	Les expressions bool&eacute;ennes sont g&eacute;r&eacute;es selon le m&ecirc;me patterne que les expressions arithm&eacute;tiques, voici le diagramme de classe de telles expressions:	</p>
	<p><img src="images/uml/image09.png" />&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>Ci-dessus sont pr&eacute;sent&eacute;es les m&eacute;ta-classes qui impl&eacute;mentent les expressions bool&eacute;ennes. L&rsquo;&eacute;num&eacute;ration en bas du diagramme liste tous les op&eacute;rateurs de comparaisons de deux expressions enti&egrave;res que la m&eacute;ta-classe Comparison utilise pour le choix de l&rsquo;op&eacute;rateur &agrave; appliquer.</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
</div>


<div class="story">
	<h4><a name="diagramme-complet"></a>5.6.3 Diagramme complet</h4>
	<p>
	
	&lt;&lt;Le diagramme complet du GAL&gt;&gt; <br />
	<a href="galImgaes/GalDiagramme.png"><img  border="0"  src="galImgaes/GalDiagramme_resized.png" /></a>
		</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
</div>

<div class="story">
	<h2><a name="generation-de-code"></a>6 G&eacute;n&eacute;ration de code </h2>
	<p>Le GAL est fourni avec un g&eacute;n&eacute;rateur de code vers Java. Ce chapitre donne un aper&ccedil;u de la fa&ccedil;on dont les syst&egrave;mes qui sont &eacute;crits en GAL seront compil&eacute;s vers Java, ainsi que des d&eacute;tails sur les interfaces respect&eacute;es par ce m&ecirc;me compilateur, afin d&rsquo;avoir tous les outils qui permettent de manipuler les automates traduits.<br />
	</p>
    <p></p>
</div>


<div class="story">
	<h3><a name="compil-vers-java"></a>6.1 Compilation vers Java </h3>
	<p>Une fois le syst&egrave;me &eacute;crit et valider par le plugin Eclipse GAL, le compilateur int&eacute;gr&eacute; &agrave; ce plugin, g&eacute;n&egrave;re automatiquement le code Java correspondant. Voici la hi&eacute;rarchie des interfaces et les classes produites par le compilateur:<br />
	</p>
    <p></p>
    <p>&lt;&lt;&lt;&lt;&lt;&lt;&lt; image de la hi&eacute;rarchie &gt;&gt;&gt;&gt;&gt;&gt;&gt;</p>
    <p></p>
</div>


<div class="story">
	<h3><a name="compilateur"></a>6.2 Le compilateur</h3>
	<p>Le compilateur g&eacute;n&egrave;re pour chaque fichier GAL, les interfaces: IGAL, IState et ITransition dans un package nomm&eacute; interfaces.<br />
	</p>
    <p>une impl&eacute;mentation de l&rsquo;interface IState est g&eacute;n&eacute;r&eacute;e dans un package nomm&eacute; environnement, dans celui-ci, le compilateur g&eacute;n&egrave;re la classe WrapBool qui permet de simuler le m&eacute;lange des expressions arithm&eacute;tiques et bool&eacute;ennes dans une m&ecirc;me expression, ce qui est une chose impossible en Java.<br />
    </p>
    <p>Un autre package cr&eacute;&eacute;s sous le nom de systems, dans lequel, il cr&eacute;e un sous package transisions qui contient toutes les les classes repr&eacute;sentant les transitions du syst&egrave;me et qui impl&eacute;ment l&rsquo;interface ITransition. Dans le package systems est g&eacute;n&eacute;rer la classe qui contient tout le syst&egrave;me impl&eacute;mentant l&rsquo;interface IGAL.<br />
    </p>
    <p>&nbsp;</p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
    <p></p>
</div>

<div class="story">
	<h4><a name="api-compilateur"></a>6.2.1 API du compilateur</h4>
	
</div>

<div class="story">
	<h5><a name="api-compilateur"></a>6.2.1.1 L'interface IGAL</h5>
	<p>
	L&rsquo;interface IGAL repr&eacute;sente un syst&egrave;me GAL, chaque syst&egrave;me doit impl&eacute;menter cette interface.	</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&lt;&lt;Diagramme uml de l&rsquo;interface&gt;&gt;</p>
	<p>&nbsp;</p>
	<p>Cette interface d&eacute;clare les m&eacute;thodes essentielles pour la manipulation d&rsquo;un syst&egrave;me &agrave; transitions.<br />
	</p>
    <p>La m&eacute;thode getName() renvoie le nom du syst&egrave;me, getInitState() retourne l&rsquo;&eacute;tat initial du syst&egrave;me, getTransitions() revoie toutes les transitions d&eacute;clarer dans le syst&egrave;me en forme d&rsquo;une liste, et getTransient(IState entryState) renvoie la valeur bool&eacute;enne du transient (cf. Transient dans le chapitre 1)<br />
    </p>
    <p></p>
</div>


<div class="story">
	<h5><a name="interface-istate"></a>6.2.1.2 L'interface IState</h5>
	<p>
	
	L&rsquo;interface IState, repr&eacute;sente l&rsquo;&eacute;tat du syst&egrave;me, voici le sch&eacute;ma UML de cette interface:</p>
	<p>&lt;&lt;sch&eacute;ma&gt;&gt;</p>
	<p><br />
	</p>
    <p>&nbsp;</p>
    <p>IState d&eacute;clare des m&eacute;thode de statistiques pour comptabiliser le nombre de  chaque  type de structures de sauvegarde (variables, tableaux et listes). <br />
    </p>
    <p>IState est fournie avec une API de manipulation de ces structure, voici le panoram de cette API:</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>Pour les variables, les m&eacute;thodes addVariable(String varName, Integer value), setVariable(String varName, Integer value) et Integer getVariable(String varName) permettent repectivement d&rsquo;ajouter une nouvelle variables &agrave; l&rsquo;&eacute;tat courant, modifier la valeur d&rsquo;une variable d&eacute;j&agrave; existante et obtenir la valeur d&rsquo;une variable de l&rsquo;&eacute;tat.</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>Pour les tableaux, <code>createArray(String, List&lt;Integer&gt; initValue)</code> permet la cr&eacute;ation d&rsquo;un vecteur avec des valeurs d&rsquo;initialisation de chacunes des cases.</p>
    <p> Et pour modifier une valeur dans un vecteur, la m&eacute;thode <code>setValueInArray(String arrayName, int indexOfvalue, Integer value)</code> est utilis&eacute;e, et la case du tableau &agrave; <code>indexOfValue</code> prend la valeur <code>value</code>. <br />
	<code>Integer getValueInArray(String arrayName, int indexOfValue)</code> renvoie la valeur stock&eacute;e dans la case dont l&rsquo;indice est <code>indexOfValue</code>. Et pour obtenir la taille du tableau, <code>IState</code> offre la m&eacute;thode <code>int getSizeOfArray(String arrayName)</code>.</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>Pour les listes (g&eacute;rer comme des pile [LIFO]), les m&eacute;thodes createList(String listName, List&lt;Integer&gt; initValue), popInList(String listName), Integer peekInList(String listName), pushInList(String listName, Integer valueToPush) et getSizeOfList(String listName) permettent respectivement de cr&eacute;er une nouvelle liste avec des valeurs initiales, de retirer le sommet de la pile sans le renvoyer (cf. pop() dans le chapitre 1), de renvoyer le sommet de la pile sans l&rsquo;extraire, d&rsquo;ajouter un nouvel &eacute;l&eacute;ment sur la pile et finalement avoir le nombre d&rsquo;&eacute;l&eacute;ments dans la pile.<br />
    </p>
    <p>&nbsp;</p>
    <p></p>
    <p>&nbsp;	</p>
</div>



<div class="story">
	<h5><a name="interface-itransition"></a>6.2.1.3 L'interface ITransition</h5>
	<p>L&rsquo;interface ITransition offre trois m&eacute;thodes, getName() qui renvoie le nom de la transition, boolean guard(final IState entryState) renvoie la valeur de la garde de la transition courante en fonction de l&rsquo;&eacute;tat donn&eacute; en param&egrave;tre et IStat successor(final IStat entryState) qui calcule et renvoie un nouvel &eacute;tat du syst&egrave;me. Voici le diagramme UML de cette interface:</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&lt;&lt;diagramme UML&gt;&gt;<br />
    </p>
    <p>&nbsp;</p>
    <p></p>
</div>


<div class="story">
	<h4><a name="impl-compil"></a>6.2.2 Impl&eacute;mentation du compilateur</h4>
	<p>Le g&eacute;n&eacute;rateur de code est &eacute;crit avec la biblioth&egrave;que Xtend, qui interagit facilement avec Xtext et le mod&egrave;le qui celui-ci g&eacute;n&egrave;re.<br />
	</p>
    <p>Les codes se trouvent dans le package fr.lip6.move.gal/src/fr/lip6/move/generator. Celui-ci contient quatre fichiers qui permettent de g&eacute;n&eacute;rer du code Java. Le fichier GalEnvGenerator.xtend g&eacute;n&egrave;re les classes State et WrapBool dans le sous-package environment. GalInterafacesGenerator.xtend g&eacute;n&egrave;re toutes les interfaces pr&eacute;sent&eacute;es dans le paragraphe pr&eacute;c&eacute;dant dans le sous-package interfaces. Le GalGenerator.xtend parcours tout le syst&egrave;me GAL et g&eacute;n&egrave;re dans le package systems la classe qui h&eacute;rite de IGAL et contenant touts le syst&egrave;me et dans le sous package transitions toutes les classes repr&eacute;sentant toutes les transitions du syt&egrave;me GAL. Le fichier GalGeneratorUtils.xtend contient des m&eacute;thodes static qui pemettent de traduire les expressions arithm&eacute;tiques et bool&eacute;ennes du GAL vers les expressions arithm&eacute;tiques et bool&eacute;ennes Java.</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
</div>




