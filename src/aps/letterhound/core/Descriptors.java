package aps.letterhound.core;

import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 * <pre>
 * Contain methods and parameters to analyze 4 descriptors that describes images by their black pixels: 
 * -Vertical projection - counted by column; 
 * -Horizontal projection - counted by rows; 
 * -Diagonal projection - counted by diagonals; 
 * -Sectors count - counted by quadrants (4 equally divided squares).
 * Changed: 11/11/17
 * </pre>
 */
public class Descriptors {
    
    //<editor-fold defaultstate="collapsed" desc="Static">
    private static final String ERROR_ONE_CHANNEL = "Image must have only one channel!";
    
     /**
     * <pre>
     * Counts all black pixels by column. 
     * !Caution! Mat must be grayscale and should be black and white for proper behavior! 
     * </pre>
     * @param mat OpenCV matrix
     * @return black pixels by column
     */
    public static double[] verticalProjection(Mat mat){
	assert(mat.channels() == 1): ERROR_ONE_CHANNEL;
        int colSize = mat.rows();
	double[] vp = new double[colSize];
        for (int i=0; i<mat.cols(); i++){
	    vp[i] = (double)(colSize - Core.countNonZero(mat.col(i)));
        }
        return vp;
    }
    
    /**
     * <pre>
     * Counts all black pixels by row. 
     * !Caution! Mat must be grayscale and should be black and white for proper behavior! 
     * </pre>
     * @param mat OpenCV matrix
     * @return black pixels by rows
     */
    public static double[] horizontalProjection(Mat mat){
	assert(mat.channels() == 1): ERROR_ONE_CHANNEL;
        int rowSize = mat.cols();
        double[] hp = new double[rowSize];
        for (int i=0; i<mat.rows(); i++){
            hp[i] = (double)(rowSize - Core.countNonZero(mat.row(i)));
        }
	return hp;
    }
    
    /**
     * <pre>
     * Counts all black pixels by 45º diagonals. 
     * !Caution! Mat should be black and white for proper behavior! 
     * </pre>
     * @param mat OpenCV matrix
     * @return black pixels by diagonals ((2*hight*width)-1)
     */
    public static double[] diagonalProjection(Mat mat){
	int order = mat.cols();
        int sum;
        double dp[] = new double[(order*2)-1];
        int c = order-1;

        for(int i=0; i<order; i++){ //top
            sum = 0;
            for(int x=0; x<=i; x++){
                double[] temp = mat.get(x, c-i+x); 
                if(temp[0] == 0)
                    sum = sum + 1;                
            }
            dp[i] = sum;
        }  

        int pos = order; //bottom
        for(int i=order-1; i>0; i--){
            sum = 0;     
            int col = order-1;
            for(int x=i; x>=1; x--){
                double[] temp = mat.get(col, x-1);               
                if(temp[0] == 0)
                    sum = sum + 1;  
                --col;
            }
            dp[pos] = sum;
            pos++;            
        }
        
        return dp;        
    }
   
    /**
     * <pre>
     * Divides the image into 4 equal parts to sum all black pixels of each quadrant. 
     * !Caution! Mat must be grayscale and should be black and white for proper behavior! 
     * </pre>
     * @param mat OpenCV matrix
     * @return black pixels by sectors
     */
    public static double[] sectorsCount(Mat mat){
	assert(mat.channels() == 1): ERROR_ONE_CHANNEL;
        double[] sc = new double[4];
	int rows = mat.rows();
        int sRows = mat.rows()/2;
        int sCols = mat.cols()/2;
	for (int i=0; i<sCols; i++){
            sc[0] += sRows - Core.countNonZero(mat.col(i).rowRange(0, sRows)); //top-left
	    sc[2] += sRows - Core.countNonZero(mat.col(i).rowRange(sRows, rows)); //top-right
	    sc[1] += sRows - Core.countNonZero(mat.col(i+sCols).rowRange(0, sRows)); //bottom-left
	    sc[3] += sRows - Core.countNonZero(mat.col(i+sCols).rowRange(sRows, rows)); //bottom-right
        }
        return sc;
    }

    /**
     * <pre>
     * Analyzes 4 descriptors sequentially: horizontal projection, vertical projection, 
     * diagonal projection, and sectors count. 
     * !Caution! Mat must be grayscale and should be black and white for proper behavior! 
     * </pre>
     * @param mat OpenCV matrix
     * @return Descriptors object with analyzed descriptors as parameters
     */
    public static Descriptors analyzeAll(Mat mat){
	Descriptors d = new Descriptors();
	d.horP = Descriptors.horizontalProjection(mat);
	d.verP = Descriptors.verticalProjection(mat);
	d.diaP = Descriptors.diagonalProjection(mat);
	d.secC = Descriptors.sectorsCount(mat);
	return d;
    }
    //</editor-fold>
    
    private double[] horP;
    private double[] verP;
    private double[] diaP;
    private double[] secC;
    
    protected Descriptors(){}

    /**
     * Returns 4 descriptors sequentially: horizontal projection, vertical projection, 
     * diagonal projection, and sectors count.
     * @return all projection results
     */
    public double[] getAll() { // TEST: process all here or during creation?
	double all[] = new double[horP.length+verP.length+diaP.length+secC.length];
	System.arraycopy(horP, 0, all, 0, horP.length);
	System.arraycopy(verP, 0, all, horP.length, verP.length);
	System.arraycopy(diaP, 0, all, horP.length+verP.length, diaP.length);
	System.arraycopy(secC, 0, all, horP.length+verP.length+diaP.length, secC.length);
	return all;
    }
    
    public double[] getHorizontalProjection() {return horP;}
    public double[] getVerticalProjection() {return verP;}
    public double[] getDiagonalProjection() {return diaP;}
    public double[] getSectorsCount() {return secC;}
    public int getNumDescriptors(){return horP.length+verP.length+diaP.length+secC.length;};
    public @Override String toString() {return Arrays.toString(this.getAll());}
  
}
