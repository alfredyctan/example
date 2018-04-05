package org.afc;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Example.class, properties = { "spring.config.name=example" })
public class ExampleTest {

	@Autowired
	private Example example;

	@Test
	public void test() {
		System.out.println(example.getMap().getLines());
		System.out.println(example.getMap().getBrackets());
		System.out.println(example.getMap().getQuestions());
		System.out.println(example.getStartDate());
		System.out.println('[' + example.getPreserve() + ']');
		System.out.println('[' + example.getFold() + ']');

		assertThat("white space", example.getWs(), is("whitespace indentation is used to denote structure;"));
		assertThat("tab", example.getT(), is("tab characters are never allowed as indentation."));
		assertThat("comment", example.getComment(), is("")); // can start anywhere on a line and continue until the end of the line.
		assertThat("hyphens", example.getList().getHyphens(), is(contains("hyphen1", "hyphen2", "hyphen3"))); 
		assertThat("brackets", example.getList().getBrackets(), is(contains("bracket", "bracket", "bracket")));
		assertThat("lines", example.getMap().getLines(), allOf(hasEntry("key1", "value1"), hasEntry("key2", "value2"), hasEntry("key3", "value3"))); 
		assertThat("lines", example.getMap().getLines().size(), is(3)); 
		assertThat("brackets", example.getMap().getBrackets(), allOf(hasEntry("key1", "value1"), hasEntry("key2", "value2"), hasEntry("key3", "value3"))); 
		assertThat("brackets", example.getMap().getBrackets().size(), is(3)); 

		assertThat("data-type-a.string1", example.getDataTypeA().getString1(), is("double quote can escape \n")); 
		assertThat("data-type-a.string2", example.getDataTypeA().getString2(), is("single quote no escape"));

		assertThat("${example.data-type-b.start_date}", example.getStartDate(), is("2018-03-29"));
		assertThat("${example.data-type-b.floatType}", example.getFloatType(), is(3.0F));
		
		assertThat("|", example.getPreserve(), is("newlines in the \n\nscalar are preserved\n"));
		assertThat(">", example.getFold(), is("the newlines between two \nnon-empty lines are removed\n"));
	}
}
