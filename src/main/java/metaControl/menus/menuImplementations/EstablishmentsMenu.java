package metaControl.menus.menuImplementations;

import infrastructure.establishments.EstablishmentHandler;
import infrastructure.establishments.EstablishmentTypes.BusinessEstablishment;
import infrastructure.establishments.EstablishmentTypes.FollowingEstablishment;
import infrastructure.establishments.EstablishmentTypes.GovernmentEstablishment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import metaControl.menus.MenuHandler;
import metaControl.main.SimState;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;

import java.util.ArrayList;

public class EstablishmentsMenu extends GenericMenu {

    private static VBox establishmentList;
    private static ScrollPane listScroll;
    private static Button exitButton;

    public EstablishmentsMenu() {
        createMenu(new Object());
    }

    @Override
    protected void createMenu(Object objectTie) {

        this.stack = new Pane();
        this.background = new Rectangle(100, 50, Color.BLACK);

        establishmentList = new VBox();
        listScroll = new ScrollPane();
        Label listContent = new Label("");

        ArrayList<BusinessEstablishment> business = EstablishmentHandler.getBusinesses();
        for (int i = 0; i < business.size(); i++) {
            listContent.setText(listContent.getText().concat(business.toString() + "\n"));
        }

        ArrayList<FollowingEstablishment> following = EstablishmentHandler.getFollowings();
        for (int i = 0; i < following.size(); i++) {
            listContent.setText(listContent.getText().concat(following.toString() + "\n"));
        }

        ArrayList<GovernmentEstablishment> government = EstablishmentHandler.getGovernments();
        for (int i = 0; i < government.size(); i++) {
            listContent.setText(listContent.getText().concat(government.toString() + "\n"));
        }

        exitButton = new Button("EXIT");

        background = new Rectangle();

        listScroll.setContent(listContent);
        listScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        exitButton.setOnAction(event -> MenuHandler.closeMenu(this));

        establishmentList.getChildren().addAll(listScroll, listContent);

        stack.getChildren().addAll(background, establishmentList, exitButton);
        PlaygroundHandler.playground.getChildren().add(stack);
        updateMenu();
    }

    @Override
    public void updateMenu() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setLayoutX(0);
        background.setLayoutY(0);
        background.setWidth(screenOffsetX);
        background.setHeight(screenOffsetY);

        listScroll.setPrefWidth(400);
        listScroll.setPrefHeight(500);

        exitButton.relocate(75, screenOffsetY - 100);

        establishmentList.relocate(300, 50);
    }

    @Override
    public void closeMenu() {
        stack.getChildren().clear();
        PlaygroundHandler.playground.getChildren().remove(stack);
        SimState.setSimState("Playing");
        PlaygroundHandler.setPlayground("Motion");
    }

    @Override
    protected void openMenu() {
    }
}
