package iris.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class IrisUtils {

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		double scaleWidth = 0;
		double scaleHeight = 0;
		
		if(imageWidth > width){
			scaleWidth = (double) width / imageWidth;
		}
		
		if(imageHeight > height){
			scaleHeight = (double) height / imageHeight;
		}
		
		double scale = scaleWidth <= scaleHeight ? scaleWidth : scaleHeight;
		
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scale, scale);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(image, new BufferedImage((int) (imageWidth * scale) , (int) (imageHeight * scale), image.getType()));
	}
	
}
