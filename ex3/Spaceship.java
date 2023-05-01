import oop.ex3.spaceship.*;

/**
 * Class representing a spaceship object.
 */
public class Spaceship {

    // Data members //

    private String name;
    private int[] crewIDs;
    private Locker[] lockersArray;
    private int[] idPerLocker;
    private Item[][] constraints;
    private LongTermStorage lts;
    private final int numOfLockers;
    private int lockerCounter = 0;


    /**
     * Constructor for spaceship object.
     * @param name Spaceship's name.
     * @param crewIDs An array of craw members Ids.
     * @param numOfLockers Number of lockers in this ship.
     * @param constraints An array of pairs of items which should not
     *                    reside in the same locker.
     */
    public Spaceship(String name, int[] crewIDs, int numOfLockers,
              Item[][] constraints){

        this.name = name;
        this.crewIDs = crewIDs;
        this.lockersArray = new Locker[numOfLockers];
        this.idPerLocker = new int[numOfLockers];
        this.constraints = constraints;
        this.lts = new LongTermStorage();
        this.numOfLockers = numOfLockers;
    }


    /**
     * Getter method that returns the long term storage object associated with this ship.
     * @return Long term storage object associated with this ship.
     */
    public LongTermStorage getLongTermStorage(){

        return this.lts;
    }


    /**
     * Method that creates a new locker object with the given capacity
     * and assigns it to the crew member with the given id.
     * @param crewID The id of the crew member to which the locker should
     *               be assigned.
     * @param capacity A positive integer representing the capacity of
     *                 the new locker.
     * @return If the id isn't valid, the locker won't be created and the method
     * will return -1.
     * If the given capacity does not meet the locker class requirements,
     * the locker won't be created and the method will return -2.
     * If the space ship already contains the allowed number of lockers,
     * the locker won't be created and the method will return -3.
     * If the locker was created successfully the method will return 0.
     */
    public int createLocker(int crewID, int capacity){

        if(!isIdValid(crewID)){
            return -1;
        }
        if(capacity < 0 ){
            return -2;
        }
        if(!(lockersArray[numOfLockers-1] == null)){
            return -3;
        }
        lockersArray[lockerCounter] = new Locker(lts,
                capacity, this.constraints);
        idPerLocker[lockerCounter] = crewID;
        lockerCounter ++;
        return 0;
    }


    /**
     * Method that returns an array containing the ids of the crew members
     * of this ship.
     * @return An array containing the ids of the crew members
     * of this ship.
     */
    public int[] getCrewIDs(){

        return crewIDs;
    }


    /**
     * Method that returns an array of lockers whose length is determined
     * by the constructor parameter.
     * @return An array of lockers whose length is determined
     * by the constructor parameter.
     */
    public Locker[] getLockers(){

        return lockersArray;
    }


    /**
     * Helper method that helps to determine whether a given id is valid.
     * @param givenID The id to be checked.
     * @return True if id is valid, false otherwise.
     */
    private boolean isIdValid(int givenID){

        for(int id : crewIDs){
            if(id == givenID){
                return true;
            }
        }
        return false;
    }
}
