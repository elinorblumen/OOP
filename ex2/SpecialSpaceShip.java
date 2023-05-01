import oop.ex2.SpaceShipPhysics;

/**
 *  Class representing a special spaceship object.
 */
public class SpecialSpaceShip extends EnemySpaceShip {

    private static final double MAX_DISTANCE_TO_FIRE = 0.20;
    private static final double MAX_ANGLE_TO_FIRE = 0.20;

    /**
     * Does all the action of a special spaceship in one round.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {


        SpaceShipPhysics closestShipPhysics = game.getClosestShipTo(this).getPhysics(); // Gets the
        // the physics object of the closest ship to this ship.

        double distanceFromClosestShip = this.getPhysics().distanceFrom(closestShipPhysics);

        double angleFromClosestShip = this.getPhysics().angleTo(closestShipPhysics);

        int directionToClosestShip = this.turnHelper(angleFromClosestShip);

        this.roundCounterSetter();


        this.getPhysics().move(true, directionToClosestShip);

        if (distanceFromClosestShip<= SpecialSpaceShip.MAX_DISTANCE_TO_FIRE
        && Math.abs(angleFromClosestShip)<SpecialSpaceShip.MAX_ANGLE_TO_FIRE){
            if(this.roundCounterHelper()){
                this.fire(game);
            }
        }
        this.currentEnergySetter(210); // Energy reset
    }
}
