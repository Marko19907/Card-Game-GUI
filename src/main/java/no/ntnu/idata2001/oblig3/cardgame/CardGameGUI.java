package no.ntnu.idata2001.oblig3.cardgame;

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

/**
 * Class ToDoListAppGUI represents the main window of the application
 */
public class CardGameGUI extends Application
{
    private final Controller controller;
    private final TilePane tilePane;

    public CardGameGUI()
    {
        this.controller = new Controller();
        this.tilePane = new TilePane();
    }

    /**
     * The main method
     */
    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        BorderPane root = new BorderPane();
        root.setBottom(this.setupBottom());
        root.setRight(this.setupRight());
        root.setCenter(this.setupCenter());

        stage.setTitle("Card game frame");
        stage.setMinWidth(400);
        stage.setMinHeight(200);

        Scene scene = new Scene(root, 600, 400, Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets up the center GridPane and ScrollPane
     * @return the already set-up center ScrollPane
     */
    private ScrollPane setupCenter()
    {
        ScrollPane scrollPane = new ScrollPane();

        this.tilePane.setPadding(new Insets(5, 5, 5, 5));
        this.tilePane.setStyle("-fx-background-color:#e7e7e7; -fx-opacity:1;");
        this.tilePane.setVgap(5);
        this.tilePane.setHgap(5);

        scrollPane.setContent(this.tilePane);
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
        checkHandButton.setOnAction(e -> this.controller.checkHand(this.tilePane));
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
        //TODO: Use a GridPane here instead?
        buttonBox.setSpacing(5);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        spacer.setMinWidth(20);
        spacer.setPrefWidth(30);

        Region edgeSpacer = new Region();
        edgeSpacer.setPrefWidth(1);

        Label sumLabel = new Label("Sum of the faces: ");

        TextField sumOfTheFacesText = new TextField("Sum of the faces test");
        sumOfTheFacesText.setEditable(false);
        sumOfTheFacesText.setDisable(false);


        Label heartsCardsLabel = new Label("Cards of hearts: ");

        TextField cardOfHeartsText = new TextField("No Hearts");
        cardOfHeartsText.setEditable(false);
        cardOfHeartsText.setDisable(false);

        buttonBox.getChildren().addAll(edgeSpacer, sumLabel, sumOfTheFacesText,
                spacer, heartsCardsLabel, cardOfHeartsText);
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

        TextField flushText = new TextField("Yes/No");
        flushText.setEditable(false);
        flushText.setDisable(false);
        flushText.setMaxWidth(60);


        Label queenOfSpadesLabel = new Label("Queen of spades: ");

        TextField queenOfSpadesText = new TextField("Yes/No");
        queenOfSpadesText.setEditable(false);
        queenOfSpadesText.setDisable(false);
        queenOfSpadesText.setMaxWidth(60);

        buttonBox.getChildren().addAll(edgeSpacer, flushLabel, flushText,
                spacer, queenOfSpadesLabel, queenOfSpadesText);
        return buttonBox;
    }
}
