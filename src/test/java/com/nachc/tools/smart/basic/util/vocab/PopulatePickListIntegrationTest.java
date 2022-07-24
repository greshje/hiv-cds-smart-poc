package com.nachc.tools.smart.basic.util.vocab;

import org.junit.Test;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PopulatePickListIntegrationTest {

	private static final String QUEST = "/questionnaire/questionnaire-NACHCA0.json";

	@Test
	public void shouldDoReplacements() {
		log.info("Starting test...");
		String quest = FileUtil.getAsString(QUEST);
		String rtn = PopulatePickList.exec(quest);
		log.info("\n---------------------------\n" + rtn + "\n---------------------------\n");
		log.info("Done.");
	}

}
