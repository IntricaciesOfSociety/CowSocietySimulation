package metaControl.menus.menuImplementations;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import metaControl.main.SimState;
import metaControl.menus.MenuHandler;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import societyProduction.technology.BranchHandler;
import societyProduction.technology.branches.BranchElement;

import java.util.ArrayList;

public class TechTreeMenu extends GenericMenu {

    private static Button exitButton;

    private static VBox treeContent;
    private static VBox elementContent;
    private static VBox dependencyContent;
    private static VBox dependencyView;
    private static VBox progressionContent;
    private static VBox progressionView;
    private static HBox treeButtons;

    private static ScrollPane mainScrollBox;
    private static ScrollPane dependencyScrollBox;
    private static ScrollPane progressionScrollBox;

    public TechTreeMenu() {
        createMenu(new Object());
    }

    @Override
    protected void createMenu(Object objectTie) {
        this.stack = new Pane();
        this.background = new Rectangle(SimState.getScreenWidth(), SimState.getScreenHeight(), Color.BLACK);

        exitButton = new Button("EXIT");
        treeContent = new VBox();
        elementContent = new VBox();
        treeButtons = new HBox();
        dependencyContent = new VBox();
        dependencyView = new VBox();
        progressionContent = new VBox();
        progressionView = new VBox();
        progressionScrollBox = new ScrollPane();
        dependencyScrollBox = new ScrollPane();
        mainScrollBox = new ScrollPane();

        Button chemButton = new Button("Chemistry");
        Button cultButton = new Button("Culture");
        Button matButton = new Button("Material");
        Button mathButton = new Button("Mathematics");
        Button phyButton = new Button("Physics");
        Button socButton = new Button("Societal");

        chemButton.setOnAction(event -> switchContent("chem"));
        cultButton.setOnAction(event -> switchContent("cult"));
        matButton.setOnAction(event -> switchContent("mat"));
        mathButton.setOnAction(event -> switchContent("math"));
        phyButton.setOnAction(event -> switchContent("phy"));
        socButton.setOnAction(event -> switchContent("soc"));

        treeButtons.setSpacing(40);
        treeButtons.getChildren().addAll(chemButton, cultButton, matButton, mathButton, phyButton, socButton);

        mainScrollBox.setContent(elementContent);
        mainScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        dependencyScrollBox.setContent(dependencyContent);
        dependencyScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        progressionScrollBox.setContent(progressionContent);
        progressionScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        exitButton.setOnAction(event -> MenuHandler.closeMenu(this));

        treeContent.getChildren().add(mainScrollBox);
        dependencyView.getChildren().add(dependencyScrollBox);
        progressionView.getChildren().add(progressionScrollBox);

        stack.getChildren().addAll(background, exitButton, treeContent, treeButtons, dependencyView, progressionView);
        PlaygroundHandler.playground.getChildren().add(stack);

        updateMenu();
    }

    @Override
    public void updateMenu() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();
        int paneWidth = (screenOffsetX - 200) / 3;
        int paneHeight = screenOffsetY - (int)(screenOffsetY * 0.33);

        background.setLayoutX(0);
        background.setLayoutY(0);
        background.setWidth(screenOffsetX);
        background.setHeight(screenOffsetY);

        treeButtons.relocate((screenOffsetX / 2.0) - treeButtons.getMaxWidth(), 50);

        mainScrollBox.setPrefSize(paneWidth, paneHeight);
        dependencyScrollBox.setPrefSize(paneWidth, paneHeight);
        progressionScrollBox.setPrefSize(paneWidth, paneHeight);

        treeContent.relocate((screenOffsetX - (paneWidth * 3)) / 2, 100);
        dependencyView.relocate(treeContent.getLayoutX() + paneWidth, 100);
        progressionView.relocate(dependencyView.getLayoutX() + paneWidth, 100);

        exitButton.relocate(75, screenOffsetY - 100);
    }

    private void switchContent(String toSwitch) {
        switch (toSwitch) {
            case "chem":
                populateTreeView(createTreeLinks(BranchHandler.getChemBranch().getTreeArray())); break;
            case "cult":
                populateTreeView(createTreeLinks(BranchHandler.getCultBranch().getTreeArray())); break;
            case "mat":
                populateTreeView(createTreeLinks(BranchHandler.getMatBranch().getTreeArray())); break;
            case "math":
                populateTreeView(createTreeLinks(BranchHandler.getMathBranch().getTreeArray())); break;
            case "phy":
                populateTreeView(createTreeLinks(BranchHandler.getPhysBranch().getTreeArray())); break;
            case "soc":
                populateTreeView(createTreeLinks(BranchHandler.getSocBranch().getTreeArray())); break;
        }
    }

    private void populateTreeView(ArrayList<Hyperlink> treeLinks) {
        elementContent.getChildren().clear();
        dependencyContent.getChildren().clear();
        progressionContent.getChildren().clear();
        elementContent.getChildren().addAll(treeLinks);
    }

    private void populateDependencyView(BranchElement elementToGetDependencies) {
        dependencyContent.getChildren().clear();

        for (int i = 0; i < elementToGetDependencies.getDependencies().size(); i++) {
            final Hyperlink callBackLink = new Hyperlink();
            callBackLink.setText(elementToGetDependencies.getDependencies().get(i));
            callBackLink.setOnMouseClicked(event -> {
                for (int j = 0; j < elementContent.getChildren().size(); j++)
                    if (((Hyperlink) elementContent.getChildren().get(j)).getText().equals(callBackLink.getText()))
                        ((Hyperlink) elementContent.getChildren().get(j)).fire();
            });
            dependencyContent.getChildren().add(callBackLink);
        }
    }

    private void populateProgressionView(BranchElement elementToGetProgression) {
        progressionContent.getChildren().clear();
        Label progressionText = new Label();
        progressionText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        progressionText.setTextFill(Color.BLACK);
        progressionText.setText(
                "" + elementToGetProgression.getName() + ": \n" +
                "Development Progress " + elementToGetProgression.getProgress()
        );
        progressionContent.getChildren().add(progressionText);
    }

    private ArrayList<Hyperlink> createTreeLinks(ArrayList<BranchElement> treeArray) {
        Hyperlink elementLink;
        ArrayList<Hyperlink> linkList = new ArrayList<>();
        for (BranchElement element : treeArray) {
            elementLink = new Hyperlink();
            elementLink.setText(element.getName());
            //if (!element.isDiscovered())
                //elementLink.setDisable(true);

            linkList.add(elementLink);
            elementLink.setOnAction(event -> {
                populateDependencyView(element);
                populateProgressionView(element);
            });
        }
        return linkList;
    }

    @Override
    public void closeMenu() {
        SimState.setSimState("Playing");
        PlaygroundHandler.setPlayground("Motion");
        stack.getChildren().clear();
        PlaygroundHandler.playground.getChildren().remove(stack);
    }

    @Override
    protected void openMenu() {

    }
}