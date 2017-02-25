import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PointExtraction{
  
  public static void main(String args[])throws IOException{
    BufferedImage img = null;
    File f = null;

    //read image
    try{
      f = new File("/home/administrator/mainimg/data6.jpg");
      img = ImageIO.read(f);
    }catch(IOException e){
      System.out.println(e);
    }
    
    //get image width and height
    int width = img.getWidth();
    int height = img.getHeight();
    int[][] whitePositions = new int[width*height][2];
    int index=-1;
    
    
    //convert to grayscale
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        int p = img.getRGB(x,y);

        int a = (p>>24)&0xff;
        int r = (p>>16)&0xff;
        int g = (p>>8)&0xff;
        int b = p&0xff;

        //calculate average
        int Y = (int)(0.3*r + 0.59*g + 0.11*b);

        //replace RGB value with avg
        if (Y < 127) {
            p = 255;
            index++;
            whitePositions[index][0]=x;
            whitePositions[index][1]=y;
      
            System.out.print("[ " + whitePositions[index][0]+","+ whitePositions[index][1]+"], ");
            p = colorToRGB(a, 7, 28, 216);
        } 
        else { 
            
            p = 0;
            p = colorToRGB(a, p, p, p);
        }
        
        img.setRGB(x, y, p);
      }
      System.out.println();
    }
    
    
    

//write image
    try{
      f = new File("/home/administrator/mainimg/data6-point.png");
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println(e);
    }
   // System.out.println(Arrays.deepToString(whitePositions));
  }//main() ends here
  
  private static int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
  }
}//class ends here