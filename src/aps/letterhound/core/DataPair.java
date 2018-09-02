package aps.letterhound.core;

public class DataPair {
    
    private Descriptors descriptors;
    private double[] result;

    public DataPair(Descriptors descriptors, double[] result) {
	this.descriptors = descriptors;
	this.result = result;
    }

    public Descriptors getDescriptors() {return descriptors;}
    public double[] getDescriptorsVector() {return descriptors.getAll();}
    public double[] getResult() {return result;}

}
