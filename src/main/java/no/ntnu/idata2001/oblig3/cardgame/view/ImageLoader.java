package no.ntnu.idata2001.oblig3.cardgame.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class ImageLoader represents an image loader
 * It is responsible for loading, storing and returning images from the disk
 * @author Marko
 * @version 2021-03-29
 */
public class ImageLoader
{
    private static final double DEFAULT_IMAGE_HEIGHT = 177.5;
    private static final double DEFAULT_IMAGE_WIDTH = 130.5;
    private final Logger logger;
    private final HashMap<String, ImageView> images;

    public ImageLoader()
    {
        this.logger = Logger.getLogger(this.getClass().toString());
        this.images = new HashMap<>();
    }

    /**
     * Returns an ImageView of the given card name
     * @param cardName The card name corresponding to the image to return,
     *                 can not be blank or null
     * @return ImageView of the given card name or,
     * null if the image can not be found or loaded or,
     * null if the given card name is blank or null
     */
    public ImageView getCardImage(String cardName)
    {
        ImageView toReturn = null;
        if (cardName != null) {
            if (!cardName.isBlank()) {
                if (!this.images.containsKey(cardName)) {
                    this.loadCardImage(cardName);
                }
                toReturn = this.images.get(cardName);
            }
        }
        return toReturn;
    }

    /**
     * Loads a card image with the given name into memory
     * @param cardName The card name corresponding to the image to load,
     *                 can not be blank or null
     */
    private void loadCardImage(String cardName)
    {
        if (cardName != null) {
            if (!cardName.isBlank()) {
                String imageLocation = cardName + ".png";

                try {
                    Image image = new Image(imageLocation);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(DEFAULT_IMAGE_HEIGHT);
                    imageView.setFitWidth(DEFAULT_IMAGE_WIDTH);
                    imageView.setPreserveRatio(true);

                    this.images.put(cardName, imageView);
                }
                catch (IllegalArgumentException e) {
                    String message = "Image " + imageLocation + " could not be loaded!";
                    this.logger.log(Level.INFO, message);
                }
            }
        }
    }
}
