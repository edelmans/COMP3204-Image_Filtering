package arthurEdelmans;

import net.didion.jwnl.data.Exc;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.processing.convolution.Gaussian2D;
import org.openimaj.image.processing.resize.ResizeProcessor;
import org.openimaj.image.typography.hershey.HersheyFont;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
    	//Create an image
        //public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma)
        MBFImage highImage = ImageUtilities.readMBF(new File("images/Turtles.jpg"));
        MBFImage lowImage = ImageUtilities.readMBF(new File("images/Jaws.jpg"));
        //Based off the lecture recordings
        int lowSigma = 4;

        MBFImage newHybridImage = MyHybridImages.makeHybrid(lowImage, lowSigma, highImage, 0f);


        //ResizeProcessor instantiation
        ResizeProcessor resizer = new ResizeProcessor(5f);
        int width = newHybridImage.getWidth();

        MBFImage canvas = new MBFImage(width + width/2 + width/4 + width/8 + 80 , newHybridImage.getHeight());
        canvas.fill(RGBColour.WHITE);

        canvas.drawImage(newHybridImage, 0, 0);

        MBFImage smallerHybrid = resizer.halfSize(newHybridImage);
        canvas.drawImage(smallerHybrid, newHybridImage.getWidth() + 20, 0);
        width+= smallerHybrid.getWidth() + 20;

        smallerHybrid = resizer.halfSize(smallerHybrid);
        canvas.drawImage(smallerHybrid, width + 20, 0);
        width += smallerHybrid.getWidth() + 20;

        smallerHybrid = resizer.halfSize(smallerHybrid);
        canvas.drawImage(smallerHybrid, width + 20, 0);
        width += smallerHybrid.getWidth() + 20;

        DisplayUtilities.display(canvas);


//        try {
//            MBFImage image = ImageUtilities.readMBF(new File("images/data/cat.bmp"));
//
//            FImage kernel = Gaussian2D.createKernelImage(9, 10);
//            MyConvolution conv = new MyConvolution(kernel.pixels);
//
//            MBFImage test = image.process(conv);
//            DisplayUtilities.display(test, "test");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
