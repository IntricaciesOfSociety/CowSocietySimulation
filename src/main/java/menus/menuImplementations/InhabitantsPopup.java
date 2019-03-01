package menus.menuImplementations;


import infrastructure.buildingTypes.GenericBuilding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import menus.GenericMenu;
import metaEnvironment.Playground;

public class InhabitantsPopup extends GenericMenu {

    Text numberOfInhabitants;

    @Override
    protected void createMenu(Object objectTie) {
        this.isOpened = true;
        this.stack = new Pane();
        this.background = new Rectangle(100, 50, Color.BLACK);
        this.objectTie = objectTie;
        this.stack.relocate(
                ((GenericBuilding) objectTie).getLayoutX() + 175, ((GenericBuilding) objectTie).getLayoutY() + 175
        );

        numberOfInhabitants = new Text(Integer.toString(((GenericBuilding) objectTie).getCurrentInhabitants().size()));
        numberOfInhabitants.setFill(Color.RED);
        numberOfInhabitants.relocate(10, 30);
        numberOfInhabitants.setFont(Font.font("Verdana", FontWeight.BOLD, 48));

        stack.getChildren().addAll(background, numberOfInhabitants);
        Playground.playground.getChildren().add(stack);
    }

    @Override
    protected void updateMenu() {
        numberOfInhabitants.setText(Integer.toString(((GenericBuilding) objectTie).getCurrentInhabitants().size()));
    }

    @Override
    protected void closeMenu() {

    }

    @Override
    protected void openMenu() {

    }

    @Override
    protected boolean getIsOpened() {
        return this.isOpened;
    }
}
