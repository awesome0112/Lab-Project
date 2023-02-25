package core.cfg;

import java.util.ArrayList;
import java.util.List;

public class CfgEndBlockNode extends CfgNode
{
    private List<CfgNode> beforeEndBoolNodeList = new ArrayList<>();

    public List<CfgNode> getBeforeEndBoolNodeList()
    {
        return beforeEndBoolNodeList;
    }

    public void setBeforeEndBoolNodeList(List<CfgNode> beforeEndBoolNodeList)
    {
        this.beforeEndBoolNodeList = beforeEndBoolNodeList;
    }
}
