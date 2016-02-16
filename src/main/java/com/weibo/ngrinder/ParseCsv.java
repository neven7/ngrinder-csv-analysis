package com.weibo.ngrinder;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

/**
 * 解析csv数据
 * 
 * @author hugang
 *
 */
public class ParseCsv {

	private static CellProcessor[] getProcessors() {

		// final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an
		// example, not very robust!
		// StrRegEx.registerMessage(emailRegex,
		// "must be a valid email address");

		// 定义javabean字段类型
		final CellProcessor[] processors = new CellProcessor[] {
				// new UniqueHashCode(), // customerNo (must be unique)
				// new NotNull(), // firstName
				// new NotNull(), // lastName
				// new ParseDate("dd/MM/yyyy"), // birthDate
				// new NotNull(), // mailingAddress
				// new Optional(new ParseBool()), // married
				// new Optional(new ParseInt()), // numberOfKids
				// new NotNull(), // favouriteQuote
				// new StrRegEx(emailRegex), // email
				// new LMinMax(0L, LMinMax.MAX_LONG) // loyaltyPoints
				new NotNull(), new ParseDouble(), new ParseDouble(),
				new ParseDouble(), new ParseDouble(), new ParseDouble(),
				new ParseDouble(), new ParseDouble(), new ParseDouble(),
				new ParseDouble(), new ParseDouble(), new ParseDouble(),
				new ParseDouble() };

		return processors;
	}

	// 原始文件
	final static String CSV_FILENAME = "src/main/resources/output.csv";
	// 将(ms)去掉
	final static String CSV_FORMAT_FILENAME = "src/main/resources/outputformat.csv";

	// 将原始csv文件中，Mean_Test_Time_(ms),Test_Time_Standard_Deviation_(ms)
	// 改成Mean_Test_Time，Test_Time_Standard_Deviation, 因为解析csv是根据javabean，
	// 带()无法定义变量
	private static void formatCsv() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(CSV_FILENAME));
		PrintStream ps = new PrintStream(new FileOutputStream(
				CSV_FORMAT_FILENAME));
		try {
			String buffer = null;
			String firstStr = null;
			int index = 0;
			while ((buffer = br.readLine()) != null) {
				// 只改第一行
				if (0 == index) {
					firstStr = buffer.replace("Mean_Test_Time_(ms)",
							"Mean_Test_Time");
					firstStr = firstStr.replace(
							"Test_Time_Standard_Deviation_(ms)",
							"Test_Time_Standard_Deviation");
					ps.println(firstStr);
				} else {
					index++;
					ps.println(buffer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
			ps.close();
		}

	}

	private static void readWithCsvBeanReader() throws Exception {

		ICsvBeanReader beanReader = null;
		try {
			beanReader = new CsvBeanReader(new FileReader(CSV_FORMAT_FILENAME),
					CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			final CellProcessor[] processors = getProcessors();

			// tps list
			List<Double> tps = new ArrayList<Double>();
			// rt list
			List<Double> meanTestTime = new ArrayList<Double>();
			// javabean 对象
			NgrinderOutput output;
			int i = 0;
			int j = 0;
			while ((output = beanReader.read(NgrinderOutput.class, header,
					processors)) != null) {
				// System.out.println(String.format("lineNo=%s, rowNo=%s, output=%s",
				// beanReader.getLineNumber(),
				// beanReader.getRowNumber(), output));
				// 无法使用数组，因为不知道个数，不能正常初始化数组大小，数组元素个数要精确
				tps.add(output.getTPS());
				meanTestTime.add(output.getMean_Test_Time());
			}

			// list转成数组， 标准库使用数组作为参数
			double[] tpsArray = new double[tps.size()];
			for (double tpsNum : tps) {
				tpsArray[i++] = tpsNum;
			}

			// list转成数组
			double[] meanTestTimeArray = new double[meanTestTime.size()];
			for (double meanTime : meanTestTime) {
				meanTestTimeArray[j++] = meanTime;
			}

			// tps 标准差
			double tpsStd = new StandardDeviation().evaluate(tpsArray);
			// tps 平均值
			double tpsMean = new Mean().evaluate(tpsArray, 0, tpsArray.length);

			System.out.println(String.format("TPS平均值：%.2f", tpsMean));
			System.out.println(String.format("TPS标准差：%.2f", tpsStd));
			System.out.println(String.format("TPS波动率：%.2f%%",
					((tpsStd / tpsMean) * 100)));

			// meanTestTime 百分位数
			Percentile percentile = new Percentile();
			// 先排序
			Arrays.sort(meanTestTimeArray);
			// meanTestTime最小值
			double MinMeanTime = meanTestTimeArray[0];
			double TwentyFiveMeanTime = percentile.evaluate(meanTestTimeArray,
					25);
			double FiftyMeanTime = percentile.evaluate(meanTestTimeArray, 50);
			double ServentyFiveMeanTime = percentile.evaluate(
					meanTestTimeArray, 75);
			double EightyMeanTime = percentile.evaluate(meanTestTimeArray, 80);
			double EightyFiveMeanTime = percentile.evaluate(meanTestTimeArray,
					85);
			double NinetyMeanTime = percentile.evaluate(meanTestTimeArray, 90);
			double NinetyFiveMeanTime = percentile.evaluate(meanTestTimeArray,
					95);
			double NinetyNineMeanTime = percentile.evaluate(meanTestTimeArray,
					99);

			int length = meanTestTimeArray.length;
			// meanTestTime最高值
			double MaxMeanTime = meanTestTimeArray[length - 1];
			// meanTestTime平均值
			double TimeMean = new Mean().evaluate(meanTestTimeArray, 0,
					meanTestTimeArray.length);

			// 格式化输出
			System.out.println(String.format("RT平均响应时间：%.2f ms", TimeMean));
			System.out.println(String.format("Min RT：%.2f ms", MinMeanTime));
			System.out.println(String.format("RT 25百分位数：%.2f ms",
					TwentyFiveMeanTime));
			System.out.println(String
					.format("RT 50百分位数：%.2f ms", FiftyMeanTime));
			System.out.println(String.format("RT 75百分位数：%.2f ms",
					ServentyFiveMeanTime));
			System.out.println(String.format("RT 80百分位数：%.2f ms",
					EightyMeanTime));
			System.out.println(String.format("RT 85百分位数：%.2f ms",
					EightyFiveMeanTime));
			System.out.println(String.format("RT 90百分位数：%.2f ms",
					NinetyMeanTime));
			System.out.println(String.format("RT 95百分位数：%.2f ms",
					NinetyFiveMeanTime));
			System.out.println(String.format("RT 99百分位数：%.2f ms",
					NinetyNineMeanTime));
			System.out.println(String.format("Max RT：%.2f ms", MaxMeanTime));

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
	}

	public static void main(String[] args) {
		try {
			ParseCsv.formatCsv();
			ParseCsv.readWithCsvBeanReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
