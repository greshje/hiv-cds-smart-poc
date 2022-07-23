package com.nachc.tools.smart.basic.util.vocab;

import org.hl7.fhir.r4.model.Questionnaire;

import com.nach.core.util.fhir.parser.r4.FhirR4JsonParser;

public class PopulatePickList {

	public static void exec(String src) {
		Questionnaire quest = FhirR4JsonParser.parse(src, Questionnaire.class);
	}

}
