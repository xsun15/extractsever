package com.cjbdi.core.developcenter.good;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.List;

import static com.hankcs.hanlp.HanLP.segment;

public class ExtractGood {
	public static String run (String text) {
		String keyword = "价值,价格,元";
		if (text!=null&&!text.isEmpty()) {
			Segment segment = HanLP.newSegment();
			segment.enableNameRecognize(true);
			segment.enableCustomDictionary(true);
			segment.enableAllNamedEntityRecognize(true);
			segment.enablePlaceRecognize(true);//使能地点识别
			segment.enableOffset(true);
			List<Term> termList = segment(text);
			List<String> saveWord = new ArrayList<>();
			boolean flag = false;
			String result = "";
			for (int i=termList.size()-1; i>=0; i--) {
				Term term = termList.get(i);
				if (keyword.contains(term.word)) {
					flag = true;
					int count = 0;
					for (String word : saveWord) {
						result += word + "\t";
						count++;
						if (count>=3) break;
					}
					saveWord.clear();
				}
				if (flag && term.nature.toString().equals("n") ) {
					saveWord.add(term.word);
				}
			}
			return result;
		}
		return "";
	}
}
