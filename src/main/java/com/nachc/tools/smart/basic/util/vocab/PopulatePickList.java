package com.nachc.tools.smart.basic.util.vocab;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemAnswerOptionComponent;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.ValueSet;
import org.hl7.fhir.r4.model.ValueSet.ConceptReferenceComponent;
import org.hl7.fhir.r4.model.ValueSet.ConceptSetComponent;

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
		for (QuestionnaireItemComponent item : items) {
			replaceAnswerValueSet(item);
		}
		log.info("Done with questionaire:");
		String rtn = FhirR4JsonParser.toJson(quest);
		log.info("\n---------------------------\n" + rtn + "\n---------------------------\n");
	}

	private static void replaceAnswerValueSet(QuestionnaireItemComponent item) {
		String answerValueSetString = item.getAnswerValueSet();
		log.info("Got answerValueSet: " + answerValueSetString);
		if (answerValueSetString != null && answerValueSetString.startsWith("https://nachc-cad.github.io/hiv-cds/resources")) {
			ValueSet valueSet = getValueSet(answerValueSetString);
			log.info("Got ValueSet: " + valueSet.getName());
			addValueSetItems(item, valueSet);
		}
		item.setAnswerValueSet(null);
	}

	private static ValueSet getValueSet(String locator) {
		locator = locator.replace("https://nachc-cad.github.io/hiv-cds/resources", "");
		String json = FileUtil.getAsString(locator);
		ValueSet valueSet = FhirR4JsonParser.parse(json, ValueSet.class);
		return valueSet;
	}

	private static void addValueSetItems(QuestionnaireItemComponent item, ValueSet valueSet) {
		log.info("Adding valueset items...");
		List<QuestionnaireItemAnswerOptionComponent> answerOptions = new ArrayList<QuestionnaireItemAnswerOptionComponent>();
		item.setAnswerOption(answerOptions);
		List<ConceptSetComponent> values = getValues(valueSet);
		if(values != null) {
			for(ConceptSetComponent value : values) {
				List<ConceptReferenceComponent> concepts = value.getConcept();
				for(ConceptReferenceComponent concept : concepts) {
					CodeableConcept codeableConcept = new CodeableConcept();
					Coding coding = new Coding();
					coding.setSystem(value.getSystem());
					coding.setDisplay(concept.getDisplay());
					coding.setCode(concept.getCode());
					List<Coding> codingList = new ArrayList<Coding>();
					codingList.add(coding);
					codeableConcept.setCoding(codingList);
					QuestionnaireItemAnswerOptionComponent option = new QuestionnaireItemAnswerOptionComponent(coding);
					answerOptions.add(option);
				}
			}
		}
	}

	private static List<ConceptSetComponent> getValues(ValueSet valueSet) {
		try {
			List<ConceptSetComponent> values = valueSet.getCompose().getInclude();
			return values;
		} catch(NullPointerException npe) {
			return null;
		}
		
	}
	
}
