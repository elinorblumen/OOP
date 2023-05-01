import oop.ex2.GameGUI;
import java.awt.*;

/**
 * Class representing an enemy (computer controlled) spaceship.
 */
public abstract class EnemySpaceShip extends SpaceShip {


    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {

        return this.getImageHelper(GameGUI.ENEMY_SPACESHIP_IMAGE,
                                    GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD);
    }


    /**
     * Helper method that determines whether the ship should turn right or left
     * or not turn at all based on it's angle from the closest ship.
     * @param angle The angle from the closest ship.
     * @return -1 if ship should turn right, 1 if left, 0 otherwise.
     */
    protected int turnHelper(double angle){

        if((angle < 0) && (angle > -Math.PI)){
            return -1;
        }
        if ((angle > 0) && (angle < Math.PI)){
            return 1;
        }
        return 0;
    }
}
