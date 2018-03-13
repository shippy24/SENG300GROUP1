package parser;

//import greenblocks.util.Util;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.HashSet;
 
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ASTParserAnalysis {
	
	public static String[] primitive = {"int", "char", "float", "boolean", "double", "long", "String"}; 
	private static String type;
	private static int declCount;
	private static int refCount;
	
	public static void main(String[] args) throws IOException {
		String path = args[0]; 
		type = args[1];
		//String newType = "";
		//String javaLang = "java.lang.";
		
		//change to array??
		String absPath =  (new File("").getAbsolutePath().concat(path));
		System.out.println(absPath);
		File root = new File(absPath);
		System.out.println(root.listFiles().toString());
		
		File[] files = root.listFiles();
		
		//Check if files are .java files
		boolean javaFile = checkFiles(files);
		
		//Check if the array of primitive types including String contains args[1]
		if (((Arrays.asList(primitive).contains(type)) || javaFile) && (files != null)) {
			//if true continue with parsing
			parseFiles(files, absPath);
		}
		else {
			//else Invalid type
			System.out.println("Invalid type");
			System.out.println("Choose from: \n"
					+ " int, char, float, boolean, double, long, String");
		}
	}
	
	public static boolean checkFiles( File[] files) {
		for (File file : files) {
			if (file.getName().contains(".java")) 
				return true; 
		}
		return false;
	}
	
	public static String readFileToString( String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		String line = null;
		while( line != null) {
			line = reader.readLine();
			fileData.append(line);
		}
		reader.close();
		
		return fileData.toString();
	}
	
	public static void parseFiles(File[] files, String absPath) throws IOException {
		for (File file : files) {
			if (file.isFile()) {
				parse(readFileToString(file.getAbsolutePath()), file);
			}
		}
	}
	
	public static void parse( String fileData, File file ) {
			//Create a JLS8 Parser (note for it to run under JLS8 should have the updated JAR files)
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			
			//Set Resolve Bindings and Binding Recovery to true to enable recovery of declarations and references
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			parser.setUnitName(file.getName());
			
			//setEnvironment sets the environment to be used when no IJavaProjet is available
			String[] classPathEntries = {file.getAbsolutePath()};
			parser.setEnvironment(classPathEntries, null, null, true);
			parser.setSource(fileData.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			
			CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			
			//Create a new object of TypeFinderVisitor class which we have defined
			TypeFinderVisitor v = new TypeFinderVisitor();
			cu.accept(v);
			
			declCount += v.getdeclCount();
			refCount += v.getrefCount();
			
	}
	
	public static String getsearchType() {
		return type;
	}
	
	public static void printCounts(int declCount, int refCount) {
		System.out.print(type + "." + " Declarations found: " + declCount + "; ");
		System.out.println("references found: " + refCount);
	}
	
}
