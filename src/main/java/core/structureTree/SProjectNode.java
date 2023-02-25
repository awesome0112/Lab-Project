package core.structureTree;

import core.parser.ProjectParser;

public class SProjectNode extends SNode{

    public static void main(String[] args) {
        ProjectParser parser = ProjectParser.getParser();
        try {
            parser.doParsing("C:\\Users\\haivt\\Desktop\\gen-test-main\\data", 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
