grammar Prod;

@lexer::header {
package main.antlr3.fr.lip6.move.coloane.extensions.importFromPROD.parser;

}

@parser::header {
package main.antlr3.fr.lip6.move.coloane.extensions.importFromPROD.parser;

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
       private IFormalism formalism;
       private INodeFormalism placeFormalism;
       private INodeFormalism transitionFormalism;
       private IArcFormalism arcFormalism;
       private IArcFormalism resetFormalism;
       private IArcFormalism inhibitorFormalism;

       private IGraph graph;
       private Map<String,INode> nodes = new HashMap<String, INode>();
       
       public void setFormalism(IFormalism formalism) {
           this.formalism = formalism;
           this.placeFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("place");
           this.transitionFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("transition");
           this.arcFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("arc");
           this.resetFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("reset");
           this.inhibitorFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("inhibitor");
           
           graph = new GraphModelFactory().createGraph(formalism);
       }
}


prodModel returns [IGraph graph] : node*
{
   graph = this.graph;
};

node : place|transition;


place : lvl=placeLevel pname=VARIABLE mk=initMarquage 
      {
             try {
                INode node = graph.createNode(placeFormalism);
                node.getAttribute("name").setValue($pname.getText());
                // add for later reference by name
                nodes.put($pname.getText(), node);
                if (mk != 0)
                  node.getAttribute("marking").setValue(Integer.toString(mk));
                
                node.getAttribute("component").setValue(Integer.toString(lvl));
                
              } catch (ModelException e) {                
                e.printStackTrace();
              }       
       }
      ;

placeLevel returns [int lvl]: 
  PLACE '(' level=ENTIER ')' 
  { lvl = Integer.parseInt($level.text); }
  | PLACE 
  { lvl= 0; };

nomtransition  returns [String name] : TRANS n=VARIABLE { name = $n.getText() ;};

transition 
scope { INode idTrans; } 
: tname=nomtransition 
{
       try {
           INode node = graph.createNode(transitionFormalism);
           node.getAttribute("label").setValue(tname);
           $transition::idTrans = node ;
       } catch (ModelException e) {                
                e.printStackTrace();
       }         
} 
entree sortie ENDTR

;

entree    : UGLYPREFIX_IN '{' arcin* '}'
    |
    ;

sortie    : UGLYPREFIX_OUT '{' arcout* '}'
  |
  ;

arcin :  
pname=VARIABLE ':' ntok=marquage ';'
{
  // A simple input arc
  INode place = nodes.get($pname.getText());
  INode tr = $transition::idTrans;
  try {
            IArc a = graph.createArc(arcFormalism,place,tr);
            a.getAttribute("valuation").setValue(Integer.toString(ntok));
  } catch (ModelException e) {
            e.printStackTrace();
  }

}
| pname=VARIABLE ':' UGLYPREFIX_RESET ';'
{
  // A reset arc
    
  INode place = nodes.get($pname.getText());
  INode tr = $transition::idTrans;
  try {
            IArc a = graph.createArc(resetFormalism,place,tr);
  } catch (ModelException e) {
            e.printStackTrace();
  }

}
    | pname=VARIABLE UGLYPREFIX_INHIBITOR ntok2=ENTIER ';'
{
 // A inhibitor arc
  INode place = nodes.get($pname.getText());
  INode tr = $transition::idTrans;
  try {
            IArc a = graph.createArc(inhibitorFormalism,place,tr);
            a.getAttribute("valuation").setValue($ntok2.getText());
  } catch (ModelException e) {
            e.printStackTrace();
  }


};

arcout : 
pname=VARIABLE ':' ntok=marquage ';'
{
  // A simple input arc
  INode place = nodes.get($pname.getText());
  INode tr = $transition::idTrans;
  try {
            IArc a = graph.createArc(arcFormalism,tr,place);
            a.getAttribute("valuation").setValue(Integer.toString(ntok));
  } catch (ModelException e) {
            e.printStackTrace();
  }

};

initMarquage returns [int mark]:
  MK '(' n=marquage ')'
  {
    mark = n;
  }
  |
  {
    mark = 0;
  };

marquage  returns [int mark]: 
     TOKEN  
    { mark = 1; }    
    | n=ENTIER TOKEN 
    { mark = Integer.parseInt($n.getText()); }    
    ;

TOKEN : '<..>'  ;

UGLYPREFIX_INHIBITOR : '<'  ;
TRANS : '#trans';
PLACE : '#place';
ENDTR : '#endtr';
UGLYPREFIX_RESET : 'RESET';
UGLYPREFIX_IN : 'in';
UGLYPREFIX_OUT: 'out';
MK:'mk';
/****** Basics */
fragment LETTER : 'a'..'z' | 'A'..'Z' | '_'
  ;
// ignore comments
COMMENT : '//'.*'\n' {$channel = HIDDEN; }
  ; 
// ignore whitespace
WS  : (' ' | '\t' | '\n' | '\r') { $channel = HIDDEN; }
  ;
fragment DIGIT  : '0'..'9'
  ;
ENTIER : DIGIT (DIGIT)*
  ;
fragment STRING : '"'.*'"'
  ;
VARIABLE  : STRING | (LETTER (LETTER | DIGIT)*)
  ;
  


