package parser;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
 
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class TypeFinderVisitor extends ASTVisitor {
	
	private int declCount;
	private int refCount;
	private Set<Object> names = new HashSet<Object>();
	
	//Counts number of declarations of search type
	public boolean visit(VariableDeclarationFragment node) {
		//name of node to be stored in Hashset to later check for references
		SimpleName name = node.getName();
		
		//Resolve the binding to get the type node carries
		IVariableBinding bind = node.resolveBinding();
		String typeFound = bind.getVariableDeclaration().toString();
		String[] typeSplit = typeFound.split("\\s+");
		
		if (Arrays.asList(typeSplit).contains(ASTParserAnalysis.getsearchType())) {
			this.names.add(name.getIdentifier());
			++declCount;
		}
		return false;
	}
	
	public boolean visit(SimpleName node) {
		if (this.names.contains(node.getFullyQualifiedName())) {
			++refCount;
		}
		return true;
	}
	
	public int getdeclCount() {
		return declCount;
	}
	
	public int getrefCount() {
		return refCount;
	}
}
