package br.unip.letterhound.core;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * <pre>
 * Contain methods to simplify an image for pixel count.
 * Changed: 11/11/17
 * </pre>
 */
public class Filters {

    //<editor-fold defaultstate="collapsed" desc="Static">
    private static final String ERROR_ONE_CHANNEL = "Image must have only one channel!";
    private static final int PADDING = 2;
    private static final int IMG_NEW_SIZE = 64;
    private static final int BLUR_SIZE = 5;
    private static final double BLUR_SIGMA = 5.0;
    
    /**
     * <pre>
     * Convert a mat to grayscale, apply a GaussianBlur with fixed 
     * size (5x5) and sigma (5.0), and using threshold method with type THRESH_OTSU 
     * transforms it to black and white. 
     * </pre>
     * @param mat OpenCV matrix
     */
    public static void simplify(Mat mat){
	if(mat.channels() != 1) {
	    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
	}
	Imgproc.GaussianBlur(mat, mat, new Size(BLUR_SIZE, BLUR_SIZE), BLUR_SIGMA);
	Imgproc.threshold(mat, mat, 0, 255, Imgproc.THRESH_OTSU);
    }
    
    /**
     * <pre>
     * Creates a Mat from original image reducing the white borders of 
     * the original Mat to 2 pixels (padding). 
     * !Caution! Mat should be grayscale to work!
     * </pre>
     * @param mat OpenCV matrix
     * @return new OpenCV matrix
     */
    public static Mat crop(Mat mat){
	assert(mat.channels() == 1): ERROR_ONE_CHANNEL;
	int top=-1;
	int bottom=-1;
	int right=-1;
	int left=-1;
	
	//some images have different values of width/height and cols/rows,
	//use first to test for one black
	for (int i=0; i<mat.rows() && (top==-1 || bottom==-1); i++){
	    if(Core.countNonZero(mat.row(i)) != mat.width() && top==-1){ top = i-PADDING; }
	    if(Core.countNonZero(mat.row(mat.rows()-i-1)) != mat.width() && bottom==-1){ 
		bottom = mat.rows()-i-1+PADDING; 
	    }
	}
	
	for (int i=0; i<mat.cols() && (left==-1 || right==-1); i++){
	    if(Core.countNonZero(mat.col(i)) != mat.height() && left ==-1){ left = i-PADDING; }
	    if(Core.countNonZero(mat.col(mat.cols()-i-1)) != mat.height() && right ==-1){ 
		right = mat.cols()-i-1+PADDING; 
	    }
	}
	
	//if cannot apply padding
	if(top<0 || top==mat.rows())top=0;
	if(bottom>mat.rows() || bottom==0) bottom = mat.rows();
	if(left<0 || left==mat.cols()) left=0;
	if(right>mat.cols() || right==0) right = mat.cols();

	return mat.submat(new Rect(new Point(left, top), new Point(right, bottom)));
    }
    
    /**
     * Creates a 64x64 pixels Mat from original image scaled and centralized. 
     * @param mat OpenCV matrix
     * @return new OpenCV matrix
     */
    public static Mat standardize(Mat mat) {
	int bigger = Math.max(mat.width(), mat.height());
	double scale = IMG_NEW_SIZE/(double)bigger;
	Imgproc.resize(mat, mat, new Size(mat.width()*scale, mat.height()*scale));

	int xStart = (IMG_NEW_SIZE-mat.width())/2;
	int yStart = (IMG_NEW_SIZE-mat.height())/2;
	
	Mat matR = Mat.zeros(IMG_NEW_SIZE, IMG_NEW_SIZE, mat.type());
	Core.bitwise_not(matR, matR);
	mat.copyTo(matR.submat(new Rect(xStart, yStart, mat.width(), mat.height())));
	return matR;
    }

    
    /**
     * <pre>
     * Creates a simplified Mat for pixel count from original image:
     * -Removes the noise;
     * -Transformes to black and white; 
     * -Removes any white borders (2px padding);
     * -Resizes to 64x64 pixels;
     * -Centralizes.
     * </pre>
     * @param mat OpenCV matrix
     * @return new OpenCV matrix
     */
    public static Mat applyAll(Mat mat){
	Filters.simplify(mat);
	mat = Filters.crop(mat);
	mat = Filters.standardize(mat);
	return mat;
    }
    //</editor-fold>
    
    protected Filters(){}

}
