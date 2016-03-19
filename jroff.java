// Patrick Russell pcrussel
// $Id: jroff.java,v 1.178 2014-02-04 19:41:52-08 - - $
//
// Name: jroff
// 
// Usage: jroff [filename]
// Synopsis: formats text files
// 
// Original code by Makey
//
// Modified by
// Patrick Russell
// 
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class jroff{
   static final String STDIN_NAME = "-";

   static void scanfile (String filename, Scanner infile) {
      linkedqueue <String> wordqueue = new linkedqueue <String> ();
      out.printf ("filename = %s%n", filename);
      int linenr = 1;
      newPage();
      for (; infile.hasNextLine(); ) {
         String line = infile.nextLine();
         //out.printf ("STUB: %s: %4d: [%s]%n", filename, linenr, line);
         //ignore comment lines
         if (line.isEmpty()) wordqueue.insert("\n");
         if (line.startsWith(".\\\"")) line = infile.nextLine();
         String[] words = line.split ("\\s+");
         for(int i = 0; i < words.length; i++){
            linenr++;
            wordqueue.insert(words[i]);
         }        
        
         //detects commands and dumps the paragraph
         if (words.length > 0 && words[0].startsWith(Character.toString(commands.controlChar))) {           
            try {
               commands.do_command (words);
            }catch (IllegalArgumentException error) {
               auxlib.warn (filename, linenr, words[0],
                            "invalid command");
            }
            
            printparagraph(wordqueue);
            out.printf("%n"); 
         }
         
      }
      //if there are no commands at the end of the file,
      //print the paragraph
      printparagraph(wordqueue);
      out.printf("%n");
   }

   public static void indent () {
     for (int i = 0; i < commands.format; i++){
        out.printf(" ");
     }
     for (int i = 0; i < commands.indent; i++){
        out.printf(" ");
     }
     if(!commands.hasBeenIndent){
     commands.hasBeenIndent = true;
     }else{
     commands.hasBeenIndent = false;
     }
   }

   public static void newPage() {
     for (int i = 0; i < commands.lineL -1; i++){
        out.printf(" ");
     }
     out.printf("%s", commands.pageNum);
     for (int i = 0; i < commands.topMarginH; i++){
        out.printf("\n");
     }
     if(commands.hasBeenIndent) indent();
     commands.hasBeenIndent = false;
     commands.pageNum++;
     commands.lineVert = 0;
   }

   public static void printparagraph(linkedqueue wordqueue){
      //lineLength measures the total characters in the line
      int lineLength = 0;
      boolean isNewPara = false;
      //the for loop dumps everything in
      //the linked queue
      indent();
      for (;!wordqueue.empty();){
         

         if (commands.lineVert >= commands.pageL || commands.isTopOfPage){
            newPage();
            commands.isTopOfPage = false;
         }
         String word = (String)wordqueue.remove();
  
         if (lineLength >= commands.lineL){
           out.printf("\n");
           commands.lineVert++;
           indent();
           lineLength = word.length ();
         }

	 if (word.startsWith(Character.toString(commands.controlChar))){
	 //tests for punctuation
            //don't print the command, or the number after it
            if (!wordqueue.empty()){
               word = (String)wordqueue.remove();
           }
         }else if (word.endsWith(".") || word.endsWith("!")
	     || word.endsWith("?") || word.endsWith(";")
             || word.endsWith(":")){

           
           //adds the length of each word plus 2
  	   lineLength += word.length() + 2;

           //the if statement tests to see if the line 
           //exceeds the length of the page if yes,
           //it then sets the length of the line to
           //2 plus the length of the word and adds
           //a new line

           if (lineLength >= commands.lineL){
             out.printf("\n");
             commands.lineVert++;
             indent();
             lineLength = word.length() + 2;
             lineLength += commands.format + commands.indent;
           }
           //it then prints the word with two spaces
           out.printf("%s  ", word);
         //if the word has no puncuation, it adds
         //the word length to the line length,
         //with an extra one for the space
         }else {     
            lineLength += word.length() + 1;
            //if the line length is greater than the page,
            //it indents and sets the length to the word
            //length, plus one for the space
            if (lineLength >= commands.lineL){
               out.printf("\n");
               commands.lineVert++;
               indent();
       	       lineLength = word.length() + 1;
               lineLength += commands.format + commands.indent;
            }  
            out.printf("%s ", word);
     	}
        if (commands.lineVert >= commands.pageL){
          newPage();
          commands.lineVert = 0;
        }
        if (word.contains("\n")) indent();
      }
      //prints a space between paragraphs
      //out.printf("\n");
      //out.printf("\n");
      //   commands.lineVert++;
     // }
   }


   public static void main (String[] args) {
      linkedqueue <String> wordqueue = new linkedqueue <String> ();
      if (args.length == 0) {
         scanfile (STDIN_NAME, new Scanner (in));
      }else {
         for (String filename : args) {
            if (filename.equals (STDIN_NAME)) {
               scanfile (STDIN_NAME, new Scanner (in));
            }else {
               try {
                  Scanner scan = new Scanner (new File (filename));
                  scanfile (filename, scan);
                  scan.close();
               }catch (IOException error) {
                  auxlib.warn (error.getMessage());
               }
            }
         }
      }
   }

}
