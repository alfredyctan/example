package regex;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexArithTest {


	@Test
	public void arthExpr() {
		Pattern PATTERN = Pattern.compile("\\\"([^\\\"]*)\\\"|(?:[=\\+\\*/]|^)(\\w*)");

		System.out.println("num1");
		Matcher matcher = PATTERN.matcher("num1");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		System.out.println("=========================");

		System.out.println("num2=num3+num4-num3*num4/num3");
		matcher = PATTERN.matcher("num2=num3+num4-num3*num4/num3");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}

		System.out.println("=========================");
		System.out.println("\"num5\"");
		matcher = PATTERN.matcher("\"num5\"");
		while (matcher.find()) {
			System.out.println(matcher.group());
		}

	}
}
