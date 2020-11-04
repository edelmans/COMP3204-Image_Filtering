package arthurEdelmans;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
    private float[][] kernel;

    public MyConvolution(float[][] kernel) {
        //note that like the image pixels kernel is indexed by [row][column]
        this.kernel = kernel;
    }

    @Override
    public void processImage(FImage image) {
        // convolve image with kernel and store result back in image
        //
        // hint: use FImage#internalAssign(FImage) to set the contents
        // of your temporary buffer image to the image.

        // the width of the padding/border equals half the size of the template
        FImage clone = image.clone();
        //get image dimensions
        int iCol = clone.width;
        int iRow = clone.height;
        //get template dimensions
        int tCol = kernel[0].length;
        int tRow = kernel.length;

        //Check if the the kernel is oddly shaped
        if(tCol % 2 != 1 || tRow % 2 != 1){
            System.out.println("Either width of height of the kernel is even sized");
            System.exit(-1);
        }

        //get templates halfpoint
        int htc =(int) Math.floor(tCol/2);
        int htr =(int) Math.floor(tRow/2);

        //utilising zero padding by padding the image by half the width and heigh of kernel
        clone = clone.padding(htr, htc, 0.0f);

        //convolve
        //take into account the padding and borders by starting on clone at half kernel width and stopping at the end of the padded clone
        for(int x = htc; x < clone.width - (htr + 1); x++){
            //same principle applies for vertical operations
            for(int y = htr; y < clone.height - (htc + 1); y++){
                //initialise sum for weightings
                float sum = 0f;
                //go through all the template columns
                for(int i = 0; i < tCol; i++){
                    //go through all the template rows
                    for(int j = 0; j < tRow; j++){
                        sum += clone.pixels[y + j - htr][x + i - htc] * kernel[j][i];
                    }
                }
                //store as new point
            clone.pixels[y][x] = sum;
            }
        }

        //remove padding before assigning clone onto image
        clone = clone.padding(-htr, -htc);
        image.internalAssign(clone);

    }
}