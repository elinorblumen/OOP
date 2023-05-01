public class RunnerSpaceShip extends EnemySpaceShip {




    @Override
    public void doAction(SpaceWars game) {


        SpaceShip closestShip = game.getClosestShipTo(this);
        double distanceFromClosestShip = this.getPhysics().distanceFrom(closestShip.getPhysics());
        double angleFromClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());


        if ((distanceFromClosestShip < 0.25) && (Math.abs(angleFromClosestShip) < 0.23)){
            this.teleport();
        }
        this.getPhysics().move(true, turnHelper(-angleFromClosestShip));

        this.currentEnergyLevelRoundGain(1); // Raises current energy level by 1.
    }
}
