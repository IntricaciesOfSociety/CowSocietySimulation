package menus.menuImplementations;


import infrastructure.buildings.buildingTypes.GenericBuilding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import menus.GenericMenu;
import metaEnvironment.Playground;

public class InhabitantsPopup extends GenericMenu {

    private Text numberOfInhabitants;

    public InhabitantsPopup(GenericBuilding building) {
        createMenu(building);
    }

    @Override
    protected void createMenu(Object objectTie) {
        this.stack = new Pane();
        this.background = new Rectangle(100, 50, Color.BLACK);
        this.objectTie = objectTie;
        this.stack.relocate(
                ((GenericBuilding) objectTie).getLayoutX() + ((GenericBuilding) objectTie).getRegion().getLayoutX() + 175,
                ((GenericBuilding) objectTie).getLayoutY() + ((GenericBuilding) objectTie).getRegion().getLayoutY() + 175
        );

        numberOfInhabitants = new Text(Integer.toString(((GenericBuilding) objectTie).getCurrentInhabitants().size()));
        numberOfInhabitants.setFill(Color.RED);
        numberOfInhabitants.relocate(10, 30);
        numberOfInhabitants.setFont(Font.font("Verdana", FontWeight.BOLD, 48));

        stack.getChildren().addAll(background, numberOfInhabitants);
        Playground.playground.getChildren().add(stack);
    }

    @Override
    public void updateMenu() {
        numberOfInhabitants.setText(Integer.toString(((GenericBuilding) objectTie).getCurrentInhabitants().size()));
    }

    @Override
    protected void closeMenu() {
        stack.getChildren().clear();
        Playground.playground.getChildren().remove(stack);
    }

    @Override
    protected void openMenu() {

    }
}
