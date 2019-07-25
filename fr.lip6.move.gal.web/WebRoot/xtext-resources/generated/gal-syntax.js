define([], function() {
	var keywords = "A|AF|AG|AX|E|EF|EG|EX|F|G|GAL|M|R|TRANSIENT|U|W|X|abort|alias|array|bounds|composite|ctl|else|extends|false|fixpoint|for|gal|hotbit|if|import|int|interface|invariant|label|ltl|main|never|predicate|property|reachable|self|synchronization|transition|true|typedef";
	return {
		id: "xtext.gal",
		contentTypes: ["xtext/gal"],
		patterns: [
			{include: "orion.c-like#comment_singleLine"},
			{include: "orion.c-like#comment_block"},
			{include: "orion.lib#string_doubleQuote"},
			{include: "orion.lib#string_singleQuote"},
			{include: "orion.lib#number_decimal"},
			{include: "orion.lib#brace_open"},
			{include: "orion.lib#brace_close"},
			{include: "orion.lib#bracket_open"},
			{include: "orion.lib#bracket_close"},
			{include: "orion.lib#parenthesis_open"},
			{include: "orion.lib#parenthesis_close"},
			{name: "keyword.gal", match: "\\b(?:" + keywords + ")\\b"}
		]
	};
});
