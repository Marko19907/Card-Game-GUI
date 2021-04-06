package no.ntnu.idata2001.oblig3.cardgame.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import no.ntnu.idata2001.oblig3.cardgame.controller.Controller;

/**
 * Class CardGameGUI represents the main window of the application
 * @author Marko
 * @version 2021-03-29
 */
public class CardGameGUI extends Application
{
    private final Controller controller;
    private final TilePane tilePane;
    private final TextField sumOfFacesTextField;
    private final TextField cardOfHeartsTextField;
    private final TextField queenOfSpadesTextField;
    private final TextField flushTextField;

    public CardGameGUI()
    {
        this.controller = new Controller();
        this.tilePane = new TilePane();

        this.sumOfFacesTextField = new TextField("---");
        this.cardOfHeartsTextField = new TextField("No Hearts");
        this.queenOfSpadesTextField = new TextField("---");
        this.flushTextField = new TextField("---");
    }

    /**
     * The main method
     */
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        BorderPane root = new BorderPane();
        root.setBottom(this.setupBottom());
        root.setRight(this.setupRight());
        root.setCenter(this.setupCenter());

        stage.setTitle("Card game frame");
        stage.setMinWidth(740);
        stage.setMinHeight(520);

        Scene scene = new Scene(root, 720, 520, Color.WHITE);
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
    }

    /**
     * Sets up the center GridPane and ScrollPane
     * @return the already set-up center ScrollPane
     */
    private ScrollPane setupCenter()
    {
        ScrollPane scrollPane = new ScrollPane();

        this.getTilePane().setPadding(new Insets(5, 5, 5, 5));
        this.getTilePane().setStyle("-fx-background-color:#e7e7e7; -fx-opacity:1;");
        this.getTilePane().setVgap(5);
        this.getTilePane().setHgap(5);

        scrollPane.setContent(this.getTilePane());
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    /**
     * Sets up the bottom of the main BorderPane
     * @return the already set-up bottom VBox
     */
    private VBox setupBottom()
    {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));

        HBox hBoxTop = this.setupUpperButtonRow();

        Region verticalSpacer = new Region();
        verticalSpacer.setMinHeight(4.5);

        HBox hBoxBottom = this.setupLowerButtonRow();

        vBox.getChildren().addAll(hBoxTop, verticalSpacer, hBoxBottom);
        return vBox;
    }

    /**
     * Sets up the right vBox with buttons
     * @return The already set-up vBox with buttons
     */
    private VBox setupRight()
    {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));

        Button dealHandButton = new Button("Deal hand");
        dealHandButton.setOnAction(e -> this.controller.dealHand());
        dealHandButton.setPrefWidth(150);
        dealHandButton.setPrefHeight(30);

        Region spacer = new Region();
        spacer.setMinHeight(10);
        spacer.setPrefHeight(15);

        Button checkHandButton = new Button("Check hand");
        checkHandButton.setOnAction(e -> this.controller.checkHand(this.getTilePane(), this.getSumOfFacesTextField(),
                this.getCardOfHeartsTextField(), this.getQueenOfSpadesTextField(), this.getFlushTextField()));
        checkHandButton.setPrefWidth(150);
        checkHandButton.setPrefHeight(30);

        vBox.getChildren().addAll(dealHandButton, spacer, checkHandButton);
        return vBox;
    }

    /**
     * Sets up the upper row of labels for the bottom VBox
     * @return the already set-up upper HBox containing all labels
     */
    private HBox setupUpperButtonRow()
    {
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        spacer.setMinWidth(20);
        spacer.setPrefWidth(30);

        Region edgeSpacer = new Region();
        edgeSpacer.setPrefWidth(1);

        this.setupSmallTextField(this.getSumOfFacesTextField());

        Label sumLabel = new Label("Sum of the faces: ");
        Label heartsCardsLabel = new Label("Cards of hearts: ");

        this.getCardOfHeartsTextField().setEditable(false);
        this.getCardOfHeartsTextField().setDisable(false);
        this.getCardOfHeartsTextField().setAlignment(Pos.CENTER);
        this.getCardOfHeartsTextField().setMaxWidth(310);

        //this.getCardOfHeartsTextField().setStyle("-fx-font-family: 'monospace';");
        //JavaFX has issues with non-monospaced fonts, avoiding the issue with a max width for now
        this.getCardOfHeartsTextField().prefColumnCountProperty()
                 .bind(this.getCardOfHeartsTextField().textProperty().length());

        buttonBox.getChildren().addAll(edgeSpacer, sumLabel, this.getSumOfFacesTextField(),
                spacer, heartsCardsLabel, this.getCardOfHeartsTextField());
        return buttonBox;
    }

    /**
     * Sets up the lower row of labels for the bottom VBox
     * @return the already set-up bottom HBox containing all labels
     */
    private HBox setupLowerButtonRow()
    {
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        spacer.setMinWidth(20);
        spacer.setPrefWidth(30);

        Region edgeSpacer = new Region();
        edgeSpacer.setPrefWidth(1);

        Label flushLabel = new Label("Flush ");

        this.setupSmallTextField(this.getFlushTextField());


        Label queenOfSpadesLabel = new Label("Queen of spades: ");

        this.setupSmallTextField(this.getQueenOfSpadesTextField());

        buttonBox.getChildren().addAll(edgeSpacer, flushLabel, this.getFlushTextField(),
                spacer, queenOfSpadesLabel, this.getQueenOfSpadesTextField());
        return buttonBox;
    }

    /**
     * Sets the size, editability and alignment of the provided TextField
     * @param textField The text field to set the parameters to,
     *                  can not be null
     */
    private void setupSmallTextField(TextField textField)
    {
        if (textField != null) {
            textField.setEditable(false);
            textField.setDisable(false);
            textField.setPrefWidth(35);
            textField.setMinWidth(35);
            textField.setAlignment(Pos.CENTER);
        }
    }

    /**
     * Returns the TilePane with cards
     * @return The TilePane with cards
     */
    private TilePane getTilePane()
    {
        return this.tilePane;
    }

    /**
     * Returns the sum of faces TextField
     * @return The sum of faces TextField
     */
    private TextField getSumOfFacesTextField()
    {
        return this.sumOfFacesTextField;
    }

    /**
     * Returns the cards of hearts TextField
     * @return The cards of hearts TextField
     */
    private TextField getCardOfHeartsTextField()
    {
        return this.cardOfHeartsTextField;
    }

    /**
     * Returns the Queen of spades TextField
     * @return The Queen of spades TextField
     */
    private TextField getQueenOfSpadesTextField()
    {
        return this.queenOfSpadesTextField;
    }

    /**
     * Returns the flush TextField
     * @return The flush TextField
     */
    private TextField getFlushTextField()
    {
        return this.flushTextField;
    }
}
