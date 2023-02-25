package core.testexecution.instrument;

import core.cfg.CfgNode;
import core.parser.ProjectParser;
import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testdata.DataNode;
import core.testdata.SubProgramDataNode;
import core.testdata.normal_datanode.NormalDataNode;
import core.utils.SearchInDataTree;
import core.utils.SearchInSTree;
import core.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Instrument {
    public static final String TAB = "    ";
    private String sourcePath;
    private CfgNode cfgNode;
    private String instrumentPath;
    private String testPath;
    private File instrumentFolder;
    private SFunctionNode functionNode;
    private TestCase testCase;

    public Instrument(String sourcePath, CfgNode cfgNode, SFunctionNode functionNode, TestCase testCase) {
        this.sourcePath = sourcePath;
        this.cfgNode = cfgNode;
        this.functionNode = functionNode;
        this.testCase = testCase;
    }

    public String getTestPath() {
        return testPath;
    }

    public void setTestPath(String testPath) {
        this.testPath = testPath;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
    }

    public File getInstrumentFolder() {
        return instrumentFolder;
    }

    public void setInstrumentFolder(File instrumentFolder) {
        this.instrumentFolder = instrumentFolder;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public CfgNode getCfgNode() {
        return cfgNode;
    }

    public void setCfgNode(CfgNode cfgNode) {
        this.cfgNode = cfgNode;
    }

    public String getInstrumentPath() {
        return instrumentPath;
    }

    public void setInstrumentPath(String instrumentPath) {
        this.instrumentPath = instrumentPath;
    }

    public void markInstrument() throws IOException {
        File source = new File(sourcePath);
        createInstrumentPath();
        File clone = new File(instrumentPath);
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

        File clone = new File(instrumentFunctionFolder.getAbsolutePath() + "/" + SearchInSTree.getJavaFileNode(functionNode).getName());
        if (clone.createNewFile()) {
            System.out.println("Create instrument function successful");
        }
        else System.out.println("Instrument function is existed");
        setInstrumentPath(clone.getAbsolutePath());

        File testPath = new File(instrumentFunctionFolder.getAbsolutePath() + "/" + SearchInSTree.getJavaFileNode(functionNode).getName()
                /*+ new Random().nextInt()*/ + ".testpath");
        if (testPath.createNewFile()) {
            System.out.println("Create instrument function successful");
        }
        else System.out.println("Instrument function is existed");
        setTestPath(testPath.getAbsolutePath());
        Utils.writeToFile("", this.testPath);
    }

    public void mark() {
        List<CfgNode> listCFG = transferCFGTreeToList(cfgNode);

        String sourceContent = Utils.readFileContent(sourcePath);
        String osname = System.getProperty("os.name");
        if (osname.startsWith("Windows")) {
            sourceContent = sourceContent.replaceAll("\n", "\r\n");
        }
        String cloneContent = sourceContent;
        //todo: here mark into instrument
        //todo: mark function under test
        for (CfgNode cfg : listCFG) {
            String mark = cfg.markContent(testPath) + "";
            if (!mark.equals("")) {
                int index = cfg.getStartPosition();
                String whitespace = Utils.getPreviousWhiteSpace(cloneContent, index - 1);
                mark += "\n";
                String s1 = cloneContent.substring(0, index);
                String s2 = cloneContent.substring(index);
                cloneContent = s1 + mark + whitespace + s2;
            }
        }
        //todo: mark main function
        String s1 = "public static void main(String[] args) {";
        String body = createFunctionCall(testCase, functionNode);

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
            cloneContent = tmp1 + Utils.importFileLibrary() + "\n" + s2;
        }
        //todo: finish mark instrument
        cloneContent = cloneContent.replaceAll("\n\r", "\n");
        Utils.writeToFile(cloneContent, instrumentPath);
        File source = new File(sourcePath);
        File clone = new File(instrumentPath);
        System.out.println();
    }


    public List<CfgNode> transferCFGTreeToList(CfgNode rootCFG) {
        List<CfgNode> list = new ArrayList<>();
        List<CfgNode> children = rootCFG.getChildren();
        for (int i = children.size() - 1; i >= 0; i--) {
            list.addAll(transferCFGTreeToList(children.get(i)));
        }
        list.add(rootCFG);
        return list;
    }

    public static String createFunctionCall(TestCase testCase, SFunctionNode functionNode) {
        StringBuffer str = new StringBuffer();
        if (functionNode.getAst().isConstructor()) {
            //example: new A(1, 2)
            str.append("new ").append(functionNode.getName());
        }
        else if (functionNode.getAst().isStatic()) {
            //example: A.func(1, 2)
            String className = functionNode.getParent().getName();
            str.append(className).append(".").append(functionNode.getName());
        }
        else if (!functionNode.getAst().isAbstract()) {
            //example: new A().func(1, 2)
            String className = functionNode.getParent().getName();
            str.append("new ").append(className).append("().");
            str.append(functionNode.getName());
        }
        str.append("(");
        SubProgramDataNode subProgramDataNode = SearchInDataTree.searchSubprogramNode(testCase.getRootDataNode());
        String tmp = "";
        for (DataNode child : subProgramDataNode.getChildren()) {
            if (child instanceof NormalDataNode) {
                String value = ((NormalDataNode) child).getValue();
                tmp += value + ",";
            }
        }
        if (!tmp.equals("")) {
            str.append(tmp.substring(0, tmp.length()-1));
        }
        str.append(");");
        return String.valueOf(str);
    }
}

