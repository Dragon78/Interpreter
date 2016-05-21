
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
public class Main {
	public static void main (String[] args)throws Exception{
		InputStream Stream = System.in;
		ANTLRInputStream input = new ANTLRInputStream(Stream);
		OptiMathLexer lexer = new OptiMathLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OptiMathParser parser = new OptiMathParser(tokens);
		
		ParseTree tree = parser.equation();
		
		new MyVisitor().visit(tree);
		/*Check the tree for comon equations <Regexchecker>
		   - QUADRATIC
		   - LINEAR
		 * Transpose for unkown variable <Transpose visitor>
		 * Solve single equations <myVisitor>
		 * */
		
	}
}
