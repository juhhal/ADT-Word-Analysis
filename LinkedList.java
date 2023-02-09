public class LinkedList<T> implements List<T> {

   private Node<T> head;
   private Node<T> current;
   public int size;

   LinkedList() {
      head = current = null;
      size = 0;
   }

   @Override
   public boolean empty() {
      return head == null;
   }

   @Override
   public void findFirst() {
      current = head;
   }

   @Override
   public void findNext() {
      current = current.next;
   }

   @Override
   public boolean last() {
      return current.next == null;
   }

   @Override
   public T retrieve() {
      return current.data;
   }

   @Override
   public void update(T e) {
     
      WordOccurrence occ = ((WordInformation) e).occList.retrieve();
      ((WordInformation) current.data).occList.insert(occ);
      ((WordInformation) current.data).size++;
   }

   @Override
   public void insert(T e) {
      this.size++;
     
      if (empty()) {
         current = head = new Node<>(e);
      
      
      }         
      else {
         current.next = new Node(e);
         current = current.next;
      
         
      }
   }

   public Node<T> Search(String word) {
      if (!empty()) 
      {
         findFirst();
         while (!last())
         {
            String w = ((WordInformation) retrieve()).word;
            
            if (!w.equals(word)) {
               findNext();
            } else {
               return current;
            }
         }
         
         String w = ((WordInformation) retrieve()).word;
         if (!w.equals(word))
         {
            return null;
         }
         else
         {
            return current;
         }
      }
      return null;
   }


   public Node<T> getHead(){
      return head;
   }

}
