package aps.letterhound.core;

import org.opencv.core.Core;
import org.opencv.core.CvType;
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
    
    /**
     * <pre>
     * Convert a mat to grayscale, apply a GaussianBlur with fixed 
     * size and sigma, and using threshold method with type THRESH_OTSU 
     * transforms the it to black and white. 
     * </pre>
     * @param mat OpenCV matrix
     */
    public static void simplify(Mat mat){
	if(mat.channels() != 1) {
	    Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
	}
	Imgproc.GaussianBlur(mat, mat, new Size(5, 5), 5.0);
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
	int padding = 2;
	int top=-1;
	int bottom=-1;
	int right=-1;
	int left=-1;
	
	//some images have different values of width/height and cols/rows,
	//use first to test for one black
	for (int i=0; i<mat.rows() && (top==-1 || bottom==-1); i++){
	    if(Core.countNonZero(mat.row(i)) != mat.width() && top==-1){ top = i-padding; }
	    if(Core.countNonZero(mat.row(mat.rows()-i-1)) != mat.width() && bottom==-1){ 
		bottom = mat.rows()-i-1+padding; 
	    }
	}
	
	for (int i=0; i<mat.cols() && (left==-1 || right==-1); i++){
	    if(Core.countNonZero(mat.col(i)) != mat.height() && left ==-1){ left = i-padding; }
	    if(Core.countNonZero(mat.col(mat.cols()-i-1)) != mat.height() && right ==-1){ 
		right = mat.cols()-i-1+padding; 
	    }
	}
	
	//if cannot apply padding
	if(top < 0)top=0;
	if(bottom > mat.rows()) bottom = mat.rows();
	if(left < 0) left=0;
	if(right > mat.cols()) right = mat.cols();

	return mat.submat(new Rect(new Point(left, top), new Point(right, bottom)));
    }
    
    /**
     * Creates a 64x64 pixels Mat from original image scaled and centralized. 
     * @param mat OpenCV matrix
     * @return new OpenCV matrix
     */
    public static Mat standardize(Mat mat) {
	int bigger = Math.max(mat.width(), mat.height());
	double scale = 64/(double)bigger;
	Imgproc.resize(mat, mat, new Size(mat.width()*scale, mat.height()*scale));

	int xStart = (64-mat.width())/2;
	int yStart = (64-mat.height())/2;
	
	Mat matR = Mat.zeros(64, 64, mat.type());
	Core.bitwise_not(matR, matR);
	mat.copyTo(matR.submat(new Rect(xStart, yStart, mat.width(), mat.height())));
	
	return matR;
    }
    
    public static void thinning(Mat mat) {
	Core.bitwise_not(mat, mat);
	Mat prev = Mat.zeros(mat.size(), CvType.CV_8UC1);
	Mat diff = new Mat();
	do {
	    zhangSuenThinningIteration(mat, 0);
	    zhangSuenThinningIteration(mat, 1);
	    Core.absdiff(mat, prev, diff);
	    mat.copyTo(prev);
	} while (Core.countNonZero(diff) > 0);
	Core.bitwise_not(mat, mat);
    }
    
    private static void zhangSuenThinningIteration(Mat img, int step) {
	// Get image pixels 
	byte[] buffer = new byte[(int) img.total() * img.channels()];
	img.get(0, 0, buffer);

	byte[] markerBuffer = new byte[buffer.length];

	int rows = img.rows();
	int cols = img.cols();

	// Process all pixels 
	for (int y = 1; y < rows - 1; ++y) {
	    for (int x = 1; x < cols - 1; ++x) {
		// Pre-calculate offsets (indices in buffer) 
		int prev = cols * (y - 1) + x;
		int cur = cols * y + x;
		int next = cols * (y + 1) + x;

		// Get 8-neighborhood of current pixel (center = p1; p2 = top middle, counting clockwise) 
		byte p2 = buffer[prev];
		byte p3 = buffer[prev + 1];
		byte p4 = buffer[cur + 1];
		byte p5 = buffer[next + 1];
		byte p6 = buffer[next];
		byte p7 = buffer[next - 1];
		byte p8 = buffer[cur - 1];
		byte p9 = buffer[prev - 1];

		// Get number of black-white transitions in ordered sequence of points in the 8-neighborhood; note: a filled pixel (white) has a value of -1 
		int a = 0;
		if (p2 == 0 && p3 == -1) {++a;}
		if (p3 == 0 && p4 == -1) {++a;}
		if (p4 == 0 && p5 == -1) {++a;}
		if (p5 == 0 && p6 == -1) {++a;}
		if (p6 == 0 && p7 == -1) {++a;}
		if (p7 == 0 && p8 == -1) {++a;}
		if (p8 == 0 && p9 == -1) {++a;}
		if (p9 == 0 && p2 == -1) {++a;}

		// Number of filled pixels in the 8-neighborhood 
		int b = Math.abs(p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9);

		// Condition 3 and 4 
		int c3 = step == 0 ? (p2 * p4 * p6) : (p2 * p4 * p8);
		int c4 = step == 0 ? (p4 * p6 * p8) : (p2 * p6 * p8);

		// Determine if the current pixel has to be eliminated; 0 = "delete pixel", -1 = "keep pixel" 
		markerBuffer[cur] = (byte) ((a == 1 && b >= 2 && b <= 6 && c3 == 0 && c4 == 0) ? 0 : -1);
	    }
	}
	// Eliminate pixels and save result 
	for (int i = 0; i < buffer.length; ++i) {
	    buffer[i] = (byte) ((buffer[i] == -1 && markerBuffer[i] == -1) ? -1 : 0);
	}
	img.put(0, 0, buffer);
    }
    
    /**
     * <pre>
     * Creates a simplified Mat for pixel count from original image:
     * -Removes the noise;
     * -Transformes to black and white; 
     * -Removes any white borders;
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
	//Filters.thinning(mat);
	return mat;
    }
    //</editor-fold>
    
    protected Filters(){}

}
