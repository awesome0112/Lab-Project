package core.cfg;

public class CfgBeginDoNode extends CfgNode
{
    private CfgEndBlockNode endBlockNode = null;

    public CfgEndBlockNode getEndBlockNode()
    {
        return endBlockNode;
    }

    public void setEndBlockNode(CfgEndBlockNode endBlockNode)
    {
        this.endBlockNode = endBlockNode;
    }
}
