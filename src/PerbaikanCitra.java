import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PerbaikanCitra {
    public static void main(String[] args) throws IOException {
        BufferedImage img = readImage();
        img = contrastPreprocesing(img,10);
        showImage(img);
    }

    public static BufferedImage readImage() throws IOException {
        File f = new File("./assets/topson.jpg");

        BufferedImage img = ImageIO.read(f);
        return img;
    }
    public static void showImage(BufferedImage img){
        JOptionPane.showMessageDialog(null, new ImageIcon(img));
    }

    public static BufferedImage brightnessPreprocessing(BufferedImage img, int br){
        for (int y=0; y<img.getHeight();y++){
            for(int x=0; x<img.getWidth(); x++){
                int p = img.getRGB(x,y);
                int a = p >> 24 & 0xff;
                int r = p >> 16 & 0xff;
                int g = p >> 8 & 0xff;
                int b = p & 0xff;

                int newR = r + br;
                int newG = g + br;
                int newB = b + br;

                if(newR>255){
                    newR = 255;
                }else {
                    if(newR<0){
                        newR=0;
                    }else {
                        newR = newR;
                    }
                }


                if(newG>255){
                    newG = 255;
                }else {
                    if(newG<0){
                        newG=0;
                    }else {
                        newG = newG;
                    }
                }


                if(newB>255){
                    newB = 255;
                }else {
                    if(newB<0){
                        newB=0;
                    }else {
                        newB = newB;
                    }
                }

                p = (a<<24) | (newR<<16) | (newG<<8) | (newB);

                img.setRGB(x,y,p);
            }
        }
        return img;
    }

    public static BufferedImage contrastPreprocesing(BufferedImage img, int ct){
        for (int y=0; y<img.getHeight();y++){
            for(int x=0; x<img.getWidth(); x++){
                int p = img.getRGB(x,y);
                int a = p >> 24 & 0xff;
                int r = p >> 16 & 0xff;
                int g = p >> 8 & 0xff;
                int b = p & 0xff;

                int factor =(259 * (ct*255))/(255*259-ct);
                int newR = (factor * (r-128)+128);
                int newG = (factor * (g-128)+128);
                int newB = (factor * (b-128)+128);

                if(newR>255){
                    newR = 255;
                }else {
                    if(newR<0){
                        newR=0;
                    }else {
                        newR = newR;
                    }
                }


                if(newG>255){
                    newG = 255;
                }else {
                    if(newG<0){
                        newG=0;
                    }else {
                        newG = newG;
                    }
                }


                if(newB>255){
                    newB = 255;
                }else {
                    if(newB<0){
                        newB=0;
                    }else {
                        newB = newB;
                    }
                }

                p = (a<<24) | (newR<<16) | (newG<<8) | (newB);

                img.setRGB(x,y,p);
            }
        }
        return img;
    }

    public static BufferedImage smoothingImage(BufferedImage img){
        float[][] filter = {{1/9f,1/9f,1/9f},
                            {1/9f,1/9f,1/9f},
                            {1/9f,1/9f,1/9f}};
        BufferedImage image = new BufferedImage(img.getWidth()+(filter.length-1), img.getHeight()+(filter[0].length-1),BufferedImage.TYPE_INT_RGB);
        int filterW = filter.length;
        int filterH = filter[0].length;

        for (int y=0; y<img.getHeight();y++){
            for(int x=0; x<img.getWidth(); x++){
                float rTemp = 0f;
                float gTemp = 0f;
                float bTemp = 0f;

                for (int i=0; i<filter.length;i++){
                    for (int j=0; j<filter[i].length;j++){
                        int imgX = (x - filterW/2 +j +img.getWidth()) % img.getWidth();
                        int imgY = (y - filterH/2 +i +img.getHeight()) % img.getHeight();

                        int p = img.getRGB(imgX,imgY);
                        int r = p >> 16 & 0xff;
                        int g = p >> 8 & 0xff;
                        int b = p & 0xff;

                        rTemp += (r*filter[j][i]);
                        gTemp += (g*filter[j][i]);
                        bTemp += (b*filter[j][i]);

                    }
                }

                if(rTemp>255){
                    rTemp = 255f;
                }else{
                    if(rTemp<0){
                        rTemp=0f;
                    }else{
                        rTemp = rTemp;
                    }
                }

                if(gTemp>255){
                    gTemp = 255F;
                }else{
                    if(gTemp<0){
                        gTemp=0f;
                    }else{
                        gTemp = gTemp;
                    }
                }

                if(bTemp>255){
                    bTemp = 255f;
                }else{
                    if(bTemp<0){
                        bTemp=0f;
                    }else{
                        bTemp = bTemp;
                    }
                }

                image.setRGB(x,y,new Color((int)rTemp,(int)gTemp,(int)bTemp).getRGB());
            }
        }
        return image;
    }

    public static BufferedImage edgeDetection(BufferedImage img){
        float[][] filter = {{0f,-1f,0f},
                            {-1f,4f,-1f},
                            {0f,-1f,0f}};

        BufferedImage image = new BufferedImage(img.getWidth()+(filter.length-1), img.getHeight()+(filter[0].length-1),BufferedImage.TYPE_INT_RGB);
        int filterW = filter.length;
        int filterH = filter[0].length;

        for (int y=0; y<img.getHeight();y++){
            for(int x=0; x<img.getWidth(); x++){
                float rTemp = 0f;
                float gTemp = 0f;
                float bTemp = 0f;

                for (int i=0; i<filter.length;i++){
                    for (int j=0; j<filter[i].length;j++){
                        int imgX = (x - filterW/2 +j +img.getWidth()) % img.getWidth();
                        int imgY = (y - filterH/2 +i +img.getHeight()) % img.getHeight();

                        int p = img.getRGB(imgX,imgY);
                        int r = p >> 16 & 0xff;
                        int g = p >> 8 & 0xff;
                        int b = p & 0xff;

                        rTemp += (r*filter[j][i]);
                        gTemp += (g*filter[j][i]);
                        bTemp += (b*filter[j][i]);

                    }
                }

                if(rTemp>255){
                    rTemp = 255f;
                }else{
                    if(rTemp<0){
                        rTemp=0f;
                    }else{
                        rTemp = rTemp;
                    }
                }

                if(gTemp>255){
                    gTemp = 255F;
                }else{
                    if(gTemp<0){
                        gTemp=0f;
                    }else{
                        gTemp = gTemp;
                    }
                }

                if(bTemp>255){
                    bTemp = 255f;
                }else{
                    if(bTemp<0){
                        bTemp=0f;
                    }else{
                        bTemp = bTemp;
                    }
                }

                image.setRGB(x,y,new Color((int)rTemp,(int)gTemp,(int)bTemp).getRGB());
            }
        }
        return image;
    }
}
