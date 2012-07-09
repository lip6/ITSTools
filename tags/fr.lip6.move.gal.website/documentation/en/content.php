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
    <b>Welcome to the documentation of GAL </b>
    <p>You will find here the official documentation of the GAL language, all its specifications, and help on the installation and usage.</p>
    <p>&nbsp;</p>
    <p><strong>Contents</strong></p>
    <div id="sommaire">
	
	<?php
	TableOfContents(__FILE__, 5); 
	?>
	</div>
	
	</span>
</div>
  <div class="story">
  <br /><br />
    <h2><a name="introduction"></a>1. Introduction</h2>
	
    <p>&nbsp;</p>
    <p>This project comes as part of the PSTL, made in the MoVe team (Modeling and Verification) Laboratory of Computer Science at the University Pierre et Marie Curie (LIP6), in the year 2012. The aim of the internship is the realization of an Eclipse plugin that will be an editor for the GAL language. This plugin has all the power of Eclipse, including auto-completion, or automatic correction ..</p>
    <p>To achieve the goals, we used Eclipse and Xtext (http://www.eclipse.org/Xtext/), an Eclipse plugin that allows you to define grammars for languages dedicated to a specific domain (Domain Specific language) in all its aspects, and this in a comprehensive manner.</p>
    <p>The GAL plugin was developed using collaborative development tools, such as version control SVN (available at https://srcdev.lip6.fr/svn/research/thierry/PSTL/GAL), the server of continuous integration (TeamCity).</p>
  </div>
  
<div class="story">
    <p>&nbsp;</p>
    <p><br />
    </p>
    <h2><a name="installation"></a>2. Installation </h2>
    <p><br />
  </p>
    <p>As mentioned, GAL is a plugin for the Eclipse IDE. It therefore requires a local installation of Eclipse on your computer and a Java virtual machine installed.</p>
  <p>If this is not yet done so,<a href="http://www.eclipse.org/downloads/" target="_blank"> download Eclipse here</a>, and here the <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html" target="_blank">Java JDK (6+)</a>. </p>
    <p>&nbsp;</p>
    <p>To use GAL with Eclipse, do the following  : </p>
    <p>1) Start Eclipse
    </p>
    <p>2) Open the  <strong>Help</strong> menu ---&gt; <strong>Install new software</strong>...    </p>
    <p>3) Click <strong>Add...</strong> </p>
    <p>4) In the second text box  (<strong>Location</strong>), paste this URL:    </p>
    <p><strong>http://teamcity-systeme.lip6.fr/guestAuth/repository/download/bt108/.lastSuccessful/update-site</strong></p>
    <p>Then validate . </p>
    <p>You will then see this window  : </p>
    <p align="center"><img src="images/captures/Capture-Install .png" width="760" height="624" /></p>
    <p>Select &ldquo;<strong>GAL Xtext based feature</strong>&rdquo; and click <strong>Next</strong>, then <strong>Next</strong> again.<br />
  Then click  &ldquo;<strong>I accept the terms of the license agreement</strong>&rdquo;, and click <strong>Finish</strong>.    </p>
    <p>Eclipse will proceed to download the plugin on your computer. When the installation is finished, you will be asked to restart Eclipse. Accept, by clicking on <strong>Restart</strong>.</p>
    <p>Upon restart, you can create a new GAL project like this: File &rarr; New &rarr; Project &rarr; General &rarr; Project...</p>
    <p>In the project,you have to simply create a file with the extension .gal, and so you can program in GAL language.</p>
</div>


<div class="story">
    <p>&nbsp;</p>
    <p><br />
    </p>
    <h2><a name="apercu"></a>3. Overview </h2>
    <p>&nbsp;</p>
    <p>Here is an example of a system written in GAL:  </p>
    <?php 
		printGalFile("galfiles/sample-1.gal");
	?>


	
    <p>This code shows the elements of an imperative language, such as variable declarations and other assignments, and subtleties that are specific to the descriptions of concurrent systems.</p>
    <p>The focus was to define a simple language, but easy to define competing systems, formalized here as &quot;transitions&quot;. They are similar to the transitions of automata or Petri nets.</p>
    <p>Later, we will report mechanisms of GAL language, <strong>and then we will reveal the implementation details of the language (TODO?).</strong></p>
    <p></p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
</div>


<div class="story">
	<h2><a name="le-langage-gal"></a>4. The GAL language </h2>
	<p>This section describes the language GAL and semantic concepts it offers.</p>
</div>

<div class="story">
	<h3><a name="what-is-gal"></a>4.1 What is GAL ? </h3>
	<p>GAL is an acronym for <strong>Guarded Action Language,</strong>          a programming language dedicated to the formal verification of concurrent systems. </p>
</div>

<div class="story">
	<h3><a name="utilite-gal"></a>4.2 What is its purpose ? </h3>
	<p>&nbsp;</p>
	<p>GAL is a semantic assembler suited to formal verification, using symbolic methods. So it's a DSL that allows tracking of automata, with a high level of abstraction (manipulation of integer expressions, integers, etc...). It is also used for modeling concurrent systems for verification by model-checking, and can be used for handling set of states using decision diagrams (<a href="http://ddd.lip6.fr">ddd.lip6.fr</a>) </p>
	<p>&nbsp;</p>
</div>
<div class="story">
	<h3><a name="concepts-gal"></a>4.3 GAL concepts  </h3>
</div>


<div class="story">	
	<h4><a name="fichiers-gal" ></a>4.3.1  GAL files</h4>
	<br />
	
	<p>GAL files are simple files with the extension .gal . For example, foo.gal is a valid GAL filename. </p>
	<p>&nbsp;</p>

</div>

<div class="story">	
	<h4><a name="systeme-gal" ></a>4.3.2  Systems in GAL</h4>
	
	
	<p>A GAL system is characterized by a name, and contains a sequence of instructions that will be detailed throughout this document. </p>
	<p> Once a GAL file created and opened, all instructions (see following sections) of the new system must be placed in a block structured. So we start to write the keyword <span class="galElement">GAL</span> (caps), obligatory followed by the system name. The name must start with a letter of the alphabet, and not a number or other symbol. A good practice is to give meaningful names to the systems created.</p>
	<p>Once the system name writing is done, it is followed by a block (brace - closing brace), which contains any other statements that the GAL language offers.</p>
	<p>Here's what it looks like plain, a declaration of a GAL system whose name is  <em>foo</em>, and saved to a file under the name<em> foo.gal</em>:</p>
	<p>
	  <?php
		printGalFile("galfiles/sample-2.gal");
	?>
</p>
	<p>&nbsp;    </p>
</div>

<div class="story">	
	<h4><a name="declaration-variables" ></a>4.3.3 Variables declarations </h4>
	
	
	
	
	<p>One of the basic concepts of  GAL is  variables declarations. The variables manipulated in GAL are integer, there are no other types as we can see  in other general-purpose languages like C or Java.</p>
	<p>The previous section showed how to introduce a new system. In this section, we describe how variables are declared.</p>
	<p>Introducing a new variable in GAL is done with the keyword <span class="galElement">int</span> followed by the variable name starting with a letter -- the name must be unique, so they are not assigned to another entity of the program. This is then followed by the &quot;=&quot; symbol, then an initial value (integer) to this variable. The declaration ends with a semicolon.<br />
  </p>
    <p>In GAL, any declared variable must be initialized.</p>
    <p>&nbsp;</p>
    <p>Below is an example of a system with two GAL variable declarations :</p>
	
	<?php
	printGalFile('galfiles/sample-3.gal');
	?>
   <p>&nbsp;
   </p> 
</div>
	

<div class="story">	
	<h4><a name="declaration-tableaux" ></a>4.3.4 Arrays declarations </h4>
	<br />
	<p>Another form of declaration is those of arrays, they can declare multiple variables in one statement and thus offer an indexed access to variables. As for simple variable, initializing each entry in the array is required.</p>
	
    <p>This is how we proceed : we write the keyword <span class="galElement">array</span> followed by the array size in square brackets, then the table name, and the &quot;=&quot; sign, then a list of values separated by commas, and surrounded with parentheses ending instruction with a semicolon. </p>
    <p>Here an example of a system with a declaration of an array:    </p>
	<?php
	printGalFile('galfiles/sample-4.gal');
	
	?>
	<p>&nbsp;</p>
</div>

<div class="story">	
	<h4><a name="declaration-listes" ></a>4.3.5  Lists declarations </h4>
	<br />
	<p>The lists provide a structure based on the principle of LIFO (Last In, First Out). Unlike variables and arrays, initialization lists is not obligatory, however they can be given initial values. Access to stored values in the lists is done by stacking and unstacking the top of the stack (see action on the lists)</p>
    <p>We declare a list with the keyword <span class="galElement">list</span> followed by the name of the list, and possibly followed by an equal sign and then a list of comma-separated values between two brackets. Like other statements, it ends with a semicolon..</p>
    <p>Below is an example of a system declaring two lists, one initialized, the other not:</p>
	<?php
		printGalFile('galfiles/sample-5.gal');
	?>
	<p>&nbsp;</p>
</div>	
	

<div class="story">
	<h4><a name="transition" ></a>4.3.6 Transition  </h4><br />
	<p>This block is used to move from one state to another state, according to a guard, which is a boolean expression. Depending on its value, it changes state by executing (see actions) written in the body of the transition.</p>
    <p>We write a transition with the keyword <span class="galElement">transition</span>, followed by a nale beginning with a letter, the a guard (boolean expression) in brackets. We can possibly follow it with a label with the keyword  <span class="galElement">label</span>. Then, we write in a block of curly braces, the body of this transition.</p>
    <p>An example with two transitions of a system, the first with a label:</p>
	<?php
		printGalFile('galfiles/sample-6.gal');
	?>
</div>	

<div class="story">
	<h4><a name="action" ></a>4.3.7 Actions </h4>
	<p>Actions are operations applied to the system variables, consisting exclusively of assignments (on variables or array elements), or action (removal or addition) on lists: <span class="galElement">pop()</span> and <span class="galElement">push()</span> (see paragraph on Actions).</p>

</div>

<div class="story">	
	<h5><a name="op-arith"></a> a) Arithmetic operations </h5>
	
	<p align="center">
		<strong>Binary</strong>	
	</p>
	<table width="200" border="1" align="center" style="border-collapse:collapse">
      <tr>
        <th scope="col">Operation</th>
        <th scope="col">Operator</th>
      </tr>
      <tr align="center">
        <td >bitwise OR</td>
        <td>|</td>
      </tr>
      <tr align="center">
        <td>bitwise XOR  </td>
        <td>^</td>
      </tr>
      <tr align="center">
        <td>bitwise AND</td>
        <td>&amp;</td>
      </tr>
      <tr align="center">
        <td>Left shift </td>
        <td>&lt;&lt;</td>
      </tr>
      <tr align="center">
        <td>Right shift </td>
        <td>&gt;&gt;</td>
      </tr>
      <tr align="center">
        <td>Addition</td>
        <td>+</td>
      </tr>
      <tr align="center">
        <td>Subtraction</td>
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
        <td>Power</td>
        <td>**</td>
      </tr>
    </table>
	<p>	</p>
	
	
	
	<p align="center">
		<strong>Unary</strong>	
	</p>
	<table width="200" border="1" align="center" style="border-collapse:collapse">
      <tr>
        <th scope="col">Operation</th>
        <th scope="col">Operator</th>
      </tr>
      <tr align="center">
        <td >Unary minus </td>
        <td>-</td>
      </tr>
      <tr align="center">
        <td>Bitwise complement </td>
        <td>~</td>
      </tr>
    </table>
	<p>	</p>
</div>


<div class="story">
	<h5><a name="expr-bool"></a>b) Boolean expressions</h5>
	<p>Boolean expressions are allowed in guards of transitions. It is also possible to write arithmetic expressions, with boolean appearance (as in C), which will be worth 1 or 0 depending on whether they are true or false (see Wrapper)</p>
    <p>The basic expressions are <span class="galElement">True</span> for &laquo;&nbsp;true&nbsp;&raquo; and <span class="galElement">False</span> for &laquo;&nbsp;false&nbsp;&raquo;. The usual boolean operators are present in GAL, such as  OR ( || ), AND ( &amp;&amp; ) and NOT( ! ). <br />
  </p>
    <p>Another case of expression is the comparison, it takes two integer expressions and compares them with the comparison operators which are:</p>
    <table width="200" border="1" style="border-collapse:collapse" align="center">
      <tr align="center">
        <th scope="col">Operation</th>
        <th scope="col">Operator</th>
      </tr>
      <tr align="center">
        <td>Greater than </td>
        <td>&gt;</td>
      </tr>
      <tr align="center">
        <td>Lower than  </td>
        <td>&lt;</td>
      </tr>
      <tr align="center">
        <td>Greater or equal </td>
        <td>&gt;=</td>
      </tr>
      <tr align="center">
        <td>Lower or equal </td>
        <td>&lt;=</td>
      </tr>
      <tr align="center">
        <td>Equals</td>
        <td>==</td>
      </tr>
      <tr align="center">
        <td>Not equal </td>
        <td>!=</td>
      </tr>
    </table>
    <p>&nbsp;</p>
</div>


<div class="story">
	<h5><a name="wrapper"></a>  c) Wrapper of boolean expressions </h5>
	<p> <br />
	</p>
    <p>Mix arithmetic expressions and Boolean expressions in a single arithmetic expression is possible in GAL, by surrounding the Boolean expression with parentheses, thus allowing to see the Boolean expression to be 0 in case its evaluation is false, otherwise 1. This technique allows us to obtain a portion of the power of expressions of C language and have a fairly similar expressiveness.</p>
    <p>
	<code>Example&nbsp;: myVariable = (a == 0) * 100&nbsp;;<i>//myVariable is 100 or 0</i></code> </p>
    <p><br />
    </p>
</div>

<div class="story">
	<h5><a name="op-listes"></a>d) Operations on lists </h5>
	<p>As mentioned before, the lists are based on the LIFO principle, therefore GAL provides operations for manipulating that structure. Here are the operations applied on lists:</p>
	
    <p>.<span class="galElement">push</span>(<em>integer expression </em>): can stack an integer value on the stack </p>

    <p>.<span class="galElement">peek()</span>: returns the top of the stack without extracting. You must also be careful that, by the invocation of this operation, the list is not empty.</p>
    <p>.<span class="galElement">pop()</span>: same as <code>peek()</code>, only that this operation extracts the top of the stack without returning it. </p>
    <p>Below is an example of a system that offers a small panorama of the majority of concepts related to the GAL language:</p>
    <?php
		printGalFile('galfiles/sample-7.gal');
	?>
</div>	


<div class="story">	
	<h4><a name="transient" ></a>4.3.8  Transient</h4>
	<p><br />
	</p>
    <p><span class="galElement">TRANSIENT</span> is a keyword that, by assigning a boolean expression, allows (depending on its value) to examine the nodes (transitions) or ignore them..</p>

    <p>A such expression is declared with the keyword <span class="galElement">TRANSIENT</span>, followed by the assignment sign =, followed then by a boolean expression.</p>

    <p>Here is a system that summarizes all the features of GAL:</p>
    <p>
      <?php
	printGalFile('galfiles/sample-8.gal');
	?>
</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;    </p>
</div>

<div class="story">	
	<h3><a name="fonctionnalites-editeur" ></a>4.4  GAL Editor features</h3>
	<p>The Eclipse plugin GAL inherits the editing features of the Eclipse IDE and this facilitates writing of concurrent systems.</p>
	<p><img src="images/captures/1.png" width="1030" height="702" /></p>
</div>


<div class="story">	
	<h4><a name="autocompletion" ></a>4.4.1  Auto-completion</h4>
	<p>One of the interesting features of Eclipse is the best-known auto-completion. By pressing CTRL + SPACE,  GAL provides a list of elements may complement the word according to the characters written, or otherwise the elements that can be placed where the cursor is located.</p>
	<p>An example of autocompletion proposed:</p>
	<p>&nbsp;</p>
    <p align="center">
  <img src="images/captures/autocomplete-1.png" /></p>
    <p></p>
    <p></p>
</div>


<div class="story"></div>

