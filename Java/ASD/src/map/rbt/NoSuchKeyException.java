package map.rbt;

public class NoSuchKeyException extends Exception {
    private Object key;

    public NoSuchKeyException(Object key, String message) {
        super(message);
        this.key = key;
    }

}
