public class Pair<T,T1> {
    T a;
    T1 b;

    public Pair(T a,     T1 b) {
        this.a = a;
        this.b = b;
    }

    public Pair() {
    }

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T1 getB() {
        return b;
    }

    public void setB(T1 b) {
        this.b = b;
    }


    @Override
    public boolean equals(Object obj) {
        return this.a.equals(((Pair<?, ?>)obj).a) && this.b.equals(((Pair<?, ?>)obj).b);
    }

    @Override
    public String toString() {
        return  "Token: " + b + ", Tipo: " + a;
    }
}
