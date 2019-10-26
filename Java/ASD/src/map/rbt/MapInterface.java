package map.rbt;

public interface MapInterface <K extends Comparable<K>, V> {
    public void setValue (K key, V value) throws NoSuchKeyException;
    public V getValue (K key) throws NoSuchKeyException;
}