package exceptions;

//represents an exception for item - where no item exists
public class NoSuchItemExistsException extends Exception {

    //makes a new exception with a message of str
    public NoSuchItemExistsException(String str) {
        super(str);
    }
}
