/**
 * Class representing a patron.
 */


class Patron {

    String firstName;
    String lastName;
    int patronComicTendency;
    int patronDramaticTendency;
    int patronEducationalTendency;
    int enjoymentThreshold;


    /**
     * Constructor for patron objects.
     * @param patronFirstName Patron's first name.
     * @param patronLastName Patron's last name.
     * @param comicTendency Patron's comic tendency.
     * @param dramaticTendency Patron's dramatic tendency.
     * @param educationalTendency Patron's educational tendency.
     * @param patronEnjoymentThreshold The minimal literary value a book must
     *                                have for this patron to enjoy it.
     */
    Patron(String patronFirstName, String patronLastName,
           int comicTendency, int dramaticTendency, int educationalTendency,
           int patronEnjoymentThreshold){

        firstName = patronFirstName;
        lastName = patronLastName;
        patronComicTendency = comicTendency;
        patronDramaticTendency = dramaticTendency;
        patronEducationalTendency = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book The book to asses.
     * @return The literary value this patron assigns to the given book
     */
    int getBookScore(Book book){

        int patronComicEvaluation = this.patronComicTendency * book.bookComicValueField;
        int patronDramaticEvaluation = this.patronDramaticTendency * book.bookDramaticValueField;
        int patronEducationalEvaluation = this.patronEducationalTendency * book.bookEducationalValueField;
        return patronComicEvaluation + patronDramaticEvaluation + patronEducationalEvaluation;
    }

    /**
     * Returns a string representation of the patron, which is a sequence of its
     * first and last name, separated by a single white space.
     * @return The String representation of this patron.
     */
    String stringRepresentation(){

        return (this.firstName + " " + this.lastName);
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     * @param book The book to asses.
     * @return True if this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book){

        return (getBookScore(book) >= this.enjoymentThreshold);
    }
}
