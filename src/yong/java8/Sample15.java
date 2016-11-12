package yong.java8;

/**
 * Created by yongju on 2016. 10. 20..
 */
public class Sample15 {
    private int number = 999;

    public static void main(String[] args) {
//        final int number = 100;
        int number = 100; //effectively final

//        number = 1; // error 가 발생함. number는 final 처럼 쓰임.
        System.out.print("[Anonymous Class] : ");
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
//                number = 1; // error 가 발생함. number는 final 처럼 쓰임.
                System.out.println(number);
            }
        };
        runnable1.run();

//        number = 1; // error 가 발생함 람다에서도 에러가 발생함.
        System.out.print("[Lambda] : ");
        Runnable runnable2 = () -> System.out.println(number);
        runnable2.run();
//        number = 1; // error 가 발생함 람다에서도 에러가 발생함.

        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(number);
            }
        });

        testClosure("Lambda", () -> System.out.println(number));


        final Sample15 sample15 = new Sample15();
        sample15.test1();
        sample15.test2();
        sample15.test3();
        sample15.test4();
        sample15.test5();
    }

    public static <T> String toString(T value) {
        return "The Value is " + String.valueOf(value);
    }

    private static void testClosure(final String name, final Runnable runnable) {
//        System.out.println("=====[testClosure]======");
        System.out.print(name + " : ");
        runnable.run();
    }

    @Override
    public String toString() {
        return "Sample15{" +
                "number=" + number +
                '}';
    }

    private void test1() {
        System.out.println("=====[test1]======");
        int number = 111;
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(Sample15.this.number);
//                System.out.println(this.number); // this.number 로 접근할 수 없음.
            }
        });

        testClosure("Lambda", () -> System.out.println(this.number));
    }

    private void test2() {
        System.out.println("=====[test2]======");
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("[this] : " + this);
            }
        });
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println("[Sample15.this] : " + Sample15.this);
            }
        });

        // lambda expression 의 scope 은 Sample15.
        testClosure("Lambda", () -> System.out.println("[this] : " + this));
    }

    private void test3() {
        System.out.println("=====[test3]======");
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // error 가 발생함. Runnanble 의 toString 을 호출하려고해서 에러가 남
                // shadowing.
//                System.out.println("[this] : "+this.toString(this));
                System.out.println("[Sample15.toString(this)] : " + Sample15.toString(this));
            }
        });

        testClosure("Lambda", () -> System.out.println("[toString(this)] : " + toString(this)));
    }

    private void test4() {
        System.out.println("=====[test4]======");
        int number = 111;
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                int number = 333; // no compile time error
                // shadowing이 발생해서 111-number에 접근할 수 없음.
                System.out.println(number);
            }
        });

        // lambda 의 enclosing class -> Sample15
        testClosure("Lambda", () -> {
            // 이미 number가 정의되어 있다는 에러가 발생함.
//            int number = 333; // compile time error
            System.out.println(number);
        });
    }

    private void test5() {
        System.out.println("=====[test5]======");
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // compile error
//                System.out.println("[Sample15.toString(this)] : "+toString(1));
                System.out.println("[Sample15.toString(this)] : " + (new Sample15()).toString(1));
            }
        });

        testClosure("Lambda", () -> System.out.println("[toString(1)] : " + toString(1)));
    }

    private String toString(final int number) {
        return "# " + number;
    }
}