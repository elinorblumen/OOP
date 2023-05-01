import oop.ex2.*;
import java.awt.*;

/**
 * Class representing a human controlled spaceship.
 */
public class HumanSpaceShip extends SpaceShip{

    /**
     * Does the actions of this ship for each round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        GameGUI gameGUI = game.getGUI();
        this.roundCounterSetter();
        this.shieldOffSetter(); // Sets off the shield in the beginning of each round.

        if (gameGUI.isTeleportPressed()){
            this.teleport();
        }
        this.getPhysics().move(this.accelHelper(gameGUI), this.turnHelper(gameGUI));

        if(gameGUI.isShieldsPressed()){
            this.shieldOn();
        }
        if(gameGUI.isShotPressed()){
            if(this.roundCounterHelper()){
                this.fire(game);
            }
        }
        this.currentEnergyLevelRoundGain(1); // Raises current energy level by 1.
    }


    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {

        return this.getImageHelper(GameGUI.SPACESHIP_IMAGE, GameGUI.SPACESHIP_IMAGE_SHIELD);
    }


    /**
     * Helper method to determine whether a ship should be accelerating.
     * @param gameGUI The GUI object of the current game.
     * @return True if up is pressed and spaceship should accelerate.
     */
    protected boolean accelHelper(GameGUI gameGUI){

        return gameGUI.isUpPressed();
    }


    /**
     * Helper method that helps to determine in which direction a space ship should move.
     * @param gameGUI The GUI object of the current game.
     * @return 1 if spaceship should turn left, -1 if spaceship should turn right,
     *  0 if it should'nt turn at all.
     */
    protected int turnHelper(GameGUI gameGUI){

        if (gameGUI.isRightPressed()){
            if (gameGUI.isLeftPressed()){
                return 0;
            }
            return -1;
        }
        if (gameGUI.isLeftPressed()){
            return 1;
        }
        return 0;
    }
}
