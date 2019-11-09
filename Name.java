
/**
 * 
 * @author Ryan Kirkpatrick
 * @version 1
 */
public class Name implements Comparable<Name> {
    private String first;
    private String last;
    private String middle;
    
    /**
     * 
     * @param f is first
     * @param l is last
     */
    public Name(String f, String l) {
        first = f.toLowerCase();
        last = l.toLowerCase();
        middle = "";
    }
    
    /**
     * 
     * @param f first
     * @param l last
     * @param m middle
     */
    public Name(String f, String l, String m) {
        first = f.toLowerCase();
        last = l.toLowerCase();
        middle = m.toLowerCase();
    }
    
    
    /**
     * 
     * @return first name
     */
    public String getFirst() {
        return first;
    }
    
    /**
     * 
     * @return last name
     */
    public String getLast() {
        return last;
    }
    
    /**
     * 
     * @return middle name
     */
    public String getMiddle() {
        return middle;
    }
    
    /**
     * @return string representation of name
     */
    public String toString() {
        return first + ' ' + last;
    }

    /**
     * @override
     * @param n object to compare
     * @return the int. >1 if bigger, <1 less, 0 if equal. 
     */
    public int compareTo(Name n) {
        if (n instanceof Name) {
            if (this.last.compareTo( ((Name)n).getLast() ) == 0) {
                return this.first.compareTo( ((Name)n).getFirst());
            }
            else {
                return this.last.compareTo( ((Name)n).last);
            }
        }
        return 99;
    }

    


  
}
