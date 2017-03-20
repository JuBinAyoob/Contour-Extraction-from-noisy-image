import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.lang.*;

public class PointLink{
  
  public static void main(String args[])throws IOException{
    BufferedImage img = null;
    File f = null;

    //read image
    try{
      f = new File("/home/jubin/Pictures/data6.jpg");
      img = ImageIO.read(f);
    }catch(IOException e){
      System.out.println(e);
    }
    
    //get image width and height
    int width = img.getWidth();
    int height = img.getHeight();
    int[][] whitePositions = new int[width*height][2];
    int index=-1;
    
    //int[][] imageArray=new int[width][height];
    
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
      //      imageArray[x][y]=1;
        } 
        else { 
            
            p = 0; 
        }
        p = colorToRGB(a, p, p, p);
        img.setRGB(x, y, p);
      }
    }
    
    int x,y,x1,y1,x2,y2;
    double root2 = Math.sqrt(2.0),val;
    int[][] V = new int[width*height][2];
    //int[][] E = new int[width*height*3][4];
    int[][] markv=new int[width][height];
    int vInd=0,eInd=0;
    
    for(int p1 = 0; p1 < index; p1++){
        x1=whitePositions[p1][0];
        y1=whitePositions[p1][1];
        
        for(int p2 = p1+1; p2 <= index; p2++){
            
            
                x2=whitePositions[p2][0];
                y2=whitePositions[p2][1];
               
                val=Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
                
                if(root2>=val)
                {
                   
                   if(markv[x1][y1]==0){
                       V[vInd][0]=x1;
                       V[vInd][1]=y1;
                       ++vInd;
                       
                       markv[x1][y1]=1;
                       
                   }
                   
                   if(markv[x2][y2]==0){
                       V[vInd][0]=x2;
                       V[vInd][1]=y2;
                       ++vInd;
                       
                       markv[x2][y2]=1;
                       
                   }
                  
                   /* E[eInd][0]=x1;
                    E[eInd][1]=y1;
                    E[eInd][2]=x2;
                    E[eInd][3]=y2;
                   
                    eInd++;*/
                }
            
            
                
            
        }
               
    }
    
    /*for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
          System.out.print(markv[j][i]+" ");
      }
      System.out.println("");
    }*/
    
    
    
    int[][] PL = new int[width*height][2];
    int pInd=-1;
    
    x=V[0][0];
    y=V[0][1];
    x1=x;
    y1=y;
    x2=V[1][0];
    y2=V[1][1];
    //markv[x1][y1]=4;
    //markv[x2][y2]=5;
    int stop=0;
    int p = colorToRGB(255, 255, 0,0);
    
    
    img.setRGB(x1, y1, p);
    img.setRGB(x2, y2, p);
    PL[++pInd][0]=x1;
    PL[pInd][1]=y1;
    PL[++pInd][0]=x2;
    PL[pInd][1]=y2;
    do
    {
        if(x2>x1)
        {
            if(y2>=y1)
            {
                if(markv[x2+1][y2-1]==1)
                {
                    x1=x2++;
                    y1=y2--;
                }
                else if(markv[x2+1][y2]==1)
                {
                    x1=x2++;
                    y1=y2;
                }
                else if(markv[x2+1][y2+1]==1)
                {
                    x1=x2++;
                    y1=y2++;
                }
                else if(markv[x2][y2+1]==1)
                {
                    x1=x2;
                    y1=y2++;
                }
                else if(markv[x2-1][y2+1]==1)
                {
                    x1=x2--;
                    y1=y2++;
                }
                else if(markv[x2-1][y2]==1)
                {
                    x1=x2--;
                    y1=y2;
                }
                else if(markv[x2-1][y2-1]==1)
                {
                    x1=x2--;
                    y1=y2--;
                }
                else
                    stop=1;
            }
            else
            {
                if(markv[x2-1][y2-1]==1)
                {
                    x1=x2--;
                    y1=y2--;
                }
                else if(markv[x2][y2-1]==1)
                {
                    x1=x2;
                    y1=y2--;
                }
                else if(markv[x2+1][y2-1]==1)
                {
                    x1=x2++;
                    y1=y2--;
                }
                else if(markv[x2+1][y2]==1)
                {
                    x1=x2++;
                    y1=y2;
                }
                else if(markv[x2+1][y2+1]==1)
                {
                    x1=x2++;
                    y1=y2++;
                }
                else if(markv[x2][y2+1]==1)
                {
                    x1=x2;
                    y1=y2++;
                }
                else if(markv[x2-1][y2+1]==1)
                {
                    x1=x2--;
                    y1=y2++;
                }
                else
                    stop=1;
                
            }
        }
        else if(x1==x2)
        {
            
            if(y2>y1)
            {
                if(markv[x2+1][y2+1]==1)
                {
                    x1=x2++;
                    y1=y2++;
                }
                else if(markv[x2][y2+1]==1)
                {
                    x1=x2;
                    y1=y2++;
                }
                else if(markv[x2-1][y2+1]==1)
                {
                    x1=x2--;
                    y1=y2++;
                }
                else if(markv[x2-1][y2]==1)
                {
                    x1=x2--;
                    y1=y2;
                }
                else if(markv[x2-1][y2-1]==1)
                {
                    x1=x2--;
                    y1=y2--;
                }
                else if(markv[x2][y2-1]==1)
                {
                    x1=x2;
                    y1=y2--;
                }
                else if(markv[x2+1][y2-1]==1)
                {
                    x1=x2++;
                    y1=y2--;
                }
                else
                    stop=1;
                
            }
            else
            {
                if(markv[x2-1][y2-1]==1)
                {
                    x1=x2--;
                    y1=y2--;
                }
                else if(markv[x2][y2-1]==1)
                {
                    x1=x2;
                    y1=y2--;
                }
                else if(markv[x2+1][y2-1]==1)
                {
                    x1=x2++;
                    y1=y2--;
                }
                else if(markv[x2+1][y2]==1)
                {
                    x1=x2++;
                    y1=y2;
                }
                else if(markv[x2+1][y2+1]==1)
                {
                    x1=x2++;
                    y1=y2++;
                }
                else if(markv[x2][y2+1]==1)
                {
                    x1=x2;
                    y1=y2++;
                }
                else if(markv[x2-1][y2+1]==1)
                {
                    x1=x2--;
                    y1=y2++;
                }
                else
                    stop=1;
            }
            
        }
        else
        {
            if(y2>y1)
            {
                if(markv[x2+1][y2+1]==1)
                {
                    x1=x2++;
                    y1=y2++;
                }
                else if(markv[x2][y2+1]==1)
                {
                    x1=x2;
                    y1=y2++;
                }
                else if(markv[x2-1][y2+1]==1)
                {
                    x1=x2--;
                    y1=y2++;
                }
                else if(markv[x2-1][y2]==1)
                {
                    x1=x2--;
                    y1=y2;
                }
                else if(markv[x2-1][y2-1]==1)
                {
                    x1=x2--;
                    y1=y2--;
                }
                else if(markv[x2][y2-1]==1)
                {
                    x1=x2;
                    y1=y2--;
                }
                else if(markv[x2+1][y2-1]==1)
                {
                    x1=x2++;
                    y1=y2--;
                }
                else
                    stop=1;
                
            }
            else
            {
                if(markv[x2-1][y2+1]==1)
                {
                    x1=x2--;
                    y1=y2++;
                }
                else if(markv[x2-1][y2]==1)
                {
                    x1=x2--;
                    y1=y2;
                }
                else if(markv[x2-1][y2-1]==1)
                {
                    x1=x2--;
                    y1=y2--;
                }
                else if(markv[x2][y2-1]==1)
                {
                    x1=x2;
                    y1=y2--;
                }
                else if(markv[x2+1][y2-1]==1)
                {
                    x1=x2++;
                    y1=y2--;
                }
                else if(markv[x2+1][y2]==1)
                {
                    x1=x2++;
                    y1=y2;
                }
                else if(markv[x2+1][y2+1]==1)
                {
                    x1=x2++;
                    y1=y2++;
                }
                else
                    stop=1;                
            }
            
        }
        
        
        PL[++pInd][0]=x2;
        PL[pInd][1]=y2;
        img.setRGB(x2,y2, p);
        if(stop==1)
            break;
    }while(!(x2==x && y2==y));
    
    
    /*int pi = colorToRGB(255, 7, 28, 216);
    img.setRGB(145,80, pi);
    img.setRGB(145,81, pi);
    System.out.println("\n\n\n");*/
    
    
     
    
//write image
    try{
      f = new File("/home/jubin/Pictures/OUTPUT_point_linking.png");
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println(e);
    }
    
    
    int b = colorToRGB(255, 0, 0,0);
    int w = colorToRGB(255, 255, 255,255);
    for(int i=2;i<=pInd;i=i+1)
    {
        x1=PL[i-2][0];
        y1=PL[i-2][1];
        x2=PL[i-1][0];
        y2=PL[i-1][1];
        x3=PL[i][0];
        y3=PL[i][1];
        
        if(x3>x1)
        {
            if(y3>y1)
            {
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    
                    for(int j=i-1;j<pInd;j++)
                    {
                        //System.out.println("1");
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
            }
            else if(y3==y1)
            {
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        //System.out.println("2");
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
                else if(y2==y1+1)
                {
                    img.setRGB(x2,y2, w);
                    img.setRGB(x2,y2-1, p);
                }
                else if(y2==y1-1)
                {
                    img.setRGB(x2,y2, b);
                    img.setRGB(x2,y2+1, p);
                }
            }
            else
            {  
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        //System.out.println("3");
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
            }
        }
        else if(x1==x3)
        {
            
            if(y3>y1)
            {
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        //System.out.println("4");
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
                else if(x2==x1+1)
                {
                    img.setRGB(x2,y2, b);
                    img.setRGB(x2-1,y2, p);
                }
                else if(x2==x1-1)
                {
                    img.setRGB(x2,y2, w);
                    img.setRGB(x2+1,y2, p);
                }
                
            }
            else
            {
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        //System.out.println("5"+j);
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
                else if(x2==x1+1)
                {
                    img.setRGB(x2,y2, w);
                    img.setRGB(x2-1,y2, p);
                }
                else if(x2==x1-1)
                {
                    img.setRGB(x2,y2,b);
                    img.setRGB(x2+1,y2, p);
                }
            }
            
        }
        else
        {
            if(y3>y1)
            {
             
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
                
            }
            else if(y3==y1)
            {
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
                else if(y2==y1+1)
                {
                    img.setRGB(x2,y2, b);
                    img.setRGB(x2,y2-1, p);
                }
                else if(y2==y1-1)
                {
                    img.setRGB(x2,y2, w);
                    img.setRGB(x2,y2+1, p);
                }
            }
            else
            {
                if(Math.abs(x1-x3)<2 && Math.abs(y1-y3)<2)
                {
                    for(int j=i-1;j<pInd;j++)
                    {
                        PL[j][0]=PL[j+1][0];
                        PL[j][1]=PL[j+1][1];
                    }
                    pInd--;
                    i=i-2;
                    img.setRGB(x2,y2, b);
                }
                          
            }
            
        }
        System.out.println(i);
    }
          
    //write image
    try{
      f = new File("/home/jubin/Pictures/OUTPUT_contour_simplification.png");
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println(e);
}
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
