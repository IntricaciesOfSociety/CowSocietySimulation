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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StoryViewMenu {
    
    private static boolean isOpened;

    private static VBox logContent;
    private static ScrollPane logScroll;
    private static Button exitButton;
    private static Rectangle background;
    private static Text idText;
    
    /**
     * Creates the story view menu which displays the given cow's entire log
     * @param cowsPreviouslySelected The cows selected when the story view was clicked to open.
     */
    static void createStoryViewMenu(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        isOpened = true;
        
        logContent = new VBox();
        logScroll = new ScrollPane();

        Label logText = new Label(EventLogger.getEntireCowLog(cowsPreviouslySelected.get(0)));

        exitButton = new Button("EXIT");
        background = new Rectangle();

        idText = new Text(cowsPreviouslySelected.get(0).getId());
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        idText.setFill(Color.RED);

        logScroll.setContent(logText);
        logScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
            isOpened = false;
        });

        logContent.getChildren().addAll(logScroll, logText);

        Playground.playground.getChildren().addAll(background, idText, logContent, exitButton);

        updateUIPlacements();
    }


    @Contract(pure = true)
    public static boolean isOpened() {
        return isOpened;
    }

    public static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setLayoutX(150);
        background.setLayoutY(0);
        background.setWidth(650);
        background.setHeight(600);

        idText.relocate(200, 10);

        logScroll.setPrefWidth(400);
        logScroll.setPrefHeight(500);

        exitButton.setLayoutX(160);
        exitButton.setLayoutY(560);

        logContent.relocate(300, 50);
    }
}
