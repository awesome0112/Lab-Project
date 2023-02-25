import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public Test(){}

    public <T extends Comparable<T>> int find(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(value) == 0) {
                return i;
            }
        }
        return -1;
    }


    public void foo(char a, int b, int c, int d)
    {
        int i = 0;int j = 0;

        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");

        for (
                String str:
                list
        )
        {
            System.out.println("str = " + str);
            int i = 5 + 10;
        }

//        do
//        {
//            i = j + 1;
//            System.out.println("i = " + i);
//        }
//        while (i < 10);


//        if (i < 2)
//        {
//            System.out.println(" i < 2 ");
//        }

        i = j + 10;

//        while (i < 10)
//        {
//            System.out.println("i = " + i);
//            i = i+1;
//        }

//        for (i = 0; i < 10; i++)
//        {
//            //Do something
//        }

//        ArrayList<int> list = new ArrayList();
//        list.add(10);
//        list.add(5);
//        for (int t: list)
//        {
//            System.out.println("t = " + t);
//        }

        float temp = 0;

        double x = temp + 10;

        return x;
    }

//    public void check (char a, int b, int c, int d)
//    {
//        double e;
//        if (a=='c')
//        {
//            return 0;
//        }
//        else
//        {
//            int x = 10;
//        }
//
//        int f = 0;
//
//        if ((a==b) || (c==d))
//            f = 2;
//        else
//            f = 3;
//
//        e = 1/f;
//
////        return f;
//    }

//    public bool checkLeafYear()
//    { // year to be checked
//        int year = 1900;
//        boolean leap = false;
//
//        // if the year is divided by 4
//        if (year % 4 == 0) {
//
//        // if the year is century
//        if (year % 100 == 0) {
//
//        // if year is divided by 400
//        // then it is a leap year
//        if (year % 400 == 0)
//        leap = true;
//        else
//        leap = false;
//        }
//
//        // if the year is not century
//        else
//        leap = true;
//        }
//
//        else
//        leap = false;
//
//        return leap;
//    }
}