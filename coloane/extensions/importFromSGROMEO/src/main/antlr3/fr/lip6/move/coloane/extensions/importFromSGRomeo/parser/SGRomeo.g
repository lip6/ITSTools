grammar SGRomeo;

@lexer::header {
package main.antlr3.fr.lip6.move.coloane.extensions.importFromSGRomeo.parser;

}

@parser::header {
package main.antlr3.fr.lip6.move.coloane.extensions.importFromSGRomeo.parser;

import fr.lip6.move.coloane.core.model.factory.GraphModelFactory;
import fr.lip6.move.coloane.interfaces.exceptions.ModelException;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.interfaces.formalism.IFormalism;
import fr.lip6.move.coloane.interfaces.formalism.INodeFormalism;
import fr.lip6.move.coloane.interfaces.formalism.IArcFormalism;


import java.util.HashMap;
import java.util.Map;
}

@parser::members {
       private static IFormalism formalism;
       private static INodeFormalism stateFormalism;
       private static IArcFormalism eventFormalism;

       private IGraph graph;
       private Map<String,INode> nodes = new HashMap<String, INode>();
       
       private INode getNode (String nodeId) {
            INode node = nodes.get(nodeId);
            if (node == null) {
              try {
                 node = graph.createNode(stateFormalism);
                 nodes.put(nodeId, node);
              } catch (ModelException e) {
                 e.printStackTrace();
              }
            }
            return node;
       }
       
       public void setFormalism(IFormalism formalism) {
           this.formalism = formalism;
           this.stateFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("state");
           this.eventFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("event");
           
           graph = new GraphModelFactory().createGraph(formalism);
       }
}


romeoSGModel returns [IGraph graph] : (sep|arc)* state* 
{
   graph = this.graph;
};

arc : idSrc=VARIABLE PREARROW trans=label POSTARROW idDest=VARIABLE 
{
  INode src = getNode($idSrc.getText()); 
  INode dest = getNode($idDest.getText()); 
        try {
        IArc arc = graph.createArc(eventFormalism,src,dest);
                arc.getAttribute("label").setValue(trans);  
        } catch (ModelException e) {
        e.printStackTrace();
          }
};

sep : LINESEPARATOR  ;

state scope { StringBuffer sb; } : 
{ 
  $state::sb = new StringBuffer();
}
(
sep id=VARIABLE sep 
'Marking'  { $state::sb.append("Marking \n");}
placeMarking* 
'Firing Domain' { $state::sb.append("Firing Domain \n");}
clockDomain* clockConstraint* 
sep
)
{
  INode node = getNode($id.getText());
  node.getAttribute("value").setValue($state::sb.toString());
  $state::sb=null;
}
;

placeMarking : mark=ENTIER '   ' pname=label 
{
  if ( ! "0".equals(mark.getText() )) {
      ($state::sb).append($mark.getText() + "  " + pname + "\n");
  }
}; 

clockDomain : label ' [' ENTIER ',' (ENTIER ']' | 'inf[') { ($state::sb).append($text+"\n"); };

clockConstraint : label MINUS label LESSTHAN ENTIER { ($state::sb).append($text+"\n"); };


label returns [String s=""] : 
  //{ s = ""; } 
  ( 
    (
      var=VARIABLE 
      { s+= $var.getText(); }
     ) 
    |
    ( 
      '-' 
      { s+="_";} 
    )
    |
    ( 
      ' ' 
      { s+="";} 
    )
    |
    (
     val=ENTIER
     {s += $val.getText();}
     )
   )*;


LINESEPARATOR: ('-')+ ('\n' | '\r') ;
PREARROW: ' -- ';
POSTARROW: ' --> ';
MINUS : ' - ';
LESSTHAN : ' <= ';
/****** Basics */
fragment LETTER : 'a'..'z' | 'A'..'Z' | '_'
  ;
fragment DIGIT  : '0'..'9'
  ;
fragment STRING : '"'.*'"'
  ;
// ignore whitespace
WS  : ( '\t' | '\n' | '\r') { $channel = HIDDEN; }
  ;
ENTIER : ('-'?) DIGIT (DIGIT)*
  ;
VARIABLE  : STRING | (LETTER (LETTER | DIGIT)*)
  ;
// LABEL : ' ' ( options{greedy=false;}: .* ) ' ';

// LETTER (LETTER | DIGIT | ' ' | '-')* );
  


