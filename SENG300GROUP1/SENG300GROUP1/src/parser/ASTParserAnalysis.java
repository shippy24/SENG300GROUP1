package parser;

//import greenblocks.util.Util;
//import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ASTParserAnalysis {
	
	public static String[] primitive = {"int", "char", "float", "boolean", "double", "long", "String"}; 
	private static String type;
	private static int declCount = 0;
	private static int refCount = 0;
	private static String abspath;
	
	public static void main(String[] args) throws IOException {
		String path = args[0];
		type = args[1];
		
		abspath = (new File("").getAbsolutePath().concat(path));
		
		//System.out.println(abspath.toString());
		//System.out.println(type);
		
		ParseFilesInDir(abspath);
		printCounts();
	}
	
 
	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return  fileData.toString();	
	}
 
	//loop directory to get file list
	public static void ParseFilesInDir(String path) throws IOException{
		//File dirs = new File(".");
		//String dirPath = dirs.getCanonicalPath() + File.separator+"src"+File.separator;
 
		File root = new File(path);
		//System.out.println(rootDir.listFiles());
		File[] files = root.listFiles();
		String filePath = null;
		
		//System.out.println(path.toString());
		
		TypeFinderVisitor t = new TypeFinderVisitor();
		
		 for (File f : files ) {
			 //filePath = f.getAbsolutePath();
			 if ((f.isFile() && f.getName().endsWith(".java"))) {
				 //System.out.println(f.getName());
				 //System.out.println(readFileToString(f.getAbsolutePath()));
				 t.parse(readFileToString(f.getAbsolutePath()).toCharArray());
			 }
		 }
		 setDeclCount(t.getdeclCount());
		 refCount = t.getrefCount();
	}
	
	//Print output
	public static void printCounts() {
		System.out.print(type + "." + " Declarations found: " + getDeclCount() + "; ");
		System.out.println("references found: " + refCount);
	}
	
	public static String getType() {
		return type;
	}


	
	public static int getDeclCount() {
		return declCount;
	}


	/**
	 * @param declCount the declCount to set
	 */
	public static void setDeclCount(int declCount) {
		ASTParserAnalysis.declCount = declCount;
	}
	
}
