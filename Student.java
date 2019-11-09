
/**
 * 
 * @author Ryan Kirkpatrick
 * @version 1
 */
public class Student implements Comparable {
    private Name name;
    private int score;
    private String grade;
    private String id;
    
    /**
     * 
     * @param n name
     * @param i id
     */
    public Student(Name n, String i) {
        name = n;
        score = 0;
        grade = "E";
        id = i;
    }
    
    /**
     * 
     * @param n name
     * @param id pid
     * @param score score
     * @param grade grade
     */
    public Student(Name n, String id, int score, String grade) {
        name = n;
        this.score = score;
        this.id = id;
        this.grade = grade;
    }
    
    /**
     * 
     * @return this
     */
    public Student getThis() {
        return this;
    }
    
    /**
     * 
     * @param s newscore
     */
    public void setScore(int s) {
        score = s;
    }
    
    /**
     * 
     * @param g newgrade
     */
    public void setGrade(String g) {
        grade = g;
    }
    
    /**
     * 
     * @return name this.name
     */
    public Name getName() {
        return name;
    }
    
    /**
     * 
     * @param i id to be set
     */
    public void setID(String i) {
        this.id = i;
    }
    
    /**
     * 
     * @return currgrade
     */
    public String getGrade() {
        return grade;
    }
    
    /**
     * 
     * @return currscore
     */
    public int getScore() {
        return score;
    }
    

    /**
     * 
     * @return id
     */
    public String getID() {
        return id;
    }

    /**
     * @Override
     * @param c object to compare to
     * @return encapsulated compareTo
     */
    public int compareTo(Object c) {
        return this.name.compareTo( ((Student)c).getName() );
    }
    
    /**
     * @return id, name, score=score
     */
    public String toString() {
        return id + ", " + name.toString() + ", " + "score = " + score;
    }
}