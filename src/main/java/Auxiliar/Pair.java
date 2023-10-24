package Auxiliar;

public class Pair<T,T1> {

    private T key;
    private T1 value;

    public Pair(T key, T1 value) {
        this.key = key;
        this.value = value;
    }

    public Pair() {
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public T1 getValue() {
        return value;
    }

    public void setValue(T1 value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return this.key.equals(((Pair<?, ?>)obj).key) && this.value.equals(((Pair<?, ?>)obj).value);
    }

    @Override
    public String toString() {
        return  "Token: " + value + ", Tipo: " + key;
    }
}
