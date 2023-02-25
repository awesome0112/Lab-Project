import java.io.*;
public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    public Solution(int numerator, int denominator) {
        if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    System.out.println("numerator=" + numerator);try{
            //Specify the file name and path here
            File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/Solution/actual/Solution.java.tracepath");
     
            /* This logic is to create the file if the
             * file is not already present
             */
            if(!file.exists()){
               file.createNewFile();
            }
     
            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("numerator=" + numerator + "\n");
            //Closing BufferedWriter Stream
            bw.close();
     
        System.out.println("Data successfully appended at the end of file");
     
          }catch(IOException ioe){
             System.out.println("Exception occurred:");
             ioe.printStackTrace();
           }
    System.out.println("denominator=" + denominator);try{
            //Specify the file name and path here
            File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/Solution/actual/Solution.java.tracepath");
     
            /* This logic is to create the file if the
             * file is not already present
             */
            if(!file.exists()){
               file.createNewFile();
            }
     
            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("denominator=" + denominator + "\n");
            //Closing BufferedWriter Stream
            bw.close();
     
        System.out.println("Data successfully appended at the end of file");
     
          }catch(IOException ioe){
             System.out.println("Exception occurred:");
             ioe.printStackTrace();
           }
    }


    public static int findGCD(int x, int y) {
        if(y== 0){
            return x;
        }
        return findGCD(y, x%y);
    }
    public static void main(String[] args) {new Solution(-1039676227,-1638931635);}
}