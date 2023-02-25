package core.testcases;

import core.generationStrategy.GenerationStrategy;
import core.structureTree.structureNode.SFunctionNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestcaseManager {
    Map<String, TestCase> testCaseMap = new HashMap<>();

    public static List<TestCase> createTestcase(SFunctionNode functionNode, GenerationStrategy strategy) {
        return strategy.generate(functionNode);
    }
}
