package com.nach.core.util.fhir.parser.r4;

import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class FhirR4JsonParser {

	private static FhirContext ctx = FhirContext.forR4();

	/**
	 * Generate a class from a json string.
	 */
	public static <T extends IBaseResource> T parse(String jsonString, Class<T> cls) {
		try {
			IParser parser = ctx.newJsonParser();
			parser.setStripVersionsFromReferences(false);
			ctx.getParserOptions().setStripVersionsFromReferences(false);
			IParser jsonParser = ctx.newJsonParser();
			T rtn = jsonParser.parseResource(cls, jsonString);
			return rtn;
		} catch (Exception exp) {
			throw new RuntimeException(exp);
		}
	}

}
