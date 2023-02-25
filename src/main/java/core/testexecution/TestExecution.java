package core.testexecution;

import core.cfg.CfgNode;
import core.cmd.CommandLine;
import core.node.FileNode;
import core.structureTree.structureNode.SFunctionNode;
import core.testcases.TestCase;
import core.testexecution.coverage.TestCoverage;
import core.testexecution.instrument.ActualValue;
import core.testexecution.instrument.Instrument;
import core.utils.CFGUtils;
import core.utils.SearchInSTree;
import core.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestExecution {
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String N_A = "n_a";
    private List<TestCase> testCaseList;
    private CfgNode rootCFG;
    private SFunctionNode functionNode;

    private Instrument instrument;
    private ActualValue actualValue;
    private String status = "N_A";
    private TestCoverage coverage;



    public TestExecution(List<TestCase> testCaseList, CfgNode rootCFG) {
        this.testCaseList = testCaseList;
        this.rootCFG = rootCFG;
    }

    public TestCoverage getCoverage() {
        return coverage;
    }

    public void setCoverage(TestCoverage coverage) {
        this.coverage = coverage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }

    public CfgNode getRootCFG() {
        return rootCFG;
    }

    public void setRootCFG(CfgNode rootCFG) {
        this.rootCFG = rootCFG;
    }

    public SFunctionNode getFunctionNode() {
        return functionNode;
    }

    public void setFunctionNode(SFunctionNode functionNode) {
        this.functionNode = functionNode;
        functionNode.getAst().setCfg(rootCFG);
    }

    public void execute() {
        for (TestCase testCase : testCaseList) {
            FileNode  fileNode = SearchInSTree.getJavaFileNode(functionNode);
            String absolutePath = fileNode.getAbsolutePath();
            instrument = new Instrument(absolutePath, rootCFG, functionNode, testCase);
            try {
                instrument.markInstrument();
                testCase.setTestPath(instrument.getTestPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (instrument != null) {
                try {
                    String command = "javac " + instrument.getInstrumentPath();
                    CommandLine.executeCommand(command);
                    String folderPath = instrument.getInstrumentFolder().getAbsolutePath();//.replaceAll("\\\\", "/");
                    System.out.println(folderPath);
                    command = "java " + SearchInSTree.getJavaFileNode(functionNode).getName().replaceAll(".java", "");
                    CommandLine.executeCommand(command, folderPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String testPath = instrument.getTestPath();
                try {
                    List<String> linesTestPath = Utils.readFileByLines(testPath);
                    for (String line : linesTestPath) {
                        int start = Utils.getStartValueOfLineInTestPath(line);
                        CfgNode finderCFG = CFGUtils.findCFGNodeByStart(rootCFG, start);
                        if (finderCFG != null) finderCFG.setVisited(true);
                    }
                } catch (IOException e) {
                    System.out.println("Fail to read test path content.");
                    e.printStackTrace();
                }
                setStatus(SUCCESS);
            }
            actualValue = new ActualValue(absolutePath, rootCFG, functionNode, testCase);
            try {
                actualValue.markInstrument();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (actualValue != null) {
                try {
                    String command = "javac " + actualValue.getActualPath();
                    CommandLine.executeCommand(command);
                    String folderPath = actualValue.getActualFolder().getAbsolutePath();//.replaceAll("\\\\", "/");
                    System.out.println(folderPath);
                    command = "java " + SearchInSTree.getJavaFileNode(functionNode).getName().replaceAll(".java", "");
                    CommandLine.executeCommand(command, folderPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String tracePath = actualValue.getTracePath();
                List<String> linesTestPath = null;
                try {
                    linesTestPath = Utils.readFileByLines(tracePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map<String, String> mapActualValue= new HashMap<>();
                for (String line : linesTestPath) {
                    int i = line.indexOf("=");
                    String name = line.substring(0, i);
                    String actualValue = line.substring(i+1);
                    mapActualValue.put(name, actualValue);
                }
                actualValue.setMapActualValue(mapActualValue);
                System.out.println();
            }
            testCase.setActualValue(actualValue);

        }
        TestCoverage cov = TestExecutionManager.generateTestCoverage(this);
        setCoverage(cov);
    }

    public void showCoverage() {
        if (coverage == null) return;
        double cov = coverage.getPassNode()* 100.0 /coverage.getTotalNode();
        StringBuilder info = new StringBuilder();
        info.append("Coverage of function ").append(getFunctionNode().getName()).append(": ").append(cov).append("%");
        System.out.println( info);
    }


}
