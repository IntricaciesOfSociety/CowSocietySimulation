package menus;

import cowParts.Cow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import metaControl.SimState;
import metaEnvironment.Playground;
import metaEnvironment.logging.EventLogger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoryViewMenu {
    /**
     * Creates the story view menu which displays the given cow's entire log
     * @param cowsPreviouslySelected The cows selected when the story view was clicked to open.
     */
    static void createStoryViewMenu(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        VBox logContent = new VBox();
        ScrollPane logScroll = new ScrollPane();

        Label logText = new Label(EventLogger.getEntireCowLog(cowsPreviouslySelected.get(0)));

        Button exitButton = new Button("EXIT");
        Rectangle background = new Rectangle();
        background.setLayoutX(150);
        background.setLayoutY(0);
        background.setWidth(650);
        background.setHeight(600);

        Text idText = new Text(cowsPreviouslySelected.get(0).getId());
        idText.setLayoutX(160);
        idText.setLayoutY(30);
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        idText.setFill(Color.RED);

        logScroll.setContent(logText);
        logScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        logScroll.setPrefWidth(400);
        logScroll.setPrefHeight(500);

        idText.relocate(200, 10);

        exitButton.setLayoutX(160);
        exitButton.setLayoutY(560);
        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
        });

        logContent.getChildren().addAll(logScroll, logText);
        logContent.relocate(300, 50);

        Playground.playground.getChildren().addAll(background, idText, logContent, exitButton);
    }
}
