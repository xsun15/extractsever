package com.cjbdi.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.money.CurrencyModel;
import com.cjbdi.core.extractcenter.utils.tracesource.MatchModel;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommonTools {
	public static Segment segment = HanLP.newSegment().enableNameRecognize(true);
	public static List<String> getFileName(String path) {
		List<String> filenameList = new ArrayList<>();
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				filenameList.add(tempList[i].getName());
			}
		}
		return filenameList;
	}

	public static String cutString(String content, String cut) {
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(cut)) return content;
		if (content.contains(cut)) {
			content = content.substring(0, content.indexOf(cut));
		}
		return content;
	}

	public static List<Pattern> object2LP(Object object, Boolean enDecrypt) {
		List<Pattern> patterns = new ArrayList<>();
		PrivateKey privateKey = new PrivateKey();
		if (object != null) {
			List<String> rules = (List) object;
			if (enDecrypt) rules = DecryptRSA.run(rules, privateKey.getRSAKey());
			if (rules!=null) {
				for (String rule : rules) {
					patterns.add(Pattern.compile(rule));
				}
			}
		}
		return patterns;
	}

	public static List<String> object2LS(Object object, Boolean enDecrypt) {
		List<String> patterns = new ArrayList<>();
		PrivateKey privateKey = new PrivateKey();
		if (object != null) {
			List<String> rules = (List) object;
			if (enDecrypt) rules = DecryptRSA.run(rules, privateKey.getRSAKey());
			if (rules!=null) {
				return rules;
			}
		}
		return null;
	}

	public static String object2Str(Object object, Boolean enDecrypt) {
		PrivateKey privateKey = new PrivateKey();
		if (object != null) {
			return (String) object;
		}
		return null;
	}

	public static Map<Pattern, Double> object2MP(Object object, Boolean enDecrypt) {
		Map<Pattern, Double> ratioMap = new HashMap<>();
		PrivateKey privateKey = new PrivateKey();
		if (object != null) {
			List<String> rules = (List) object;
			if (enDecrypt) rules = DecryptRSA.run(rules, privateKey.getRSAKey());
			if (rules!=null) {
				for (String rule : rules) {
					List<String> list = Arrays.asList(rule.split("ratio"));
					ratioMap.put(Pattern.compile(list.get(0)), Double.parseDouble(list.get(1)));
				}
			}
		}
		return ratioMap;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static String matchTextFirstGroup(String content, List<String> ruleList) {
		if (StringUtils.isEmpty(content)) return null;
		if (ruleList!=null) {
			for (String rule : ruleList) {
				Pattern pattern = Pattern.compile(rule);
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					String matchText = matcher.group(1);
					return matchText;
				}
			}
		}
		return null;
	}

	public static String matchTextFirstGroup(String content, String rule) {
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(rule)) return null;
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String matchText = matcher.group(1);
			return matchText;
		}
		return null;
	}

	public static MatchModel matchModelFirstGroup(String content, List<Pattern> positiveRule, List<Pattern> negativeRule) {
		if (StringUtils.isEmpty(content)) return null;
		content = mosicText(content, negativeRule);
		if (positiveRule!=null && StringUtils.isNotEmpty(content)) {
			for (Pattern pattern : positiveRule) {
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					String matchText = matcher.group(1);
					MatchModel matchModel = new MatchModel();
					matchModel.setMatchText(matchText);
					matchModel.setUsedRule(pattern.toString());
					matchModel.setStartPos(content.indexOf(matchText));
					matchModel.setEndPos(content.indexOf(matchText)+matchText.length());
					return matchModel;
				}
			}
		}
		return null;
	}

	public static MatchModel matchModel(String content, List<Pattern> positiveRule, List<Pattern> negativeRule) {
		if (StringUtils.isEmpty(content)) return null;
		content = mosicText(content, negativeRule);
		if (positiveRule!=null && StringUtils.isNotEmpty(content)) {
			for (Pattern pattern : positiveRule) {
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					String matchText = matcher.group();
					MatchModel matchModel = new MatchModel();
					matchModel.setMatchText(matchText);
					matchModel.setUsedRule(pattern.toString());
					matchModel.setStartPos(content.indexOf(matchText));
					matchModel.setEndPos(content.indexOf(matchText)+matchText.length());
					return matchModel;
				}
			}
		}
		return null;
	}

	public static MatchModel matchModel(List<MatchModel> matchModelList, List<Pattern> positiveRule) {
		if (matchModelList.isEmpty() || positiveRule.isEmpty()) return null;
		MatchModel maxMatchModel = null;
		for (MatchModel matchModel : matchModelList) {
			if (StringUtils.isNotEmpty(matchModel.getMatchLongSentence())) {
				for (Pattern pattern : positiveRule) {
					Matcher matcher = pattern.matcher(matchModel.getMatchLongSentence());
					if (matcher.find()) {
						if (maxMatchModel!=null) {
							if (maxMatchModel.getValue() < maxMatchModel.getValue()) maxMatchModel = matchModel;
						} else {
							maxMatchModel = matchModel;
						}
					}
				}
			}
		}
		return maxMatchModel;
	}

	public static List<MatchModel> matchModelAll(String content, JSONObject moneyCurrency) {
		if (StringUtils.isEmpty(content)) return null;
		if (moneyCurrency!=null) {
			List<MatchModel> matchModelList = new ArrayList<>();
			List<String> contentList = Arrays.asList(content.split("\n"));
			for (int i=0; i<contentList.size(); i++) {
				String line = contentList.get(i);
				for (String currency : moneyCurrency.keySet()) {
					double ratio = moneyCurrency.getObject(currency, CurrencyModel.class).getRatio();
					List<Pattern> ruleList = moneyCurrency.getObject(currency, CurrencyModel.class).getRule();
					if (ruleList!=null) {
						for (Pattern pattern : ruleList) {
							Matcher matcher = pattern.matcher(line);
							while (matcher.find()) {
								String matchText = matcher.group();
								MatchModel matchModel = new MatchModel();
								matchModel.setMatchText(matchText);
								matchModel.setUsedRule(pattern.toString());
								matchModel.setStartPos(content.indexOf(matchText));
								matchModel.setEndPos(content.indexOf(matchText) + matchText.length());
								if (StringUtils.isNotEmpty(matchLongSentence(content, matchModel.getStartPos(), matchModel.getEndPos())))
									matchModel.setMatchLongSentence(matchLongSentence(content, matchModel.getStartPos(), matchModel.getEndPos()));
								if (StringUtils.isNotEmpty(matchShortSentence(content, matchModel.getStartPos(), matchModel.getEndPos())))
									matchModel.setMatchShortSentence(matchShortSentence(content, matchModel.getStartPos(), matchModel.getEndPos()));
								if (CN2Num.isNum(cleanMoneyCN(matchText))) {
									matchModel.setValue(CN2Num.cn2Num(cleanMoneyCN(matchText)) * ratio);
								}
								content = content.replaceFirst(matchText, CommonTools.copyStr("x", matchText.length()));
								line = line.replaceFirst(matchText, CommonTools.copyStr("x", matchText.length()));
								matchModelList.add(matchModel);
							}
						}
					}
				}
			}
			return matchModelList;
		}
		return null;
	}

	public static MatchModel matchModel(String content, List<Pattern> positiveRule) {
		if (StringUtils.isEmpty(content)) return null;
		if (positiveRule!=null) {
			for (Pattern pattern : positiveRule) {
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					String matchText = matcher.group();
					MatchModel matchModel = new MatchModel();
					matchModel.setMatchText(matchText);
					matchModel.setUsedRule(pattern.toString());
					matchModel.setStartPos(content.indexOf(matchText));
					matchModel.setEndPos(content.indexOf(matchText)+matchText.length());
					return matchModel;
				}
			}
		}
		return null;
	}

	public static List<MatchModel> matchModelAll(String content, List<Pattern> ruleList) {
		if (StringUtils.isEmpty(content)) return null;
		if (ruleList!=null) {
			List<MatchModel> matchModelList = new ArrayList<>();
			List<String> contentList = Arrays.asList(content.split("\n"));
			for (String line : contentList) {
				for (Pattern pattern : ruleList) {
					Matcher matcher = pattern.matcher(line);
					while (matcher.find()) {
						String matchText = matcher.group();
						MatchModel matchModel = new MatchModel();
						matchModel.setMatchText(matchText);
						matchModel.setUsedRule(pattern.toString());
						matchModel.setStartPos(content.indexOf(matchText));
						matchModel.setEndPos(content.indexOf(matchText) + matchText.length());
						matchModelList.add(matchModel);
						line = line.replaceFirst(matchText, copyStr("x", matchText.length()));
						content = content.replaceFirst(matchText, copyStr("x", matchText.length()));
					}
				}
			}
		}
		return null;
	}

	public static String mosicText(String content, List<Pattern> negativeRule) {
		if (StringUtils.isEmpty(content)) return content;
		if (negativeRule != null) {
			for (Pattern pattern : negativeRule) {
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					String matchText = matcher.group();
					content = content.replaceAll(matchText, copyStr("x", matchText.length()));
				}
			}
			return content;
		}
		return null;
	}

	public static String mosicText(String content, String rule) {
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(rule)) return content;
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			String matchText = matcher.group();
			content = content.replaceAll(matchText, copyStr("x", matchText.length()));
		}
		return content;
	}


	public static boolean isMatch(String content, List<String> ruleList ) {
		if (StringUtils.isEmpty(content)) return false;
		if (ruleList != null) {
			for (String rule : ruleList) {
				Pattern pattern = Pattern.compile(rule);
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isMatchPattern(String content, List<Pattern> ruleList ) {
		if (StringUtils.isEmpty(content)) return false;
		if (ruleList != null) {
			for (Pattern pattern : ruleList) {
				Matcher matcher = pattern.matcher(content);
				if (matcher.find()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isMatch(String content, String rule ) {
		if (StringUtils.isEmpty(content)) return false;
		if (StringUtils.isNotEmpty(rule)) {
			Pattern pattern = Pattern.compile(rule);
			Matcher matcher = pattern.matcher(content);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	public static String copyStr(String str, int num) {
		String result="";
		for (int i=0; i<num;i++) {
			result = result + str;
		}
		return result;
	}

	public static String getCaseId(String caseCause) {
		if (StringUtils.isNotEmpty(caseCause)) {
			for (String caseId : BeanFactoryConfig.casecauseConfig.getCasecause().keySet()) {
				if (BeanFactoryConfig.casecauseConfig.getCasecause().getString(caseId).equals(caseCause)) {
					return caseId;
				}
			}
		}
		return null;
	}

	public static String getNumber(String text) {
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
		Matcher matcher = pattern.matcher(text);
		return matcher.group();
	}

	public static Set<String> getNameSet(String text, Set<String> defendants) {
		List<Term> termList = segment.seg(text);
		Set<String> nameSet = new HashSet<>();
		for (Term term : termList) {
			if (term.nature.toString().equals("nr")) {
				if (!defendants.contains(term.word) && !nameSet.contains(term.word)) {
					nameSet.add(term.word);
				}
			}
		}
		return nameSet;
	}

	public static List<Pattern> replacePlaceHolder(List<Pattern> patternList, String oldWord, String neWord) {
		if (patternList==null) return null;
		List<Pattern> patterns = new ArrayList<>();
		for (Pattern pattern : patternList) {
			String rule = pattern.toString().replaceAll(oldWord, neWord);
			patterns.add(Pattern.compile(rule));
		}
		return patterns;
	}

	public static String deleteDigitDot(String string) {
		string = string.replaceAll(" ", "");
		String regxExp = "\\d[，,]\\d";
		Pattern pattern = Pattern.compile(regxExp);

		String matchedString;
		String matchedString1;
		for(Matcher matcher = pattern.matcher(string); matcher.find(); string = string.replaceAll(matchedString, matchedString1)) {
			matchedString = matcher.group();
			matchedString1 = matchedString.replace("，", "");
			matchedString1 = matchedString1.replace(",", "");
		}
		return string;
	}

	public static String cleanText(String content) {
		if (StringUtils.isNotEmpty(content)) {
			content = content.replaceAll("＊", "某");
			content = content.replaceAll(",", "，");
			String cleanContent = "";
			for (String line : content.split("\n")) {
				String regx = "^[0-9一二三四五六七八九十]{1,2}[^元]";
				Pattern pattern = Pattern.compile(regx);
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					line = line.replaceFirst("\\.", "、");
				}
				line = line.replaceAll("\\.", "。");
				cleanContent += line + "\n";
			}
			cleanContent = cleanContent.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：:*×\\n《》（）Oo〇]+", "");
			cleanContent = cleanContent.replaceAll("\\s+", "");
			cleanContent = deleteDigitDot(cleanContent);
			return cleanContent;
		}
		return content;
	}

	public static String cleanMoneyCN(String content) {
		if (StringUtils.isNotEmpty(content)) {
			content = content.replaceAll("[余多]", "");
		}
		return content;
	}

	public static String matchLongSentence(String content, String target) {
		if (StringUtils.isEmpty(content) || StringUtils.isEmpty(target)) return null;
		List<String> contentList = Arrays.asList(content.split("。|；"));
		for (String longSentence : contentList) {
			if (longSentence.contains(target)) return longSentence;
		}
		return null;
	}

	public static String matchLongSentence(String content, int startPos, int endPos) {
		if (StringUtils.isEmpty(content) || startPos>=endPos || startPos > content.length() || endPos > content.length()) return null;
		List<String> contentList = Arrays.asList(content.split("。|；"));
		for (String longSentence : contentList) {
			if (content.indexOf(longSentence) >= startPos && content.indexOf(longSentence) + longSentence.length() <= endPos)
				return longSentence;
		}
		return null;
	}

	public static String matchShortSentence(String content, int startPos, int endPos) {
		if (StringUtils.isEmpty(content) || startPos>=endPos || startPos > content.length() || endPos > content.length()) return null;
		List<String> contentList = Arrays.asList(content.split("，|、|。|；"));
		for (String longSentence : contentList) {
			if (content.indexOf(longSentence) >= startPos && content.indexOf(longSentence) + longSentence.length() <= endPos)
				return longSentence;
		}
		return null;
	}

	public static List<String> string2List(String string) {
		if (StringUtils.isEmpty(string)) return null;
		string =string.replaceAll("\\[", "").replaceAll("]", "");
		List<String> list = Arrays.asList(string.split(",")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList());
		return list;
	}
}
