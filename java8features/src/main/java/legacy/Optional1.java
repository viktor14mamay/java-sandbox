package legacy;

import java.util.Optional;

public class Optional1 {

	static class Outer {
		Nested nested;
	}

	static class Nested {
		Inner inner;
	}

	static class Inner {
		String foo;
	}

	public static void main(String[] args) {
		Optional.of(new Outer()).flatMap(o -> Optional.ofNullable(o.nested))
				.flatMap(n -> Optional.ofNullable(n.inner))
				.flatMap(i -> Optional.ofNullable(i.foo))
				.ifPresent(System.out::println);

		Optional<String> optional = Optional.of("bam");
		optional.isPresent(); // true
		optional.get(); // "bam"
		optional.orElse("fallback"); // "bam"
		optional.ifPresent((s) -> System.out.println(s.charAt(0))); // "b"
	}
}
