package menus;

import buildings.Building;
import cowParts.CowHandler;
import javafx.scene.CacheHint;
import metaControl.SimState;
import cowParts.Cow;
import cowParts.cowThoughts.Social;
import metaEnvironment.Playground;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

/**
 * A MenuCreation object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal menus.
 */
public class MenuCreation {

    private Cow clickedCow;
    private Tile clickedBuilding;

    private Text numberOfInhabitants;

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
     * @param cowsPreviouslySelected The cows that were previously selected
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        if (SimState.getSimState().equals("DetailedView"))
            createDetailedViewMenu(cowsPreviouslySelected);
        else if (SimState.getSimState().equals("StoryView"))
            createStoryViewMenu(cowsPreviouslySelected);
    }

    /**
     * Creates a new menu for the clicked on building showing the amount of inhabitants in the building.
     * @param buildingToCreateMenuFrom The building to create a menu for
     */
    MenuCreation(Building buildingToCreateMenuFrom) {
        createInhabitantsMenu(buildingToCreateMenuFrom);
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
     * Creates the menu for the given building that shows the number of inhabitants in that building.
     * @param buildingToCreateMenuFrom The building to create an inhabitants menu for
     */
    private void createInhabitantsMenu(@NotNull Tile buildingToCreateMenuFrom) {
        Rectangle background = new Rectangle(50, 50, Color.BLACK);
        numberOfInhabitants = new Text(Integer.toString(((Building) buildingToCreateMenuFrom).getCurrentInhabitants().size()));
        stack = new Pane();

        numberOfInhabitants.setFill(Color.RED);
        numberOfInhabitants.relocate(10, 30);
        numberOfInhabitants.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
        clickedBuilding = buildingToCreateMenuFrom;

        stack.relocate(clickedBuilding.getLayoutX() + 175, clickedBuilding.getLayoutY() + 175);

        stack.getChildren().addAll(background, numberOfInhabitants);
        Playground.playground.getChildren().add(stack);
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
    private Hyperlink createSocialLink(String cowToCreateLinkTo, Cow cowInMenu) {
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
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("anger")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("ANTICIPATION: " + firstCow.self.getAnticipation());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("anticipation")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DISGUST: " + firstCow.self.getDisgust());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("disgust")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("FEAR: " + firstCow.self.getFear());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("fear")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HAPPINESS: " + firstCow.self.getHappiness());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("happiness")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SURPRISE: " + firstCow.self.getSurprise());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("surprise")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TRUST: " + firstCow.self.getTrust());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("trust")));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Finance Links
        hyperlink = new Hyperlink("INCOME: " + firstCow.self.getIncome());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("income")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("BILLS: " + firstCow.self.getBills());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("bills")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TAXES: " + firstCow.self.getTaxes());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("taxes")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SAVINGS: " + firstCow.self.getSavings());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("savings")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DEBT: " + firstCow.self.getDebt());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("debt")));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Social links
        hyperlink = new Hyperlink("BOREDOM: " + firstCow.self.getBoredom());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("boredom")));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("COMPANIONSHIP: " + firstCow.self.getCompanionship());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("companionship")));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Physical
        hyperlink = new Hyperlink("HUNGER: " + firstCow.self.getHunger());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("hunger")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("THIRST: " + firstCow.self.getThirst());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("thirst")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("AGE: " + firstCow.self.getAge());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("age")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.self.getPhysicalHealth());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("physicalHealth")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SLEEPINESS: " + firstCow.self.getSleepiness());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("sleepiness")));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Mental links
        hyperlink = new Hyperlink("FAITH: " + firstCow.self.getFaith());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("faith")));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.self.getMentalHealth());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("mentalHealth")));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Academic links
        hyperlink = new Hyperlink("INTELLIGENCE: " + firstCow.self.getAge());
        hyperlink.setOnAction(event -> switchContent(firstCow.getLogger().getEventsFromEmotion("intelligence")));
        academicLinks.getChildren().add(new TreeItem<>(hyperlink));

        treeTier.getChildren().addAll(emotionLinks, financeLinks, socialLinks, physicalLinks, mentalLinks, academicLinks);
        TreeView<Object> tree = new TreeView<>(treeTier);
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

    /**
     * Switches the socialView content to the given int value.
     * @param socialValue The value to display to the socialView
     */
    private void switchSocialContent(int socialValue) {
        socialViewContent.setText(Integer.toString(socialValue));
    }

    /**
     * Switches the socialView content to the given String value.
     * @param socialValue The value to display to the socialView
     */
    private void switchSocialContent(String socialValue) {
        socialViewContent.setText((socialValue));
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

        overallEmotion = new Text(5, 30, "Emotion: " + cow.self.getEmotionAggregate());
        overallFinance = new Text(5, 30, "Finance: " + cow.self.getFinanceAggregate());
        overallSocial = new Text(5, 30, "Social: " + cow.self.getSocialAggregate());
        overallPhysical = new Text(5, 30, "Physical: " + cow.self.getPhysicalAggregate());
        overallMental = new Text(5, 30, "Mental: " + cow.self.getMentalAggregate());
        overallAcademic = new Text(5, 30, "Academic: " + cow.self.getAcademicAggregate());

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

        stack.setCacheHint(CacheHint.SPEED);
        stack.setCache(true);

        cognitiveAggregates.getChildren().addAll(overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic);
        stack.getChildren().addAll(background, idText, cognitiveAggregates);

        Playground.playground.getChildren().add(stack);

        updateCowMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    void updateCowMenu() {
        stack.relocate((clickedCow.getTranslateX() + 55), (clickedCow.getTranslateY() + 40));
    }

    /**
     * Updates the text containing the amount of inhabitants in the given building's inhabitants menu.
     */
    void updateInhabitantMenu() {
        numberOfInhabitants.setText(Integer.toString(((Building) clickedBuilding).getCurrentInhabitants().size()));
    }

    /**
     * @return The id of the cow who this menu belongs to
     */
    Cow getCowFromMenu() {
        return clickedCow;
    }


}