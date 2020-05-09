package com.cjbdi.core.extractcenter.extract;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.util.CommonTools;
import com.cjbdi.core.util.SortByLengthComparator;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class CharacterExtract {
	public static Map<String, String> run (String fullText, String justice, Set<String> defendant) {
		Segment segment = HanLP.newSegment().enableNameRecognize(true);
		// 利用hanlp的词法分析提取人名
		List<Term> termList = segment.seg(justice);
		Set<String> nameSet = new HashSet<>();
		for (Term term : termList) {
			if (term.nature.toString().equals("nr")) {
				nameSet.add(term.word);
			}
		}
		nameSet.addAll(defendant);
		List<String> nameListSorted = new ArrayList<>(nameSet);
		Collections.sort(nameListSorted, new SortByLengthComparator());
		return extractCharacter(fullText, nameListSorted);
	}

	public static Map<String, String> extractCharacter(String fullText, List<String> nameSet) {
		Map<String, String> result = new HashMap<>();
		if (StringUtils.isNotEmpty(fullText)) {
			for (String name : nameSet) {
				Map<String, List<String>> characterParty = BeanConfigCenter.extractConfig.getPersonPartyConfig().getCharacterParty();
				boolean flag = false;
				for (String party : characterParty.keySet()) {
					List<String> rules = characterParty.get(party);
					for (String rule : rules) {
						rule = rule.replaceAll("placeholder", name);
						if (CommonTools.ismatch(fullText, rule)) {
							result.put(name, party);
							flag = true;
							fullText = fullText.replaceAll(name, "xxx");
							break;
						}
					}
					if (flag) break;
				}
			}
		}
		return result;
	}
}
