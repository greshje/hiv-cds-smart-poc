package com.nachc.tools.smart.basic.util.vocab;

import java.util.List;

import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;

import com.nach.core.util.fhir.parser.r4.FhirR4JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PopulatePickList {

	public static void exec(String src) {
		Questionnaire quest = FhirR4JsonParser.parse(src, Questionnaire.class);
		List<QuestionnaireItemComponent> items = quest.getItem();
		log.info("Got questionnaire: " + quest.getName());
		for(QuestionnaireItemComponent item : items) {
			log.info("\tGot item: " + item.getAnswerValueSet());
		}
	}

}
