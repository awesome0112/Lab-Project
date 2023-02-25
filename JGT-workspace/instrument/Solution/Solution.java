import java.io.*;
public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    public Solution(int numerator, int denominator) {
        try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/Solution/Solution.java.testpath");
 
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
        bw.write("156CFGIfStatementNode{StartAt:156,EndAt:276" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
        if (denominator != 0) {
            try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/Solution/Solution.java.testpath");
 
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
        bw.write("193CFGExpressionStatement{StartAt:193,EndAt:220" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            this.numerator = numerator;
            try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/Solution/Solution.java.testpath");
 
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
        bw.write("234CFGExpressionStatement{StartAt:234,EndAt:265" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            this.denominator = denominator;
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