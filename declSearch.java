import java.io.*;
import java.util.*;
import java.io.LineNumberReader;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class declSearch {
	
	/*
	 * This method reads a file into a string
	 */
	public static String fileReader(String args[])throws IOException{	   		
	    // open input
	    FileInputStream in_file = new FileInputStream(args[0]);
	 	// read input file into a byte array
		byte[] msg = new byte[in_file.available()];
		
		//Converts byte array to string    
		String content =  new String(msg);
		in_file.close();
		//Print contents of string to terminal Testing only.
		return content;
	}
	/*
	 * This method creates a Parser 
	 * then looks for declarations within the java code 
	 */
	public static void searchParser(String content, String args[]){
		ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setSource(content.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
	 
			CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
			cu.accept(new ASTVisitor(){
				
				//Set names = new HashSet();
				 
				//public boolean visit(VariableDeclarationFragment node) {
				//	SimpleName name = node.getName();
				//	int lineNum;
				//	return false;
				//}
			

			});
		}
	public static void main(String args[]) {
		
	}
}
