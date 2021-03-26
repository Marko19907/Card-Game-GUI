package no.ntnu.idata2001.oblig3.cardgame;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * The main controller of the application
 * Responsible for handling all actions related to the buttons in the user interface
 */
public class Controller
{
    private static final int MAX_HORIZONTAL_ROWS = 3;
    private final PlayingHand hand;
    private final ImageLoader imageLoader;
    private int horizontalPos;
    private int verticalPos;

    public Controller()
    {
        this.hand = new PlayingHand();
        this.imageLoader = new ImageLoader();

        this.horizontalPos = 0;
        this.verticalPos = 0;
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

    public void checkHand(GridPane gridPane)
    {
        List<PlayingCard> activeCards = this.hand.getActiveCards();

        for (PlayingCard card : activeCards) {
            ImageView imageView = this.imageLoader.getCardImage(card.getAsString());

            if (imageView != null) {
                if (this.horizontalPos >= MAX_HORIZONTAL_ROWS) {
                    this.horizontalPos = 0;
                    this.verticalPos++;
                }
                gridPane.add(imageView, this.horizontalPos, this.verticalPos);
                this.horizontalPos++;
            }
        }
    }

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
        alert.setTitle("Information");
        alert.setHeaderText("Not enough cards");
        alert.setContentText("The number of cards you requested is bigger than " +
                "the number of cards available in the deck" + "\n" + "\n" +
                "Number of cards left in the deck: " + this.hand.getNumberOfCardsLeft());

        alert.showAndWait();
    }
}
