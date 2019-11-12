import java.util.Arrays;

public class Record implements Comparable {
    private long pid;
    private double aScore;
    private int flag;
    
    public Record(long p, double s) {
        pid = p;
        aScore = s; 
    }
    
    public Record(byte[] data) {
        byte[] p = Arrays.copyOfRange(data, 0, 8);
        byte[] a = Arrays.copyOfRange(data, 8, 16);
        pid = Parser.bytesToLong(p);
        aScore = Parser.bytesToDouble(a);
    }
    
    public Record(byte[] data, int f) {
        byte[] p = Arrays.copyOfRange(data, 0, 8);
        byte[] a = Arrays.copyOfRange(data, 8, 16);
        pid = Parser.bytesToLong(p);
        aScore = Parser.bytesToDouble(a);
        flag = f;
    }
    
    public int getFlag() {
        return flag;
    }
    
    public long getPid() {
        return pid;
    }
    
    public double getAscore() {
        return aScore;
    }
    
    
    public String toString() {
        return "" + pid + " " + aScore;
    }
    
    public byte[] toBytes() {
        byte[] ret = new byte[16];
        byte[] pidBytes = Parser.longToBytes(pid);
        byte[] scoreBytes = Parser.doubleToBytes(aScore);
        for(int i = 0; i < 8; i++) {
            ret[i] = pidBytes[i];
            ret[i+8] = scoreBytes[i];
        }
        return ret;
    }

    @Override
    public int compareTo(Object com) {
        Record comp = (Record)com;
        double comparator = comp.getAscore();
        if(aScore < comparator)
            return -1;
        else if(aScore > comparator)
            return 1;
        else
            return 0; 
    }
    
}
