

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    private static int blockSize = 16384;
    
    private int currOutputIndex = 0;
    private int outputOffset = 0;
    private byte[] inputBuffer;
    private byte[] outputBuffer;
    private MaxHeap maxHeap;
    private ArrayList<RunData> runs;
    private Student[] studentData;
    private RandomAccessFile input;
    private RandomAccessFile output;
    int testCounter = 0;
    private RandomAccessFile currFile;
    
    private PrintWriter writer;
    
    public Parser(String recordFile, String studentFile) throws Exception {

        inputBuffer = new byte[blockSize];
        outputBuffer = new byte[blockSize];
        //open byte file
        input = new RandomAccessFile(new File(recordFile), "rw");
        //input = new RandomAccessFile(new File("output.bin"), "rw");
        output = new RandomAccessFile(new File("output.bin"), "rw");
        //for testing
        writer = new PrintWriter("file.txt", "UTF-8");

        maxHeap = new MaxHeap(new Record[8192], 0, 8192);
        int full = 0;
        runs = new ArrayList<RunData>();
        full = input.read(inputBuffer);
        //fill maxheap initially
        while(maxHeap.heapsize() < 8192 && full != -1) {
            int lBound = 0;
            int rBound = 16;
            for(int i = 0 ; i < 1024; i++) {
                Record temp = new Record(Arrays.copyOfRange(inputBuffer, lBound, rBound));
                lBound += 16;
                rBound += 16;
                maxHeap.insert(temp);
            }
            full = input.read(inputBuffer);
        }
        boolean atLeast8blocks = false;
        
        //replacement selection
        int hiddenVals = 0;
        while(full != -1) {
            atLeast8blocks = true;
            int lBound = 0;
            int rBound = 16;
            Record curr;
            for(int i = 0; i < 1024; i++) {
                Record temp = new Record(Arrays.copyOfRange(inputBuffer, lBound, rBound));
                lBound += 16;
                rBound += 16;
                curr = (Record)maxHeap.removemax();
                toOutputBuffer(curr);
                
                if(temp.compareTo(curr) > 0) {
                    hiddenVals++;
                    maxHeap.hide(temp);
                }
                else {
                    maxHeap.insert(temp);
                }
                
                //check if all hidden values
                if(maxHeap.heapsize() == 0 || hiddenVals == 8192) {
                    maxHeap.unhide();
                    maxHeap.buildheap();
                    hiddenVals = 0;
                }
                
            }
            full = input.read(inputBuffer);
        }
        
        while(maxHeap.heapsize() > 0) {
            Record currMax = (Record)maxHeap.removemax();
            toOutputBuffer(currMax);
        }
        if(atLeast8blocks) {
            //unhide arbitray #of hidden objects
            if(hiddenVals > 0) {
                maxHeap.unhide(hiddenVals);
                maxHeap.buildheap();
                while(maxHeap.heapsize() > 0) {
                    Record currMax = (Record)maxHeap.removemax();
                    toOutputBuffer(currMax);
                }
            }
        }
        
        //merge sorting between two files
        currFile = output;
        
        merge(runs);
        
        
        //take at most 8 runs
        
        //put into heap of 8
        
        //merging for loop into outputbuffer
        //dump when full, keep going until all 8 are empty
        
        //track new run: size = size * 8, offset = 0+size
        
        //same for rest until all runs processed
        
        //repeat process with new bigger runs, track which direction
        
        
        
        
        
    }
    
        
    private void merge(ArrayList<RunData> stacks) throws Exception {
        swapFiles();
        maxHeap.emptyHeap();
        int index = 0; 
        System.out.println(maxHeap.heapsize());
        while(index < stacks.size() && index < 8*index) {
            
            input.seek(stacks.get(index).getOffset());
            input.read(inputBuffer);
            int lBound = 0;
            int rBound = 16;
            for(int i = 0 ; i < 1024; i++) {
                Record temp = new Record(Arrays.copyOfRange(inputBuffer, lBound, rBound));
                lBound += 16;
                rBound += 16;
                maxHeap.insert(temp);
            }         
            index++;    
        }//done
        
        while (maxHeap.heapsize() > 0) {
            //removemax
            //dumptooutputbuffer
            //ifoutputbuffer full dump to file track run
            
            //replace with next from run, if empty load next run
        }

        
    }
    
    private void swapFiles() {
        RandomAccessFile temp = input;
        input = output;
        output = temp;
    }

    
    
    
    private void toOutputBuffer(Record x) throws IOException{
        testCounter++;
        writer.println(x);
        if(x.getPid() == 173199307468L) {

        }
        if (currOutputIndex < blockSize) {
            byte[] record = x.toBytes();            
            for(int i = 0; i < record.length; i++) {
               outputBuffer[currOutputIndex+i] = record[i];
            }
            currOutputIndex+=16;
        }
        else {
            System.out.println("error");
        }
        if ( currOutputIndex == blockSize) {
            dumpToFile();
            currOutputIndex = 0;
        }
    }
    
    private void dumpToFile() throws IOException {
        runs.add(new RunData(outputOffset, blockSize));
        output.write(outputBuffer);
        outputOffset += blockSize;
    }
    
    
    
    
    public String readStudentDataFile(String s) throws IOException {    
        int current = 0;
        InputStream iS = new FileInputStream(s);
        int currOffset = 0;
        byte[] allBytes = new byte[1000];
        iS.read(allBytes, currOffset, 10);
        int prevOffset = currOffset;
        currOffset+=10;
        
        byte[] recNumArray = new byte[4];
        iS.read(allBytes, currOffset, 4);
        prevOffset = currOffset;
        currOffset+=4;
        recNumArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
        int recNum = byteArrayToInt(recNumArray);
        
        for (int i = 0; i < recNum; i++) {
            byte[] pidArray = new byte[8];
            iS.read(allBytes, currOffset, 8);
            prevOffset = currOffset;
            currOffset+=8;
            pidArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
            long pidLong = bytesToLong(pidArray);
            String pid = String.valueOf(pidLong);
            if (pid.length() < 9) {
                for (int i1 = 0; i1 < 9 - pid.length(); i1++) {
                    pid = "0" + pid;
                }
            }
            
            String fName = "";
            boolean delimFound = false;
            while (!delimFound) {
                iS.read(allBytes, currOffset, 1);
                prevOffset = currOffset;
                currOffset+=1;
                byte[] fNameArray = new byte[1];
                fNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                String c = new String(fNameArray);
                if (c.equals("$")) {
                    delimFound = true;
                } else {
                    fName+= c;
                }
            }
            
            String mName = "";
            delimFound = false;
            while (!delimFound) {
                iS.read(allBytes, currOffset, 1);
                prevOffset = currOffset;
                currOffset+=1;
                byte[] mNameArray = new byte[1];
                mNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                String c = new String(mNameArray);
                if (c.equals("$")) {
                    delimFound = true;
                } else {
                    mName+= c;
                }
            }

            
            String lName = "";
            delimFound = false;
            while (!delimFound) {
                iS.read(allBytes, currOffset, 1);
                prevOffset = currOffset;
                currOffset+=1;
                byte[] lNameArray = new byte[1];
                lNameArray = Arrays.copyOfRange(allBytes, prevOffset, currOffset);
                String c = new String(lNameArray);
                if (c.equals("$")) {
                    delimFound = true;
                } else {
                    lName+= c;
                }
            }
            
            iS.read(allBytes, currOffset, 8);
            prevOffset = currOffset;
            currOffset+=8;
            
            studentData[current] = new Student(
                new Name(fName, lName, mName), pid);
            current++;
        }
        iS.close();
        return s + " has been successfully loaded!";
    }

    private int byteArrayToInt(byte[] recNumArray) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
    
    public static byte[] doubleToBytes(double x) {
        byte[] output = new byte[8];
        long lng = Double.doubleToLongBits(x);
        for(int i = 0; i < 8; i++) output[i] = (byte)((lng >> ((7 - i) * 8)) & 0xff);
        return output;
    }
    
    
    public static long bytesToLong(byte[] b) {
        return ByteBuffer.wrap(b).getLong();
    }
    
    public static double bytesToDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
