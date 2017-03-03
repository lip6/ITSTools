grammar Tina;

@lexer::header {
package main.antlr3.fr.lip6.move.coloane.extensions.importExportTINA.importFromTINA.parser;

}

@parser::header {
package main.antlr3.fr.lip6.move.coloane.extensions.importExportTINA.importFromTINA.parser;


import fr.lip6.move.coloane.core.model.factory.GraphModelFactory;
import fr.lip6.move.coloane.interfaces.exceptions.ModelException;
import fr.lip6.move.coloane.interfaces.model.IArc;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.INode;
import fr.lip6.move.coloane.interfaces.model.IAttribute;
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
       private IArcFormalism readFormalism;
       private IArcFormalism inhibitorFormalism;

       private IGraph graph;
       private Map<String,INode> nodes = new HashMap<String, INode>();
       private INode source;
       private INode destination;
       
       public void setFormalism(IFormalism formalism) {
           this.formalism = formalism;
           this.placeFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("place");
           this.transitionFormalism = (INodeFormalism) formalism.getRootGraph().getElementFormalism("transition");
           this.arcFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("arc");
           this.readFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("read");
           this.inhibitorFormalism = (IArcFormalism) formalism.getRootGraph().getElementFormalism("inhibitor");
           
           graph = new GraphModelFactory().createGraph(formalism);
       }
}

tinaModel returns [IGraph graph] : (trdesc|pldesc|lbdesc|prdesc|netdesc)*
{
   graph = this.graph;
};

netdesc : 'net' NAME
{
};

prdesc  : 'pr' 
  (node=tname 
  {}
  )+ ('<'|'>') 
  (node=tname
  {}
  )+ ;


trdesc  
scope { INode idTrans; } 
: 'tr' node=tname 
{
  $trdesc::idTrans = node;
}
(':' label=NAME
{
  $trdesc::idTrans.getAttribute("label").setValue($label.getText());
}
)? (interval)? (tinput '->' toutput)?
;

pldesc 
scope { INode idPlace; } 
: 'pl' node=pname 
{
       $pldesc::idPlace = node;
}
(':' <label>)? 
{
  // labels on places not supported currently.
}
('(' mk=integer ')'
{  
  // marking
  if (mk != 0)
         $pldesc::idPlace.getAttribute("marking").setValue(Integer.toString(mk));
  
}
)?
(pinput '->' poutput)?
{
  // place based arc description not supported yet.
}                
;


interval  : isOpen=('['|']') 
{
  // TODO : complain if open interval
}
eft=ENTIER 
{
  $trdesc::idTrans.getAttribute("earliestFiringTime").setValue($eft.getText());
}
',' 
( (lft=ENTIER 
{
  $trdesc::idTrans.getAttribute("latestFiringTime").setValue($lft.getText());
}
('['|']')
{
  // TODO : complain if open interval
}
)| 'w[' 
{
  $trdesc::idTrans.getAttribute("latestFiringTime").setValue("inf");
}
) ;

lbdesc    : 'lb' nodeName=NAME label=NAME
{
  INode node = nodes.get(nodeName.getText());
  if (node != null) {
    IAttribute lab = node.getAttribute("label");
    if (lab != null) {
      lab.setValue(label.getText());
    }
  }
};

pname returns [INode node] : name=NAME
{
    node = nodes.get($name.getText());
    if (node == null) {
        try {
                node = graph.createNode(placeFormalism);
                node.getAttribute("name").setValue(name.getText());
                // add for later reference by name
                nodes.put(name.getText(), node);                
       }catch (ModelException e) {                
                e.printStackTrace();
       }        
    }
};

tname returns [INode node] : name=NAME
{
    node = nodes.get($name.getText());
    if (node == null) {
        try {
                node = graph.createNode(transitionFormalism);
                node.getAttribute("label").setValue(name.getText());
                // add for later reference by name
                nodes.put(name.getText(), node);                
       }catch (ModelException e) {                
                e.printStackTrace();
       }        
    }
};


tinput : (node=pname 
  {
    this.destination = $trdesc::idTrans;
    this.source = node;    
  }
  arc)*;

toutput : (node=pname 
  {
    this.destination = node;
    this.source = $trdesc::idTrans;
  }
  arc)*;

poutput: (node=tname 
  {
    this.destination = $pldesc::idPlace;
    this.source = node;
  }
  arc)*;

pinput: (node=tname 
  {
    this.destination = node;
    this.source = $pldesc::idPlace;
  }
  arc)*;

arc : type=('*'|'?'|'?-'|'!'|'!-') value=integer  
{
  IArc a = null;
  try {
  if ("*".equals(type.getText())) {
      // normal arc
      a = graph.createArc(arcFormalism,source,destination);
  } else if ("?".equals(type.getText())) {
      // test arc
     a = graph.createArc(readFormalism,source,destination);
  } else if ("?-".equals(type.getText())) {
      // inhibitor arc
     a = graph.createArc(inhibitorFormalism,source,destination);  
  } else if ("!".equals(type.getText())) {
      // stopwatch arc
    // TODO !!
  } else if ("!-".equals(type.getText())) {
      // stopwatch inhibitor arc
    // TODO !!
  }
  }catch (ModelException e) {                
                e.printStackTrace();
  }  
  if (a != null) {
    a.getAttribute("valuation").setValue(Integer.toString(value));
  }
}
| 
{
 try {
  IArc a = graph.createArc(arcFormalism,source,destination);
  a.getAttribute("valuation").setValue("1");
  }catch (ModelException e) {                
                e.printStackTrace();
  }  
}
;

integer  returns [int value]:  
    n=ENTIER 
    { value = Integer.parseInt($n.getText()); }
    ('K'
    { value = 1000 * value; }
    |'M'
    { value = 1000000 * value; }
    )?
    ;

/****** Basics */
fragment LETTER : 'a'..'z' | 'A'..'Z' | '_' | '\''
  ;
// ignore comments
COMMENT : '#'.*'\n' {$channel = HIDDEN; }
  ; 
// ignore whitespace
WS  : (' ' | '\t' | '\n' | '\r') { $channel = HIDDEN; }
  ;
fragment DIGIT  : '0'..'9'
  ;
ENTIER : DIGIT (DIGIT)*
  ;
fragment STRING : '{'.*'}'
  ;
NAME  : STRING | (LETTER (LETTER | DIGIT)*)
  ;
  
VARIABLE  : STRING ' ##' 
  ;

