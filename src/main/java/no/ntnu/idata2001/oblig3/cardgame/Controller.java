package no.ntnu.idata2001.oblig3.cardgame;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.util.List;

/**
 * The main controller of the application
 * Responsible for handling all actions related to the buttons in the user interface
 */
public class Controller
{
    private final PlayingHand hand;
    private final ImageLoader imageLoader;

    public Controller()
    {
        this.hand = new PlayingHand();
        this.imageLoader = new ImageLoader();
    }

    /**
     * Hands out a specified number of random cards
     * from the deck to the playing hand
     */
    public void dealHand()
    {
        int numberOfCards = this.showChooseNumberOfCardsDialog();

        if (numberOfCards > 52) {
            this.showNumberIsBiggerThan52Dialog();
        }
        else if (numberOfCards > this.hand.getNumberOfCardsLeft()) {
            this.showNotEnoughCardsLeftDialog();
        }
        else {
            this.hand.dealHand(numberOfCards);
        }
    }

    /**
     * Responsible for responding to the check hand button
     * @param tilePane The tile pane to set the card images to, can not be null
     * @param sumOfTheFacesTextField The text field to set the sum of faces text to, can not be null
     * @param cardOfHeartsTextField The text field to set the cards of hearts text to, can not be null
     * @param queenOfSpadesTextField The text field to set the queen of spades text to, can not be null
     * @param flushTextField The text field to set the cards flush text to, can not be null
     */
    public void checkHand(TilePane tilePane, TextField sumOfTheFacesTextField, TextField cardOfHeartsTextField,
                          TextField queenOfSpadesTextField, TextField flushTextField)
    {
        this.setCardImagesToTilePane(tilePane);

        this.checkSumOfFaces(sumOfTheFacesTextField);
        this.checkCardsOfHearts(cardOfHeartsTextField);
        this.checkQueenOfSpadesPresence(queenOfSpadesTextField);
        this.checkFlush(flushTextField);
    }

    /**
     * Sets the card images to the provided TilePane
     * @param tilePane The TilePane to set the images to, can not be null
     */
    private void setCardImagesToTilePane(TilePane tilePane)
    {
        if (tilePane != null) {
            List<PlayingCard> activeCards = this.hand.getActiveCards();

            for (PlayingCard card : activeCards) {
                ImageView imageView = this.imageLoader.getCardImage(card.getAsString());

                if (imageView != null) {
                    tilePane.getChildren().add(imageView);
                }
            }
        }
    }

    /**
     * Sets the text of the provided cardOfHeartsText text field
     * @param textField The text field to set the text to,
     *                  can not be null
     */
    private void checkCardsOfHearts(TextField textField)
    {
        String cardsOfHeartsText = this.hand.getCardsOfHeartsString();

        if (cardsOfHeartsText != null && textField != null) {
            if (!cardsOfHeartsText.isBlank()) {
                textField.setText(cardsOfHeartsText);
            }
        }
    }

    /**
     * Sets the text of the provided sumOfTheFacesTextField text field
     * @param sumOfTheFacesTextField The text field to set the text to,
     *                          can not be null
     */
    private void checkSumOfFaces(TextField sumOfTheFacesTextField)
    {
        if (sumOfTheFacesTextField != null) {
            sumOfTheFacesTextField.setText("" + this.hand.getUsedCardsValue());
        }
    }

    /**
     * Sets the text of the provided queenOfSpadesTextField text field
     * @param queenOfSpadesTextField The text field to set the text to,
     *                               can not be null
     */
    private void checkQueenOfSpadesPresence(TextField queenOfSpadesTextField)
    {
        if (queenOfSpadesTextField != null) {
            boolean cardPresent = this.hand.checkQueenOfSpadesPresence();
            if (cardPresent) {
                queenOfSpadesTextField.setText("Yes");
            }
            else {
                queenOfSpadesTextField.setText("No");
            }
        }
    }

    /**
     * Sets the text of the provided flushTextField text field
     * @param flushTextField The text field to set the text to,
     *                               can not be null
     */
    private void checkFlush(TextField flushTextField)
    {
        if (flushTextField != null) {
            boolean flushPresent = this.hand.checkFlush();
            if (flushPresent) {
                flushTextField.setText("Yes");
            }
            else {
                flushTextField.setText("No");
            }
        }
    }

    // -----------------------------------------------------------
    //    DIALOGS
    // -----------------------------------------------------------

    /**
     * Shows the "ChooseNumberOfCardsDialog" and returns the number input as an int
     * @return The number of cards requested,
     * or -1 if the input was empty,
     * or -2 if canceled
     */
    private int showChooseNumberOfCardsDialog()
    {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.getDialogPane().setPrefWidth(270);
        dialog.getDialogPane().setMinHeight(110);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10, 10, 0, 10));

        Label label = new Label("Enter the number of cards to hand out");
        label.setMinHeight(20);
        label.setMinWidth(250);

        TextField numberField = new TextField();
        numberField.setMinWidth(150);
        numberField.setPromptText("Number of cards");

        gridPane.add(label, 0, 0);
        gridPane.add(numberField, 0, 1);

        dialog.getDialogPane().getChildren().addAll(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue.length() > 0) {
                    Integer.parseInt(newValue);
                }
            } catch (NumberFormatException e) {
                numberField.setText(oldValue);
            }
        });

        dialog.setResultConverter((ButtonType button) -> {
            int result = -2;
            if (button == ButtonType.OK) {
                String text = numberField.getText();
                if (!text.isBlank()) {
                    result = Integer.parseInt(text);
                }
            }
            return result;
        });

        return dialog.showAndWait().orElse(-1);
    }

    /**
     * Shows the "number of cards is bigger than 52" dialog
     */
    private void showNumberIsBiggerThan52Dialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The number of cards requested is bigger than 52");

        alert.showAndWait();
    }

    /**
     * Shows the "not enough cards left" dialog
     */
    private void showNotEnoughCardsLeftDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not enough cards");
        alert.setHeaderText("The number of cards you requested is bigger than " + "\n" +
                "the number of cards available in the deck");
        alert.setContentText("Number of cards left in the deck: " +
                this.hand.getNumberOfCardsLeft() + "\n" + "\n" +
                "No cards were handed out . . .");

        alert.showAndWait();
    }
}
