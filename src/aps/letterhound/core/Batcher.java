package aps.letterhound.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.opencv.core.Mat;

public class Batcher implements Progressable {
   
    private final ArrayList<DataPair> dataSet;
    private final IO io;
    private final boolean dump;
    private final int numFiles;
    private volatile int counter;
    
    public Batcher(IO io, boolean dump){
	dataSet = new ArrayList<>();
	counter = 0;
	this.numFiles = io.getImages().size();
	this.io = io;
	this.dump = dump;
    }
    
    public Batcher(IO io){
	this(io, false);
    }
    
    private String e;
    public void start() throws IOException{
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		try {
		    process();
		    counter = numFiles;
		    e = null;
		} catch (IOException ex) {
		    e = ex.getMessage();
		}
	    }
	}).start();
	if(e != null){
	    throw new IOException(e);
	}
    }
 
    public void process() throws IOException {
	for (File image : io.getImages()) {
	    Mat m = io.loadImgToGrayMat(image.getName());
	    m = Filters.applyAll(m);
	    Descriptors d = Descriptors.analyzeAll(m);
	    double[] r = NeuralNetwork.letterResult(image.getName().charAt(0));
	    dataSet.add(new DataPair(d,r));
	    if (dump) {
		io.saveMatToImg(m, image.getName());
		io.appendText(d.toString() + Arrays.toString(r));
	    }
	    counter++;
	}
    }

    public ArrayList<DataPair> getDataSet() {return dataSet;}
    public @Override int getCounter() {return counter;}
    public @Override int getMaxCount() {return numFiles;}
    public IO getIo() {return io;}
    public boolean isDump() {return dump;} 

}
