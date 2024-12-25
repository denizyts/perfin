class amountNegativeException extends Exception {
    public amountNegativeException(String message) {
        super(message);
    }
}
public class emptyStringException extends Exception {
    public emptyStringException(String message) {
        super(message);
    }
    
}
class invalidQuantityException extends Exception {
    
    public invalidQuantityException(String message){
        super(message);
    }
}

class invalidSideException extends Exception{
    
    public invalidSideException(String message){
        super(message);
    }
}
class InvalidSymbolException extends Exception {
    public InvalidSymbolException(String message) {
        super(message);
    }   
}
class priceNegativeException extends Exception {
    public priceNegativeException(String message) {
        super(message);
    }
}
class symbolNotSellable extends Exception {
    public symbolNotSellable(String message){
        super(message);
    }
}


