//Patrick Russell pcrussel
//$Id: commands.java,v 1.51 2014-02-04 18:23:24-08 - - $

import java.util.HashMap;
import static java.lang.System.*;

class commands {

   static char controlChar = '.';//control Character
   static int format = 10;//number of spaces for offset
   static int indent = 0;//number of spaces for indentation
   static int lineL = 65;//length of the line
   static int topMarginH = 6;//length of top margin
   static int pageL = 60;//length of page
   static int lineNum = 1;//number of times to make a new line
   static int lineVert = 0;//total number of lines in the page
   static int pageNum = 1;//number of pages
   static boolean dumpParagraph = false;//dumps paragraph
   static boolean isTopOfPage = false;//starts top of page mode
   static boolean hasBeenIndent = false;//test to see if the paragraph 
                                        //has already been indented

   private static void command_00 (String[] words) {
      // Executing a comment does nothing.
   }

   //switch to top of page mode
   private static void command_bp (String[] words) {
      isTopOfPage = true;
   }

   //calls dump paragraph
   private static void command_br (String[] words) {
      dumpParagraph = true;
   }

  //change control character, or reset to default '.'
   private static void command_cc (String[] words) {
      if(words.length<2){
      controlChar = '.';
      }else{
         controlChar = words[1].charAt(0);
      }
   }

   //indent and remember number
   private static void command_in (String[] words) {
      indent = Integer.parseInt(words[1]);
   }

   //set the line length to specified number
   private static void command_ll (String[] words) {
      lineL = Integer.parseInt(words[1]);
   }

   //height of top margin
   private static void command_mt (String[] words) {
      topMarginH = Integer.parseInt(words[1]);
   }

   //sets page length
   private static void command_pl (String[] words) {
      pageL = Integer.parseInt(words[1]);
   }

   //every line of output is preceded by
   //this number of spaces
   private static void command_po (String[] words) {
      lineNum = Integer.parseInt(words[1]);
      for(int i = 0; i < lineNum; i++){
         out.printf("\n");
      }
   }

   //dumps the paragraph and prints blank lines
   private static void command_sp (String[] words) {
      dumpParagraph = true;
      out.printf("\n");
   }

   private static void STUB (String[] words) {
      out.printf ("%s: STUB: %s",
                  auxlib.PROGNAME, auxlib.caller());
      for (String word: words) out.printf (" %s", word);
      out.printf ("%n");
   }

   public static void do_command (String[] words) {
      //makes it so that the switchcase ignores the
      //control character
      //
      //The if statement in the jroff class
      //checks for the control character
      String word = (String)words[0];
      word = word.substring(1);

      switch (word) {
         case "\\\"" : command_00 (words); break;
         case "bp"   : command_bp (words); break;
         case "br"   : command_br (words); break;
         case "cc"   : command_cc (words); break;
         case "in"   : command_in (words); break;
         case "ll"   : command_ll (words); break;
         case "mt"   : command_mt (words); break;
         case "pl"   : command_pl (words); break;
         case "po"   : command_po (words); break;
         case "sp"   : command_sp (words); break;
         default     : throw new IllegalArgumentException (words[0]);
      }
   }
} 
