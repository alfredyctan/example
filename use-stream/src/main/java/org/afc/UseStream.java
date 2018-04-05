package org.afc;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UseStream {
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		System.out.println("sum " + numbers.stream().parallel().mapToInt(o -> o).sum());
		
		System.out.println("parallel");
		numbers.stream().parallel().mapToInt(o -> o * 3).filter(o -> (o % 2 == 0)).forEach(System.out::println);
		
		System.out.println("non-parallel");
		numbers.stream().mapToInt(o -> o * 3).filter(o -> (o % 2 == 0)).forEach(System.out::println);

		System.out.println("tripleEven list");
		List<Integer> tripleEvens = numbers.stream().parallel().mapToInt(o -> o * 3).filter(o -> (o % 2 == 0)).boxed().collect(Collectors.toList());
		System.out.println(tripleEvens);
	}
}
