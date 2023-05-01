import java.awt.Image;
import oop.ex2.*;

/**
 * Abstract class representing a generic Spaceship.
 *  
 * @author elinor123
 */
public abstract class SpaceShip {

    //** Data members **//

    private SpaceShipPhysics shipPhysics;
    private int maxEnergyLevel = 210;
    private int currentEnergyLevel = 190;
    private int healthLevel = 22;
    private int roundNumber = 0;
    private boolean shieldOn = false;


    /**
     * Constructor for a Space-ship pbject.
     */
    public SpaceShip() {

        this.shipPhysics = new SpaceShipPhysics();
    }


    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);


    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {

        this.gotHit();
        if(this.shieldOn){
            this.currentEnergyLevel += 18;
            this.maxEnergyLevel += 18;
        }
    }


    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {

        this.newPhysics();
        this.maxEnergyLevel = 210;
        this.currentEnergyLevel = 190;
        this.healthLevel = 22;
        this.shieldOn = false;
        this.roundNumber = 0;
    }


    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {

        if(this.healthLevel == 0){
            return true;
        }
        return false;
    }


    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.shipPhysics;
    }


    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {

        if(!this.shieldOn){

            this.healthReduceHelper();

            if(this.maxEnergyLevel > 10){
                this.maxEnergyLevel -= 10;
            }
            if(this.energyCheckHelper(this.maxEnergyLevel)){
                this.currentEnergyLevel = this.maxEnergyLevel;
            }
        }
    }


    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public abstract Image getImage();


    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game){

        if (this.energyCheckHelper(19)){
            game.addShot(this.shipPhysics);
            this.currentEnergyLevel -= 19;
        }
    }


    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn(){

        if(this.energyCheckHelper(3)){
            shieldOn = true;
            this.currentEnergyLevel -= 3;
        }
    }


    /**
     * Attempts to teleport.
     */
    public void teleport() {

        if (this.energyCheckHelper(140)) {
            this.newPhysics();
            this.currentEnergyLevel -= 140;
        }
    }


    /**
     * Helper method for getImage method.
     * @param shipImage The image of the given ship with shield off.
     * @param shipImageWithShield The image of the given ship with shield on.
     * @return The image of the Spaceship.
     */
    protected Image getImageHelper(Image shipImage, Image shipImageWithShield){

        if (this.shieldOn){
                return shipImageWithShield;
            }
            return shipImage;
    }


    /**
     * Helper method that determines whether the spaceship
     * has enough energy to execute a given action.
     *
     * @param energyNeeded The amount of energy needed for execution.
     * @return true if space ship has enough enegy, false otherwise.
     */
    private boolean energyCheckHelper(int energyNeeded) {

        return (this.currentEnergyLevel >= energyNeeded);
    }


    /**
     * Helper method that resets the spacship physics.
     */
    private void newPhysics(){
        this.shipPhysics = new SpaceShipPhysics();
    }


    /**
     * Helper method that reduces the ship's health level by one.
     */
    private void healthReduceHelper(){

        this.healthLevel -=1;
    }


    /**
     * Helper method that helps determine whether a shot can be made based on the round counter.
     * It is called only when a shot is attempted.
     * @return True if a shot can be made, false otherwise.
     */
    protected boolean roundCounterHelper(){

        if(this.roundNumber == 0) {
            this.roundNumber += 1;
            return true;
        }
        return false;
    }


    /**
     * Helper method to be called in each round, sets the round counter.
     */
    protected void roundCounterSetter() {

        if ((this.roundNumber == 7) | (this.roundNumber == 0)) {
            this.roundNumber = 0;
            return;
        }
        this.roundNumber += 1;
    }


    /**
     * Setter method to set the spaceship shield to be off.
     */
    protected void shieldOffSetter(){

        this.shieldOn = false;
    }


    /**
     * Setter method to increase the current energy level.
     * @param positiveInt A positive integer that increases the energy level.
     */
    protected void currentEnergyLevelRoundGain(int positiveInt){

        this.currentEnergyLevel += positiveInt;
    }

    /**
     * Setter method to set ship's Current energy.
     * @param num Integer to replace current energy level.
     */
    protected void currentEnergySetter(int num){

        this.currentEnergyLevel = num;
    }
}
    

