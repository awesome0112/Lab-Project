package core.testexecution.instrument;

import core.cfg.CfgNode;
import core.cfg.CfgReturnStatementNode;
import core.parser.ProjectParser;
import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testdata.DataNode;
import core.testdata.SubProgramDataNode;
import core.testdata.ValueDataNode;
import core.testdata.normal_datanode.NormalDataNode;
import core.utils.CFGUtils;
import core.utils.SearchInDataTree;
import core.utils.SearchInSTree;
import core.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.testexecution.instrument.Instrument.TAB;

public class ActualValue {
    private String sourcePath;
    private String actualPath;
    private File instrumentFolder;
    private File actualFolder;
    private String tracePath;
    private CfgNode cfgNode;
    private Map<String,String> mapActualValue;


    private SFunctionNode functionNode;
    private TestCase testCase;

    public ActualValue(String sourcePath, CfgNode cfgNode, SFunctionNode functionNode, TestCase testCase) {
        this.sourcePath = sourcePath;
        this.cfgNode = cfgNode;
        this.functionNode = functionNode;
        this.testCase = testCase;
    }

    public CfgNode getCfgNode() {
        return cfgNode;
    }

    public void setCfgNode(CfgNode cfgNode) {
        this.cfgNode = cfgNode;
    }

    public Map<String, String> getMapActualValue() {
        return mapActualValue;
    }

    public void setMapActualValue(Map<String, String> mapActualValue) {
        this.mapActualValue = mapActualValue;
    }

    public String getTracePath() {
        return tracePath;
    }

    public void setTracePath(String tracePath) {
        this.tracePath = tracePath;
    }

    public File getActualFolder() {
        return actualFolder;
    }

    public void setActualFolder(File actualFolder) {
        this.actualFolder = actualFolder;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getActualPath() {
        return actualPath;
    }

    public void setActualPath(String actualPath) {
        this.actualPath = actualPath;
    }

    public File getInstrumentFolder() {
        return instrumentFolder;
    }

    public void setInstrumentFolder(File instrumentFolder) {
        this.instrumentFolder = instrumentFolder;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public void markInstrument() throws IOException {
        File source = new File(sourcePath);
        createInstrumentPath();
        File clone = new File(actualPath);
        clone.setWritable(true);
        mark();
    }

    private void createInstrumentPath() throws IOException {
        String projectPath = ProjectParser.getParser().getProjectPath();

        File workspace = new File(new File(projectPath).getParentFile().getAbsolutePath() + "/JGT-workspace");
        if (workspace.mkdir()) {
            System.out.println("Create workspace successful");
        }
        else System.out.println("Workspace is existed");
        File instrumentFolder = new File(workspace.getAbsolutePath() + "/instrument");
        if (instrumentFolder.mkdir()) {
            System.out.println("Create instrument folder successful");
        }
        else System.out.println("Instrument folder is existed");
        File instrumentFunctionFolder = new File(instrumentFolder.getAbsolutePath() + "/" + functionNode.getName());
        if (instrumentFunctionFolder.mkdir()) {
            System.out.println("Create instrument Function folder successful");
        }
        else System.out.println("Instrument folder is existed");
        setInstrumentFolder(instrumentFunctionFolder);

        File actualFolder = new File(instrumentFunctionFolder.getAbsolutePath() + "/" + "actual");
        if (actualFolder.mkdir()) {
            System.out.println("Create instrument actual folder successful");
        }
        else System.out.println("Instrument actual folder is existed");
        setActualFolder(actualFolder);

        File clone = new File(actualFolder.getAbsolutePath() + "/" + SearchInSTree.getJavaFileNode(functionNode).getName());
        if (clone.createNewFile()) {
            System.out.println("Create instrument function successful");
        }
        else System.out.println("Instrument function is existed");
        setActualPath(clone.getAbsolutePath());

        File trace = new File(actualFolder.getAbsolutePath() + "/" + SearchInSTree.getJavaFileNode(functionNode).getName()
                /*+ new Random().nextInt()*/ + ".tracepath");
        if (trace.createNewFile()) {
            System.out.println("Create trace function successful");
        }
        else System.out.println("trace function is existed");
        setTracePath(trace.getAbsolutePath());
        Utils.writeToFile("", this.tracePath);
    }

    private void mark() {
        String sourceContent = Utils.readFileContent(sourcePath);
        String osname = System.getProperty("os.name");
        if (osname.startsWith("Windows")) {
            sourceContent = sourceContent.replaceAll("\n", "\r\n");
        }
        String cloneContent = sourceContent;

        //todo: instrument actual value for test case
        //todo: mark function under test
        SubProgramDataNode subProgramDataNode = SearchInDataTree.searchSubprogramNode(testCase.getRootDataNode());
        String argInstrumentActualValue = "";
        for (DataNode node : subProgramDataNode.getChildren()) {
            argInstrumentActualValue += getActualInstrumentForDataNode(node) + "\n";
        }
        if (!argInstrumentActualValue.equals("")) argInstrumentActualValue = argInstrumentActualValue.substring(0, argInstrumentActualValue.lastIndexOf("\n"));

        String returnType = functionNode.getAst().getReturnType();
        if (returnType.equals("")) {
            //todo: insert instrument arg into the last line of function under test
            int end = functionNode.getAst().getCfg().getEndPosition() - 2;
            String whiteSpace = Utils.getPreviousWhiteSpace(cloneContent, end);
            String arg = argInstrumentActualValue.replaceAll("\n", "\n" + whiteSpace);
            String s1 = cloneContent.substring(0, end + 1);
            String s2 = cloneContent.substring(end + 1);
            cloneContent = s1 + arg + "\n" + whiteSpace + s2;
        } else {
            //todo: insert instrument arg into the 1st previous line of the return Node in function under test
            List<CfgReturnStatementNode> listReturnCFG = new ArrayList<>();
            listReturnCFG = CFGUtils.findCFGReturnStatementDecreaseSort(cfgNode);
            for (CfgReturnStatementNode statement : listReturnCFG) {
                int start = statement.getStartPosition();
                String whiteSpace = Utils.getPreviousWhiteSpace(cloneContent, start - 1);
                String arg = argInstrumentActualValue.replaceAll("\n", "\n" + whiteSpace);
//                start -= 1;
                String s1 = cloneContent.substring(0, start);
                String s2 = cloneContent.substring(start);
                cloneContent = s1 + arg + s2;
            }
        }
        //todo: mark main function
        String s1 = "public static void main(String[] args) {";
        String body = Instrument.createFunctionCall(testCase, functionNode);

        String s2 = "}";
        String main = s1 + body + s2;
        for (int i = cloneContent.length()-1; i >=0; i--) {
            if (cloneContent.charAt(i) == '}') {
                String tmp1 = cloneContent.substring(0, i);
                String tmp2 = cloneContent.substring(i, cloneContent.length()-1);
                cloneContent = tmp1 + TAB + main + "\n" + tmp2;
                break;
            }
        }
        int importindex = SearchInSTree.getJavaFileNode(functionNode).getCfg().getStartPosition();
        if (importindex == 0) {
            cloneContent = Utils.importFileLibrary() + "\n" + cloneContent;
        }else {
            String tmp1 = cloneContent.substring(0, importindex);
            String tmp2 = cloneContent.substring(importindex, cloneContent.length()-1);
            cloneContent = tmp1 + Utils.importFileLibrary() + "\n" + tmp2;
        }
        //todo: finish intrument actual value for test case

        cloneContent = cloneContent.replaceAll("\n\r", "\n");
        Utils.writeToFile(cloneContent, actualPath);
    }

    private String getActualInstrumentForDataNode(DataNode node) {
        StringBuilder instrument = new StringBuilder();
        System.out.println();
        if (node instanceof ValueDataNode) {
            if (node instanceof NormalDataNode) {
                String name = node.getName();
                instrument.append("System.out.println(").append("\"").append(name).append("=\"").append(" + ").append(name);
                instrument.append(");");
                String content = Utils.getWriteToActualPathContent(name, tracePath);
                instrument.append(content);
            }
        }
        return String.valueOf(instrument);
    }
}
