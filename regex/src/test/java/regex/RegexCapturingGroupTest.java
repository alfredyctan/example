package regex;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexCapturingGroupTest {

	private static final Logger logger = LoggerFactory.getLogger(RegexCapturingGroupTest.class);
	
	/*
	 * #log-pattern, #non-greedy, #named-capture 
	 */
	@Test
	public void testNamedCaptureOfLog() {
		logger.info("running test of [{}]", getClass().getName());
		
		String log = "2018-04-13 23:22:15.083 [main][INFO ][regex.RegexTest] : running test of [regex.RegexTest]";
		Pattern pattern = Pattern.compile("(?<datetime>^.*?) \\[(?<thread>.*?)\\]\\[(?<severity>.*?)\\]\\[(?<class>.*?)\\] : (?<msg>.*$)");
		Matcher matcher = pattern.matcher(log);
		assertThat("found", matcher.find(), is(true));
		assertThat("group count", matcher.group("datetime"), is("2018-04-13 23:22:15.083"));
		assertThat("datetime",    matcher.group("thread"), is("main"));
		assertThat("thread",      matcher.group("severity"), is("INFO "));
		assertThat("serverity",   matcher.group("class"), is("regex.RegexTest"));
		assertThat("class",       matcher.group("msg"), is("running test of [regex.RegexTest]"));
	}

	/*
	 * #index-capture, #index-replace 
	 */
	@Test
	public void testIndexCaptureAndReplace() {
		String log = "2018-04-13 23:22:15.083 [main][INFO ][regex.RegexTest] : running test of [regex.RegexTest]";
		String replaced = log.replaceAll("(?<datetime>^.*?) \\[(?<thread>.*?)\\]\\[(?<severity>.*?)\\]\\[(?<class>.*?)\\] : (?<msg>.*$)", "$1 [$4]-[$3]-[$2] - $5");
		assertThat("index replace", replaced, is("2018-04-13 23:22:15.083 [regex.RegexTest]-[INFO ]-[main] - running test of [regex.RegexTest]"));
	}

	/*
	 * #named-capture, #named-replace 
	 */
	@Test
	public void testNamedCaptureAndReplace() {
		String log = "2018-04-13 23:22:15.083 [main][INFO ][regex.RegexTest] : running test of [regex.RegexTest]";
		String replaced = log.replaceAll("(?<datetime>^.*?) \\[(?<thread>.*?)\\]\\[(?<severity>.*?)\\]\\[(?<class>.*?)\\] : (?<msg>.*$)", "${datetime} [${class}]-[${severity}]-[${thread}] - ${msg}");
		assertThat("named replace", replaced, is("2018-04-13 23:22:15.083 [regex.RegexTest]-[INFO ]-[main] - running test of [regex.RegexTest]"));
	}

	/*
	 * #nested-capture, #group-capture 
	 */	
	@Test
	public void testNestedCapture() {
		String string = "AB and CD";
		Pattern pattern = Pattern.compile("((.*?) and (.*$))");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("count", matcher.groupCount(), is(3));
		assertThat("group 1", matcher.group(1), is("AB and CD"));
		assertThat("group 2", matcher.group(2), is("AB"));
		assertThat("group 3", matcher.group(3), is("CD"));
	}
	 
	/*
	 * #lookbehind 
	 */	
	@Test
	public void testPositiveLookBehind() {
		String string = "USD100/EUR80/780HKD/12000JPY";
		Pattern pattern = Pattern.compile("(?<=USD)\\d+");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("100"));


		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("(?<=USD)\\d+?");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("1"));

		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("(?<=USD)\\d*");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("100"));
	}

	/*
	 * #lookahead
	 */	
	@Test
	public void testPositiveLookAhead() {
		String string = "USD100/EUR80/780HKD/12000JPY";
		Pattern pattern = Pattern.compile("\\d+(?=HKD)");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("780"));

		// matched (?=\\d+HKD) 1st, then match \\d{2} inside the match
		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("(?=\\d+HKD)\\d{2}");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("78"));

		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("\\d+(?=HKD)\\\\d{2}");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(false));
	}

	/*
	 * #lookbehind 
	 */	
	@Test
	public void testNegativeLookBehind() {
		// (?<!USD) matched SD1
		String string = "USD100/EUR80/780HKD/12000JPY";
		Pattern pattern = Pattern.compile("(?<!USD)\\d{2}");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("00"));


		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("(?<!USD)\\d+?");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("0"));

		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("(?<!USD)\\d+");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("00"));
	}

	/*
	 * #lookahead
	 */	
	@Test
	public void testNegativeLookAhead() {
		
		// (?!HKD) matched /EU
		String string = "USD100/EUR80/780HKD/12000JPY";
		Pattern pattern = Pattern.compile("\\d+(?!HKD)");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("100"));


		// (?!HKD) matched 00/
		string = "USD100/EUR80/780HKD/12000JPY";
		pattern = Pattern.compile("\\d+?(?!HKD)");
		matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("match", string.substring(matcher.start(), matcher.end()), is("1"));

	}
	
	@Test
	public void testCaptureNameValuePair() {
		String string = "USD=100,EUR=80,780=HKD,12000=JPY";
		Pattern pattern = Pattern.compile("(?<=,|^)(.*?=.*?)(?=,|$)");
		Matcher matcher = pattern.matcher(string);
		
		assertThat("found", matcher.find(), is(true));
		assertThat("group", matcher.groupCount(), is(1));
		assertThat("group 1", matcher.group(1), is("USD=100"));

		assertThat("found", matcher.find(), is(true));
		assertThat("group", matcher.groupCount(), is(1));
		assertThat("group 1", matcher.group(1), is("EUR=80"));

		assertThat("found", matcher.find(), is(true));
		assertThat("group", matcher.groupCount(), is(1));
		assertThat("group 1", matcher.group(1), is("780=HKD"));

		assertThat("found", matcher.find(), is(true));
		assertThat("group", matcher.groupCount(), is(1));
		assertThat("group 1", matcher.group(1), is("12000=JPY"));
	}

}
