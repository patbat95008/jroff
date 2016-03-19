// Patrick Russell pcrussel
// $Id: linkedqueue.java,v 1.13 2014-02-03 18:50:42-08 - - $
//
// Insert and remove functions by:
// Patrick Russell
// 
// Username:
// pcrussel
//

import java.util.NoSuchElementException;

class linkedqueue <item_t> {

   private class node{
      item_t item;
      node link;

      //sets what the item in the node is
      void setItem (item_t any){
         item = any;
      }

      //returns the item in the node
      item_t getItem (){
         return item;
      }

      //links the node to another node
      void setLink (node link){
         this.link = link;
      }

      //returns the next node in the list
      node getLink (){
         return link;
      }
         
   }

   //
   // INVARIANT:  front == null && rear == null
   //          || front != null && rear != null
   // In the latter case, following links from the node pointed 
   // at by front will lead to the node pointed at by rear.
   //
 
  private node front = null;
  private node rear = null;

   //empty() tests to see if the list has any elements in it
   public boolean empty (){
      return front == null;
   }

   //pushes a value onto the rear of the queue
   public void insert (item_t any) {
      //node tmp is a temporary node used to store and
      //link the values into the queue
      node tmp = new node();
      tmp.setItem(any);
      
      //if there are no items in the list, add tmp to
      //the list and point front and rear to it
      if(front == null){
         front = tmp;
         rear  = tmp;
      //otherwise, set the link of the rear of the list
      //to tmp, then make rear point to tmp
      }else{
         rear.setLink(tmp);
         rear = tmp;         
      }
      
   }
   
   //removes and returns the item at the front of the
   //queue
   public item_t remove (){
      //if the list is empty, throw an error
      if (empty()) throw new NoSuchElementException();
      //point the temporary node to the front of the list
      //node tmp = front;
      //pull the value out of the front item
      item_t value = front.getItem();
      //point the front to equal the next 
      //item in the queue
      front = front.getLink();
      
      return value;
   }

}
