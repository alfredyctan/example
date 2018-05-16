package regex;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexQuantifierTest {

	private static final Logger logger = LoggerFactory.getLogger(RegexQuantifierTest.class);

	/*
	 * #zero-or-more, *
	 */
	@Test
	public void testQuantifiersZeroOrMore() {
		String string = "A";
		Pattern pattern = Pattern.compile("AB*");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
	}

	/*
	 * #zero-or-one, #non-greedy?
	 */
	@Test
	public void testQuantifiersZeroOrOne() {
		String string = "A";
		Pattern pattern = Pattern.compile("AB?");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("A"));

		string = "AB";
		pattern = Pattern.compile("AB?");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("AB"));

		string = "ABB";
		pattern = Pattern.compile("AB?");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("AB"));
	}
	
	/*
	 * #one-or-more, +
	 */
	@Test
	public void testQuantifiersOneOrMore() {
		String string = "AB and CD";
		Pattern pattern = Pattern.compile("AB+");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
			
		string = "BA and CD";
		pattern = Pattern.compile("AB+");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(false));

		string = "BA and CD";
		pattern = Pattern.compile("B+");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
	}

	/*
	 * #exact7ly, {n}
	 */
	@Test
	public void testQuantifiersExactly() {
		String string = "ABABAB and CD";
		Pattern pattern = Pattern.compile("AB{3}");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(false));

		string = "BABBBA and CD";
		pattern = Pattern.compile("AB{3}");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));

		string = "BABABA and CD";
		pattern = Pattern.compile("AB{3}");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(false));
	}

	/*
	 * #between, {m,n}
	 */
	@Test
	public void testQuantifiersBetween() {
		String string = "ABBAB and CD";
		Pattern pattern = Pattern.compile("AB{2,3}");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));

		string = "BABBBA and CD";
		pattern = Pattern.compile("AB{2,3}");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));

		string = "BABABA and CD";
		pattern = Pattern.compile("AB{2,3}");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(false));
	}
}
