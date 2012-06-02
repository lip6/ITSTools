SyntaxHighlighter.brushes.Custom = function()
{
  var funcs       = 'peek pop push';
  var keywords    = 'GAL transition list array int TRANSIENT label True False';
  //var operators   = 'True False';
 
  this.regexList = [
      { regex: SyntaxHighlighter.regexLib.singleLineCComments,            css: 'comments' },// simple comment
      { regex: SyntaxHighlighter.regexLib.multiLineCComments,		      css: 'comments' },// multiline comments
      { regex: SyntaxHighlighter.regexLib.multiLineDoubleQuotedString,    css: 'string' },  // Strings
      { regex: new RegExp(this.getKeywords(funcs), 'gmi'),                css: 'color2' },  // Functions
      { regex: new RegExp("\\b[0-9]+\\b", 'gmi'),                         css: 'color1' },  // Numbers
      { regex: new RegExp(this.getKeywords(keywords), 'gmi'),             css: 'keyword' }
	  
      ];
};
SyntaxHighlighter.brushes.Custom.prototype = new SyntaxHighlighter.Highlighter();
SyntaxHighlighter.brushes.Custom.aliases  = ['gal', 'GAL', 'Gal'];

/* Config */
SyntaxHighlighter.defaults['toolbar'] = false;
