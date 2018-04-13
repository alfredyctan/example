package regex;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexTest {

	private static final Logger logger = LoggerFactory.getLogger(RegexTest.class);
	
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

	@Test
	public void testIndexCaptureAndReplace() {
		String log = "2018-04-13 23:22:15.083 [main][INFO ][regex.RegexTest] : running test of [regex.RegexTest]";
		String replaced = log.replaceAll("(?<datetime>^.*?) \\[(?<thread>.*?)\\]\\[(?<severity>.*?)\\]\\[(?<class>.*?)\\] : (?<msg>.*$)", "$1 [$4]-[$3]-[$2] - $5");
		assertThat("index replace", replaced, is("2018-04-13 23:22:15.083 [regex.RegexTest]-[INFO ]-[main] - running test of [regex.RegexTest]"));
	}

	@Test
	public void testNamedCaptureAndReplace() {
		String log = "2018-04-13 23:22:15.083 [main][INFO ][regex.RegexTest] : running test of [regex.RegexTest]";
		String replaced = log.replaceAll("(?<datetime>^.*?) \\[(?<thread>.*?)\\]\\[(?<severity>.*?)\\]\\[(?<class>.*?)\\] : (?<msg>.*$)", "${datetime} [${class}]-[${severity}]-[${thread}] - ${msg}");
		assertThat("index replace", replaced, is("2018-04-13 23:22:15.083 [regex.RegexTest]-[INFO ]-[main] - running test of [regex.RegexTest]"));
	}

}
