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
    <p>This project comes as part of the PSTL, made in the MoVe team (Modeling and Verification) of Laboratory of Computer Science at the University Pierre et Marie Curie (LIP6), in the year 2012. The aim of the internship is the realization of an Eclipse plugin that will be an editor for the GAL language. This plugin has all the power of Eclipse, including auto-completion, or automatic correction ..</p>
    <p>To achieve the goals, we used Eclipse and Xtext (<a href="http://www.eclipse.org/Xtext/" target="_blank">http://www.eclipse.org/Xtext/</a>), an Eclipse plugin that allows you to define grammars for languages dedicated to a specific domain (<strong>D</strong>omain <strong>S</strong>pecific <strong>L</strong>anguage) in all its aspects, and this in a comprehensive manner.</p>
    <p>The GAL plugin was developed using collaborative development tools, such as version control SVN (available at <a href="https://srcdev.lip6.fr/svn/research/thierry/PSTL/GAL" target="_blank">https://srcdev.lip6.fr/svn/research/thierry/PSTL/GAL</a>), and also the server of continuous integration (TeamCity).</p>
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
    <p>2) Open the  <strong>Help</strong> menu &rarr; <strong>Install new software</strong>...    </p>
    <p>3) Click <strong>Add...</strong> </p>
    <p>4) In the second text box  (<strong>Location</strong>), paste this URL:    </p>
    <p><strong>http://teamcity-systeme.lip6.fr/guestAuth/repository/download/bt108/.lastSuccessful/update-site</strong></p>
    <p>Then validate . </p>
    <p>You will then see this window  : </p>
    <p align="center"><img src="images/captures/Capture-Install .png" width="760" height="624" /></p>
    <p>Select &ldquo;<strong>GAL Xtext based feature</strong>&rdquo; and click <strong>Next</strong>, then <strong>Next</strong> again.<br />
  Then click  &ldquo;<strong>I accept the terms of the license agreement</strong>&rdquo;, and click <strong>Finish</strong>.    </p>
    <p>Eclipse will proceed to download the plugin on your computer. When the installation is finished, you will be asked to restart Eclipse. Accept, by clicking on <strong>Restart</strong>.</p>
  <p>After that, congratulation, GAL is successfully installed in your system. </p>
    <p>&nbsp;</p>
</div>


<div class="story">
    <p>&nbsp;</p>
    <p><br />
    </p>
    <h2><a name="apercu"></a>3. Overview </h2>
    <p>&nbsp;</p>
    <p>When GAL is installed in your Eclipse, you can create a new GAL project like this: <br />
    File &rarr; New &rarr; Project &rarr; Xtext &rarr; Gal Project...</p>
    <p align="center"><img src="images/captures/new_gal_project.png" /></p>
    <p>Click <strong>Next</strong>. You will be prompted to enter the name of your project. Once done, you will see normally a pre-filled file, named <em>ExampleSystem.gal</em> , that contains some simple GAL code. </p>
    <p>Later in the project, you will have to simply create a file with the extension .gal, and so you can program in GAL language.</p>
	</div>
	
	<div class="story">
    <p><h3><a name="package-presentation"></a>3.1 Package presentation</h3></p>
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
	
    <p><span class="galElement">push</span>(<em>listName, integer expression </em>): can stack an integer value on the stack </p>

    <p><span class="galElement">peek(</span><em>listName</em><span class="galElement">)</span>: returns the top of the stack without extracting. You must also be careful that, by the invocation of this operation, the list is not empty.</p>
    <p><span class="galElement">pop(</span><em>listName</em><span class="galElement">)</span>: same as <code>peek()</code>, only that this operation extracts the top of the stack without returning it. </p>
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
	<p><img src="images/captures/1_.png" /></p>
</div>


<div class="story">	
	<h4><a name="autocompletion" ></a>4.4.1  Auto-completion</h4>
	<p>One of the interesting features of Eclipse is the best-known auto-completion. By pressing CTRL + SPACE,  GAL provides a list of elements may complement the word according to the characters written, or otherwise the elements that can be placed where the cursor is located.</p>
	<p>Here an example of autocompletion proposed:</p>
	<p>&nbsp;</p>
    <p align="center">
  <img src="images/captures/autocomplete-1.png" /></p>
    <p></p>
    <p></p>
</div>



<div class="story">
	<h2><a name="execution-gal"></a>5. Running GAL programs </h2>
	<p>&nbsp;</p>
	<p>As in any imperative language, GAL comes with the ability to run, indirectly, any program you have written in GAL. You can also debug your program, for example by setting breakpoints directly into into the GAL editor, and following step by step, the transitions that will be crossed, and also the state of program variables .</p>
	<p>&nbsp;</p>
</div>


<div class="story">
	<h3><a name="execution"></a>5.1 Simple running into the Editor </h3>
	<p>&nbsp;</p>
	<p>As mentioned above, the source files in GAL must be positioned in the src folder of your project. For example in the image below, we created a program MyFirstGAL.gal</p>
	<p align="center"><img src="images/captures/galproject.png" /></p>
	<p>&nbsp;</p>
	<p>When the program is properly saved, the Java source files are automatically generated, to allow you to run the written GAL program. To do this, in the folder src-gen /, there are three packages:</p>
	<ul>
	  <li><strong>gal</strong> : contains the equivalent Java program to your GAL file. </li>
      <li><strong>main</strong> : contains the Java file that will run the Java version of your program. There are several strategies for program execution, that we will see in the sections below. </li>
      <li><strong>transitions.<em>NameOfYourGalSystem</em> </strong>: contains the Java classes representing the transitions written in your GAL.</li>
  </ul>
	<p>&nbsp;</p>
	<p>To &quot;run&quot; yourGAL program, run the <em>NameOfYourGALSystem</em>_Main.java, which is in package <strong>main</strong>.</p>
	<p>&nbsp;</p>
	<p><strong><em>Execution strategies of the program : </em></strong></p>
	<p>The execution of GAL program starts from an initial state, and then tries to cross transitions, depending on whether their guards are true or not. For a given state, there may be several transitions that are passable. So which transition to choose ? That is why there are several modes of GAL program execution, so the user can choose how to behave when it comes to selecting crossable transitions.</p>
	<p>There are many ways (three in total) to run a GAL program :</p>
	<ul>
	  <li><strong>The Random mode </strong> : in this mode, the program that runs, choose to cross randomly a transition, among all those which are crossable. To enable this mode, just add on the command line of the main Java file ( <em>NameOfYourGALSystem</em>_Main.java ), the option <code class="launchmode">--random</code><br />
	  &nbsp;</li>
      <li><strong>The interactive mode </strong> : mode in which the program interacts with the user, allowing him to choose (with the keyboard) a transition to cross, among all those that are passable, until there is no more crossable transitions. <br />
        Enable this mode with the option <code class="launchmode">--keyboard </code><br />
      &nbsp;</li>
      <li><strong>The Trace mode  </strong>: in this mode, you specify to the program, a trace of transitions (supposed to be passable) it must follow. The trace file is a simple text file, containing a number of transition per line. The numbers of transitions are normally ordered according to their appearance in the GAL file, and start at 0. For example if a GAL file contains, in order, transitions t1, t2, t3, then t1 will number 0,  t2 number 1, t3 number 2.<br />
        This mode is activated with the option  <code class="launchmode">--trace <em>fileOfTrace</em></code> <br />
      &nbsp;</li>
      <li><strong>The Store mode  </strong>: Is not really an execution mode, and being optional, it specifies to the program, a file name in which the execution trace traveled will be saved.<br />
      It is specified with the option <code class="launchmode">--store <em>fileOfTraceOutput</em></code></li>
  </ul>
	<p>&nbsp;</p>
	<p>To pass arguments to the program in Eclipse, do a right-click on the file  <em>YourGalSystem</em>_Main.java which is in the <strong>main </strong>package, then do  Run As --&gt; Run configurations...</p>
	<p>In the Arguments tab, fill in the &quot;Program arguments&quot;, the arguments you want to add on the command line of the GAL program (one of the seen above)</p>
	<p>&nbsp;</p>
	<p align="center"><img src="images/captures/Arguments-RunConfigurations.png" /></p>
	<p align="center">&nbsp;</p>
	<p align="center">&nbsp;</p>
</div>

<div class="story">
	<h3><a name="debug-gal"></a>5.2 Debugging in GAL </h3>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>One of the interesting features of the plugin is the possibility to debug the GAL code during its execution. Thus, it becomes possible to see step by step what transition is crossed, which is not, etc. ...</p>
	<p>Here is an overview of debugging in GAL</p>
	<p align="center"><img src="images/captures/debugMyFirstGAL.png" /></p>
	<p>&nbsp;</p>
	<p>To start debugging a GAL program, do the following:</p>
	<p>- Right-click on the file  <em>YourGalSystem</em>_Main.java of  <strong>main</strong> package, then do <strong>Debug As</strong> --&gt; <strong>Java Application</strong><br />
  - You can optionally (but recommended) open the <strong>Debug</strong> perspective, by doing :  <strong>Window</strong> menu --&gt; <strong>Open perspective</strong> --&gt; <strong>Debug</strong> (or <strong>Other</strong> submenu --&gt; <strong>Debug</strong> ) </p>
	<p>It is also possible to specify arguments to the program (which are the options seen above). To do this, right-click the file  <em>YourGalSystem</em>_Main.java from the package  <strong>main</strong>, then  do <strong>Debug As</strong> --&gt; <strong>Debug configuration</strong> , then add it in the Arguments tab. </p>
	<p>&nbsp; </p>
	<p>&nbsp;</p>
</div>



<div class="story">
	<h3><a name="api-gal"></a>5.3 GAL API  </h3>
	<p>&nbsp;</p>
  <p>By default in the plugin, the main program and several other files (classes of transitions, Java class representing the GAL system) are automatically generated each time you save a GAL file, and are located in src-gen/ folder. But it is also possible to create your own Java files to manipulate the system. Thus, you will be able to customize the execution of GAL to your liking, by creating for example your own &quot;main&quot; file.</p>
	<p>However, keep in mind that in order to manipulate a GAL, you must have an instance of this system. For example:</p>
	<pre><code>
	IGAL mySystem = <span class="galElement">new</span> <em>Name_of_your_GAL_system</em>() ; 
	</code></pre>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p class="galInterfaceTitle">IGAL </p>
	<p>IGAL is the interface that describes a GAL system, and everything in it. When you write a GAL file, by default a Java file that implements this interface is automatically generated, in src-gen/gal/<em style="color:#990000">YourGALSystem</em>.java </p>
	<p><strong>Methods of IGAL </strong></p>
	
	
	<table width="688" border="1"  style="border-collapse:collapse;" >
      <tr valign="top" >
        <th width="108" scope="col">Return value </th>
        <th width="564" scope="col">Method name  </th>
      </tr>
      <tr valign="top">
        <td valign="top" ><code>String</code></td>
        <td valign="top"><code>getName()</code><br /><br />
        <span class="codeDescription">Returns the name of the GAL system. </span></td>
      </tr>
      <tr>
        <td valign="top"><code><a href="#desc-istate">IState</a></code></td>
        <td valign="top"><code>getInitState()</code> <br />
        <br />
		<span class="codeDescription"> Returns the initial state of the system. 	 The initial state contains all variables and their values assigned at initialization.</span></td>
      </tr>
      <tr>
        <td valign="top"><code>List&lt;<a href="#desc-itransition">ITransition</a>&gt;</code></td>
        <td valign="top"><code>getTransitions() </code><br />
        <br />
		<span class="codeDescription">Returns the list of all transitions of the GAL system</span>		</td>
      </tr>
	  
	   <tr>
        <td valign="top"><code>boolean</code></td>
        <td valign="top"><code>getTransient(<a href="#desc-istate">IState</a> entryState) </code><br />
        <br />
		<span class="codeDescription">Returns the boolean value of the TRANSIENT statement. <code>entryState</code> is the state in which variables values will be retrieved</span>		</td>
      </tr>
    </table>
	<p>&nbsp;</p>
	<p class="galInterfaceTitle"><a name="desc-itransition"></a>ITransition</p>
	<p>This interface represent a transition in a GAL system.</p>
	<p><strong>Methods of ITransition </strong></p>
	<table width="688" border="1"  style="border-collapse:collapse;" >
      <tr valign="top" >
        <th width="108" scope="col">Return value </th>
        <th width="564" scope="col">Method name </th>
      </tr>
      <tr valign="top">
        <td valign="top" ><code>String</code></td>
        <td valign="top"><code>getName()</code><br />
            <br />
            <span class="codeDescription">Returns the name of the transition. </span></td>
      </tr>
      <tr>
        <td valign="top"><code>boolean</code></td>
        <td valign="top"><code>guard(<a href="#desc-istate">IState</a> entryState)</code> <br />
            <br />
        <span class="codeDescription"> Returns the boolean value of a transition guard.. <code>entryState</code> is the state in which variables values will be retrieved. </span></td>
      </tr>
      <tr>
        <td valign="top"><code>IState</code></td>
        <td valign="top"><code>successor(<a href="#desc-istate">IState</a> entryState) </code><br />
            <br />
        <span class="codeDescription">Evaluate the body of the transition, and returns a state in which variables have been 	  modified.. <code>entryState</code> is </span> the state in which variables values will be retrived</td>
      </tr>
      
    </table>
	<p>&nbsp;</p>
	<p class="galInterfaceTitle"><a name="desc-istate"></a>IState</p>
	<p>This interface represents a &quot;state&quot; in a GAL system.  A state can be seen as a great set, which contains lists, integers variables, and arrays,    and their associated values, at a precise time.</p>
	<p><strong>Methods of IState </strong></p>
	<table width="688" border="1"  style="border-collapse:collapse;" >
      <tr valign="top" >
        <th width="108" scope="col">Return value </th>
        <th width="564" scope="col">Method name </th>
      </tr>
      <tr valign="top">
        <td valign="top" ><code>int</code></td>
        <td valign="top"><code>getNumberOfVariables()</code><br />
            <br />
            <span class="codeDescription"> Returns the number of variables in the system. </span></td>
      </tr>
      <tr>
        <td valign="top"><code>int</code></td>
        <td valign="top"><code>getNumberOfArrays() </code> <br />
            <br />
            <span class="codeDescription"> Returns the number of arrays in the system. </span></td>
      </tr>
      <tr>
        <td valign="top"><code>int</code></td>
        <td valign="top"><code>getNumberOfLists() </code><br />
          <br />
        <span class="codeDescription">Returns the number of lists in the system</span></td>
      </tr>
      <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>addVariable(String varName, Integer value) </code><br />
          <br />
			<span class="codeDescription">Add a variable in the state.</span>        <br />
			<strong>varName</strong> : The name of the new variable<br />
			<strong>value</strong> :   Value of this new variable</td>
      </tr>
	   <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>setVariable(String varName, Integer value) </code><br />
          <br />
			<span class="codeDescription">Sets a value to an existing variable.</span></td>
      </tr>
	  
	   <tr>
        <td valign="top"><code>Integer</code></td>
        <td valign="top"><code>getVariable(String varName) </code><br />
          <br />
			<span class="codeDescription">Returns the value of a variable</span></td>
      </tr>
	  
      <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>createArray(String arrayName, List&lt;Integer&gt;initValues)</code><br />
          <br />
          <span class="codeDescription">Create an array, initialized with each elements of a list of integers. </span></td>
      </tr>
      <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>setValueInArray(String arrayName, int indexOfValue, Integer value)<br />
          <br />
</code>
        <span class="codeDescription">Sets a <em>value</em> to an array element, which is at position <em>indexOfValue</em> in the array <em>arrayName</em>.</span></td>
      </tr>
      <tr>
        <td valign="top"><code>Integer</code></td>
        <td valign="top"><code>getValueInArray(String arrayName, int indexOfValue)<br />
          <br />
</code>
          <span class="codeDescription"></span>Returns the value of an array element</td>
      </tr>
      <tr>
        <td valign="top"><code>int</code></td>
        <td valign="top"><code>getSizeOfArray(String arrayName)<br />
          <br />
</code>
        <span class="codeDescription">Return the size of the array  <em>arrayName</em> </span></td>
      </tr>
      <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>createList(String listName, List&lt;Integer&gt; initValues)<br />
          <br />
</code>
        <span class="codeDescription">Create a list, initialized with each elements of the list </span></td>
      </tr>
      <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>popInList(String listName)<br />
          <br />
        </code>
        <span class="codeDescription">Remove the first element of the list  <em>listName</em> </span></td>
      </tr>
      <tr>
        <td valign="top"><code>Integer</code></td>
        <td valign="top"><code>peekInList(String listName) </code> <br />
          <br />
        <span class="codeDescription">Returns the first element of a list, without removing it </span></td>
      </tr>
      <tr>
        <td valign="top"><code>void</code></td>
        <td valign="top"><code>pushInList(String listName, Integer valueToPush)<br />
          <br />
</code>
          <span class="codeDescription">Push an integer to a list </span></td>
      </tr>
      <tr>
        <td valign="top"><code>Integer</code></td>
        <td valign="top"><code>getValueInList(String listName, int indexOfValue)<br />
          <br />
</code>
        <span class="codeDescription">Returns the value at position <em>indexOfValue</em> in the list </span></td>
      </tr>
      <tr>
        <td valign="top"><code>int </code></td>
        <td valign="top"><code>getSizeOfList(String listName)<br />
          <br />
</code>
        <span class="codeDescription">Returns the size of the list </span></td>
      </tr>
      <tr>
        <td valign="top"><code>Object</code></td>
        <td valign="top"><code>clone()</code><br />
          <br />
        <span class="codeDescription">Clone the list. </span></td>
      </tr>
    </table>
	<p>&nbsp; </p>
</div>


