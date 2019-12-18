package map.rbt;

public class NoKeyInTreeException extends RuntimeException {

    private Object key;

    public NoKeyInTreeException(Object key, String message) {
        super(message);
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

}
