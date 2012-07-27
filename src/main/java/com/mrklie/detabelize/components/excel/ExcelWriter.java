package com.mrklie.detabelize.components.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.mrklie.detabelize.util.ResultTable;

/**
 * This class wraps a function to write down statistics given by a ResultTable
 * as an Excel file.
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class ExcelWriter {

	/**
	 * Saves the result table given to an excel file.
	 * @param r The result table holding the statistic to write.
	 * @param f The file to write to.
	 * @throws FileNotFoundException If one cannot write to the file given.
	 */
	@SuppressWarnings("unused")
	public void write(ResultTable r, File f) throws FileNotFoundException {
		FileOutputStream out = new FileOutputStream(f);
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet();
	}

}
