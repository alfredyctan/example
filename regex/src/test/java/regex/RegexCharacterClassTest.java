package regex;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexCharacterClassTest {

	private static final Logger logger = LoggerFactory.getLogger(RegexCharacterClassTest.class);
	
	/*
	 * #or-stroke 
	 */	
	@Test
	public void testOrMatch() {
		String string = "AB and CD";
		Pattern pattern = Pattern.compile("(.*?) (and|or) (.*$)");
		Matcher matcher = pattern.matcher(string);
		assertThat("found", matcher.find(), is(true));
		assertThat("count", matcher.groupCount(), is(3));
		assertThat("group 1", matcher.group(1), is("AB"));
		assertThat("group 2", matcher.group(2), is("and"));
		assertThat("group 3", matcher.group(3), is("CD"));

		String string2 = "AB or CD";
		matcher = pattern.matcher(string2);
		assertThat("found", matcher.find(), is(true));
		assertThat("count", matcher.groupCount(), is(3));
		assertThat("group 1", matcher.group(1), is("AB"));
		assertThat("group 2", matcher.group(2), is("or"));
		assertThat("group 3", matcher.group(3), is("CD"));
	}


	@Test
	public void testCaseInsensitiveWithWhitespaceAhead() {
		//regex case insensitive with whitespace ahead
		Pattern SELECT = Pattern.compile("^\\s*?(?i)select(?-i)");
		
		assertThat(SELECT.matcher(" select").find(), is(true));
		assertThat(SELECT.matcher(" SeLecT").find(), is(true));
		assertThat(SELECT.matcher(" SeLecTed").find(), is(true));
		assertThat(SELECT.matcher(" TeLecT").find(), is(false));
	}
}
