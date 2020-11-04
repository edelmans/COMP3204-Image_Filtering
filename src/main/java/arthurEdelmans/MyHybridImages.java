package arthurEdelmans;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MyHybridImages {
    /**
     * Compute a hybrid image combining low-pass and high-pass filtered images
     *
     * @param lowImage
     *            the image to which apply the low pass filter
     * @param lowSigma
     *            the standard deviation of the low-pass filter
     * @param highImage
     *            the image to which apply the high pass filter
     * @param highSigma
     *            the standard deviation of the low-pass component of computing the
     *            high-pass filtered image
     * @return the computed hybrid image
     */
    public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
        //implement your hybrid images functionality here.
        //Your submitted code must contain this method, but you can add
        //additional static methods or implement the functionality through
        //instance methods on the `MyHybridImages` class of which you can create
        //an instance of here if you so wish.
        //Note that the input images are expected to have the same size, and the output
        //image will also have the same height & width as the inputs.

        // -- Low Pass Filtering --
        // "Can be achieved by convolving the image with a Gaussian filter"

        //Using given formula for size given in the specification
        int size = (int) (8.0f * lowSigma + 1.0f); // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
        if (size % 2 == 0) size++; // size must be odd
        //System.out.println("Kernel size: " + size);

        //getting the 2D array needed for myConvolution using createKernelImage and pixels as suggested in the specification
        FImage gaussianKernel = Gaussian2D.createKernelImage(size, lowSigma);
        float[][] kernelArray = gaussianKernel.pixels;

        MyConvolution myConv = new MyConvolution(kernelArray);
        lowImage = lowImage.process(myConv);
        //DisplayUtilities.display(lowImage, "Low image convoluted");

        // -- High Pass Filtering --
        // "Can be most easily achieved by subtracting a low-pass version of an image itself"
        highImage = highImage.subtract(highImage.process(myConv));

        //save created images - commented out because it's not part of the coursework specification
//        File lowImgFile = new File("images/output/lowPassImage.jpg");
//        try {
//            ImageIO.write(ImageUtilities.createBufferedImageForDisplay(lowImage), "jpg", lowImgFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        File highImgFile = new File("images/output/highPassImage.jpg");
//        try {
//            ImageIO.write(ImageUtilities.createBufferedImageForDisplay(highImage), "jpg", highImgFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // -- Making a hybrid image --
        MBFImage hybridImage = lowImage.add(highImage);
        return hybridImage;

    }


}