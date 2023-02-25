import core.cfg.CfgBlock;
import core.cfg.CfgNode;
import core.parser.ASTHelper;
import core.parser.ProjectParser;
import core.utils.Utils;
import extent.Exporter;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
//import sun.nio.ch.Util;

//import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class AppStart {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CppApi.class);

    private static long totalUsedMem = 0;
    private static long tickCount = 0;

    public static void main(String[] args) throws IOException {
        String path = "data\\child\\CFG4J_Test.java";
        System.out.println("Start parsing...");
        ArrayList<ASTNode> funcAstNodeList = ProjectParser.parseFile(path);

        System.out.println("count = " + funcAstNodeList.size());

        for (ASTNode func : funcAstNodeList) {
            if (((MethodDeclaration)func).getName().getIdentifier().equals("checkLeafYear"))
            {
//                System.out.println("func = " + ((MethodDeclaration)func).getName());
//                System.out.println("parameters.size() = " + ((MethodDeclaration)func).parameters().size());

                Timer T = new Timer(true);

                TimerTask memoryTask = new TimerTask(){
                    @Override
                    public void run(){
                        totalUsedMem += (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
//                        System.out.println("totalUsedMem = " + totalUsedMem);
                        tickCount += 1;
//                        System.out.println("tickCount = " + tickCount);
                        //callback.accept(totalUsedMem);
                    }
                };



                T.scheduleAtFixedRate(memoryTask, 0, 1); //0 delay and 5 ms tick

                LocalDateTime beforeTime = LocalDateTime.now();


                Block functionBlock = Utils.getFunctionBlock(func);

                CfgNode cfgBeginCfgNode = new CfgNode();
                cfgBeginCfgNode.setIsBeginCfgNode(true);

                CfgNode cfgEndCfgNode = new CfgNode();
                cfgEndCfgNode.setIsEndCfgNode(true);

                CfgNode block = new CfgBlock();
                block.setAst(functionBlock);

                block.setBeforeStatementNode(cfgBeginCfgNode);
                block.setAfterStatementNode(cfgEndCfgNode);

                ASTHelper.generateCFGFromASTBlockNode(block);

                LocalDateTime afterTime = LocalDateTime.now();

                Duration duration = Duration.between(beforeTime, afterTime);

                float diff = Math.abs((float) duration.toMillis());

                T.cancel();

                System.out.println("func = " + ((MethodDeclaration)func).getName());
                System.out.println("used time = " + diff + " ms");
//                System.out.println("tickCount = " + tickCount);
                float usedMem = ((float)totalUsedMem)/tickCount/1024/1024;
                System.out.print("used mem = ");
                System.out.printf("%.2f", usedMem);
                System.out.println(" MB");
            }
        }

        //print the template of test report
//        try {
//            Utils.printReport(AppStart.class.getResource(Exporter._TEMPLATE_REPORT_PATH).toURI().getPath());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }
}
