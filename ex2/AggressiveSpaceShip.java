/**
 * Class representing an aggressive spaceship object.
 */
public class AggressiveSpaceShip extends EnemySpaceShip{


    /**
     * Does all the actions of the spaceship in one round.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {


        this.roundCounterSetter();
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleFromClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());


        this.getPhysics().move(true, this.turnHelper(angleFromClosestShip));

        if(Math.abs(angleFromClosestShip) < 0.21){
            if(this.roundCounterHelper()){
                this.fire(game);
            }
        }
        this.currentEnergyLevelRoundGain(1); // Raises current energy level by 1.
    }
}
