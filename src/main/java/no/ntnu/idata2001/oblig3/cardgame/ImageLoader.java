package no.ntnu.idata2001.oblig3.cardgame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * Class ImageLoader represents an image loader
 * It is responsible for loading, storing and returning images from the disk
 */
public class ImageLoader
{
    private static final double DEFAULT_IMAGE_HEIGHT = 177.5;
    private static final double DEFAULT_IMAGE_WIDTH = 130.5;
    private final HashMap<String, ImageView> images;

    public ImageLoader()
    {
        this.images = new HashMap<>();
    }

    /**
     * Returns an ImageView of the given card name
     * @param cardName The card name corresponding to the image to return,
     *                 can not be blank or null
     * @return ImageView of the given card name, null if the given card name is blank or null
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

                Image image = new Image(imageLocation);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(DEFAULT_IMAGE_HEIGHT);
                imageView.setFitWidth(DEFAULT_IMAGE_WIDTH);
                imageView.setPreserveRatio(true);

                this.images.put(cardName, imageView);
            }
        }
    }
}
