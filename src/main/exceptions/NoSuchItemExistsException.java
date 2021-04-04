package exceptions;

//represents an exception for item - where no item exists
public class NoSuchItemExistsException extends Exception {

    public NoSuchItemExistsException(String str) {
        super(str);
    }
}
