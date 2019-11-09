package legacy.streams;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams4 {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 1) {
				System.out.println(i);
			}
		}

		IntStream.range(0, 10).forEach(i -> {
			if (i % 2 == 1)
				System.out.println(i);
		});

		IntStream.range(0, 10).filter(i -> i % 2 == 1)
				.forEach(System.out::println);

		OptionalInt reduced1 = IntStream.range(0, 10).reduce((a, b) -> a + b);
		System.out.println(reduced1.getAsInt());

		int reduced2 = IntStream.range(0, 10).reduce(7, (a, b) -> a + b);
		System.out.println(reduced2);

		int[] ints = { 1, 3, 5, 7, 11 };
		Arrays.stream(ints).average().ifPresent(System.out::println);
		IntStream.builder().add(1).add(3).add(5).add(7).add(11).build()
				.average().ifPresent(System.out::println);
		IntStream.range(0, 10).average().ifPresent(System.out::println);
		Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
				.mapToDouble(BigDecimal::doubleValue).average()
				.ifPresent(System.out::println);
	}
}
