package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class TilesetPadding {
	public static void main(String args[])throws IOException{
	    BufferedImage img = null;
	    File f = null;
	    Scanner sc = new Scanner(System.in);

	    System.out.println("Tileset path? z.b: ./assets/maps/originals/PathAndObjects.png");
	    //read image
	    try{
	      f = new File(sc.nextLine());
	      img = ImageIO.read(f);
	    }catch(IOException e){
	      System.out.println(e);
	    }
	    
	    System.out.println("Tile size?");
	    int tWidth = Integer.parseInt(sc.nextLine());
	    int tHeight = tWidth;
	    System.out.println("Padding?");
	    int pad = Integer.parseInt(sc.nextLine());

	    //get image width and height
	    int width = img.getWidth();
	    int height = img.getHeight();

	    int oWidth = width+(width/tWidth+1)*pad;
	    int oHeight = height+(height/tHeight+1)*pad;
	    
	    BufferedImage out = new BufferedImage(oWidth, oHeight, BufferedImage.TYPE_INT_ARGB);
	    
	    int ox = 0;
	    int oy = 0;
	    
	    int[] cols = new int[width*height];
	    
	    img.getRGB(0, 0, width, height, cols, 0, width);

	    for(int x = 0; x < width; x++, oy=0) {
	    	for(int y = 0; y < height; y++) {
	    	    //get pixel value
	    	    int col = cols[x+y*height];

	    	    if(x % tWidth == 0) {
    	    		if(y!=0)
    	    			ox-=pad;
	    	    	for(int i = 0; i < pad; i++) {
	    	    		int pCol = 0;
	    	    		if(i<pad/2)
	    	    			pCol=cols[Math.max(x-1,0)+y*height];
    	    			else
    	    				pCol=col;
			    	    out.setRGB(x+ox, y+oy, pCol);
			    	    ox++;
	    	    	}
	    	    }
	    	    if(y % tHeight == 0) {
	    	    	for(int i = 0; i < pad; i++) {
	    	    		int pCol = 0;
	    	    		if(i<pad/2)
	    	    			pCol=cols[x+(Math.max(y-1,0))*height];
		    			else
		    				pCol=col;
	    	    		if(x % tWidth == 0) pCol = col;
	    	    		
			    	    out.setRGB(x+ox, y+oy, pCol);
			    	    oy++;
	    	    	}
	    	    }
	    	    
	    	    out.setRGB(x+ox, y+oy, col);
	    	}
	    }
	
	    //write image
	    String newPath = f.getAbsolutePath().replaceFirst("[.][^.]+$", "")+"_"+pad+"px_pad"+f.getName().substring(f.getName().lastIndexOf('.'));
	    try{
	      f = new File(newPath);
	      ImageIO.write(out, "png", f);
	    }catch(IOException e){
	      System.out.println(e);
	    }
	    System.out.println("Saved as \""+newPath+"\"");
	    sc.close();
	  }
}
