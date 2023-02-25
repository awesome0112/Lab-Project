import java.io.*;
public class findGCD {
    public static int findGCD(int x, int y) {
        try{
        //Specify the file name and path here
        File file =new File("F:/VietData/Github/TG4J/JGT-workspace/instrument/findGCD/findGCD.java.testpath");
 
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
        bw.write("79CFGIfStatementNode{StartAt:79,EndAt:123" + "\n");
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
        File file =new File("F:/VietData/Github/TG4J/JGT-workspace/instrument/findGCD/findGCD.java.testpath");
 
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
        bw.write("103CFGReturnStatement{StartAt:103,EndAt:112" + "\n");
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
        File file =new File("F:/VietData/Github/TG4J/JGT-workspace/instrument/findGCD/findGCD.java.testpath");
 
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
        bw.write("133CFGReturnStatement{StartAt:133,EndAt:156" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
        return findGCD(y, x%y);
    }
    public static void main(String[] args) {findGCD.findGCD(639711671,2138063685);}
}