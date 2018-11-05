package menus;

import control.SimState;
import environment.Cow;
import environment.Playground;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jdk.nashorn.api.tree.Tree;
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

    Pane stack;

    /**
     * Creates a new menu (detailedView) from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected.
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        StringBuilder idTextString = new StringBuilder();

        TreeItem<Object> treeTier = new TreeItem<>();
        TreeItem<Object> emotionLinks = new TreeItem<>(new Text("Emotions")),
                financeLinks = new TreeItem<>(new Text("Finances")),
                socialLinks = new TreeItem<>(new Text("Social")),
                physicalLinks = new TreeItem<>(new Text("Physical")),
                mentalLinks = new TreeItem<>(new Text("Mental")),
                academicLinks = new TreeItem<>(new Text("Academics"));

        for (Cow cowAlreadySelected : cowsPreviouslySelected) {
            idTextString.append(cowAlreadySelected.getId()).append(" | ");

            Hyperlink hyperlink;

            //Emotion links
            hyperlink = new Hyperlink("ANGER: " + cowAlreadySelected.getAnger());
            hyperlink.setOnAction(event -> System.out.println("Anger breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("ANTICIPATION: " + cowAlreadySelected.getAnticipation());
            hyperlink.setOnAction(event -> System.out.println("Anticipation breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("DISGUST: " + cowAlreadySelected.getDisgust());
            hyperlink.setOnAction(event -> System.out.println("Disgust breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("FEAR: " + cowAlreadySelected.getFear());
            hyperlink.setOnAction(event -> System.out.println("Fear breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("HAPPINESS: " + cowAlreadySelected.getHappiness());
            hyperlink.setOnAction(event -> System.out.println("Happiness breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("SURPRISE: " + cowAlreadySelected.getSurprise());
            hyperlink.setOnAction(event -> System.out.println("Surprise breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("TRUST: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Trust breakdown"));
            emotionLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            //Finance Links
            hyperlink = new Hyperlink("INCOME: " + cowAlreadySelected.getIncome());
            hyperlink.setOnAction(event -> System.out.println("Income breakdown"));
            financeLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("BILLS: " + cowAlreadySelected.getBills());
            hyperlink.setOnAction(event -> System.out.println("Bills breakdown"));
            financeLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("FOOD: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Food breakdown"));
            financeLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("TAXES: " + cowAlreadySelected.getTaxes());
            hyperlink.setOnAction(event -> System.out.println("Taxes breakdown"));
            financeLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("SAVINGS: " + cowAlreadySelected.getSavings());
            hyperlink.setOnAction(event -> System.out.println("Savings breakdown"));
            financeLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("DEBT: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Debt breakdown"));
            financeLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            //Social links
            hyperlink = new Hyperlink("BOREDOM: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Boredom breakdown"));
            socialLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("COMPANIONSHIP: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Companionship breakdown"));
            socialLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            //Physical
            hyperlink = new Hyperlink("HUNGER: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Hunger breakdown"));
            physicalLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("AGE: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Age breakdown"));
            physicalLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("HEALTH: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Health breakdown"));
            physicalLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            //Mental links
            hyperlink = new Hyperlink("FAITH: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Faith breakdown"));
            mentalLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            hyperlink = new Hyperlink("HEALTH: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Health breakdown"));
            mentalLinks.getChildren().addAll(new TreeItem<>(hyperlink));

            //Academic links
            hyperlink = new Hyperlink("HERE: " + cowAlreadySelected.getTrust());
            hyperlink.setOnAction(event -> System.out.println("Here breakdown"));
            academicLinks.getChildren().addAll(new TreeItem<>(hyperlink));
        }

        Button exitButton = new Button("EXIT");
        Text idText = new Text(160, 30, idTextString.toString());
        Rectangle background = new Rectangle(150, 0, 650, 600);

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

        background.setFill(Color.BLACK);
        idText.setFill(Color.RED);

        exitButton.setLayoutX(160);
        exitButton.setLayoutY(560);
        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
        });

        treeTier.getChildren().addAll(emotionLinks, financeLinks, socialLinks, physicalLinks, mentalLinks, academicLinks);
        TreeView tree = new TreeView<>(treeTier);
        tree.setShowRoot(false);
        tree.relocate(150, 40);
        Playground.playground.getChildren().addAll(background, idText, exitButton, tree);
    }
    /**
     * Temp Creates a menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    MenuCreation(@NotNull Cow cow) {
        clickedCow = cow;

        stack = new Pane();
        Rectangle background = new Rectangle(0,0, 110, 150);
        Text idText = new Text(5,15, cow.getId());
        overallEmotion = new Text(5, 30, "Emotion: ");

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        overallEmotion.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

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
    public void updateCowMenu() {
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