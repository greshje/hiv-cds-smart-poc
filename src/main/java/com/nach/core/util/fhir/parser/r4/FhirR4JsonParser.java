package com.nach.core.util.fhir.parser.r4;

import org.hl7.fhir.instance.model.api.IBaseResource;

import com.nach.core.util.json.JsonUtil;

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
	
	public static String toJson(IBaseResource obj) {
		IParser parser = ctx.newJsonParser();
		String rtn = parser.encodeResourceToString(obj);
		rtn = JsonUtil.prettyPrint(rtn);
		return rtn;
	}

}
