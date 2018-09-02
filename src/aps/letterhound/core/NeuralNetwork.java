package aps.letterhound.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.encog.engine.network.activation.ActivationElliott;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class NeuralNetwork implements Progressable {
    
    //<editor-fold defaultstate="collapsed" desc="Static">
    public static final int NUM_RESULT = 26;
    public static final int NUM_HIDDEN_LAYER = 50;
    private static final int COUNTER_MAX = 100;

    private static final HashMap<Character, Integer> LETTER_MAP = new HashMap();
    static{
	Character start = 'a';
	for(int i=0; i<NUM_RESULT; i++){
	    LETTER_MAP.put(start++, i);
	}
    }
    public static double[] letterResult(char letter){
	double[] result = new double[NUM_RESULT];
	Arrays.fill(result, 0);
	result[LETTER_MAP.get(Character.toLowerCase(letter))] = 1;
	return result;
    }
    //</editor-fold>
    
    public BasicNetwork net;
    private volatile int status = 0;
    
    public NeuralNetwork(){
	net = new BasicNetwork();
	net.addLayer(new BasicLayer(null,true,Descriptors.NUM_DESCRIPTORS));
	net.addLayer(new BasicLayer(new ActivationElliott(),true,NUM_HIDDEN_LAYER));
	net.addLayer(new BasicLayer(new ActivationElliott(),true, NUM_RESULT));
	net.getStructure().finalizeStructure();
    }
    
    public NeuralNetwork(BasicNetwork net){
	this.net = net;
	status = 100;
    }
    
    public void train(ArrayList<DataPair> trainingList, ArrayList<DataPair> testList){
	assert(!trainingList.isEmpty()): "Training lists cannot be empty!";
	net.reset(); //generate weight

	MLDataSet trainingSet = new BasicMLDataSet(
		descListToArray(trainingList), 
		resuListToArray(trainingList)
	);
	
	MLDataSet testSet;
	if(testList == null || testList.isEmpty()){
	    testSet = null;
	}else{
	    testSet = new BasicMLDataSet(
		descListToArray(testList), 
		resuListToArray(testList)   
	    );
	}
	
	//Backpropagation prop = new Backpropagation(net, dataset, 0.03, 0.12);
	//ResilientPropagation prop = new ResilientPropagation(net, trainingSet, 0.01, 50.0);
	ResilientPropagation prop = new ResilientPropagation(net, trainingSet);
	
	
	
	
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		int epoch = 0;
		double lastValError = 100;
		do {
		    prop.iteration();
		    status = (int) (COUNTER_MAX - (prop.getError() * COUNTER_MAX));
//		     System.out.println("Época: " + epoch + " Erro: " + prop.getError());
//		     System.out.println(status);
		    if (testSet != null) {
			double valError = net.calculateError(testSet);
//			System.out.println("Época: " + epoch + " Erro: " + valError);
			if (valError > lastValError + 0.01 && valError > prop.getError()) {
//		        System.out.println("-------------------------");
			    break;
			}
			lastValError = valError;
		    }
		    epoch++;
		} while (epoch < 50000 && prop.getError() > 0.005);

		prop.finishTraining();
		status = COUNTER_MAX;
	    }
	}).start();
	
    }
    
    protected double[][] descListToArray(List<DataPair> list){
	double[][] v = new double[list.size()][Descriptors.NUM_DESCRIPTORS];
	for(int i=0;i<list.size();i++){
	    v[i] = list.get(i).getDescriptorsVector();
	}
	return v;
    }
    
    protected double[][] resuListToArray(List<DataPair> list){
	double[][] v = new double[list.size()][NUM_RESULT];
	for(int i=0;i<list.size();i++){
	    v[i] = list.get(i).getResult();
	}
	return v;
    }
    
    public double[] test(Descriptors desc){
	MLData input = new BasicMLData(desc.getAll());
	return net.compute(input).getData();
    }
    
    public char testResult(Descriptors desc){
	MLData input = new BasicMLData(desc.getAll());
	double[] output = net.compute(input).getData();
	
	int pos = 0;
	for(int i=0; i<output.length; i++){
	    double x = output[i];
	    double y = output[pos];
	    if(Math.abs(x-1) < Math.abs(y-1)){
		pos = i;
	    }
	}
	for(Map.Entry<Character,Integer> entry : LETTER_MAP.entrySet()){
	    if(entry.getValue() == pos) return entry.getKey();
	}
	return '?';
    }

    public @Override int getCounter() {return status;}
    public @Override int getMaxCount() {return COUNTER_MAX;}
    public BasicNetwork getNet() {return net;}

}
