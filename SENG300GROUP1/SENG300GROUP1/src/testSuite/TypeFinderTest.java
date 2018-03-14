package testSuite;

import static org.junit.Assert.*;

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
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.WorkingCopyOwner;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTRequestor;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.junit.Before;
import org.junit.Test;

import parser.ASTParserAnalysis;
import parser.TypeFinderVisitor;
public class TypeFinderTest {
	private ASTParserAnalysis parser;
	private TypeFinderVisitor tester;
	String[] input0 = {"files", "int"};
	String[] input1 = {"", "int"};
	String[] input2 = {};
	
	private static String BASEDIR = "\\src\\parser";
	String[] input3 = {BASEDIR, "int"}; 
	String[] input4 = {BASEDIR, "String"};
	
	@Before
	public void setup() throws IOException {
		parser = new ASTParserAnalysis();
		tester = new TypeFinderVisitor();
		
	}
		@SuppressWarnings("static-access")
	@Test 
	public void existingdirtest() throws IOException {
		//existing directory, has 0 long declarations
		parser.main(input0);
		
	}
	@Test
	public void illegalargtest() throws IOException {
		//empty string as parameter for file name, 
		//should reject string since there is no directory name
		parser.main(input1);
	}
	@Test
	public void emptyargstest() throws IOException {
		//Throw an error because there is no arguments in input2
		parser.main(input2);
	}

	@Test
	//Using the source code of the program, should parse the code and assert true for counts
	public void searchForInt() throws IOException {	
		parser.main(input3);
		assertEquals(tester.getdeclCount(), 7);
		assertEquals(tester.getrefCount(), 7);
	}
	
	public void searchForString() throws IOException {
		parser.main(input4);
		assertEquals(tester.getdeclCount(), 20);
		assertEquals(tester.getrefCount(), 7);
	}

}
