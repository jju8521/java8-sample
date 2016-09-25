package yong.java8.sample;

/**
 * Created by yongju on 16. 9. 25.
 */
public class Sample6 {
    public static void main(String[] args) {
        print(1, 2, 3, new Function3<Integer, Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2, Integer integer3) {
                return String.valueOf(integer+integer2+integer3);
            }
        });

        print(1, 2, 3, (i1, i2, i3) -> String.valueOf(i1+i2+i3));

        print("area is ", 12, 20,
                (message, length, width) -> message + String.valueOf(length * width));
        print(1L, "yong", "test@email.com",
                (id, name, email) -> "User info: ID: "+id+", name:"+name+", email:"+email);


    }

    private static <T1, T2, T3> void print(T1 t1, T2 t2, T3 t3,
                                    Function3<T1, T2, T3, String> function) {
        System.out.println(function.apply(t1, t2, t3));
    }

    @FunctionalInterface
    interface Function3<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);

        // error
//        void print(int i);
    }
}
