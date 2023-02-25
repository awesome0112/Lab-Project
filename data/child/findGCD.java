public class findGCD {
    public static int findGCD(int x, int y) {
        if(y== 0){
            return x;
        }
        return findGCD(y, x%y);
    }
}