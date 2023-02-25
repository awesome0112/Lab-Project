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
        if(y== 0){
            System.out.println("x=" + x);try{
                    //Specify the file name and path here
                    File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/actual/Solution.java.tracepath");
             
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
                    bw.write("x=" + x + "\n");
                    //Closing BufferedWriter Stream
                    bw.close();
             
                System.out.println("Data successfully appended at the end of file");
             
                  }catch(IOException ioe){
                     System.out.println("Exception occurred:");
                     ioe.printStackTrace();
                   }
            System.out.println("y=" + y);try{
                    //Specify the file name and path here
                    File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/actual/Solution.java.tracepath");
             
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
                    bw.write("y=" + y + "\n");
                    //Closing BufferedWriter Stream
                    bw.close();
             
                System.out.println("Data successfully appended at the end of file");
             
                  }catch(IOException ioe){
                     System.out.println("Exception occurred:");
                     ioe.printStackTrace();
                   }
            return x;
        }
        System.out.println("x=" + x);try{
                //Specify the file name and path here
                File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/actual/Solution.java.tracepath");
         
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
                bw.write("x=" + x + "\n");
                //Closing BufferedWriter Stream
                bw.close();
         
            System.out.println("Data successfully appended at the end of file");
         
              }catch(IOException ioe){
                 System.out.println("Exception occurred:");
                 ioe.printStackTrace();
               }
        System.out.println("y=" + y);try{
                //Specify the file name and path here
                File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/findGCD/actual/Solution.java.tracepath");
         
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
                bw.write("y=" + y + "\n");
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