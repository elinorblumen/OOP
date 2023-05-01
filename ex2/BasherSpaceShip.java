



public class BasherSpaceShip extends EnemySpaceShip {




    @Override
    public void doAction(SpaceWars game) {

        this.shieldOffSetter(); // Sets off the shield in the beginning of each round.
        SpaceShip closestShip = game.getClosestShipTo(this);
        double distanceFromClosestShip = this.getPhysics().distanceFrom(closestShip.getPhysics());
        double angleFromClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());


        this.getPhysics().move(true, this.turnHelper(angleFromClosestShip));

        if(distanceFromClosestShip <= 0.19){
            this.shieldOn();
        }

        this.currentEnergyLevelRoundGain(1); // Raises current energy level by 1.
    }
}
