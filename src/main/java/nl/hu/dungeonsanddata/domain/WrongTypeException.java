package nl.hu.dungeonsanddata.domain;

public class WrongTypeException extends Exception {
    public WrongTypeException(String message){
        super(message);
    }
}
