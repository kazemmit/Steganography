/*
 * This project was created by Kazem Qazanfari as a part of his research in the field of data hiding.
 * You are free to use or edit this code for your research.
 * If you use this project, or some parts of code please kindly cite the following papers:
 * 
 * Qazanfari, Kazem, and Reza Safabakhsh. "A new steganography method which preserves histogram: Generalization of LSB++." 
 * Information Sciences 277 (2014): 90-101.
 * 
 * 
 * Ghazanfari, Kazem, Shahrokh Ghaemmaghami, and Saeed R. Khosravi. "LSB++: an improvement to LSB+ steganography."
 * TENCON 2011-2011 IEEE Region 10 Conference. IEEE, 2011.
 */



import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.*;
import java.awt.color.ColorSpace;


public class CreateStego {
	/*
	 * * ****NOTE****
	 * 
	 * The purpose of implementing this project is to provide the embedding behavior of some baseline steganography methods including: LSB, LSB+ [LSBP], LSB++ [LSBPP], LSB Matching [LSBM], LSB Matching Revisited [LSBMR].
	 * Actually, we are going to provide/simulate the embedding process of some steganography methods, which creates the exact statistical artifacts that are created by these steganography methods.  
	 * so researchers in the field of data hiding [steganography/steganalysis] will be able to compare their methods [steganography/steganalysis] to these baseline methods.
	 *  
	 * So,1) we use a pseudo random generator (using java random library) as the encrypted message that should be embedded. 
	 * 			therefore, you do not need to provide the message. 
	 *    2) by using a random process, we simulate the embedding key
	 *    		therefore, you do not need to provide the embedding key. 
	 *    3) we just provide the embedding process
	 *    		therefore, you can use the stego images created by these methods and analyze them. 
	 */
	 
	 
	public static void LSB(String BMPFileName, int MessageLength)  {
		 /*
		 * Params:
		 * 		1- BMPFileName: input image [path and file name] like /User/kazemmit/Documents/test.bmp
		 * 		2- MessageLength: number of bits that you want to hide in the input image like 1000
		 * LSB embedding method embeds the message bits [0s and 1s] based on embedding key [this key is used to select a target pixel to embed the message bit]. 
		 * So, if the message bit [i] is zero, LSB embedding method changes the least significant bit of the pixel to zero;
		 * and if the message bit [i] is one , LSB embedding method changes the least significant bit of the pixel to one.
		 * 
		 */
		try{
			// Read the input image
			BufferedImage image = ImageIO.read(new File(BMPFileName)); 
			
			// Extract the color space of input image (gray or color [RBG])
			ColorSpace cs = image.getColorModel().getColorSpace();
			boolean isGrayscale = cs.getType() == ColorSpace.TYPE_GRAY;
			float Probability = 0;

			// Calculating the embedding rate [bit per pixel]
			if (isGrayscale)
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight());
			else
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight()*3);

			//System.out.println(isGrayscale);

			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
			{
				for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
				{
					// Extract the gray value of the current pixel [for gray scale images]
					// Extract the red value of the current pixel [for color images]
					// Extract the green value of the current pixel [for color images]
					// Extract the blue value of the current pixel [for color images]

					Color c = new Color(image.getRGB(xPixel, yPixel));
					int blue_channel = c.getBlue();
					int green_channel = c.getGreen();
					int red_channel = c.getRed();
					int gray = c.getBlue();

					if (isGrayscale){ //For gray scale images
						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability) //Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){ //Simulating the encrypted message
								// message bit is 0
								gray = (gray/2)*2;
								//System.out.println(0);
							}
							else{
								// message bit is 1
								gray = (gray/2)*2+1;
								//System.out.println(1);
							}
						}
						image.setRGB(xPixel, yPixel, 256*256*gray+256*gray+gray);	
						//c = new Color(image.getRGB(xPixel, yPixel));
						//System.out.println(c.getBlue());

					}
					else //Color Image
					{
						// Blue channel
						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5)//Simulating the encrypted message
								// message bit is 0
								blue_channel = (blue_channel/2)*2;
							else
								// message bit is 1
								blue_channel = (blue_channel/2)*2+1;
						}
						// Red channel
						rand = new Random();
						P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5)//Simulating the encrypted message
								// message bit is 0
								red_channel = (red_channel/2)*2;
							else
								// message bit is 1
								red_channel = (red_channel/2)*2+1;
						}
						// Green channel
						rand = new Random();
						P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5)//Simulating the encrypted message
								// message bit is 0
								green_channel = (green_channel/2)*2;
							else
								// message bit is 1
								green_channel = (green_channel/2)*2+1;
						}
						image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);
					}        
				}
			}
			//The result [stego image] will be stored in the same input path, and the file name will be changed to:
			// inputfilename.bmp.LSB.bmp
			
			// For example if the input file name and path is "/User/kazemmit/Documents/test.bmp" the result will be a stego image
			// with following file name and path:
			//"/User/kazemmit/Documents/test.bmp.LSB.bmp"
			
			File outputfile = new File(BMPFileName+".LSB.bmp");
			ImageIO.write(image, "bmp", outputfile);
		}
		catch (Exception err)
		{
			System.out.println(err.toString());
		}
	}

	public static void LSBPLUS_LSBPLUSPLUS(String BMPFileName, int MessageLength, boolean LSBPP)  {
		/*
		* Params:
		* 		1- BMPFileName: input image [path and file name] like /User/kazemmit/Documents/test.bmp
		* 		2- MessageLength: number of bits that you want to hide in the input image like 1000
		* 		3- LSBPP: determines which steganography method to run: false: LSB+, true: LSB++
		* For the details about this method, check the paper with title "LSB Matching Revisited"
		* This function is the implementation of both LSB+ and LSB++ methods
		* Both of these methods keep the histogram of stego image exact like cover image
		* Which means the histogram of image before and after the embedding process are the same.
		* For the details about this method, check one of these papers:
		* 
		* "LSB++: an improvement to LSB+ steganography"
		* or
		* "A new steganography method which preserves histogram: Generalization of LSB++"
		*/
		try{
			// Read the input image
			BufferedImage image = ImageIO.read(new File(BMPFileName));
			
			// Extract the color space of input image (gray or color [RBG])
			ColorSpace cs = image.getColorModel().getColorSpace();
			boolean isGrayscale = cs.getType() == ColorSpace.TYPE_GRAY;
			float Probability = 0;

			// Calculating the embedding rate [bit per pixel]
			if (isGrayscale)
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight());
			else
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight()*3);

			//System.out.println(isGrayscale);

			int [] gray_hist = new int[256];
			int [] red_hist = new int[256];
			int [] green_hist = new int[256];
			int [] blue_hist = new int[256];
			
			int [] gray_hist_Embeded_data = new int[256];
			int [] red_hist_Embeded_data = new int[256];
			int [] green_hist_Embeded_data = new int[256];
			int [] blue_hist_Embeded_data = new int[256];

			int [] gray_hist_stego = new int[256];
			int [] red_hist_stego = new int[256];
			int [] green_hist_stego = new int[256];
			int [] blue_hist_stego = new int[256];
			boolean [] gray_bin_available = new boolean[128];
			boolean [] green_bin_available = new boolean[128];
			boolean [] red_bin_available = new boolean[128];
			boolean [] blue_bin_available = new boolean[128];
			
			int [] gray_hist_dif = new int[256];
			int [] red_hist_dif = new int[256];
			int [] green_hist_dif = new int[256];
			int [] blue_hist_dif = new int[256];
			

			// Calculating the image histogram
			// If the input image is a gray image, calculate the histogram just for gray channel
			// If the input image is a color image, calculate the histogram of red, blue and green channels separately
			
			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
			{
				for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
				{
					Color c = new Color(image.getRGB(xPixel, yPixel));
					
					
					
					if (isGrayscale){
						int gray = c.getBlue();
						gray_hist[gray] +=1;
					}
					else{
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						red_hist[red_channel] +=1;
						blue_hist[blue_channel] +=1;
						green_hist[green_channel] +=1;
					}
				}
			}
			
			// This part of code is used for LSB++ method 
			//Check the above mentioned papers for better understanding the code of this function
			// Calculating the different between two bins
			if (LSBPP){
				for (int i=0;i<128;i++){
					if (isGrayscale){ // For gray scale image
						if (gray_hist[i*2]>gray_hist[i*2+1])
							gray_hist_dif[i*2] = gray_hist[i*2]-gray_hist[i*2+1];
						if (gray_hist[i*2]<gray_hist[i*2+1])
							gray_hist_dif[i*2+1] = gray_hist[i*2+1]-gray_hist[i*2];
					}
					else{// For Color image
						if (green_hist[i*2]>green_hist[i*2+1])
							green_hist_dif[i*2] = green_hist[i*2]-green_hist[i*2+1];
						if (green_hist[i*2]<green_hist[i*2+1])
							green_hist_dif[i*2+1] = green_hist[i*2+1]-green_hist[i*2];
						if (blue_hist[i*2]>blue_hist[i*2+1])
							blue_hist_dif[i*2] = blue_hist[i*2]-blue_hist[i*2+1];
						if (blue_hist[i*2]<blue_hist[i*2+1])
							blue_hist_dif[i*2+1] = blue_hist[i*2+1]-blue_hist[i*2];
						if (red_hist[i*2]>red_hist[i*2+1])
							red_hist_dif[i*2] = red_hist[i*2]-red_hist[i*2+1];
						if (red_hist[i*2]<red_hist[i*2+1])
							red_hist_dif[i*2+1] = red_hist[i*2+1]-red_hist[i*2];
					}
				}
			}
			
			ArrayList locked_pixels_gray = new ArrayList();
			ArrayList locked_pixels_red = new ArrayList();
			ArrayList locked_pixels_green = new ArrayList();
			ArrayList locked_pixels_blue = new ArrayList();
			
			// This part of code is used for LSB++ mthod
			// Check the above mentioned papers for better understanding the code of this function
			// Locking Process [refer to the mentioned papers]
			// Locked pixel will be never used on embedding process
			
			if (LSBPP){
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						// Extract the gray value of the current pixel [for gray scale images]
						// Extract the red value of the current pixel [for color images]
						// Extract the green value of the current pixel [for color images]
						// Extract the blue value of the current pixel [for color images]
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();
						
						if (isGrayscale){
							if (gray_hist_dif[gray]>0){
								gray_hist_dif[gray] -=1;
								locked_pixels_gray.add(image.getHeight()*xPixel+yPixel);
								
							}
						}
						else //Color Image
						{
							if (green_hist_dif[green_channel]>0){
								green_hist_dif[green_channel] -=1;
								locked_pixels_green.add(image.getHeight()*xPixel+yPixel);
							}
							if (red_hist_dif[red_channel]>0){
								red_hist_dif[red_channel] -=1;
								locked_pixels_red.add(image.getHeight()*xPixel+yPixel);
							}
							if (blue_hist_dif[blue_channel]>0){
								blue_hist_dif[blue_channel] -=1;
								locked_pixels_blue.add(image.getHeight()*xPixel+yPixel);
							}
							
						}
						
					}
				}
			}
			ArrayList used_pixels_gray = new ArrayList();
			ArrayList used_pixels_red = new ArrayList();
			ArrayList used_pixels_green = new ArrayList();
			ArrayList used_pixels_blue = new ArrayList();
			
			if (isGrayscale){ // For gray scale images
				
				Arrays.fill(gray_bin_available, Boolean.TRUE);
				
				Arrays.fill(gray_hist_Embeded_data, 0);
				
				Arrays.fill(gray_hist_stego, 0);
				
				boolean failed_to_hide = false;
				
				// Both LSB+ and LSB++: after calculating of image histogram, if one of bins [of a unit] is zero, then non of pixels related that unit will be used for embedding process
				// [more info: refer to above mentioned papers]
				for (int i=0;i<128;i++){
					if ((gray_hist[i*2]==0) || (gray_hist[i*2+1]==0))
						gray_bin_available[i] = false;
				}
				
				// Embedding process
				// [more info: refer to above mentioned papers]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int gray = c.getBlue();

						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability || failed_to_hide)//Simulating the embedding key
						{
							if (LSBPP){
								if (locked_pixels_gray.indexOf(image.getHeight()*xPixel+yPixel)>-1){
									failed_to_hide = true;
									continue;
								}
							}
							if (!gray_bin_available[(gray/2)]){
								failed_to_hide = true;
								continue;
							}
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								gray = (gray/2)*2;
							}
							else{
								gray = (gray/2)*2+1;
							}
							failed_to_hide = false;
							image.setRGB(xPixel, yPixel, 256*256*gray+256*gray+gray);
							gray_hist_Embeded_data[gray] +=1;
							used_pixels_gray.add(image.getHeight()*xPixel+yPixel);
							if (gray_hist[gray] == gray_hist_Embeded_data[gray])
								gray_bin_available[gray/2] = false;
						}
					}

				}
				
				// Calculating of stego image histogram
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int gray = c.getBlue();
						gray_hist_stego[gray] +=1;
					}
				}
				
				// Intentional embedding for preserving [restoring] the cover histogram
				// So, after this step the cover and stego histograms will be the same
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						if (used_pixels_gray.indexOf(image.getHeight()*xPixel+yPixel)>-1)
							continue;
							
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int gray = c.getBlue();
						if (gray_hist[(gray/2)*2+1] < gray_hist_stego[(gray/2)*2+1])
							if (gray == (gray/2)*2+1){
								gray = (gray/2)*2;
								gray_hist_stego[(gray/2)*2+1] -=1;
								gray_hist_stego[(gray/2)*2] +=1;
							}
						
						if (gray_hist[(gray/2)*2] < gray_hist_stego[(gray/2)*2])
							if (gray == (gray/2)*2){
								gray = (gray/2)*2+1;
								gray_hist_stego[(gray/2)*2] -=1;
								gray_hist_stego[(gray/2)*2+1] +=1;
							}	
						image.setRGB(xPixel, yPixel, 256*256*gray+256*gray+gray);		            		
					}
				}
			}
			else //Color Image
			{
				// Green Channel
				boolean failed_to_hide = false;
				Arrays.fill(green_bin_available, Boolean.TRUE);
				
				Arrays.fill(green_hist_Embeded_data, 0);
				
				Arrays.fill(green_hist_stego, 0);
				
				
				for (int i=0;i<128;i++){
					if ((green_hist[i*2]==0) || (green_hist[i*2+1]==0))
						green_bin_available[i] = false;
				}
				
				// Embedding process
				// [more info: refer to above mentioned papers]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();


						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability || failed_to_hide)//Simulating the embedding key
						{
							if (LSBPP){
								if (locked_pixels_green.indexOf(image.getHeight()*xPixel+yPixel)>-1){
									failed_to_hide = true;
									continue;
								}
							}
							if (!green_bin_available[(green_channel/2)]){
								failed_to_hide = true;
								continue;
							}
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								green_channel = (green_channel/2)*2;
							}
							else{
								green_channel = (green_channel/2)*2+1;
							}
							failed_to_hide = false;
							image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);		            		
							green_hist_Embeded_data[green_channel] +=1;
							used_pixels_green.add(image.getHeight()*xPixel+yPixel);
							if (green_hist[green_channel] == green_hist_Embeded_data[green_channel])
								green_bin_available[green_channel/2] = false;
						}
					}

				}
				// Calculating of stego image histogram [green channel]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();

						green_hist_stego[green_channel] +=1;
					}
				}
				
				// Intentional embedding for preserving [restoring] the cover histogram
				// So, after this step the cover and stego histograms will be the same
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						if (used_pixels_green.indexOf(image.getHeight()*xPixel+yPixel)>-1)
							continue;
							
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();
						if (green_hist[(green_channel/2)*2+1] < green_hist_stego[(green_channel/2)*2+1])
							if (green_channel == (green_channel/2)*2+1){
								green_channel = (green_channel/2)*2;
								green_hist_stego[(green_channel/2)*2+1] -=1;
								green_hist_stego[(green_channel/2)*2] +=1;
							}
						
						if (green_hist[(green_channel/2)*2] < green_hist_stego[(green_channel/2)*2])
							if (green_channel == (green_channel/2)*2){
								green_channel = (green_channel/2)*2+1;
								green_hist_stego[(green_channel/2)*2] -=1;
								green_hist_stego[(green_channel/2)*2+1] +=1;
							}	
						image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);		            		
					}
				}
				// Blue Channel
				failed_to_hide = false;
				
				Arrays.fill(blue_bin_available, Boolean.TRUE);
				
				Arrays.fill(blue_hist_Embeded_data, 0);
				
				Arrays.fill(blue_hist_stego, 0);
				
				for (int i=0;i<128;i++){
					if ((blue_hist[i*2]==0) || (blue_hist[i*2+1]==0))
						blue_bin_available[i] = false;
				}
				
				// Embedding process
				// [more info: refer to above mentioned papers]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();


						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability || failed_to_hide)//Simulating the embedding key
						{
							if (LSBPP){
								if (locked_pixels_blue.indexOf(image.getHeight()*xPixel+yPixel)>-1){
									failed_to_hide = true;
									continue;
								}
							}
							if (!blue_bin_available[(blue_channel/2)]){
								failed_to_hide = true;
								continue;
							}
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								blue_channel = (blue_channel/2)*2;
							}
							else{
								blue_channel = (blue_channel/2)*2+1;
							}
							failed_to_hide = false;
							image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);		            		
							blue_hist_Embeded_data[blue_channel] +=1;
							used_pixels_blue.add(image.getHeight()*xPixel+yPixel);
							if (blue_hist[blue_channel] == blue_hist_Embeded_data[blue_channel])
								blue_bin_available[blue_channel/2] = false;
						}
					}

				}
				
				// Calculating of stego image histogram [blue channel]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();

						blue_hist_stego[blue_channel] +=1;
					}
				}
				
				// Intentional embedding for preserving [restoring] the cover histogram
				// So, after this step the cover and stego histograms will be the same
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						if (used_pixels_blue.indexOf(image.getHeight()*xPixel+yPixel)>-1)
							continue;
							
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();
						if (blue_hist[(gray/2)*2+1] < blue_hist_stego[(blue_channel/2)*2+1])
							if (blue_channel == (blue_channel/2)*2+1){
								blue_channel = (blue_channel/2)*2;
								blue_hist_stego[(blue_channel/2)*2+1] -=1;
								blue_hist_stego[(blue_channel/2)*2] +=1;
							}
						
						if (blue_hist[(blue_channel/2)*2] < blue_hist_stego[(blue_channel/2)*2])
							if (blue_channel == (blue_channel/2)*2){
								blue_channel = (blue_channel/2)*2+1;
								blue_hist_stego[(blue_channel/2)*2] -=1;
								blue_hist_stego[(blue_channel/2)*2+1] +=1;
							}	
						image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);		            		
					}
				}
				// Red Channel
				failed_to_hide = false;
				Arrays.fill(red_bin_available, Boolean.TRUE);
				
				Arrays.fill(red_hist_Embeded_data, 0);
				
				Arrays.fill(red_hist_stego, 0);
				
				for (int i=0;i<128;i++){
					if ((red_hist[i*2]==0) || (red_hist[i*2+1]==0))
						red_bin_available[i] = false;
				}
				
				// Embedding process
				// [more info: refer to above mentioned papers]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();


						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability || failed_to_hide)//Simulating the embedding key
						{
							if (LSBPP){
								if (locked_pixels_red.indexOf(image.getHeight()*xPixel+yPixel)>-1){
									failed_to_hide = true;
									continue;
								}
							}
							if (!red_bin_available[(red_channel/2)]){
								failed_to_hide = true;
								continue;
							}
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								red_channel = (red_channel/2)*2;
							}
							else{
								red_channel = (red_channel/2)*2+1;
							}
							failed_to_hide = false;
							image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);		            		
							red_hist_Embeded_data[red_channel] +=1;
							used_pixels_red.add(image.getHeight()*xPixel+yPixel);
							if (red_hist[red_channel] == red_hist_Embeded_data[red_channel])
								red_bin_available[red_channel/2] = false;
						}
					}

				}
				
				// Calculating of stego image histogram [red channel]
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();

						red_hist_stego[red_channel] +=1;
					}
				}
				
				// Intentional embedding for preserving [restoring] the cover histogram
				// So, after this step the cover and stego histograms will be the same
				for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
				{
					for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
					{
						if (used_pixels_red.indexOf(image.getHeight()*xPixel+yPixel)>-1)
							continue;
							
						Color c = new Color(image.getRGB(xPixel, yPixel));
						int blue_channel = c.getBlue();
						int green_channel = c.getGreen();
						int red_channel = c.getRed();
						int gray = c.getBlue();
						if (red_hist[(red_channel/2)*2+1] < red_hist_stego[(red_channel/2)*2+1])
							if (red_channel == (red_channel/2)*2+1){
								red_channel = (red_channel/2)*2;
								red_hist_stego[(red_channel/2)*2+1] -=1;
								red_hist_stego[(red_channel/2)*2] +=1;
							}
						
						if (red_hist[(red_channel/2)*2] < red_hist_stego[(red_channel/2)*2])
							if (red_channel == (red_channel/2)*2){
								red_channel = (red_channel/2)*2+1;
								red_hist_stego[(red_channel/2)*2] -=1;
								red_hist_stego[(red_channel/2)*2+1] +=1;
							}	
						image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);		            		
					}
				}

			}
			//The result [stego image] will be stored in the same input path, and the file name will be changed to:
			//for LSBPLUS: inputfilename.bmp.LSBP.bmp
			//for LSBPLUS-PLUS: inputfilename.bmp.LSBPP.bmp
			
			// For example if the input file name and path is "/User/kazemmit/Documents/test.bmp" the result will be a stego image
			// with following file name and path:
			
			//for LSBPLUS: "/User/kazemmit/Documents/test.bmp.LSBP.bmp"
			//for LSBPLUS-PLUS: "/User/kazemmit/Documents/test.bmp.LSBPP.bmp"
			
			if (LSBPP){ // for LSB++  method
				File outputfile = new File(BMPFileName+".LSBPP.bmp");
				ImageIO.write(image, "bmp", outputfile);
			}
			else// for LSB+  method
			{
				File outputfile = new File(BMPFileName+".LSBP.bmp");
				ImageIO.write(image, "bmp", outputfile);
			}
		}
		catch (Exception err)
		{
			System.out.println(err.toString());
		}
	}

	


	public static void LSBM(String BMPFileName, int MessageLength)  {
		/*
		 * Params:
		 * 		1- BMPFileName: input image [path and file name] like /User/kazemmit/Documents/test.bmp
		 * 		2- MessageLength: number of bits that you want to hide in the input image like 1000
		 * LSBM embedding method embeds the message bits [0s and 1s] based on embedding key [this key is used to select a target pixel to embed the message bit]. 
		 * So, if the message bit [i] and least significant bit of the pixel are the same, nothing will be changes;
		 * however, if the message bit [i] and least significant bit of the pixel are NOT the same, then, the pixel value will be decreased or increased by 1 with the same probability.
		 * 
		 */
		
		try{
			// Read the input image
			BufferedImage image = ImageIO.read(new File(BMPFileName));
			
			// Extract the color space of input image (gray or color [RBG])
			ColorSpace cs = image.getColorModel().getColorSpace();
			boolean isGrayscale = cs.getType() == ColorSpace.TYPE_GRAY;
			float Probability = 0;

			// Calculating the embedding rate [bit per pixel]
			if (isGrayscale)
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight());
			else
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight()*3);

			//System.out.println(isGrayscale);

			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
			{
				for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
				{
					// Extract the gray value of the current pixel [for gray scale images]
					// Extract the red value of the current pixel [for color images]
					// Extract the green value of the current pixel [for color images]
					// Extract the blue value of the current pixel [for color images]
					Color c = new Color(image.getRGB(xPixel, yPixel));
					int blue_channel = c.getBlue();
					int green_channel = c.getGreen();
					int red_channel = c.getRed();
					int gray = c.getBlue();

					if (isGrayscale){
						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								// message bit and lease significant bit of current pixel are not the same
								float P_change_type   = rand.nextFloat();
								if (P_change_type <= 0.5){ 
									//Decreasing the pixel value
									if (gray == 0)
										gray +=1;
									else if (gray == 255)
										gray -=1;
									else
										gray -=1;
								}
								else//Increasing the pixel value
								{
									if (gray == 0)
										gray +=1;
									else if (gray == 255)
										gray -=1;
									else
										gray +=1;
								}
								//System.out.println(0);
							}
							else{
								// message bit and lease significant bit of current pixel are the same.
							}
						}
						image.setRGB(xPixel, yPixel, 256*256*gray+256*gray+gray);	
						//c = new Color(image.getRGB(xPixel, yPixel));
						//System.out.println(c.getBlue());

					}
					else // Color image
					{
						// Red Channel
						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								// message bit and lease significant bit of current pixel are not the same
								float P_change_type   = rand.nextFloat();
								if (P_change_type <= 0.5){
									//Decreasing the pixel value
									if (red_channel == 0)
										red_channel +=1;
									else if (red_channel == 255)
										red_channel -=1;
									else
										red_channel -=1;
								}
								else//Increasing the pixel value
								{
									if (red_channel == 0)
										red_channel +=1;
									else if (red_channel == 255)
										red_channel -=1;
									else
										red_channel +=1;
								}
								//System.out.println(0);
							}
							else{
								// message bit and lease significant bit of current pixel are the same.
							}
						}
						
						// Green Channel
						rand = new Random();
						P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								// message bit and lease significant bit of current pixel are not the same
								float P_change_type   = rand.nextFloat();
								if (P_change_type <= 0.5){
									//Decreasing the pixel value
									if (green_channel == 0)
										green_channel +=1;
									else if (green_channel == 255)
										green_channel -=1;
									else
										green_channel -=1;
								}
								else//Increasing the pixel value
								{
									if (green_channel == 0)
										green_channel +=1;
									else if (green_channel == 255)
										green_channel -=1;
									else
										green_channel +=1;
								}
								//System.out.println(0);
							}
							else{
								// message bit and lease significant bit of current pixel are the same.
							}
						}

						//	Blue Channel
						rand = new Random();
						P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							float P_change   = rand.nextFloat();
							if (P_change <= 0.5){//Simulating the encrypted message
								// message bit and lease significant bit of current pixel are not the same
								float P_change_type   = rand.nextFloat();
								if (P_change_type <= 0.5){
									//Decreasing the pixel value
									if (blue_channel == 0)
										blue_channel +=1;
									else if (blue_channel == 255)
										blue_channel -=1;
									else
										blue_channel -=1;
								}
								else//Increasing the pixel value
								{
									if (blue_channel == 0)
										blue_channel +=1;
									else if (blue_channel == 255)
										blue_channel -=1;
									else
										blue_channel +=1;
								}
								//System.out.println(0);
							}
							else{
								// message bit and lease significant bit of current pixel are the same.
							}
						}
						image.setRGB(xPixel, yPixel, 256*256*red_channel+256*green_channel+blue_channel);
					}        
				}
			}
			//The result [stego image] will be stored in the same input path, and the file name will be changed to:
			// inputfilename.bmp.LSBM.bmp
			
			// For example if the input file name and path is "/User/kazemmit/Documents/test.bmp" the result will be a stego image
			// with following file name and path:
			//"/User/kazemmit/Documents/test.bmp.LSBM.bmp"
			
			File outputfile = new File(BMPFileName+".LSBM.bmp");
			ImageIO.write(image, "bmp", outputfile);
		}
		catch (Exception err)
		{
			System.out.println(err.toString());
		}
	}
	private static int f_LSBMR(int xi,int xj){
		return ((xi/2)+xj) - (((xi/2)+xj)/2)*2;
	}
	private static int LSB_bit(int x){
		return x - (x/2)*2;
	}
	public static void LSBMR(String BMPFileName, int MessageLength)  {
		/*
		* Params:
		* 		1- BMPFileName: input image [path and file name] like /User/kazemmit/Documents/test.bmp
		* 		2- MessageLength: number of bits that you want to hide in the input image like 1000
		* For the details about this method, search for the paper with title "LSB Matching Revisited" 
		*/
		
		try{
			// Read the input image
			BufferedImage image = ImageIO.read(new File(BMPFileName));
			
			// Extract the color space of input image (gray or color [RBG])
			ColorSpace cs = image.getColorModel().getColorSpace();
			boolean isGrayscale = cs.getType() == ColorSpace.TYPE_GRAY;
			float Probability = 0;

			// Calculating the embedding rate [bit per pixel]
			if (isGrayscale)
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight());
			else
				Probability = (float)MessageLength / (image.getWidth()*image.getHeight()*3);

			//System.out.println(isGrayscale);

			for (int xPixel = 0; xPixel < image.getWidth()/2; xPixel++)
			{
				for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
				{
					// Extract the gray value of the pixel xi and xj [for gray scale images]
					// Extract the red value of the pixel xi and xj [for color images]
					// Extract the green value of the pixel xi and xj [for color images]
					// Extract the blue value of the pixel xi and xj [for color images]
					
					Color c_1 = new Color(image.getRGB(xPixel*2, yPixel));
					int blue_channel_1 = c_1.getBlue();
					int green_channel_1 = c_1.getGreen();
					int red_channel_1 = c_1.getRed();
					int gray_1 = c_1.getBlue();

					Color c_2 = new Color(image.getRGB(xPixel*2+1, yPixel));
					int blue_channel_2 = c_2.getBlue();
					int green_channel_2 = c_2.getGreen();
					int red_channel_2 = c_2.getRed();
					int gray_2 = c_2.getBlue();



					if (isGrayscale){// For gray scale images
						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							int mi = 0;
							int mj = 0;
							float P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mi = 1;

							P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mj = 1;
							//Check the above mentioned paper for better understanding the code of this function
							if (mi==LSB_bit(gray_1)){ 
								if (mj!=f_LSBMR(gray_1,gray_2))
								{
									float P_increase_decrease   = rand.nextFloat();
									if (P_increase_decrease <= 0.5){
										if (gray_2<255)
											gray_2 +=1;
									}
									else{
										if (gray_2>0)
											gray_2 -=1;
									}
								}
							}
							else
							{
								if (mj==f_LSBMR(gray_1-1,gray_2)){
									if (gray_1>0)
										gray_1 -=1;
								}
								else{
									if (gray_1<255)
										gray_1 +=1;
								}	
							}


						}
						image.setRGB(xPixel*2, yPixel, 256*256*gray_1+256*gray_1+gray_1);	
						image.setRGB(xPixel*2+1, yPixel, 256*256*gray_2+256*gray_2+gray_2);	
					}
					else // Color Image
					{
						
						// Red channel
						Random rand = new Random();
						float P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							int mi = 0;
							int mj = 0;
							float P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mi = 1;

							P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mj = 1;
							//Check the above mentioned paper for better understanding the code of this function
							if (mi==LSB_bit(red_channel_1)){
								if (mj!=f_LSBMR(red_channel_1,red_channel_2))
								{
									float P_increase_decrease   = rand.nextFloat();
									if (P_increase_decrease <= 0.5){
										if (red_channel_2<255)
											red_channel_2 +=1;
									}
									else{
										if (red_channel_2>0)
											red_channel_2 -=1;
									}
								}
							}
							else
							{
								if (mj==f_LSBMR(red_channel_1-1,red_channel_2)){
									if (red_channel_1>0)
										red_channel_1 -=1;
								}
								else{
									if (red_channel_1<255)
										red_channel_1 +=1;
								}	
							}

						}

						// Blue channel
						rand = new Random();
						P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							int mi = 0;
							int mj = 0;
							float P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mi = 1;

							P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mj = 1;
							//Check the above mentioned paper for better understanding the code of this function
							if (mi==LSB_bit(blue_channel_1)){
								if (mj!=f_LSBMR(blue_channel_1,blue_channel_2))
								{
									float P_increase_decrease   = rand.nextFloat();
									if (P_increase_decrease <= 0.5){
										if (blue_channel_2<255)
											blue_channel_2 +=1;
									}
									else{
										if (blue_channel_2>0)
											blue_channel_2 -=1;
									}
								}
							}
							else
							{
								if (mj==f_LSBMR(blue_channel_1-1,blue_channel_2)){
									if (blue_channel_1>0)
										blue_channel_1 -=1;
								}
								else{
									if (blue_channel_1<255)
										blue_channel_1 +=1;
								}	
							}

						}

						// Green channel
						rand = new Random();
						P_select   = rand.nextFloat();
						if (P_select<=Probability)//Simulating the embedding key
						{
							int mi = 0;
							int mj = 0;
							float P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mi = 1;

							P_m   = rand.nextFloat();
							if (P_m <= 0.5)//Simulating the encrypted message
								mj = 1;
							//Check the above mentioned paper for better understanding the code of this function
							if (mi==LSB_bit(green_channel_1)){
								if (mj!=f_LSBMR(green_channel_1,green_channel_2))
								{
									float P_increase_decrease   = rand.nextFloat();
									if (P_increase_decrease <= 0.5){
										if (green_channel_2<255)
											green_channel_2 +=1;
									}
									else{
										if (green_channel_2>0)
											green_channel_2 -=1;
									}
								}
							}
							else
							{
								if (mj==f_LSBMR(green_channel_1-1,green_channel_2)){
									if (green_channel_1>0)
										green_channel_1 -=1;
								}
								else{
									if (green_channel_1<255)
										green_channel_1 +=1;
								}	
							}

						}
						image.setRGB(xPixel*2, yPixel, 256*256*red_channel_1+256*green_channel_1+blue_channel_1);
						image.setRGB(xPixel*2, yPixel, 256*256*red_channel_2+256*green_channel_2+blue_channel_2);
					}        
				}
			}
			//The result [stego image] will be stored in the same input path, and the file name will be changed to:
			// inputfilename.bmp.LSBMR.bmp
			
			// For example if the input file name and path is "/User/kazemmit/Documents/test.bmp" the result will be a stego image
			// with following file name and path:
			//"/User/kazemmit/Documents/test.bmp.LSBMR.bmp"
			
			File outputfile = new File(BMPFileName+".LSBMR.bmp");
			ImageIO.write(image, "bmp", outputfile);
		}
		catch (Exception err)
		{
			System.out.println(err.toString());
		}
	}



		
	public static int [] ImgHist(String BMPFileName)  {
		try{
			BufferedImage image = ImageIO.read(new File(BMPFileName));
			int[][] array2D = new int[image.getWidth()][image.getHeight()];
			ColorSpace cs = image.getColorModel().getColorSpace();
			boolean isGrayscale = cs.getType() == ColorSpace.TYPE_GRAY;
			float Probability = 0;

			

			int [] gray_hist = new int[256];
			int [] red_hist = new int[256];
			int [] green_hist = new int[256];
			int [] blue_hist = new int[256];
			
			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++)
			{
				for (int yPixel = 0; yPixel < image.getHeight(); yPixel++)
				{
					Color c = new Color(image.getRGB(xPixel, yPixel));
					int blue_channel = c.getBlue();
					int green_channel = c.getGreen();
					int red_channel = c.getRed();
					int gray = c.getBlue();

					gray_hist[gray] +=1;
					red_hist[red_channel] +=1;
					blue_hist[blue_channel] +=1;
					green_hist[green_channel] +=1;
				}
			}
			return blue_hist;

		}
		catch (Exception err)
		{
			System.out.println(err.toString());
			return new int[1];
		}
		
	}

	public static double MSE(String Cover,String Stego)  {
		try{
			BufferedImage image_cover = ImageIO.read(new File(Cover));
			BufferedImage image_stego = ImageIO.read(new File(Stego));

			ColorSpace cs = image_cover.getColorModel().getColorSpace();
			boolean isGrayscale = cs.getType() == ColorSpace.TYPE_GRAY;

			int total_diff = 0;
			for (int xPixel = 0; xPixel < image_cover.getWidth(); xPixel++)
			{
				for (int yPixel = 0; yPixel < image_cover.getHeight(); yPixel++)
				{
					Color c = new Color(image_cover.getRGB(xPixel, yPixel));
					int blue_channel_cover = c.getBlue();
					int green_channel_cover = c.getGreen();
					int red_channel_cover = c.getRed();
					int gray_cover = c.getBlue();
					
					c = new Color(image_stego.getRGB(xPixel, yPixel));
					int blue_channel_stego = c.getBlue();
					int green_channel_stego = c.getGreen();
					int red_channel_stego = c.getRed();
					int gray_stego = c.getBlue();
					
					if (isGrayscale)
						total_diff += Math.pow(gray_cover - gray_stego,2);
					else
					{
						total_diff += Math.pow(blue_channel_cover - blue_channel_stego,2);
						total_diff += Math.pow(green_channel_cover - green_channel_stego,2);
						total_diff += Math.pow(red_channel_cover - red_channel_stego,2);
					}
				}
			}
			return total_diff;

		}
		catch (Exception err)
		{
			System.out.println(err.toString());
			return -1;
		}
		
	}
	private static void print_copyRight_message()
	{
		System.out.println("\033[31;1m");
		System.out.println("Copy right: \n This project was created by Kazem Qazanfari as a part of his research in the field of data hiding.You are free to use or edit this project for your research. If you use this project or some parts of this code, please kindly cite the following papers:\n");
		System.out.println("*  Qazanfari, Kazem, and Reza Safabakhsh. A new steganography method which preserves histogram: Generalization of LSB++. Information Sciences 277 (2014): 90-101.");
		System.out.println("*  Ghazanfari, Kazem, Shahrokh Ghaemmaghami, and Saeed R. Khosravi. LSB++: an improvement to LSB+ steganography. TENCON 2011-2011 IEEE Region 10 Conference. IEEE, 2011.\n");
		System.out.println("\033[31;1m\033[0m");
	}
	private static void print_message(){
		System.out.println("\033[31;1m");
		System.out.println("The number or the type of arguments is not correct. Please read following instruction:");
		System.out.println("****NOTE****");
		System.out.println("*");
		System.out.println("* The purpose of implementing this project is to provide the embedding behavior of some baseline steganography methods: LSB, LSB+ [LSBP], LSB++ [LSBPP], LSB Matching [LSBM] and LSB Matching Revisited [LSBMR].\n");
		System.out.println("* Actually, we are going to provide/simulate the embedding process of some steganography methods, which creates the exact statistical artifacts that are created by these steganography methods.\n");  
		System.out.println("* so researchers in the field of data hiding [steganography/steganalysis] will be able to compare their methods [steganography/steganalysis] to these base-line methods.\n");
		System.out.println("*");  
		System.out.println("* So,1) we use a pseudo random message (using java random library) as the encrypted message that should be embedded.");
		System.out.println("* 			therefore, you do not need to provide the message." );
		System.out.println("*    2) by using a random process, we simulate the embedding key");
		System.out.println("*    		therefore, you do not need to provide the embedding key."); 
		System.out.println("*    3) we just provide the embedding process");
		System.out.println("*    		therefore, you can use the stego images created by these methods and analyze them. \n");
		System.out.println("***************************\n\n");
		
		System.out.println("**********How to use it (.class file)**********");
		System.out.println("java CreateStego MethodName FileNamePath MessegeLength\n");
		System.out.println("MethodName: it should be one of following options:");
		System.out.println("   *LSB: for simple LSB steganography method.");
		System.out.println("   *LSBP: for LSB+ steganography method.");
		System.out.println("   *LSBPP: for LSB++ steganography method.");
		System.out.println("   *LSBM: for LSB matching steganography method.");
		System.out.println("   *LSBMR: for LSB matching revisited steganography method.\n");
		System.out.println("FileNamePath: it should be the path and file name of the cover bitmap image.");
		System.out.println("   *If your file is in the current directory, you do not need to specify the path, just provide the file name.\n");
		System.out.println("   *If your file is not in the current directory, you need to specify the complete file path and name.\n");
		System.out.println("MessageLength: it should be an integer number which determines the message length in bit.\n");
		
		System.out.println("Examples:");
		System.out.println("java CreateStego LSB test.bmp 1000");
		System.out.println("java CreateStego LSBP /User/kazemmit/Document/test.bmp 2000");
		System.out.println("java CreateStego LSBPP /User/kazemmit/Document/test.bmp 1500");
		System.out.println("java CreateStego LSBM /User/kazemmit/Document/test.bmp 2000");
		System.out.println("java CreateStego LSBMR test.bmp 1000");
		System.out.println("\033[31;1m\033[0m");
		
	}
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
	public static void main(String[] args)  {
		
		if(args.length < 3)
	    {
			print_message();
			print_copyRight_message();
			System.exit(-1);
	        //System.exit(0);
	    }
		else 
	    {
			args[0] = args[0].trim();					
			if ((args[0].toUpperCase().equals("LSB")) || (args[0].toUpperCase().equals("LSBP"))|| (args[0].toUpperCase().equals("LSBPP"))|| (args[0].toUpperCase().equals("LSBM"))|| (args[0].toUpperCase().equals("LSBMR")))
			{
				File f = new File(args[1]);
				if(f.exists() && !f.isDirectory()) { 
					if (getFileExtension(new File(args[1])).toLowerCase().equals("bmp")){
						int messageLength=0;
						try{
							messageLength = Integer.parseInt(args[2]);
						}
						catch(Exception err)
						{
							System.out.println("\033[31;1m");
							System.out.println("Only an integer number is acceptable as message length like:");						
							System.out.println("java CreateStego LSBPP test.bmp 1500");	
							System.out.println("\033[31;1m\033[0m");
							System.exit(-1);	
						}
						try{
							switch (args[0].toUpperCase()){
								case "LSB":
									LSB(args[1],messageLength);
									break;
								case "LSBP":
									LSBPLUS_LSBPLUSPLUS(args[1],messageLength,false);
									break;
								case "LSBPP":
									LSBPLUS_LSBPLUSPLUS(args[1],messageLength,true);
									break;
								case "LSBM":
									LSBM(args[1],messageLength);
									break;
								case "LSBMR":
									LSBMR(args[1],messageLength);
									break;
							}
							System.exit(1);							
						}
						catch(Exception err){
							System.out.println("\033[31;1m");
							System.out.println("Something wrong happended during embedding process!");						
							System.out.println("Check the image file to be valid and try again");
							System.out.println("\033[31;1m\033[0m");
							System.exit(-1);	
						}
					}
					else
					{
						System.out.println("\033[31;1m");
						System.out.println("Only bitmap [bmp] image is acceptable!!!!");						
						System.out.println("Please use a bitmap image as input.");	
						System.out.println("\033[31;1m\033[0m");
						System.exit(-1);	
					}

				}
				else{
					System.out.println("\033[31;1m");
					System.out.println("The file that you provided does not exist!");
					System.out.println("    If your file is in the current directory, you do not need to specify the path, just provide the file name.\n");
					System.out.println("    If your file is not in the current directory, you need to specify the complete file path and name.\n");
					System.out.println("\033[31;1m\033[0m");
					System.exit(-1);
				}
				
			}
			else{
				System.out.println("\033[31;1m");
				System.out.println("Method name should be one of following options:");
				System.out.println("   LSB: for simple LSB steganography method.");
				System.out.println("   LSBP: for LSB+ steganography method.");
				System.out.println("   LSBPP: for LSB++ steganography method.");
				System.out.println("   LSBM: for LSB matching steganography method.");
				System.out.println("   LSBMR: for LSB matching revisited steganography method.");
				System.out.println("\033[31;1m\033[0m");
				System.exit(-1);
			}
				
			
	    }
		
		// Test purpose
		/*for (int i=0;i<256;i++){
			if ((cover_hist[i] - stego_hist[i])!=0)
				System.out.println(cover_hist[i] - stego_hist[i]);
			
		}*/
			
		
	}

}
