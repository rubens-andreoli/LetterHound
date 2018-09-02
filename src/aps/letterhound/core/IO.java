package aps.letterhound.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * <pre>
 * Creates an input/output object linked to one folder to manipulate (load/save) image files 
 * (jpg, jpeg, png, bmp) into OpenCV Mat, dump filtered Mats into image files, 
 * and processed descriptors into a text file.
 * Changed: 12/11/2017
 * </pre>
 */
public class IO {

    //<editor-fold defaultstate="collapsed" desc="Static">
    static{
	System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //Load OpenCV library for reading files.
    }
    
    private static final String ERROR_DUMP_FOLDER = "Couldn't create destination folder: ";
    private static final String ERROR_FOLDER = "Folder doesn't exist: ";
    private static final String ERROR_EMPTY_FOLDER = "Folder is empty: ";
    private static final String ERROR_SAVE_IMG = "Couldn't create image file: ";
    private static final String ERROR_SAVE_TXT = "Couldn't create text file: ";
    private static final int FILE_NUM_LIMIT = 100000;
    private static final int FILE_NUM_PAD = 7;
    private static final String FILE_MARK = "_LH";
    private static final int NAME_MAX_TRIES = 1000;
    private static final String ERROR_NAME_GEN = "Coundn't create random name!";
    private static final String SAVE_IMG_EXTENSION = ".png";
    
    public static final String DUMP_FOLDER = "dump";
    public static final String DUMP_TXT_FILE = "descriptors.txt";
    public static final String[] SUPPORTED_IMG_EXTENSIONS = new String[] { "jpg", "jpeg", "png", "bmp" };
    
     /**
     * Creates an IO object linked to a folder, scanning it for SUPPORTED_IMG_EXTENSIONS images.
     * @param folderpath absolute path to folder
     * @return an IO object
     * @throws IOException: link folder doesn't exist; empty folder
     */
    public static IO getIO(String folderpath) throws IOException{
	return IO.getIO(new File(folderpath));
    }

     /**
     * Creates an IO object linked to a folder, scanning it for SUPPORTED_IMG_EXTENSIONS images.
     * @param folder folder file
     * @return an IO object
     * @throws IOException: link folder doesn't exist; empty folder
     */
    public static IO getIO(File folder) throws IOException{
	IO io = new IO();
	if (!IO.checkFolder(folder)) {
	    throw new IOException(ERROR_FOLDER + folder.getAbsolutePath());
	}
	io.rootFolder = folder;
	io.dumpFolder = new File(folder.getAbsolutePath() + "/" + DUMP_FOLDER);
	io.textFile = new File(folder.getAbsolutePath() + "/" + DUMP_FOLDER + "/" + DUMP_TXT_FILE);
	io.updateImageList();
	return io;
    }

    protected static boolean checkFolder(File folder){
	return (folder.exists() && folder.isDirectory());
    }
    
    /**
     * Saves an OpenCV Mat to given image file.
     * @param mat OpenCV Mat
     * @param image destination file
     * @throws IOException: failed to save image
     */
    public static void saveMatToImg(Mat mat, File image) throws IOException{
	if(!Imgcodecs.imwrite(image.getAbsolutePath(),mat)){
	    throw new IOException(ERROR_SAVE_IMG+image.getAbsolutePath());
	}
    }
    
    /**
     * Load an image file to an OpenCV object.
     * @param image file to be loaded
     * @return OpenCV Mat with image; null if failed
     */
    public static Mat loadImgToMat(File image){
	return Imgcodecs.imread(image.getAbsolutePath());
    }
    
    /**
     * Load an image file to a grayscale OpenCV object.
     * @param image file to be loaded
     * @return OpenCV Mat with grayscale image; null if failed
     */
    public static Mat loadImagtoGrayMat(File image){
	return Imgcodecs.imread(image.getAbsolutePath(), Imgproc.COLOR_BGR2GRAY);
    }
    
    protected static String lPadZero(int in, int fill) {
	int value, len = 0;
	value = in;
	if (value == 0) {
	    len = 1;
	} else {
	    for (; value != 0; len++) {
		value /= 10;
	    }
	}
	StringBuilder sb = new StringBuilder();
	for (int i = fill; i > len; i--) {
	    sb.append('0');
	}
	sb.append(in);
	return sb.toString();
    }
    //</editor-fold>
    
    private File rootFolder;
    private File dumpFolder;
    private File textFile;
    private boolean isDumpFolder;
    private List<File> images;
    
    protected IO(){}

    protected void createDumpFolder() throws IOException { 
	if(!IO.checkFolder(dumpFolder)){
	    try {
		Files.createDirectory(dumpFolder.toPath());
	    } catch (IOException ex) {
		throw new IOException(ERROR_DUMP_FOLDER+dumpFolder.getAbsolutePath());
	    }
	}
	isDumpFolder = true;
    }

    /**
     * Scans IO linked folder for SUPPORTED_IMG_EXTENSIONS images and updates 
     * "images" attribute list.
     * @throws IOException: empty folder
     */
    public void updateImageList() throws IOException{
	LinkedList<File> files = new LinkedList<>();
	files.addAll(Arrays.asList(rootFolder.listFiles((File file) -> {
	    for (String extension : SUPPORTED_IMG_EXTENSIONS) {
		if (file.getName().toLowerCase().endsWith(extension)) {
		    return true;
		}
	    }
	    return false;
	})));
	if(files.isEmpty()){
	    throw new IOException(ERROR_EMPTY_FOLDER+rootFolder.getAbsolutePath());
	}
	images = files;
    }
    
    /**
     * Saves OpenCV Mat to DUMP_FOLDER with given filename, creating DUMP_FOLDER if it doesn't exists.
     * @param mat OpenCV Mat
     * @param filename filename with extension
     * @throws IOException: error saving image
     */
    public void saveMatToImg(Mat mat, String filename) throws IOException{
	if(!isDumpFolder) createDumpFolder();
	String filepath = dumpFolder.getAbsolutePath()+"/"+filename;
	if(!Imgcodecs.imwrite(filepath,mat)){
	    throw new IOException(ERROR_SAVE_IMG+filepath);
	}
    }

    public void saveMatToImg(Mat mat, char letter) throws IOException{
	String randomName;
	String filepath;
	File file;	
	int tries = 0;
	boolean found = false;
	
	do{
	    randomName = letter
		    +FILE_MARK
		    +lPadZero((int)(Math.random()*FILE_NUM_LIMIT), FILE_NUM_PAD)
		    +SAVE_IMG_EXTENSION;
	    filepath = rootFolder.getAbsolutePath()+"/"+randomName;
	    file = new File(filepath);
	    tries++;
	}while((found = images.contains(file)) || tries < NAME_MAX_TRIES);
	
	if(found){
	  throw new IOException(ERROR_NAME_GEN);  
	}else{
	    if(!Imgcodecs.imwrite(filepath, mat)){
		throw new IOException(ERROR_SAVE_IMG+filepath);
	    }
	    images.add(file);
	}
    }

    /**
     * Load image to grayscale OpenCV Mat from IO folder with given filename.
     * @param filename filename with extension
     * @return grayscale OpenCV Mat, null if failed loading
     */
    public Mat loadImgToGrayMat(String filename){
	return Imgcodecs.imread(rootFolder.getAbsolutePath()+"/"+filename, Imgproc.COLOR_BGR2GRAY);
    }
        
    /**
     * Load image to OpenCV Mat from IO folder with given filename.
     * @param filename filename with extension
     * @return OpenCV Mat, null if failed loading
     */
    public Mat loadImgToMat(String filename){
	return Imgcodecs.imread(rootFolder.getAbsolutePath()+"/"+filename);
    }
 
    /**
     * Append text to DUMP_TXT_FILE in DUMP_FOLDER, creating DUMP_FOLDER if it doesn't exists.
     * @param text string to be appended
     * @throws IOException error writing to file
     */
    public void appendText(String text) throws IOException{
	if(!isDumpFolder) createDumpFolder();
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile, true))) {
	    bw.write(text);
	    bw.newLine();
	} catch (IOException ex) {
	    throw new IOException(ERROR_SAVE_TXT+textFile);
	}
    }
    
    /**
     * Get list of SUPPORTED_IMG_EXTENSIONS scanned image files.
     * @return list with image files
     */
    public List<File> getImages() {return images;}

}
