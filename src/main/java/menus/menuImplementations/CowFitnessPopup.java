package menus.menuImplementations;

import cowParts.Cow;
import javafx.scene.CacheHint;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import menus.GenericMenu;
import metaEnvironment.Regioning.regionContainers.Playground;
import metaEnvironment.Regioning.regionContainers.PlaygroundHandler;

public class CowFitnessPopup extends GenericMenu {

    public CowFitnessPopup(Cow cow) {
        createMenu(cow);
    }

    @Override
    public void createMenu(Object objectTie) {
        this.objectTie = objectTie;
        this.background = new Rectangle(0,0, 110, 150);
        this.stack = new Pane();

        Text idText = new Text(5,15, ((Cow)objectTie).getId());
        Text overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic;
        VBox cognitiveAggregates = new VBox();

        cognitiveAggregates.setSpacing(8);
        cognitiveAggregates.setLayoutX(5);
        cognitiveAggregates.setLayoutY(20);

        overallEmotion = new Text(5, 30, "Emotion: " + ((Cow)objectTie).self.getEmotionAggregate());
        overallFinance = new Text(5, 30, "Finance: " + ((Cow)objectTie).self.getFinanceAggregate());
        overallSocial = new Text(5, 30, "Social: " + ((Cow)objectTie).self.getSocialAggregate());
        overallPhysical = new Text(5, 30, "Physical: " + ((Cow)objectTie).self.getPhysicalAggregate());
        overallMental = new Text(5, 30, "Mental: " + ((Cow)objectTie).self.getMentalAggregate());
        overallAcademic = new Text(5, 30, "Academic: " + ((Cow)objectTie).self.getAcademicAggregate());

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

        PlaygroundHandler.playground.getChildren().add(stack);
        stack.toFront();

        updateMenu();
    }

    @Override
    public void updateMenu() {
        stack.relocate((((Cow)objectTie).getTranslateX() + 55), (((Cow)objectTie).getTranslateY() + 40));
    }

    @Override
    protected void closeMenu() {
        stack.getChildren().clear();
        PlaygroundHandler.playground.getChildren().remove(stack);
    }

    @Override
    protected void openMenu() {

    }
}
