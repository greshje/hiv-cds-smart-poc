package com.nachc.tools.smart.basic.util.vocab;

import java.util.List;

import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.ValueSet;

import com.nach.core.util.fhir.parser.r4.FhirR4JsonParser;
import com.nach.core.util.file.FileUtil;
import com.nach.core.util.json.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PopulatePickList {

	public static void exec(String src) {
		Questionnaire quest = FhirR4JsonParser.parse(src, Questionnaire.class);
		List<QuestionnaireItemComponent> items = quest.getItem();
		log.info("Got questionnaire: " + quest.getName());
		for(QuestionnaireItemComponent item : items) {
			replaceAnswerValueSet(item);
		}
		log.info("Done with questionaire:");
		String rtn = FhirR4JsonParser.toJson(quest);
		log.info("\n---------------------------\n" + rtn + "\n---------------------------\n");
	}

	private static void replaceAnswerValueSet(QuestionnaireItemComponent item) {
		String answerValueSetString = item.getAnswerValueSet(); 
		log.info("Got answerValueSet: " + answerValueSetString);
		if(answerValueSetString != null && answerValueSetString.startsWith("https://nachc-cad.github.io/hiv-cds/resources")) {
			ValueSet valueSet = getValueSet(answerValueSetString);
			log.info("Got ValueSet: " + valueSet.getName());
		}
		item.setAnswerValueSet(null);
	}
	
	private static ValueSet getValueSet(String locator) {
		locator = locator.replace("https://nachc-cad.github.io/hiv-cds/resources", "");
		String json = FileUtil.getAsString(locator);
		ValueSet valueSet = FhirR4JsonParser.parse(json, ValueSet.class);
		return valueSet;
	}
	
}




