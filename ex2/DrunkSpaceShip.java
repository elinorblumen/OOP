/**
 * Class representing a drunk spaceship.
 */
public class DrunkSpaceShip extends EnemySpaceShip {


    private int roundByte = 0;


    /**
     * Does drunk spaceship actions in each round.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {

        this.roundCounterSetter();

        if(roundCounterHelper()){
            if (roundByte == 0){
                roundByte = 1;
                for (int i = 0 ; i < 5 ; i++){
                    this.getPhysics().move(true, 1);
                }
            }else{
                this.getPhysics().move(true, -1);
                roundByte = 0;
            }
        }
        else{
            this.getPhysics().move(false,0);
        }
    }
}
