package com.sehamalharbi.javaparser.solutions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class ResolveMethodCalls {

	private static final String FILE_PATH = "src/main/java/com/sehamalharbi/javaparser/samples/ListLinks.java";

	/**
	 * A helper method to get the maximum value of a map
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<V>> V maxValue(Map<K, V> map) {
		Optional<Entry<K, V>> maxEntry = map.entrySet().stream()
				.max((Entry<K, V> e1, Entry<K, V> e2) -> e1.getValue().compareTo(e2.getValue()));

		return maxEntry.get().getValue();
	}

	/**
	 * A helper method to get the minimum value of a map
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map is the methodCallsFrequencyCounter <String , Integer>
	 * @return
	 */
	public static <K, V extends Comparable<V>> V minValue(Map<K, V> map) {
		Optional<Entry<K, V>> minEntry = map.entrySet().stream()
				.min((Entry<K, V> e1, Entry<K, V> e2) -> e1.getValue().compareTo(e2.getValue()));

		return minEntry.get().getValue();
	}

	/**
	 * A helper method to get all keys of the maximum value in a map <key,value>
	 * 
	 * @param map is the methodCallsFrequencyCounter <String , Integer>
	 */
	public static void getAllKeysWithMaxValue(Map<String, Integer> map) {

		ArrayList<String> keys = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue().equals(maxValue(map))) {
				keys.add(entry.getKey() + "()");
			}
		}

		System.out.println("Most Used Call: " + keys);
	}

	/**
	 * A helper method to get all keys of the minimum value in a map <key,value>
	 * 
	 * @param map is the methodCallsFrequencyCounter <String , Integer>
	 */
	public static void getAllKeysWithMinValue(Map<String, Integer> map) {

		ArrayList<String> keys = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue().equals(minValue(map))) {
				keys.add(entry.getKey() + "()");
			}
		}

		System.out.println("Less Used Call: " + keys);
	}

	public static void main(String[] args) throws Exception {

		// Using a combined types since not all method calls are Jsoup related
		CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
		TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
		TypeSolver jarTypeSolver = new JarTypeSolver("jars/jsoup-1.11.2.jar");

		combinedSolver.add(reflectionTypeSolver);
		combinedSolver.add(jarTypeSolver);

		// JavaSymbolsolver is a part of the JavaParser library but with extra features
		// such as relations and references between AST nodes.
		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
		ArrayList<String> methodCallsNamesList = new ArrayList<String>();

		// String is the method name, Integer is its frequency
		Map<String, Integer> methodCallsFrequencyCounter = new HashMap<String, Integer>();

		StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
		CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));

		// Filter all method calls to only include Jsoup API calls
		cu.findAll(MethodCallExpr.class).forEach(mce -> {
			if (mce.resolve().getPackageName().contains("org.jsoup")) {
				methodCallsNamesList.add(mce.resolve().getName());

			}
		});

		// Put the method calls and their number of occurrence in a map
		for (String name : methodCallsNamesList) {
			Integer j = methodCallsFrequencyCounter.get(name);
			methodCallsFrequencyCounter.put(name, (j == null) ? 1 : j + 1);
		}

		System.out.println("[Jsoup Libraby Calls]\n");
		// Displaying the occurrence of elements in the ArrayList
		for (Map.Entry<String, Integer> val : methodCallsFrequencyCounter.entrySet()) {
			System.out
					.println("Method Call: " + val.getKey() + "()  ------>  " + "Occurs: " + val.getValue() + " times");
		}

		System.out.println();

		// Most and less used API calls
		getAllKeysWithMaxValue(methodCallsFrequencyCounter);
		getAllKeysWithMinValue(methodCallsFrequencyCounter);

	}
}
