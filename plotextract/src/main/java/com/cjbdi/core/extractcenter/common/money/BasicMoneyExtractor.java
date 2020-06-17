package com.cjbdi.core.extractcenter.common.money;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.extractfeature.money.ExtractMoneyModel;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.extractcenter.utils.tracesource.MatchModel;
import com.cjbdi.core.utils.CommonTools;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class BasicMoneyExtractor {

	//如果金额个数大于等于三个，且最大金额为其他金额的排列组合，则认为最大金额为全文总结性金额
	public boolean combineEffectMoney(List<MatchModel> allMoney, Label label) {
		if(allMoney != null && allMoney.size() >= 3) {
			List<MatchModel> effectMoney = selectMatchModel(allMoney, "有效");
			MatchModel maxMoney = maxMatchModel(effectMoney);
			if(maxMoney != null) {
				double sum = sumMatchModel(selectMatchModel(allMoney, maxMoney));
				if(Math.abs(maxMoney.getValue() - sum) < 0.01) {
					if (selectMatchModel(allMoney, maxMoney).isEmpty()) {
						for (MatchModel matchModel : selectMatchModel(allMoney, maxMoney)) {
							matchModel.setType("无效");
						}
					}
					label.setValue(String.valueOf(sum));
					label.setMatchModelList(allMoney);
					return true;
				}
			}
		}
		return false;
	}

	// 得到指定类型的money
	public List<MatchModel> selectMatchModel(List<MatchModel> allMoney, String moneyType) {
		if(allMoney!=null) {
			List<MatchModel> matchModelList = new ArrayList();
			for (MatchModel matchModel : matchModelList) {
				if (StringUtils.isNotEmpty(matchModel.getType()) && matchModel.getType().equals(moneyType)) {
					matchModelList.add(matchModel);
				}
			}
			return matchModelList;
		}
		return null;
	}

	// 得到无效可转换金额
	public List<MatchModel> selectConvertMatchModel(List<MatchModel> allMoney) {
		if(allMoney!=null) {
			List<MatchModel> matchModelList = new ArrayList();
			for (MatchModel matchModel : matchModelList) {
				if (StringUtils.isNotEmpty(matchModel.getType()) && matchModel.getType().equals("无效") && matchModel.getIsConvert()) {
					matchModelList.add(matchModel);
				}
			}
			return matchModelList;
		}
		return null;
	}

	// 得到最大金额money
	public MatchModel maxMatchModel (List<MatchModel> allMoney) {
		if(allMoney!=null&&!allMoney.isEmpty()) {
			MatchModel maxMoney = allMoney.get(0);
			for (MatchModel matchModel : allMoney) {
				if (matchModel.getValue() > maxMoney.getValue()) {
					maxMoney = matchModel;
				}
			}
			return maxMoney;
		}
		return null;
	}

	// 得到总计金额
	public double sumMatchModel (List<MatchModel> allMoney) {
		double sum = 0.0;
		if(allMoney!=null&&!allMoney.isEmpty()) {
			for (MatchModel matchModel : allMoney) {
				sum += matchModel.getValue();
			}
		}
		return sum;
	}

	// 排除targetMatchModel
	public List<MatchModel> selectMatchModel (List<MatchModel> allMoney, MatchModel targetMatchModel) {
		if(allMoney!=null&&!allMoney.isEmpty()) {
			List<MatchModel> matchModelList = new ArrayList<>();
			for (MatchModel matchModel : allMoney) {
				if (targetMatchModel.equals(matchModel)) matchModelList.add(matchModel);
			}
		}
		return allMoney;
	}

	// 得到指定段落金额
	public static List selectFixParaIndex(List<MatchModel> allMoney, int index, String moneyType) {
		if (!allMoney.isEmpty()) {
			List<MatchModel> matchModelList = new ArrayList();
			for (MatchModel matchModel : allMoney) {
				if (matchModel.getLineIndex()==index && StringUtils.isNotEmpty(matchModel.getType())&& matchModel.getType().equals(moneyType)) {
					matchModelList.add(matchModel);
				}
			}
			return  matchModelList;
		}
		return null;
	}

	// 判断每个犯罪事实的总结性金额是部分总结还是全部总计，并去掉总计已包括的分项金额
	public double resetEffectInvalid(String content, List<MatchModel> effectMoney, JSONObject caseMoney) {
		double sum = 0.0;
		List<Pattern> ruleList = new ArrayList<>();
		if (caseMoney.containsKey("positivePureRule")) {
			if (!caseMoney.getJSONObject("positivePureRule").isEmpty()) {
				if (caseMoney.getJSONObject("positivePureRule").containsKey("1th"))
					ruleList.addAll(caseMoney.getJSONObject("positivePureRule").getObject("1th", ExtractMoneyModel.class).getRule());
				if (caseMoney.getJSONObject("positivePureRule").containsKey("2th"))
					ruleList.addAll(caseMoney.getJSONObject("positivePureRule").getObject("2th", ExtractMoneyModel.class).getRule());
			}
		}
		MatchModel totalMoney = CommonTools.matchModel(effectMoney, ruleList);
		if(totalMoney == null) {
			sum += sumMatchModel(effectMoney);
		} else if(this.isTotalMoney(totalMoney, effectMoney)) {
			sum += totalMoney.getValue();
			resetMatchModelType(selectMatchModel(effectMoney, totalMoney), "无效");
		} else {
			String sentence = totalMoney.getMatchShortSentence();
			Segment segment = HanLP.newSegment();
			List<Term> termList = segment.seg(sentence);
			String connectWord = "";
			// 抽取shortSentence的中的连词，并按连词分割shortSentence
			for (Term term : termList){
				if(term.nature.toString().equals("cc") || term.nature.toString().equals("c")) {
					connectWord = connectWord + term.word + "|";
				}
			}
			if(StringUtils.isNotEmpty(connectWord)) {
				connectWord = connectWord.substring(0, connectWord.length() - 1);
			}
			List<String> shortSentenceList = new ArrayList();
			if(StringUtils.isNotEmpty(connectWord)) {
				shortSentenceList = Arrays.asList(sentence.split(connectWord));
			}
			if(shortSentenceList.isEmpty()) {
				shortSentenceList.add(sentence);
			}
			// 根据shortSentence中出现的描述，判断总计金额是不是前面所有金额和
			boolean flagFront = false;
			for (String fragment : shortSentenceList) {
				if(StringUtils.isNotEmpty(fragment) && fragment.contains(totalMoney.getMatchText()) && !fragment.contains("款物") &&
						!fragment.contains("财物") && (!fragment.contains("现金") || !fragment.contains("物品")) && (!fragment.contains("赃款") || !fragment.contains("赃物"))) {
					// 再次判断是不是含有物品名词
					String moneyConfig = "n or nz";
					termList = segment.seg(fragment);
					for (Term term : termList) {
						if(moneyConfig.contains(term.nature.toString()) && !CommonTools.isMatch(term.word, "(价值|人民币)")) {
							flagFront = true;
						}
					}
				}
			}
			// 判断总结性金额的后面金额是不是有效金额
			boolean flagTail = true;
			if(content.lastIndexOf("其中") > totalMoney.getStartPos()) {
				flagTail = false;
			}
			// 重新定义判定金额有效、无效性
			for (MatchModel matchModel : effectMoney) {
				if(matchModel.getStartPos() == totalMoney.getStartPos()) {
					sum += totalMoney.getValue();
				} else if(flagFront && matchModel.getStartPos() < totalMoney.getStartPos()) {
					sum += matchModel.getValue();
				} else if(flagTail && matchModel.getStartPos() > totalMoney.getStartPos()) {
					sum += matchModel.getValue();
				} else {
					matchModel.setType("无效");
				}
			}
		}
		return sum;
	}

	// 判断金额是不是总金额
	public boolean isTotalMoney(MatchModel totalMoney, List<MatchModel> matchModelList) {
		if (totalMoney!=null&&matchModelList!=null) {
			if (Math.abs(totalMoney.getValue() - sumMatchModel(matchModelList)) < 0.01) {
				return true;
			}
		}
		return false;
	}

	// 重置金额有效、无效
	public void resetMatchModelType(List<MatchModel> matchModelList, String type) {
		if (!matchModelList.isEmpty()) {
			for (MatchModel matchModel : matchModelList) {
				matchModel.setType(type);
			}
		}
	}

	//溯源偏置
	public List<MatchModel> addPosOffset(List<MatchModel> matchModelList, int offset) {
		List<MatchModel> matchModels = new ArrayList<>();
		if(matchModelList!= null) {
			for(MatchModel matchModel : matchModelList) {
				matchModel.setStartPos(matchModel.getStartPos()+offset);
				matchModel.setEndPos(matchModel.getEndPos()+offset);
				matchModels.add(matchModel);
			}
			return matchModels;
		}
		return null;
	}

	// 抽取金额
	public static List<MatchModel> extractMoney(String text, JSONObject moneyCurrency) {
		List<MatchModel> matchModelList= new ArrayList();
		if(StringUtils.isNotEmpty(text)) {
			matchModelList = CommonTools.matchModelAll(text, moneyCurrency);
		}
		return matchModelList;
	}

	// 判断是不是只有一个有效金额或者只有一个可转换的无效金额
	public boolean isOneEffectMoney(List<MatchModel> allMoney, Label label) {
		if (allMoney!=null) {
			int count = 0;
			MatchModel effectMatchModel =  null;
			for (MatchModel matchModel : allMoney) {
				if (StringUtils.isNotEmpty(matchModel.getType())&&matchModel.getType().equalsIgnoreCase("有效")) {
					effectMatchModel = matchModel;
					count++;
				}
			}
			if (count==1) {
				label.addMatchModel(effectMatchModel);
				return true;
			}
			if (count==0) {
				for (MatchModel matchModel : allMoney) {
					if (StringUtils.isNotEmpty(matchModel.getType())&&matchModel.getType().equalsIgnoreCase("无效") && matchModel.getIsConvert()) {
						count++;
					}
				}
				if (count==1) {
					label.addMatchModel(effectMatchModel);
					return true;
				}
			}
		}
		return false;
	}

	// 从经审理查明的body段抽取金额
	public Label extractMoneyFromFactBody(List<MatchModel> allMoney, String factBody, JSONObject caseMoney) {
		Label label = new Label();
		if (isOneEffectMoney(allMoney, label)) {  //排除无效金额只有一个有效金额或只有一个可转换为有效金额的无效金额
			return label;
		} else if(combineEffectMoney(allMoney, label)) { // 当金额个数大于等于3时，如果最大金额为其他金额的排列组合，则认为最大金额为需要提取的金额
			return label;
		} else { // 对经审理查明段的每个犯罪事实中的金额进行有效、无效再判断（主要解决含有总计描述时，金额认定问题）
			double sum = 0.0D;
			List<String> factBodyList = Arrays.asList(factBody.split("\n"));
			for(int i = 0; i < factBodyList.size(); ++i) {
				List<MatchModel> effectMoneyConfigList = selectFixParaIndex(allMoney, i, "有效");
				List<MatchModel> invalidMoneyConfigList = selectFixParaIndex(allMoney, i, "无效");
				if(!effectMoneyConfigList.isEmpty()) {
					if (effectMoneyConfigList.size()==1) {
						sum += sumMatchModel(effectMoneyConfigList);
					} else {
						sum += resetEffectInvalid(factBody, effectMoneyConfigList, caseMoney);
					}
				} else {
					sum += sumMatchModel(selectConvertMatchModel(invalidMoneyConfigList));
					resetMatchModelType(selectConvertMatchModel(invalidMoneyConfigList), "有效");
				}
			}
			return null;
		}
	}

}
