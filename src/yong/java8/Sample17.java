package yong.java8;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yongju on 2016. 10. 31..
 */
public class Sample17 {
    public static void main(String[] args) {
        Something something = new Something(123);

        System.out.println(
                something.equals(new Something(123))
        );

        final Map<Something, String> m = new HashMap<>();
        m.put(something, "a");
        m.put(new Something(2), "b");

        System.out.println(m.get(something));

        something.setValue(1);
        System.out.println(m.get(something));

        System.out.println(m.get(new Something(123)));

        System.out.println("===================================");
        Something2 something2 = new Something2(123);
        System.out.println(
                something.equals(new Something2(123))
        );
        System.out.println(
                "something2.hashcode : " + something2.hashCode()
        );
        System.out.println(
                "new Something2(123).hashcode : " + new Something2(123).hashCode()
        );

    }
}

class Something<E> {
    private E value;

    public Something(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Something<?> something = (Something<?>) o;
        return value.equals(something.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

class Something2<E> {
    private E value;

    public Something2(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Something2<?> something = (Something2<?>) o;
        return value.equals(something.value);
    }

//    @Override
//    public int hashCode() {
//        return value.hashCode();
//    }
}

class GoodSomething<E> {
    private E value;

    public GoodSomething(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

//    public void setValue(E value) {
//        this.value = value;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodSomething<?> something = (GoodSomething<?>) o;
        return value.equals(something.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}