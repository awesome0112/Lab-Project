import java.io.*;
public class check {
    public boolean check(int num) {
        if (num % 3 == 0)
            if (num % 5 == 0)
                System.out.println("num=" + num);try{
                        //Specify the file name and path here
                        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/actual/check.java.tracepath");
                 
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
                        bw.write("num=" + num + "\n");
                        //Closing BufferedWriter Stream
                        bw.close();
                 
                    System.out.println("Data successfully appended at the end of file");
                 
                      }catch(IOException ioe){
                         System.out.println("Exception occurred:");
                         ioe.printStackTrace();
                       }
                return true;
            else
                System.out.println("num=" + num);try{
                        //Specify the file name and path here
                        File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/actual/check.java.tracepath");
                 
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
                        bw.write("num=" + num + "\n");
                        //Closing BufferedWriter Stream
                        bw.close();
                 
                    System.out.println("Data successfully appended at the end of file");
                 
                      }catch(IOException ioe){
                         System.out.println("Exception occurred:");
                         ioe.printStackTrace();
                       }
                return false;
        else
            System.out.println("num=" + num);try{
                    //Specify the file name and path here
                    File file =new File("C:/Users/haivt/Desktop/gen-test-main/JGT-workspace/instrument/check/actual/check.java.tracepath");
             
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
                    bw.write("num=" + num + "\n");
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