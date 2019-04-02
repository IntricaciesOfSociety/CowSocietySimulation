package menus.menuImplementations;

import cowParts.Cow;
import cowParts.CowHandler;
import cowParts.cowThoughts.Social;
import javafx.scene.control.*;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StatsViewMenu extends GenericMenu {

    private static Label topContent;
    private static Label bottomContent;
    private static Label socialViewContent;

    private static VBox topView;
    private static VBox bottomView;
    private static VBox socialView;
    private static VBox socialRelationsView;
    private static ScrollPane topScrollPane;
    private static ScrollPane bottomScrollPane;
    private static ScrollPane socialViewScrollPane;
    private static ScrollPane socialRelationsScrollPane;

    private static Button exitButton;

    private static Text currentStatusText;
    private static Text idText;

    private static TreeView<Object> tree;

    public StatsViewMenu(ArrayList<Cow> cowsPreviouslySelected) {
        createMenu(cowsPreviouslySelected);
    }

    @Override
    protected void createMenu(Object objectTie) {
        this.stack = new Pane();
        this.background = new Rectangle();
        this.objectTie = objectTie;

        topContent = new Label();
        bottomContent = new Label();
        socialViewContent = new Label("Here");

        topView = new VBox();
        bottomView = new VBox();
        socialView = new VBox();
        topScrollPane = new ScrollPane();
        bottomScrollPane = new ScrollPane();
        socialViewScrollPane = new ScrollPane();

        exitButton = new Button("EXIT");

        currentStatusText = new Text("JOB: " + ((ArrayList<Cow>)objectTie).get(0).getJob().getJobName()
                + "    CURRENTLY: " + ((ArrayList<Cow>)objectTie).get(0).getcurrentBehavior());
        idText = new Text(((ArrayList<Cow>)objectTie).get(0).getId());

        topContent.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        bottomContent.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        currentStatusText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

        background.setFill(Color.BLACK);
        currentStatusText.setFill(Color.RED);
        idText.setFill(Color.RED);

        exitButton.setOnAction(event -> closeMenu());

        socialViewScrollPane.setContent(socialViewContent);
        socialViewScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        topScrollPane.setContent(topContent);
        topScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        bottomScrollPane.setContent(bottomContent);
        bottomScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        topView.getChildren().addAll(topContent, topScrollPane);
        bottomView.getChildren().addAll(bottomScrollPane);
        socialView.getChildren().addAll(socialViewContent, socialViewScrollPane);

        stack.getChildren().addAll(background, idText, exitButton, topView, bottomView, socialView, currentStatusText);
        PlaygroundHandler.playground.getChildren().add(stack);

        createCognitionTree(((ArrayList<Cow>)objectTie).get(0));
        createSocialLinks((ArrayList<Cow>)objectTie);

        updateMenu();
    }

    @Override
    public void updateMenu() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setWidth(screenOffsetX);
        background.setHeight(screenOffsetY);
        background.relocate(0, 0);

        tree.relocate(175, 80);
        tree.setPrefHeight((screenOffsetY / 2) - 50);

        exitButton.relocate(75, screenOffsetY - 100);

        currentStatusText.relocate(175, tree.getLayoutY() + tree.getPrefHeight() + 5);

        idText.relocate(160, 30);

        socialViewScrollPane.setPrefWidth(300);
        socialViewScrollPane.setPrefHeight(screenOffsetY / 4);

        socialRelationsScrollPane.setPrefWidth(250);
        socialRelationsScrollPane.setPrefHeight(screenOffsetY / 4);

        topScrollPane.setPrefWidth(300);
        topScrollPane.setPrefHeight(125);

        bottomScrollPane.setPrefWidth(300);
        bottomScrollPane.setPrefHeight(125);

        socialRelationsScrollPane.relocate(currentStatusText.getLayoutX(), currentStatusText.getLayoutY() + 10);
        topView.relocate(455, 50);
        bottomView.relocate(455, 185);
        socialView.relocate((socialRelationsScrollPane.getLayoutX() + 260), socialRelationsScrollPane.getLayoutY());
    }

    @Override
    protected void closeMenu() {
        SimState.setSimState("Playing");
        PlaygroundHandler.setPlayground("Motion");
        stack.getChildren().clear();
        PlaygroundHandler.playground.getChildren().remove(stack);
    }

    @Override
    protected void openMenu() {
    }

    /**
     * Creates both the links and the link structures for the socialView. The links within the view are dependant on the
     * cow(s) selected.
     * @param cowsPreviouslySelected The cows that were selected when the detailedView button was clicked.
     */
    private void createSocialLinks(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        socialRelationsScrollPane = new ScrollPane();
        socialRelationsView = new VBox();

        if (cowsPreviouslySelected.size() == 1) {
            ArrayList<String> relations = Social.getAllRelations(cowsPreviouslySelected.get(0));
            if (relations != null) {
                for (String relation : relations) {
                    socialRelationsView.getChildren().add( createSocialLink(relation, cowsPreviouslySelected.get(0)) );
                }
            }
        }
        else {
            for (int i = 1; i < cowsPreviouslySelected.size(); i++) {
                if (Social.relationExists(cowsPreviouslySelected.get(0), cowsPreviouslySelected.get(i)))
                    socialRelationsView.getChildren().add(createSocialLink(cowsPreviouslySelected.get(i).getId(), cowsPreviouslySelected.get(0)));
            }
        }

        socialRelationsScrollPane.setContent(socialRelationsView);
        socialRelationsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        stack.getChildren().addAll(socialRelationsView, socialRelationsScrollPane);
    }

    /**
     * Creates a hyperlink given a cow's id to be used in the socialView.
     * @param cowToCreateLinkTo The cow that the hyperlink is to be created from.
     * @param cowInMenu The cow whose detailed menu view is opened.
     * @return The hyperlink that was created.
     */
    private static Hyperlink createSocialLink(String cowToCreateLinkTo, Cow cowInMenu) {
        if (CowHandler.findCow(cowToCreateLinkTo) == null) {
            Hyperlink hyperlink = new Hyperlink(cowToCreateLinkTo);
            hyperlink.setOnAction(event -> switchSocialContent("Deceased"));
            return hyperlink;
        }

        Hyperlink hyperlink = new Hyperlink(cowToCreateLinkTo);
        hyperlink.setOnAction(event -> switchSocialContent(Social.getRelationValue(cowInMenu, cowToCreateLinkTo)));
        return hyperlink;
    }

    /**
     * Creates the tree structure and links within the tree structure that show the selected cow's stats within the
     * detailed view menu.
     * @param firstCow The first cow in the input array to have its stats be drawn.
     */
    private void createCognitionTree(@NotNull Cow firstCow) {
        TreeItem<Object> treeTier = new TreeItem<>();
        TreeItem<Object> emotionLinks = new TreeItem<>(new Text("Emotions")),
                financeLinks = new TreeItem<>(new Text("Finances")),
                socialLinks = new TreeItem<>(new Text("Social")),
                physicalLinks = new TreeItem<>(new Text("Physical")),
                mentalLinks = new TreeItem<>(new Text("Mental")),
                academicLinks = new TreeItem<>(new Text("Academics"));

        Hyperlink hyperlink;

        //Emotion links
        hyperlink = new Hyperlink("ANGER: " + firstCow.self.getAnger());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "anger")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("ANTICIPATION: " + firstCow.self.getAnticipation());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "anticipation")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DISGUST: " + firstCow.self.getDisgust());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "disgust")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("FEAR: " + firstCow.self.getFear());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "fear")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HAPPINESS: " + firstCow.self.getHappiness());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "happiness")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SURPRISE: " + firstCow.self.getSurprise());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "surprise")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TRUST: " + firstCow.self.getTrust());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "trust")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Finance Links
        hyperlink = new Hyperlink("INCOME: " + firstCow.self.getIncome());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "income")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("BILLS: " + firstCow.self.getBills());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "bills")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TAXES: " + firstCow.self.getTaxes());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "taxes")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SAVINGS: " + firstCow.self.getSavings());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "savings")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DEBT: " + firstCow.self.getDebt());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "debt")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Social links
        hyperlink = new Hyperlink("BOREDOM: " + firstCow.self.getBoredom());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "boredom")));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("COMPANIONSHIP: " + firstCow.self.getCompanionship());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "companionship")));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Physical
        hyperlink = new Hyperlink("HUNGER: " + firstCow.self.getHunger());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "hunger")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("THIRST: " + firstCow.self.getThirst());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "thirst")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("AGE: " + firstCow.self.getAge());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "age")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.self.getPhysicalHealth());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "physicalHealth")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SLEEPINESS: " + firstCow.self.getSleepiness());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "sleepiness")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Mental links
        hyperlink = new Hyperlink("FAITH: " + firstCow.self.getFaith());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "faith")));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.self.getMentalHealth());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "mentalHealth")));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Academic links
        hyperlink = new Hyperlink("INTELLIGENCE: " + firstCow.self.getAge());
        hyperlink.setOnAction(event -> switchContent(EventLogger.getCowStatLog(firstCow, "intelligence")));
        academicLinks.getChildren().add(new TreeItem<>(hyperlink));

        treeTier.getChildren().addAll(emotionLinks, financeLinks, socialLinks, physicalLinks, mentalLinks, academicLinks);

        tree = new TreeView<>(treeTier);
        tree.setShowRoot(false);
        stack.getChildren().add(tree);
    }

    /**
     * Switches the events content to the according top/bottom view and moves the other top/bottom view's content either
     * out entirely, or to the opposite top/bottom view.
     * @param events The content to be switched to the top or bottom view.
     */
    private static void switchContent(String events) {
        if (topContent.getText().length() != 0 && events.length() != 0)
            bottomContent.setText(topContent.getText());
        if (events.length() != 0)
            topContent.setText(events);
    }

    /**
     * Switches the socialView content to the given int value.
     * @param socialValue The value to display to the socialView
     */
    private static void switchSocialContent(int socialValue) {
        socialViewContent.setText(Integer.toString(socialValue));
    }

    /**
     * Switches the socialView content to the given String value.
     * @param socialValue The value to display to the socialView
     */
    private static void switchSocialContent(String socialValue) {
        socialViewContent.setText((socialValue));
    }
}
