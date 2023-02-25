package core.utils;

import core.structureTree.SNode;
import core.node.Node;
import extent.Exporter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jdt.core.dom.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Utils {
    public static SNode parseFolderNodeToSNode(Node node) {
//        System.out.println("parse " + ASTNode.getName());
        SNode root = node.parseToSNode();
        if (root == null) return null;
        for (Node child : node.getChildren()) {
            System.out.println("parse " + child.getName());
            SNode sNodeChild = Utils.parseFolderNodeToSNode(child);
            if (sNodeChild != null) {
                root.getChildren().add(sNodeChild);
            }
        }
        for (SNode child : root.getChildren()) {
            child.setParent(root);
        }

        return root;
    }

    public static List<ASTNode> getChildren(org.eclipse.jdt.core.dom.ASTNode node) {
        List<ASTNode> children = new ArrayList<>();
        if (node instanceof MethodDeclaration) {
            Block block = ((MethodDeclaration) node).getBody();
            children.add(block);
//            List<Statement> statements = block.statements();
//            for (Statement statement : statements) {
//                if (statement instanceof IfStatement) {
//
//                }
//            }
        }
        else if (node instanceof TypeDeclaration) {
            List<FieldDeclaration> atributes = Arrays.asList(((TypeDeclaration) node).getFields());
            for (FieldDeclaration attribute : atributes) {
                children.add(attribute);
            }

            List<MethodDeclaration> methods = Arrays.asList(((TypeDeclaration) node).getMethods());
            for (MethodDeclaration method : methods) {
                children.add(method);
            }

        }
        else if (node instanceof Block) {
            List<Statement> statements = ((Block) node).statements();
            for (ASTNode statement : statements) {
                children.add(statement);
            }
        }
        else if (node instanceof IfStatement) {
            children.add(((IfStatement) node).getExpression());
            children.add(((IfStatement) node).getThenStatement());
            children.add(((IfStatement) node).getElseStatement());
        }
        else if (node instanceof ExpressionStatement) {
            children.add(((ExpressionStatement) node).getExpression());
        }

        return children;
    }


    public static Block getFunctionBlock(org.eclipse.jdt.core.dom.ASTNode node) {

        if (node instanceof MethodDeclaration) {
            Block block = ((MethodDeclaration) node).getBody();
            return block;
        }

        return null;
    }

    public static void getFunctionChildren(ASTNode node, List<ASTNode> astFuncList) {

        List<MethodDeclaration> methods = Arrays.asList(((TypeDeclaration) node).getMethods());
        for (MethodDeclaration method : methods) {
            if (method.isConstructor() == false)
            {
                astFuncList.add(method);
            }
        }
    }

    public static String readFileContent(String path) {
        String content = "";
        File fileToRead = new File(path);

        try(FileReader fileStream = new FileReader( fileToRead );
            BufferedReader bufferedReader = new BufferedReader( fileStream ) ) {

            String line = null;

            while( (line = bufferedReader.readLine()) != null ) {
                //do something with line
                content += line + "\n";
            }

        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return content;
    }

    public static void writeToFile(String content, String absolutePath) {
        try {
            //Whatever the file path is.
            File statText = new File(absolutePath);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(content);
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file " + absolutePath);
        }
    }

    public static String insertString(
            String originalString,
            String stringToBeInserted,
            int index)
    {

        // Create a new string
        String newString = originalString.substring(0, index + 1)
                + stringToBeInserted
                + originalString.substring(index + 1);

        // return the modified String
        return newString;
    }

    public static String getPreviousWhiteSpace(String str, int from) {
        String res = "";
        while (str.charAt(from) == ' ') {
            res += " ";
            from --;
        }
        return res;
    }

    public static String getWriteToTestPathContent(String content, String absolutePath) {
        return "try{\n" +
                "        //Specify the file name and path here\n" +
                "        File file =new File(\"" + absolutePath.replaceAll("\\\\", "/")+ "\");\n" +
                " \n" +
                "        /* This logic is to create the file if the\n" +
                "         * file is not already present\n" +
                "         */\n" +
                "        if(!file.exists()){\n" +
                "           file.createNewFile();\n" +
                "        }\n" +
                " \n" +
                "        //Here true is to append the content to file\n" +
                "        FileWriter fw = new FileWriter(file,true);\n" +
                "        //BufferedWriter writer give better performance\n" +
                "        BufferedWriter bw = new BufferedWriter(fw);\n" +
                "        bw.write(\"" +content+ "\" + \"\\n\");\n" +
                "        //Closing BufferedWriter Stream\n" +
                "        bw.close();\n" +
                " \n" +
                "    System.out.println(\"Data successfully appended at the end of file\");\n" +
                " \n" +
                "      }catch(IOException ioe){\n" +
                "         System.out.println(\"Exception occurred:\");\n" +
                "         ioe.printStackTrace();\n" +
                "       }";
    }

    public static String getWriteToActualPathContent(String arg, String absolutePath) {
        return "try{\n" +
                "        //Specify the file name and path here\n" +
                "        File file =new File(\"" + absolutePath.replaceAll("\\\\", "/")+ "\");\n" +
                " \n" +
                "        /* This logic is to create the file if the\n" +
                "         * file is not already present\n" +
                "         */\n" +
                "        if(!file.exists()){\n" +
                "           file.createNewFile();\n" +
                "        }\n" +
                " \n" +
                "        //Here true is to append the content to file\n" +
                "        FileWriter fw = new FileWriter(file,true);\n" +
                "        //BufferedWriter writer give better performance\n" +
                "        BufferedWriter bw = new BufferedWriter(fw);\n" +
                "        bw.write(\"" + arg + "=\" + " + arg + " + \"\\n\");\n" +
                "        //Closing BufferedWriter Stream\n" +
                "        bw.close();\n" +
                " \n" +
                "    System.out.println(\"Data successfully appended at the end of file\");\n" +
                " \n" +
                "      }catch(IOException ioe){\n" +
                "         System.out.println(\"Exception occurred:\");\n" +
                "         ioe.printStackTrace();\n" +
                "       }";
    }

    public static String importFileLibrary() {
        return "import java.io.*;";
    }

    public static List<String> readFileByLines(String path) throws IOException {
        List<String> lines = new ArrayList<>();
        // Open the file
        FileInputStream fstream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console
            lines.add(strLine);
        }

        //Close the input stream
        fstream.close();
        return lines;
    }

    public static int getStartValueOfLineInTestPath(String line) {
        String val = "";
        for (char c : line.toCharArray()) {
            if ('0' <= c && c <= '9') {
                val += c;
            } else break;
        }
        return Integer.valueOf(val);
    }

    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Utils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found " + fileName);
        } else {
            return inputStream;
        }
    }

    public static void printReport(String path) {
        try
        {
            File file = new File(path);   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
//creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_STRING:    //field that represents string cell type
                            System.out.print(cell.getStringCellValue() + "\t\t\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type
                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            break;
                        default:
                    }
                }
                System.out.println("");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
