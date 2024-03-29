

public class RunData {
    
    private int currOffset;
    private int offset;
    private int length;
    
    public RunData() {
        offset = 0;
        length = 0;
        currOffset = 0;
    }
    
    public RunData(int o, int l) {
        offset = o;
        length = l;
        currOffset = 0;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public int getLength() {
        return length;
    }
    
    public String toString() {
        return "off: " + offset + " len: " + length + "\n   "+ ((offset+length)/16);
    }
    
    public int getPos() {
        return currOffset;
    }
    
    public int getCurrOffset() {
        return currOffset;
    }
    
    public void end() {
        currOffset = -1;
    }
    
    public void increment() {
        currOffset += 16;
    }
    
    public void incrementB() {
        currOffset += 16384;
    }
}

