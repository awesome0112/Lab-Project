import java.io.*;
public class check {
    public boolean check(int num) {
        try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/check.java.testpath");
 
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
        bw.write("67CFGIfStatementNode{StartAt:67,EndAt:235" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
        if (num % 3 == 0)
            try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/check.java.testpath");
 
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
        bw.write("98CFGIfStatementNode{StartAt:98,EndAt:194" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            if (num % 5 == 0)
                try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/check.java.testpath");
 
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
        bw.write("133CFGReturnStatement{StartAt:133,EndAt:145" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
                return true;
            else
                try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/check.java.testpath");
 
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
        bw.write("181CFGReturnStatement{StartAt:181,EndAt:194" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
                return false;
        else
            try{
        //Specify the file name and path here
        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/check.java.testpath");
 
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
        bw.write("222CFGReturnStatement{StartAt:222,EndAt:235" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            return false;
        }
    public static void main(String[] args) {new check().check(-435321010);}
}