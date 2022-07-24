package com.nachc.tools.smart.basic.util.vocab;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriteQuestionnairesToFiles {

	private static final String DIR = "/hiv-cds/resources/questionnaire";
	
	private static final String OUT = "_ETC/output/questionnaire";
	
	@Test
	public void shouldWriteFiles() {
		log.info("Starting test...");
		File dir = FileUtil.getFile(DIR);
		List<File> fileList = FileUtil.list(dir);
		log.info("Got " + fileList.size() + " files");
		for(File file : fileList) {
			String fileName = file.getName();
			log.info("\t" + fileName);
		}
		log.info("Writing files...");
		File outDir = FileUtil.getFromProjectRoot(OUT);
		for(File file : fileList) {
			String fileName = file.getName();			
			File out = new File(outDir, fileName);
			String src = FileUtil.getAsString(file);
			String json = PopulatePickList.exec(src);
			FileUtil.write(json, out);
			log.info("\t" + FileUtil.getCanonicalPath(out));
		}
		List<File> outFileList = FileUtil.list(outDir);
		log.info("Wrote " + outFileList.size() + " files");
		log.info("Out File Dir: " + FileUtil.getCanonicalPath(outDir));
		log.info("Done.");
	}
	
}
