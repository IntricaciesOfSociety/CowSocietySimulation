package menus;

import metaControl.SimState;
import cowParts.Cow;
import cowParts.Social;
import metaEnvironment.Playground;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A MenuCreation object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal menus.
 */
public class MenuCreation {

    private Cow clickedCow;

    //Statistics texts
    private VBox cognitiveAggregates = new VBox();

    private Label topContent = new Label();
    private Label bottomContent = new Label();
    private Label socialViewContent = new Label("Here");

    Pane stack;

    /**
     * Calls the creation of a new popup menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    MenuCreation(@NotNull Cow cow) {
        createPopupMenu(cow);
    }

    /**
     * Creates a new menu (detailedView) from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected.
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        if (SimState.getSimState().equals("DetailedView"))
            createDetailedViewMenu(cowsPreviouslySelected);
        else if (SimState.getSimState().equals("StoryView"))
            createStoryViewMenu(cowsPreviouslySelected);
    }

    /**
     * Creates the UI elements for the detailedView menu.
     * @param cowsPreviouslySelected The cows previously selected when the detailedView was clicked to open
     */
    private void createDetailedViewMenu(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        VBox topView = new VBox();
        VBox bottomView = new VBox();
        VBox socialView = new VBox();
        ScrollPane topScrollPane = new ScrollPane();
        ScrollPane bottomScrollPane = new ScrollPane();
        ScrollPane socialViewScrollPane = new ScrollPane();

        Button exitButton = new Button("EXIT");
        Rectangle background = new Rectangle(150, 0, 650, 600);

        Text currentStatusText = new Text("JOB: " + cowsPreviouslySelected.get(0).getJob()
                              + "    CURRENTLY: " + cowsPreviouslySelected.get(0).getCurrentAction());
        Text idText = new Text(160, 30, cowsPreviouslySelected.get(0).getId());

        topContent.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        bottomContent.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        currentStatusText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

        background.setFill(Color.BLACK);
        currentStatusText.setFill(Color.RED);
        idText.setFill(Color.RED);

        currentStatusText.relocate(160, 325);

        exitButton.relocate(160, 560);
        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
        });

        socialViewScrollPane.setContent(socialViewContent);
        socialViewScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        socialViewScrollPane.setPrefWidth(300);
        socialViewScrollPane.setPrefHeight(200);

        topScrollPane.setContent(topContent);
        topScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        topScrollPane.setPrefWidth(300);
        topScrollPane.setPrefHeight(125);

        bottomScrollPane.setContent(bottomContent);
        bottomScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        bottomScrollPane.setPrefWidth(300);
        bottomScrollPane.setPrefHeight(125);

        topView.getChildren().addAll(topContent, topScrollPane);
        topView.relocate(455, 50);
        bottomView.getChildren().addAll(bottomScrollPane);
        bottomView.relocate(455, 185);
        socialView.getChildren().addAll(socialViewContent, socialViewScrollPane);
        socialView.relocate(455, 350);

        Playground.playground.getChildren().addAll(background, idText, exitButton, topView, bottomView, socialView, currentStatusText);

        createCognitionTree(cowsPreviouslySelected.get(0));
        createSocialLinks(cowsPreviouslySelected);
    }

    /**
     * TODO: Implement storyView
     * @param cowsPreviouslySelected The cows selected when the story view was clicked to open.
     */
    private void createStoryViewMenu(ArrayList<Cow> cowsPreviouslySelected) {
        Button exitButton = new Button("EXIT");
        Rectangle background = new Rectangle(150, 0, 650, 600);

        exitButton.setLayoutX(160);
        exitButton.setLayoutY(560);
        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
        });

        Playground.playground.getChildren().addAll(background, exitButton);
    }

    /**
     * Creates both the links and the link structures for the socialView. The links within the view are dependant on the
     * cow(s) selected.
     * @param cowsPreviouslySelected The cows that were selected when the detailedView button was clicked.
     */
    private void createSocialLinks(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        VBox socialRelationsView = new VBox();
        ScrollPane socialRelationsScrollPane = new ScrollPane();

        if (cowsPreviouslySelected.size() == 1) {
            ArrayList<String> relations = Social.getAllRelations(cowsPreviouslySelected.get(0));
            for (String relation : relations) {
                socialRelationsView.getChildren().add(createSocialLink(Cow.findCow(relation), cowsPreviouslySelected.get(0)));
            }
        }
        else {
            for (int i = 1; i < cowsPreviouslySelected.size(); i++) {
                if (Social.relationExists(cowsPreviouslySelected.get(0), cowsPreviouslySelected.get(i)))
                    socialRelationsView.getChildren().add(createSocialLink(cowsPreviouslySelected.get(i), cowsPreviouslySelected.get(0)));
            }
        }

        socialRelationsScrollPane.setContent(socialRelationsView);
        socialRelationsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        socialRelationsScrollPane.setPrefWidth(250);
        socialRelationsScrollPane.setPrefHeight(150);

        socialRelationsScrollPane.relocate(175, 350);

        Playground.playground.getChildren().addAll(socialRelationsView, socialRelationsScrollPane);
    }

    /**
     * Creates a hyperlink given a cow's id to be used in the socialView.
     * @param cowToCreateLinkTo The cow that the hyperlink is to be created from.
     * @param cowInMenu The cow whose detailed menu view is opened.
     * @return The hyperlink that was created.
     */
    private Hyperlink createSocialLink(@NotNull Cow cowToCreateLinkTo, Cow cowInMenu) {
        Hyperlink hyperlink = new Hyperlink(cowToCreateLinkTo.getId());
        hyperlink.setOnAction(event -> switchSocialContent(Social.getRelationValue(cowInMenu, cowToCreateLinkTo.getId())));
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
        hyperlink = new Hyperlink("ANGER: " + firstCow.getAnger());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("anger")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("ANTICIPATION: " + firstCow.getAnticipation());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("anticipation")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DISGUST: " + firstCow.getDisgust());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("disgust")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("FEAR: " + firstCow.getFear());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("fear")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HAPPINESS: " + firstCow.getHappiness());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("happiness")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SURPRISE: " + firstCow.getSurprise());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("surprise")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TRUST: " + firstCow.getTrust());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("trust")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Finance Links
        hyperlink = new Hyperlink("INCOME: " + firstCow.getIncome());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("income")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("BILLS: " + firstCow.getBills());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("bills")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("FOOD: " + firstCow.getFood());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("food")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TAXES: " + firstCow.getTaxes());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("taxes")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SAVINGS: " + firstCow.getSavings());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("savings")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DEBT: " + firstCow.getDebt());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("debt")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Social links
        hyperlink = new Hyperlink("BOREDOM: " + firstCow.getBoredom());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("boredom")));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("COMPANIONSHIP: " + firstCow.getCompanionship());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("companionship")));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Physical
        hyperlink = new Hyperlink("HUNGER: " + firstCow.getHunger());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("hunger")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("AGE: " + firstCow.getAge());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("age")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.getPhysicalHealth());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("physicalHealth")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Mental links
        hyperlink = new Hyperlink("FAITH: " + firstCow.getFaith());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("faith")));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.getMentalHealth());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("mentalHealth")));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Academic links
        hyperlink = new Hyperlink("INTELLIGENCE: " + firstCow.getAge());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("intelligence")));
        academicLinks.getChildren().add(new TreeItem<>(hyperlink));

        treeTier.getChildren().addAll(emotionLinks, financeLinks, socialLinks, physicalLinks, mentalLinks, academicLinks);
        TreeView tree = new TreeView<>(treeTier);
        tree.setShowRoot(false);
        tree.relocate(175, 50);
        tree.setPrefHeight(260);
        Playground.playground.getChildren().add(tree);
    }

    /**
     * Switches the events content to the according top/bottom view and moves the other top/bottom view's content either
     * out entirely, or to the opposite top/bottom view.
     * @param events The content to be switched to the top or bottom view.
     */
    private void switchContent(String events) {
        if (topContent.getText().length() != 0 && events.length() != 0)
            bottomContent.setText(topContent.getText());
        if (events.length() != 0)
            topContent.setText(events);
    }

    private void switchSocialContent(int socialValue) {
        socialViewContent.setText(Integer.toString(socialValue));
    }

    /**
     * Creates the UI elements for the popup menu.
     * @param cow The cow who's menu is being created.
     */
    private void createPopupMenu(@NotNull Cow cow) {
        clickedCow = cow;

        Text overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic;
        Rectangle background = new Rectangle(0,0, 110, 150);

        cognitiveAggregates.setSpacing(8);
        cognitiveAggregates.setLayoutX(5);
        cognitiveAggregates.setLayoutY(20);

        overallEmotion = new Text(5, 30, "Emotion: " + cow.getEmotionAggregate());
        overallFinance = new Text(5, 30, "Finance: " + cow.getFinanceAggregate());
        overallSocial = new Text(5, 30, "Social: " + cow.getSocialAggregate());
        overallPhysical = new Text(5, 30, "Physical: " + cow.getPhysicalAggregate());
        overallMental = new Text(5, 30, "Mental: " + cow.getMentalAggregate());
        overallAcademic = new Text(5, 30, "Academic: " + cow.getAcademicAggregate());

        Text idText = new Text(5,15, cow.getId());
        stack = new Pane();

        overallEmotion.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallFinance.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallSocial.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallPhysical.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallMental.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallAcademic.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        overallEmotion.setFill(Color.RED);
        overallFinance.setFill(Color.RED);
        overallSocial.setFill(Color.RED);
        overallPhysical.setFill(Color.RED);
        overallMental.setFill(Color.RED);
        overallAcademic.setFill(Color.RED);

        background.setFill(Color.BLACK);
        idText.setFill(Color.WHITE);

        cognitiveAggregates.getChildren().addAll(overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic);
        stack.getChildren().addAll(background, idText, cognitiveAggregates);

        Playground.playground.getChildren().add(stack);

        updateCowMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    void updateCowMenu() {
        stack.relocate((clickedCow.getAnimatedX() + 55), (clickedCow.getAnimatedY() + 40));
    }

    /**
     * @return The id of the cow who this menu belongs to
     */
    Cow getCowFromMenu() {
        return clickedCow;
    }
}