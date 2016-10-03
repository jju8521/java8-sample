package yong.java8;

/**
 * Created by yongju on 16. 10. 2.
 */
public class Sample08 {
    public static void main(String[] args) {
        final CalculatorService1 calculatorService1 = new CalculatorService1();
        System.out.println("[1+1] : " + calculatorService1.calculate(1, 1));

        final CalculatorService2 calculatorService2 = new CalculatorService2();
        System.out.println("[1 + 1] : " + calculatorService2.calculate('+', 1, 1));
        System.out.println("[1 - 1] : " + calculatorService2.calculate('-', 1, 1));
        System.out.println("[2 * 2] : " + calculatorService2.calculate('*', 2, 2));
        System.out.println("[8 / 4] : " + calculatorService2.calculate('/', 8, 4));

        final Addition addition = new Addition();
        final Substraction substraction = new Substraction();
        final Multiplication multiplication = new Multiplication();
        final Division division = new Division();

        final CalculatorService3 addtionService = new CalculatorService3(addition);
        final CalculatorService3 substractionService = new CalculatorService3(substraction);
        final CalculatorService3 multiplicationService = new CalculatorService3(multiplication);
        final CalculatorService3 divisionService = new CalculatorService3(division);

        System.out.println("[1 + 1] : " + addtionService.calculate(1, 1));
        System.out.println("[1 - 1] : " + substractionService.calculate(1, 1));
        System.out.println("[2 * 2] : " + multiplicationService.calculate(2, 2));
        System.out.println("[8 / 4] : " + divisionService.calculate(8, 4));

        final CalculatorService4 addtionService2 = new CalculatorService4(addition, substraction);
        final CalculatorService4 substractionService2 = new CalculatorService4(substraction, multiplication);
        final CalculatorService4 multiplicationService2 = new CalculatorService4(multiplication, division);
        final CalculatorService4 divisionService2 = new CalculatorService4(division, addition);

        System.out.println("[11 + 1] : " + addtionService2.calculate(11, 1));
        System.out.println("[11 - 1] : " + substractionService2.calculate(11, 1));
        System.out.println("[20 * 2] : " + multiplicationService2.calculate(20, 2));
        System.out.println("[80 / 4] : " + divisionService2.calculate(80, 4));

        final FpCalculatorService fpCalculatorService = new FpCalculatorService();
        System.out.println("[11 + 1] : " + fpCalculatorService.calculate(addition, 11, 1));
        System.out.println("[11 - 1] : " + fpCalculatorService.calculate(substraction, 11, 1));
        System.out.println("[20 * 2] : " + fpCalculatorService.calculate(multiplication, 20, 2));
        System.out.println("[80 / 4] : " + fpCalculatorService.calculate(division, 80, 4));


        /*
         * First Class Citizen
         * 1. Element elm = new Element();
         *    getNumber(elm);
         * 2. Element result = getResult();
         * 3. List<Element> list = Arrays.asList(elm, result);
         *    Element e = elm;
         * ex) Object
         *      public String getName() {}
         * ex1) findByName(getName);   (X)
         * ex2) public String doSomething() { return getName; } (X)
         * ex3) List<?> list = Arrays.asList(getName) (X)
         * ex4) SomeMethod m = getName (X)
         *
         * 다른 언어의 function에 해당하는 Java의 method는
         * First Class Citizen 이 아닌게 됨.
         */

        System.out.println("[11 + 1] : " + fpCalculatorService.calculate((i1, i2) -> i1 + i2, 11, 1));
        System.out.println("[11 - 1] : " + fpCalculatorService.calculate((i1, i2) -> i1 - i2, 11, 1));
        System.out.println("[20 * 2] : " + fpCalculatorService.calculate((i1, i2) -> i1 * i2, 20, 2));
        System.out.println("[80 / 4] : " + fpCalculatorService.calculate((i1, i2) -> i1 / i2, 80, 4));

        final Calculation customCalculation = (i1, i2) -> ((i1 + i2) * 2) / i2;
        System.out.println("((80 + 4) * 2) / 4 : " + fpCalculatorService.calculate(customCalculation, 80, 4));
    }
}

class CalculatorService1 {
    public int calculate(int num1, int num2) {
        return num1 + num2;
    }
}

class CalculatorService2 {
    public int calculate(char calculation, int num1, int num2) {
        if (calculation == '+') {
            return num1 + num2;
        } else if (calculation == '-') {
            return num1 - num2;
        } else if (calculation == '*') {
            return num1 * num2;
        } else if (calculation == '/') {
            return num1 / num2;
        } else {
            throw new IllegalArgumentException("Unknown calculation : " + calculation);
        }
    }
}

interface Calculation {
    int calculate(final int num1, final int num2);
}

class Addition implements Calculation {
    @Override
    public int calculate(final int num1, final int num2) {
        return num1 + num2;
    }
}

class Substraction implements Calculation {
    @Override
    public int calculate(final int num1, final int num2) {
        return num1 - num2;
    }
}

class Multiplication implements Calculation {
    @Override
    public int calculate(final int num1, final int num2) {
        return num1 * num2;
    }
}

class Division implements Calculation {
    @Override
    public int calculate(final int num1, final int num2) {
        return num1 / num2;
    }
}

class CalculatorService3 {
    private final Calculation calculation;

    public CalculatorService3(final Calculation calculation) {
        this.calculation = calculation;
    }

    public int calculate(final int num1, final int num2) {
        return calculation.calculate(num1, num2);
    }
}

class CalculatorService4 {
    private final Calculation calculation1;
    private final Calculation calculation2;

    public CalculatorService4(final Calculation calculation1,
                              final Calculation calculation2) {
        this.calculation1 = calculation1;
        this.calculation2 = calculation2;
    }

    public int calculate(final int num1, final int num2) {
        if (num1 > 10 && num2 < num1) {
            return calculation1.calculate(num1, num2);
        } else {
            throw new IllegalArgumentException("Invalid input num1 : " + num1 + ", num2 : " + num2);
        }
    }

    public int compute(final int num1, final int num2) {
        if (num1 > 10 && num2 < num1) {
            return calculation2.calculate(num1, num2);
        } else {
            throw new IllegalArgumentException("Invalid input num1 : " + num1 + ", num2 : " + num2);
        }
    }
}

class FpCalculatorService {
    public int calculate(Calculation calculation, final int num1, final int num2) {
        if (num1 > 10 && num2 < num1) {
            return calculation.calculate(num1, num2);
        } else {
            throw new IllegalArgumentException("Invalid input num1 : " + num1 + ", num2 : " + num2);
        }
    }
}

