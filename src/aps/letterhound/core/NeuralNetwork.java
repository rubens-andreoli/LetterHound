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
    public static final int NUM_DESCRIPTORS = 259;
    public static final int NUM_RESULT = 26;
    public static final int NUM_HIDDEN_LAYER = 50;
    private static final int STATUS_MAX = 100;
    private static final int TRAIN_MAX_EPOCH = 50000;
    private static final double TRAIN_TARGET_ERROR = 5*10E-3;
    private static final double VALID_ERROR_VARIATION = 0.01;

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
    private volatile int status;
    
    public NeuralNetwork(){
	net = new BasicNetwork();
	net.addLayer(new BasicLayer(null,true,NUM_DESCRIPTORS));
	net.addLayer(new BasicLayer(new ActivationElliott(),true,NUM_HIDDEN_LAYER));
	net.addLayer(new BasicLayer(new ActivationElliott(),true, NUM_RESULT));
	net.getStructure().finalizeStructure();
    }
    
    public NeuralNetwork(BasicNetwork net){
	this.net = net;
	status = 0;
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
	
	ResilientPropagation prop = new ResilientPropagation(net, trainingSet);
	
	
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		int epoch = 0;
		double lastValError = 100;
		do {
		    prop.iteration();
		    status = (int) (STATUS_MAX - ((prop.getError()-TRAIN_TARGET_ERROR) * STATUS_MAX));
                    System.out.println("Época: " + epoch);
                    System.out.println("Treinamento Erro: " + prop.getError());
		    if (testSet != null) {
			double valError = net.calculateError(testSet);
			System.out.println("Validação Erro: " + valError);
			if (valError > lastValError+VALID_ERROR_VARIATION && valError > prop.getError()) {
                            System.out.println("Treinamento Interrompido");
			    break;
			}
			lastValError = valError;
		    }
		    epoch++;
		} while (epoch < TRAIN_MAX_EPOCH && prop.getError() > TRAIN_TARGET_ERROR);

		prop.finishTraining();
		status = STATUS_MAX;
	    }
	}).start();
	
    }
    
    protected double[][] descListToArray(List<DataPair> list){
	double[][] v = new double[list.size()][NUM_DESCRIPTORS];
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
            System.out.println("Saída pos. "+i+": " + x);
	    double y = output[pos];
	    if(Math.abs(x-1) < Math.abs(y-1)){
		pos = i;
	    }
	}
        System.out.println("");
	for(Map.Entry<Character,Integer> entry : LETTER_MAP.entrySet()){
	    if(entry.getValue() == pos) return entry.getKey();
	}
	return '?';
    }

    public @Override int getCounter() {return status;}
    public @Override int getMaxCount() {return STATUS_MAX;}
    public BasicNetwork getNet() {return net;}

}
