package aps.letterhound.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.opencv.core.Mat;

public class Batcher implements Progressable {
   
    //<editor-fold defaultstate="collapsed" desc="Static">
    public static Batcher start(String folder, boolean dump) throws IOException{
	IO io = IO.getIO(folder);
	Batcher b = new Batcher(io, folder, io.getImages().size(), dump);
	new Thread(new Runnable(){
	    @Override
	    public void run() {
		try {
		    b.process();
		    b.counter = b.numFiles;
		} catch (IOException ex) {}
	    }
	}).start();
	return b;
    }
    
    public static Batcher start(String folder) throws IOException{
	return Batcher.start(folder, false);
    }
    //</editor-fold>
    
    private final ArrayList<DataPair> dataSet;
    private final IO io;
    private final String folder;
    private final boolean dump;
    private final int numFiles;
    private volatile int counter;
    
    protected Batcher(IO io, String folder, int numFiles, boolean dump){
	dataSet = new ArrayList<>();
	counter = 0;
	this.folder = folder;
	this.numFiles = numFiles;
	this.io = io;
	this.dump = dump;
    }
 
    protected void process() throws IOException{
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

}
