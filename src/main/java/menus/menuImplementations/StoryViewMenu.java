package menus.menuImplementations;

import cowParts.Cow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import menus.GenericMenu;
import metaControl.main.SimState;
import metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import metaEnvironment.logging.EventLogger;

import java.util.ArrayList;

public class StoryViewMenu extends GenericMenu {

    private static VBox logContent;
    private static ScrollPane logScroll;
    private static Button exitButton;
    private static Text idText;

    public StoryViewMenu(ArrayList<Cow> cowsPreviouslySelected) {
        createMenu(cowsPreviouslySelected);
    }

    @Override
    protected void createMenu(Object objectTie) {
        this.stack = new Pane();
        this.background = new Rectangle(100, 50, Color.BLACK);
        this.objectTie = objectTie;

        logContent = new VBox();
        logScroll = new ScrollPane();

        Label logText = new Label(EventLogger.getEntireCowLog(((ArrayList<Cow>)objectTie).get(0)));

        exitButton = new Button("EXIT");
        background = new Rectangle();

        idText = new Text(((ArrayList<Cow>)objectTie).get(0).getId());
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        idText.setFill(Color.RED);

        logScroll.setContent(logText);
        logScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        exitButton.setOnAction(event -> {
            closeMenu();
        });

        logContent.getChildren().addAll(logScroll, logText);

        PlaygroundHandler.playground.getChildren().addAll(background, idText, logContent, exitButton);

        updateMenu();
    }

    @Override
    public void updateMenu() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setLayoutX(150);
        background.setLayoutY(0);
        background.setWidth(650);
        background.setHeight(600);

        idText.relocate(200, 10);

        logScroll.setPrefWidth(400);
        logScroll.setPrefHeight(500);

        exitButton.relocate(75, screenOffsetY - 100);

        logContent.relocate(300, 50);
    }

    @Override
    protected void closeMenu() {
        SimState.setSimState("Playing");
        PlaygroundHandler.setPlayground("Motion");
    }

    @Override
    protected void openMenu() {

    }
}
