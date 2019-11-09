package legacy.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Streams5 {

	public static void main(String[] args) {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");

		test1(stringCollection);
		test2(stringCollection);
		test3(stringCollection);
		test4(stringCollection);
		test5(stringCollection);
		test6(stringCollection);
		test7(stringCollection);
	}

	// stream has already been operated upon or closed
	private static void test7(List<String> stringCollection) {
		Stream<String> stream = stringCollection.stream().filter(
				s -> s.startsWith("a"));

		//stream.anyMatch(s -> true);
		System.out.println(stream.noneMatch(s -> s.length()==8));
	}

	// short-circuit
	private static void test6(List<String> stringCollection) {
		stringCollection.stream().filter(s -> {
			System.out.print(" filter: " + s);
			return s.startsWith("b");
		}).map(s -> {
			System.out.println(" map:    " + s);
			return s.toUpperCase();
		}).anyMatch(s -> s.startsWith("B"));
		System.out.println();
	}

	private static void test5(List<String> stringCollection) {
		stringCollection.stream().filter(s -> {
			System.out.println("filter:  " + s);
			return s.toLowerCase().startsWith("b");
		}).sorted((s1, s2) -> {
			System.out.printf("sort: %s-%s\n", s1, s2);
			return s1.compareTo(s2);
		}).map(s -> {
			System.out.print("map:     " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println(" forEach: " + s));
		System.out.println();
	}

	// sorted = horizontal
	private static void test4(List<String> stringCollection) {
		stringCollection.stream().sorted((s1, s2) -> {
			System.out.printf(" sort: %s-%s\n", s1, s2);
			return s1.compareTo(s2);
		}).filter(s -> {
			System.out.print(" filter:  " + s);
			return s.toLowerCase().startsWith("a");
		}).map(s -> {
			System.out.print(" map:     " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println(" forEach: " + s));
		System.out.println("\n");
	}

	private static void test3(List<String> stringCollection) {
		stringCollection.stream().filter(s -> {
			System.out.print(" filter:  " + s);
			return s.startsWith("a");
		}).map(s -> {
			System.out.print(" map:     " + s);
			return s.toUpperCase();
		}).forEach(s -> System.out.println(" forEach: " + s));
		System.out.println("\n");
	}

	private static void test2(List<String> stringCollection) {
		stringCollection.stream().map(s -> {
			System.out.print(" map:     " + s);
			return s.toUpperCase();
		}).filter(s -> {
			System.out.print(" filter:  " + s);
			return s.startsWith("A");
		}).forEach(s -> System.out.println(" forEach: " + s));
		System.out.println("\n");
	}

	private static void test1(List<String> stringCollection) {
		stringCollection.stream().filter(s -> {
			System.out.print("filter:  " + s);
			return true;
		}).forEach(s -> System.out.println(" forEach: " + s));
	}

}