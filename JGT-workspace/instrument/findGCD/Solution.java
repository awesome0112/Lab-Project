import java.io.*;
public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    public Solution(int numerator, int denominator) {
        if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }


    public static int findGCD(int x, int y) {
        try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/Solution.java.testpath");
 
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
        bw.write("344CFGIfStatementNode{StartAt:344,EndAt:388" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
        if(y== 0){
            try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/Solution.java.testpath");
 
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
        bw.write("368CFGReturnStatement{StartAt:368,EndAt:377" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            return x;
        }
        try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/Solution.java.testpath");
 
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
        bw.write("398CFGReturnStatement{StartAt:398,EndAt:421" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
        return findGCD(y, x%y);
    }
    public static void main(String[] args) {Solution.findGCD(-1509100872,709472808);}
}