import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class ReColor {
    BufferedImage img;
    String imageName;
    String redImageName;
    int cube;
    int height = 0;
    int width = 0;
    int colorLimit = 0;

    /**
     * Set up the ReColor Class
     * @param filename  File containing the original image
     * @param cube Size of cube for which will accumulate colors
     * @param colorLimit
     */
    ReColor(String filename, int cube, int colorLimit) {
        img = null;
        this.cube = cube;
        this.colorLimit = colorLimit;
        File f = null;
        String[] p = filename.split("\\.");
        System.out.println("File name " + filename);
        imageName = p[0] + colorLimit + "." + p[1];
        redImageName = p[0] + "Red." + p[1];
        try {
            f = new File(
                    filename);
            img = ImageIO.read(f);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        width = img.getWidth();
        height = img.getHeight();
    }

    public void makeRed() {
        BufferedImage img2 = new BufferedImage(width, height, img.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);
                int alpha = (p >> 24) & 0xff; //transparency measure
                int r = (p >> 16) & 0xff;  //red
                int g = (p >> 8) & 0xff;   //green
                int b = p & 0xff;          //blue
                // set new RGB keeping the alpha and the color wanted. Setting others to 0.
                p = (alpha << 24) | (r << 16) | (0 << 8) | 0;//Show red
                img2.setRGB(x, y, p);
            }
        }
        try {
            File f = new File(
                    redImageName);
            ImageIO.write(img2, "png", f);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getNewImage() {
        // Make a hash table of all color maps
        // Gets a list of all color maps in the hash table
        // Makes color table of most prevalent color maps
        HashTable<ColorMap> map = this.getColorMaps();
        ArrayList<ColorMap> list = map.getAll();
        list.sort(Comparator.reverseOrder());

        ColorMap[] colorTable = new ColorMap[this.colorLimit];
        for (int i = 0; i < colorTable.length; i++) {
            colorTable[i] = list.get(i);
        }


    }

    /**
     * Creates a hash table of all the color maps in the image
     * Keeps track of the count of occurrences of each color map
     * @return A Hash table with all color maps from image
     */
    private HashTable<ColorMap> getColorMaps() {
        HashTable<ColorMap> colorMaps = new HashTable<>();
        BufferedImage newImg = new BufferedImage(this.width, this.height, this.img.getType());
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                // Get value for each color
                int p = img.getRGB(x, y);
                int alpha = (p >> 24) & 0xff; //transparency measure
                int r = (p >> 16) & 0xff;  //red
                int g = (p >> 8) & 0xff;   //green
                int b = p & 0xff;

                // Finds the color map for the current pixel
                // If it is in the hash table then increase the count
                // If not, then insert in the hash table
                ColorMap currentMap = new ColorMap(alpha, r, g, b, this.cube);
                ColorMap found = colorMaps.find(currentMap);
                if (found == null) {
                    colorMaps.insert(currentMap);
                } else {
                    found.occurCt++;
                }
            }
        }
        return colorMaps;
    }


    public static void main(String[] args) {
        String[] files = {"chart.png", "bird.png", "butterfly.png", "cat.png", "dice.png", "flowers.png"};
        int[] colorMax = {5, 100, 100, 25, 6, 40};

        for (int i = 0; i < 1; i++) {

            ReColor r = new ReColor(files[i], 6, colorMax[i]);
            r.getNewImage();

//            r.makeRed();
            //r.grabColors();
        }
    }
}
