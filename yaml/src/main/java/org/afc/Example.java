package org.afc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("example")
public class Example {

	private String ws;

	private String t;

	private String comment;

	private MyList list;

	private MyMap map;

	private DataTypeA dataTypeA;

	@Value("${example.data-type-b.start_date}")
	private String startDate;

	@Value("${example.data-type-b.floatType}")
	private float floatType;

	private String preserve;

	private String fold;

	public String getPreserve() {
		return preserve;
	}

	public void setPreserve(String preserve) {
		this.preserve = preserve;
	}

	public String getFold() {
		return fold;
	}

	public void setFold(String fold) {
		this.fold = fold;
	}

	public float getFloatType() {
		return floatType;
	}

	public void setFloatType(float floatType) {
		this.floatType = floatType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public DataTypeA getDataTypeA() {
		return dataTypeA;
	}

	public void setDataTypeA(DataTypeA dataTypeA) {
		this.dataTypeA = dataTypeA;
	}

	public MyList getList() {
		return list;
	}

	public void setList(MyList list) {
		this.list = list;
	}

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public MyMap getMap() {
		return map;
	}

	public void setMap(MyMap map) {
		this.map = map;
	}

	public static class MyList {

		private List<String> hyphens;

		private List<String> brackets;

		public List<String> getHyphens() {
			return hyphens;
		}

		public void setHyphens(List<String> hyphens) {
			this.hyphens = hyphens;
		}

		public List<String> getBrackets() {
			return brackets;
		}

		public void setBrackets(List<String> brackets) {
			this.brackets = brackets;
		}
	}

	public static class MyMap {

		private Map<String, String> lines;

		private Map<String, String> brackets;

		private Map<List<String>, String> questions;

		public Map<String, String> getLines() {
			return lines;
		}

		public void setLines(Map<String, String> lines) {
			this.lines = lines;
		}

		public Map<String, String> getBrackets() {
			return brackets;
		}

		public void setBrackets(Map<String, String> brackets) {
			this.brackets = brackets;
		}

		public Map<List<String>, String> getQuestions() {
			return questions;
		}

		public void setQuestions(Map<List<String>, String> questions) {
			this.questions = questions;
		}
	}

	public static class DataTypeA {

		private String string1;

		private String string2;

		public String getString1() {
			return string1;
		}

		public void setString1(String string1) {
			this.string1 = string1;
		}

		public String getString2() {
			return string2;
		}

		public void setString2(String string2) {
			this.string2 = string2;
		}
	}
}
