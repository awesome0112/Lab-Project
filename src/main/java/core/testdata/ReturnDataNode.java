package core.testdata;

public class ReturnDataNode extends ValueDataNode {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ReturnDataNode() {
        setName("RETURN");
    }
}
