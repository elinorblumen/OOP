/**
 * Class representing a library system.
 */


class Library {

    int maximalBookCapacity;
    int maximalBorrowedBooks;
    int maximalPatronCapacity;
    Book[] librariesBooks;
    Patron[] librariesPatrons;
    int[][] patronsBorrowedBooks;


    /**
     * Constructor for a library system object.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows a
     *                         single patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library
     *                          can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks,
            int maxPatronCapacity){

        maximalBookCapacity = maxBookCapacity;
        maximalBorrowedBooks = maxBorrowedBooks;
        maximalPatronCapacity = maxPatronCapacity;
        librariesBooks = new Book[maximalBookCapacity + 1];
        librariesPatrons = new Patron[maximalPatronCapacity];
        patronsBorrowedBooks = new int[maximalPatronCapacity][maximalBorrowedBooks];
    }

    /**
     * Adds the given book to this library, if there is place available,
     * and it isn't already in the library.
     * @param book The book to add to this library.
     * @return A non-negative id number for the book if there was a spot and
     * the book was successfully added, or if the book was already in the library;
     * a negative number otherwise.
     */
    int addBookToLibrary(Book book){

        int bookId = getBookId(book);

        if(bookId < 0){
            for(int i = 1 ; i < this.maximalBookCapacity + 1 ; i++){
                if(librariesBooks[i] == null){
                    librariesBooks[i] = book;
                    return i;
                }
            }
        }
        return bookId;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the
     * given patron id, if this book is available, the given patron isn't already
     * borrowing the maximal number of books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to borrow.
     * @param patronId The id number of the book to borrow.
     * @return True if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId){


        if(isBookIdValid(bookId) && isPatronIdValid(patronId)){
            if(isBookAvailable(bookId)){
                if(librariesPatrons[patronId].willEnjoyBook(librariesBooks[bookId])){
                    for(int i = 0 ; i < maximalBorrowedBooks ; i++){
                        if(patronsBorrowedBooks[patronId][i] == 0){
                            patronsBorrowedBooks[patronId][i] = bookId;
                            librariesBooks[bookId].setBorrowerId(patronId);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the non-negative id number of the given book if he
     * is owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return A non-negative id number of the given book if he is
     * owned by this library, -1 otherwise.
     */
    int getBookId(Book book){

        for(int i = 1 ; i < this.maximalBookCapacity+1 ; i++){
            if(librariesBooks[i] == book){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the non-negative id number of the given patron if he is
     * registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     */
    int getPatronId(Patron patron){

        for(int i = 0 ; i < this.maximalPatronCapacity ; i++){
            if(librariesPatrons[i] == patron){
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId The id number of the book to check.
     * @return True if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId){

        if (isBookIdValid(bookId)){
            return (librariesBooks[bookId].getCurrentBorrowerId() < 0);
        }
        return false;
    }

    /**
     * Returns true if the given number is an id of some book in the library,
     * false otherwise.
     * @param bookId The id to check.
     * @return True if the given number is an id of some book in the library,
     * false otherwise.
     */
    boolean isBookIdValid(int bookId){

        if((bookId <= maximalBookCapacity + 1) && (bookId > 0)){
            return librariesBooks[bookId] != null;
        }
        return false;
    }

    /**
     * Returns true if the given number is an id of a patron in the library,
     * false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library,
     * false otherwise.
     */
    boolean isPatronIdValid(int patronId){

        if((patronId < maximalPatronCapacity) && (patronId >= 0)){
            return librariesPatrons[patronId] != null;
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the
     * patron was successfully registered or if the patron was already registered.
     * a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron){

        int patronId = getPatronId(patron);

        if (patronId >= 0){
            return patronId;
        }
        for (int i = 0 ; i < maximalPatronCapacity ; i++){
            if(librariesPatrons[i] == null){
                librariesPatrons[i] = patron;
                return i;
            }
        }
        return -1;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId){

        if(isBookIdValid(bookId)){

            int patronId = librariesBooks[bookId].getCurrentBorrowerId();

            if (isPatronIdValid(patronId)){
                for (int j = 0 ; j < maximalBorrowedBooks ; j++){
                    if(patronsBorrowedBooks[patronId][j] == bookId){
                        patronsBorrowedBooks[patronId][j] = 0;
                        librariesBooks[bookId].returnBook();
                        return;
                    }
                }
            }
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most,
     * out of all available books he will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given ID will enjoy the most.
     * Null if no book is available.
     */
    Book suggestBookToPatron(int patronId){

        Patron patron = librariesPatrons[patronId];
        Book mostEnjoyedBook = null;
        int score = 0;

        if(isPatronIdValid(patronId)){
            for(int i = 1 ; i < maximalBookCapacity + 1 ; i++){
                if(patron.willEnjoyBook(librariesBooks[i])){
                    int tempScore = patron.getBookScore(librariesBooks[i]);
                    if (tempScore >= score){
                        score = tempScore;
                        mostEnjoyedBook = librariesBooks[i];
                    }
                }
            }
        }
        return mostEnjoyedBook;
    }
}

