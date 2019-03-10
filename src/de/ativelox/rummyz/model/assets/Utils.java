package de.ativelox.rummyz.model.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 * Provides a utility class for image related methods.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Utils {

    /**
     * Returns a possibly empty {@link Optional} of the image located at
     * <tt>fileName</tt>.
     * 
     * @param fileName The path to the image file.
     * @return <tt>Optional.of(BufferedImage)</tt> if the fileName could be resolved
     *         to an image, and <tt>Optional.empty()</tt> else.
     */
    public static Optional<BufferedImage> loadImage(final String fileName) {
	try {
	    return Optional.of(ImageIO.read(new File(fileName)));

	} catch (IOException e) {
	    e.printStackTrace();

	    return Optional.empty();
	}
    }
}
