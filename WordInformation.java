public class WordInformation {
    String word;
    LinkedList<WordOccurrence> occList;
    int size;

    WordInformation(String word, int row, int pos) {
        this.word = word;
        occList = new LinkedList();
        occList.insert(new WordOccurrence(row, pos));
        size = 1;
    }    
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        String str = "";
        if (!occList.empty()) {
            occList.findFirst();
            while (!occList.last()) {
                str += occList.retrieve() +" , ";
                occList.findNext();
            }
            str += occList.retrieve();
        }
        return str;
    }
}
