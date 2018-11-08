package menus;

import control.SimState;
import environment.Cow;
import environment.Playground;
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
    private Text overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic;

    private Label topContent = new Label();
    private Label bottomContent = new Label();

    Pane stack;

    /**
     * Creates a new menu (detailedView) from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected.
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        VBox topView = new VBox();
        VBox bottomView = new VBox();

        ScrollPane topScrollPane = new ScrollPane();
        ScrollPane bottomScrollPane = new ScrollPane();

        StringBuilder idTextString = new StringBuilder();

        for (Cow selectedCow : cowsPreviouslySelected) {
            idTextString.append(selectedCow.getId()).append(" | ");
        }

        Rectangle background = new Rectangle(150, 0, 650, 600);

        Text idText = new Text(160, 30, idTextString.toString());
        Button exitButton = new Button("EXIT");

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

        background.setFill(Color.BLACK);
        idText.setFill(Color.RED);

        exitButton.setLayoutX(160);
        exitButton.setLayoutY(560);
        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
        });

        topContent.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        bottomContent.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        topScrollPane.setContent(topContent);
        topScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        topScrollPane.setPrefWidth(300);
        topScrollPane.setPrefHeight(200);

        bottomScrollPane.setContent(bottomContent);
        bottomScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        bottomScrollPane.setPrefWidth(300);
        bottomScrollPane.setPrefHeight(200);

        topView.getChildren().addAll(topContent, topScrollPane);
        topView.setLayoutX(455);
        topView.setLayoutY(30);
        bottomView.getChildren().addAll(bottomScrollPane);
        bottomView.setLayoutX(455);
        bottomView.setLayoutY(250);

        Playground.playground.getChildren().addAll(background, idText, exitButton, topView, bottomView);
        createCognitionTree(cowsPreviouslySelected.get(0));
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
        hyperlink.setOnAction(event -> {
            if (firstCow.getLogger().effectedEmotions.contains("anger")) switchContent(firstCow.getLogger().getEventsFromEmotion("anger"));
        });
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("ANTICIPATION: " + firstCow.getAnticipation());
        hyperlink.setOnAction(event -> System.out.println("Anticipation breakdown"));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DISGUST: " + firstCow.getDisgust());
        hyperlink.setOnAction(event -> System.out.println("Disgust breakdown"));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("FEAR: " + firstCow.getFear());
        hyperlink.setOnAction(event -> System.out.println("Fear breakdown"));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HAPPINESS: " + firstCow.getHappiness());
        hyperlink.setOnAction(event -> System.out.println("Happiness breakdown"));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SURPRISE: " + firstCow.getSurprise());
        hyperlink.setOnAction(event -> System.out.println("Surprise breakdown"));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TRUST: " + firstCow.getTrust());
        hyperlink.setOnAction(event -> System.out.println("Trust breakdown"));
        emotionLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Finance Links
        hyperlink = new Hyperlink("INCOME: " + firstCow.getIncome());
        hyperlink.setOnAction(event -> System.out.println("Income breakdown"));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("BILLS: " + firstCow.getBills());
        hyperlink.setOnAction(event -> System.out.println("Bills breakdown"));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("FOOD: " + firstCow.getFood());
        hyperlink.setOnAction(event -> System.out.println("Food breakdown"));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("TAXES: " + firstCow.getTaxes());
        hyperlink.setOnAction(event -> System.out.println("Taxes breakdown"));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("SAVINGS: " + firstCow.getSavings());
        hyperlink.setOnAction(event -> System.out.println("Savings breakdown"));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("DEBT: " + firstCow.getDebt());
        hyperlink.setOnAction(event -> System.out.println("Debt breakdown"));
        financeLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Social links
        hyperlink = new Hyperlink("BOREDOM: " + firstCow.getBoredom());
        hyperlink.setOnAction(event -> System.out.println("Boredom breakdown"));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("COMPANIONSHIP: " + firstCow.getCompanionship());
        hyperlink.setOnAction(event -> System.out.println("Companionship breakdown"));
        socialLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Physical
        hyperlink = new Hyperlink("HUNGER: " + firstCow.getHunger());
        hyperlink.setOnAction(event -> { if (firstCow.getLogger().effectedEmotions.contains("hunger")) switchContent(firstCow.getLogger().getEventsFromEmotion("hunger")); });
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("AGE: " + firstCow.getAge());
        hyperlink.setOnAction(event -> { if (firstCow.getLogger().effectedEmotions.contains("age")) switchContent(firstCow.getLogger().getEventsFromEmotion("age")); });
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.getPhysicalHealth());
        hyperlink.setOnAction(event -> System.out.println("Health breakdown"));
        physicalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Mental links
        hyperlink = new Hyperlink("FAITH: " + firstCow.getFaith());
        hyperlink.setOnAction(event -> System.out.println("Faith breakdown"));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        hyperlink = new Hyperlink("HEALTH: " + firstCow.getMentalHealth());
        hyperlink.setOnAction(event -> System.out.println("Health breakdown"));
        mentalLinks.getChildren().add(new TreeItem<>(hyperlink));

        //Academic links
        hyperlink = new Hyperlink("HERE: " + firstCow.getAge());
        hyperlink.setOnAction(event -> System.out.println("Here breakdown"));
        academicLinks.getChildren().add(new TreeItem<>(hyperlink));

        treeTier.getChildren().addAll(emotionLinks, financeLinks, socialLinks, physicalLinks, mentalLinks, academicLinks);
        TreeView tree = new TreeView<>(treeTier);
        tree.setShowRoot(false);
        tree.relocate(150, 40);
        Playground.playground.getChildren().add(tree);
    }

    /**
     * Switches the stats content to the according top/bottom view and moves the other top/bottom view's content either
     * out entierly, or to the opposite top/bottom view.
     * @param events The content to be switched to the top or bottom view.
     */
    private void switchContent(String events) {
        if (!topContent.getText().equals(""))
            bottomContent.setText(topContent.getText());
        topContent.setText(events);

    }

    /**
     * Temp Creates a menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    MenuCreation(@NotNull Cow cow) {
        clickedCow = cow;

        Rectangle background = new Rectangle(0,0, 110, 150);
        overallEmotion = new Text(5, 30, "Emotion: ");
        Text idText = new Text(5,15, cow.getId());
        stack = new Pane();

        overallEmotion.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        overallEmotion.setFill(Color.RED);
        background.setFill(Color.BLACK);
        idText.setFill(Color.WHITE);

        stack.getChildren().addAll(background, idText, overallEmotion);
        Playground.playground.getChildren().add(stack);

        updateCowMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    void updateCowMenu() {
        stack.relocate((clickedCow.getAnimatedX() + 55), (clickedCow.getAnimatedY() + 40));
        overallEmotion.setText("Emotion: ");

    }

    /**
     * @return The id of the cow who this menu belongs to
     */
    Cow getCowFromMenu() {
        return clickedCow;
    }
}