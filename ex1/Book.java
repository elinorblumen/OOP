import java.util.Arrays;

/**
 * Class representing a book object.
 */

class Book {

    String bookTitleField;
    String bookAuthorField;
    int bookYearOfPublicationField;
    int bookComicValueField;
    int bookDramaticValueField;
    int bookEducationalValueField;
    int currentBorrowerId = -1;
    int NO_BORROWER = -1;

    /**
     * Constructor for book objects.
     * @param bookTitle The title of the book.
     * @param bookAuthor The name of the author of the book.
     * @param bookYearOfPublication The year the book was published in.
     * @param bookComicValue The comic value of the book.
     * @param bookDramaticValue The dramatic value of the book.
     * @param bookEducationalValue The educational value of the book.
     */
    Book(String bookTitle,
         String bookAuthor,
         int bookYearOfPublication,
         int bookComicValue,
         int bookDramaticValue,
         int bookEducationalValue){

        bookTitleField = bookTitle;
        bookAuthorField = bookAuthor;
        bookYearOfPublicationField = bookYearOfPublication;
        bookComicValueField = bookComicValue;
        bookDramaticValueField = bookDramaticValue;
        bookEducationalValueField = bookEducationalValue;
    }

    /**
     * Returns the Id of the current borrower of the book.
     * @return The Id of the book's current borrower.
     */
    int getCurrentBorrowerId(){

        return this.currentBorrowerId;
    }

    /**
     * Returns the literary value of the book.
     * @return The literary value of this book, which is defined as the sum of its comic value,
     * its dramatic value and its educational value.
     */
    int getLiteraryValue(){

        return this.bookComicValueField +
                this.bookDramaticValueField +
                this.bookEducationalValueField;
    }

    /**
     * Marks this book as returned/
     */
    void returnBook(){

        setBorrowerId(NO_BORROWER);
    }

    /**
     * Sets the given id as the id of the current borrower of this book,
     * -1 if no patron is currently borrowing it.
     * @param borrowerId Given id.
     */
    void setBorrowerId(int borrowerId){

        this.currentBorrowerId = borrowerId;
    }

    /**
     * Returns a string representation of the book details.
     * @return A string representation of the book, which is a sequence of the title,
     * author, year of publication and the total literary value of the book, separated
     * by commas, inclosed in square brackets.
     */
    String stringRepresentation(){

        int literaryValue = this.getLiteraryValue();
        String[] stringRepresentationArray = new String[4];

        stringRepresentationArray[0] = this.bookTitleField;
        stringRepresentationArray[1] = this.bookAuthorField;
        stringRepresentationArray[2] = String.valueOf(this.bookYearOfPublicationField);
        stringRepresentationArray[3] = String.valueOf(literaryValue);
        return Arrays.toString(stringRepresentationArray);
    }
}