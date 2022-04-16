package com.github.anilople.javalua.compiler.ast.stat;

/**
 * 	stat ::=  ‘;’ |
 * 		 varlist ‘=’ explist |
 * 		 functioncall |
 * 		 label |
 * 		 break |
 * 		 goto Name |
 * 		 do block end |
 * 		 while exp do block end |
 * 		 repeat block until exp |
 * 		 if exp then block {elseif exp then block} [else block] end |
 * 		 for Name ‘=’ exp ‘,’ exp [‘,’ exp] do block end |
 * 		 for namelist in explist do block end |
 * 		 function funcname funcbody |
 * 		 local function Name funcbody |
 * 		 local namelist [‘=’ explist]
 *
 * @author wxq
 */
public interface Stat {}
