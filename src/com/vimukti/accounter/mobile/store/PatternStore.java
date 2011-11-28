/**
 * 
 */
package com.vimukti.accounter.mobile.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.vimukti.accounter.main.ServerConfiguration;
import com.vimukti.accounter.mobile.AccounterMobileException;
import com.vimukti.accounter.mobile.CommandList;
import com.vimukti.accounter.mobile.PatternResult;
import com.vimukti.accounter.mobile.Result;

/**
 * @author Prasanna Kumar G
 * 
 */
public class PatternStore {
	Logger log = Logger.getLogger(PatternStore.class);

	public static PatternStore INSTANCE = new PatternStore();

	private Map<String, Result> patterns = new HashMap<String, Result>();

	public Result find(String pattern) {
		if (pattern.isEmpty()) {
			return null;
		}
		Result result = patterns.get(pattern.toLowerCase().trim());
		if (result == null) {
			return null;
		}
		Result result2 = new PatternResult();
		result2.addAll(0, result.getResultParts());
		return result2;
	}

	public void reload() throws AccounterMobileException {
		try {
			log.info("Loading Patterns...");
			XStream xStream = getPatternXStream();

			File file = getFile("");

			List<Object> objects = (List<Object>) xStream
					.fromXML(new FileInputStream(file));

			updateMap(objects);

		} catch (Exception e) {
			e.printStackTrace();
			throw new AccounterMobileException(e);
		}
	}

	private XStream getPatternXStream() {
		XStream xStream = new XStream(new DomDriver());

		xStream.alias("patterns", List.class);
		xStream.alias("include", String.class);

		xStream.alias("pattern", Pattern.class);
		xStream.alias("input", String.class);

		xStream.addImplicitCollection(Pattern.class, "inputs");

		xStream.alias("output", Output.class);

		xStream.alias("text", String.class);
		xStream.alias("command", String.class);

		xStream.addImplicitCollection(Output.class, "commands");

		return xStream;

	}

	/**
	 * @param objects
	 * @throws FileNotFoundException
	 */
	private void updateMap(List<Object> objects) throws FileNotFoundException {
		for (Object obj : objects) {
			if (obj instanceof String) {
				XStream xStream = getPatternXStream();
				File include = new File(ServerConfiguration.getMobileStore()
						+ File.separator + (String) obj);
				List<Object> fromXML = (List<Object>) xStream
						.fromXML(new FileInputStream(include));
				updateMap(fromXML);
			} else if (obj instanceof Pattern) {
				Pattern pattern = (Pattern) obj;
				PatternResult result = new PatternResult();
				CommandList commands = new CommandList();
				if (pattern.output != null) {
					List<String> texts = pattern.output.texts;
					if (texts != null) {
						for (String s : texts) {
							result.add(s);
						}
					}
					List<String> strings = pattern.output.commands;
					if (strings != null) {
						for (String s : strings) {
							commands.add(s);
						}
					}
				}
				result.add(commands);
				if (pattern.inputs != null) {
					for (String input : pattern.inputs) {
						patterns.put(input.toLowerCase(), result);
					}
				}
			}
		}
	}

	/**
	 * @param string
	 * @return
	 */
	private File getFile(String language) {
		return new File(ServerConfiguration.getMobileStore() + File.separator
				+ "patterns.xml");
	}

	public static class Pattern {
		List<String> inputs;
		Output output;

		public Pattern() {

		}
	}

	public static class Output {
		List<String> texts = new ArrayList<String>();
		List<String> commands = new ArrayList<String>();

		public Output() {
		}
	}
}
