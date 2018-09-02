package aps.letterhound.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * <pre>
 * Creates an input/output object to:
 * -Get a list of images (jpg, jpeg, png, bmp) not yet processed (in DONE_FOLDER) in a folder;
 * -Save OpenCV Mat to image file at DONE_FOLDER;
 * -Load OpenCV Mat from image file at IO folder;
 * -Append text to DONE_TXT_FILE at DONE_FOLDER.
 * Changed: 11/11/2017
 * </pre>
 */
public class IO {

    //<editor-fold defaultstate="collapsed" desc="Static">
    static{
	System.loadLibrary(Core.NATIVE_LIBRARY_NAME); //Load OpenCV library for reading files.
    }
    
    private static final String MSG_DONE_FOLDER_ERROR = "Couldn't create destination folder: ";
    private static final String MSG_FOLDER_ERROR = "Folder doesn't exist: ";
    private static final String MSG_SAVE_IMG_ERROR = "Couldn't create image file: ";
    private static final String MSG_SAVE_TXT_ERROR = "Couldn't create text file: ";
    
    public static final String DONE_FOLDER = "done";
    public static final String DONE_TXT_FILE = "descriptors.txt";
    public static final String[] SUPPORTED_IMG_EXTENSIONS = new String[] { "jpg", "jpeg", "png", "bmp" };
    
     /**
     * Creates an IO object linked to a folder, scanning it for non processed images.
     * @param folderpath absolute path to folder
     * @return an IO object
     * @throws IOException create DONE_FOLDER error
     */
    public static IO getIO(String folderpath) throws IOException{
	return IO.getIO(new File(folderpath));
    }
    
    /**
     * Creates an IO object linked to a folder, scanning it for non processed images.
     * @param folder folder file
     * @return an IO object
     * @throws IOException error creating DONE_FOLDER
     */
    public static IO getIO(File folder) throws IOException{
	IO io = new IO();
	if (!IO.checkFolder(folder)) {
	    throw new IOException(MSG_FOLDER_ERROR + folder.getAbsolutePath());
	}
	io.rootFolder = folder;
	io.doneFolder = new File(folder.getAbsolutePath() + "/" + DONE_FOLDER);
	io.textFile = new File(folder.getAbsolutePath() + "/" + DONE_FOLDER + "/" + DONE_TXT_FILE);
	io.updateImageList();
	return io;
    }
    
    protected static boolean checkFolder(File folder){
	return (folder.exists() && folder.isDirectory());
    }
    //</editor-fold>
    
    private File rootFolder;
    private File doneFolder;
    private File textFile;
    private boolean isDoneFolder;
    private List<File> images;
    
    protected IO(){}

    protected void createDoneFolder() throws IOException { 
	if(!IO.checkFolder(doneFolder)){
	    if(doneFolder.mkdir()){
		throw new IOException(MSG_DONE_FOLDER_ERROR+doneFolder.getAbsolutePath());
	    }
	}
	isDoneFolder = true;
    }

    /**
     * Updates list of images in IO folder, removing any processed image (in DONE_FOLDER) from it.
     */
    public void updateImageList(){
	LinkedList<File> files = new LinkedList<>();
	files.addAll(Arrays.asList(rootFolder.listFiles((File file) -> {
	    for (String extension : SUPPORTED_IMG_EXTENSIONS) {
		if (file.getName().toLowerCase().endsWith(extension)) {
		    return true;
		}
	    }
	    return false;
	})));

	if(IO.checkFolder(doneFolder)){
	    ArrayList<File> doneFiles = new ArrayList<>();
	    doneFiles.addAll(Arrays.asList(doneFolder.listFiles()));
	    doneFiles.stream().forEach((doneFile) -> {
		files.remove(new File(rootFolder.getAbsolutePath()+"/"+doneFile.getName()));
	    });
	}
	images = files;
    }
    
    /**
     * Saves OpenCV Mat to DONE_FOLDER with given filename, creating DONE_FOLDER if it doesn't exists.
     * @param mat OpenCV Mat
     * @param filename filename with extension
     * @throws IOException error saving image
     */
    public void saveMatToImg(Mat mat, String filename) throws IOException{
	if(!isDoneFolder) createDoneFolder(); //FIX problem?
	String filepath = doneFolder.getAbsolutePath()+"/"+filename;
	if(!Imgcodecs.imwrite(filepath,mat)){
	    throw new IOException(MSG_SAVE_IMG_ERROR+filepath);
	}
    }
    
    public static void saveMatToImg(Mat mat, File file) throws IOException{
	if(!Imgcodecs.imwrite(file.getAbsolutePath(),mat)){
	    throw new IOException(MSG_SAVE_IMG_ERROR+file.getAbsolutePath());
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
    
    public static Mat loadImagtoGrayMat(File file){
	return Imgcodecs.imread(file.getAbsolutePath(), Imgproc.COLOR_BGR2GRAY);
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
     * Append text to DONE_TXT_FILE in DONE_FOLDER, creating DONE_FOLDER if it doesn't exists.
     * @param text string to be appended
     * @throws IOException error writing to file
     */
    public void appendText(String text) throws IOException{
	if(!isDoneFolder) createDoneFolder(); //FIX problem?
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(textFile, true))) {
	    bw.write(text);
	    bw.newLine();
	} catch (IOException ex) {
	    throw new IOException(MSG_SAVE_TXT_ERROR+textFile);
	}
    }
    
    /**
     * Reads text from DONE_TXT_FILE if it exists.
     * @return string with file content
     * @throws IOException error reading file
     */
    public String importText() throws IOException{
	if(textFile.exists()){
	    StringBuilder text = new StringBuilder();
	    try(BufferedReader br = new BufferedReader(new FileReader(textFile))){
		while (br.readLine() != null) {
		    text.append(text);
		}
		return text.toString();
	    }
	}else{
	    return null;
	}
    }

    /**
     * Get list of non processed image files.
     * @return list with image files
     */
    public List<File> getImages() {return images;}

}
