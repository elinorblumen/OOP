
/**
 * A class representing a Spaceship factory.
 */
public class SpaceShipFactory {


    /**
     * Static method that receives user input and return an array of spaceship
     * objects in accordance to input.
     * @param args Letters representing the wanted spaceships.
     * @return Array of spaceships in accordance to input.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {


        int size = args.length;
        SpaceShip[] shipArray = new SpaceShip[size];
        int shipArrayIndex = 0;

        for (String letter : args){

            SpaceShip currentShip = SpaceShipFactory.shipMakerHelper(letter);

            shipArray[shipArrayIndex] = currentShip;

            shipArrayIndex += 1;
        }
        return shipArray;
    }


    /**
     * Helper method that receives a letter and returns the matching
     * spaceship.
     * @param letter One of the letters representing a space ship.
     * @return One of the possible spaceships.
     */
    private static SpaceShip shipMakerHelper(String letter){


        if (letter.equals("h")){
            return new HumanSpaceShip();
        }
        if (letter.equals("r")){
            return new RunnerSpaceShip();
        }
        if (letter.equals("b")){
            return new BasherSpaceShip();
        }
        if(letter.equals("a")){
            return new AggressiveSpaceShip();
        }
        if(letter.equals("d")){
            return new DrunkSpaceShip();
        }
        return new SpecialSpaceShip();
    }
}
