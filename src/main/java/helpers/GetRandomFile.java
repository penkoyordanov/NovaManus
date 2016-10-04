package helpers;

import java.io.File;
import java.util.Random;

public class GetRandomFile {
	
	public static String getRandomFileToUpload(){
		File my_dir = new File("D:\\AutoIT\\fileUpload");
		assert(my_dir.exists()); // the directory exists
		assert(my_dir.isDirectory()); // and is actually a directory

		
		String[] filenames_in_dir = my_dir.list();
		String random = (filenames_in_dir[new Random().nextInt(filenames_in_dir.length)]);
		System.out.println("Image file path: "+my_dir.getAbsolutePath()+"\\"+random);
		
		return my_dir.getAbsolutePath()+"\\"+random;
	}
	

}
