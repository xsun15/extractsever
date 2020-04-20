package com.cjbdi.core.extractcenter.extract;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefendantExtract {
    private List<String> defendantPattern = BeanConfigCenter.extractConfig.getPersonPartyConfig().getPersonParty().getDefendant();

    public Set<String> extract(String content) {
        String[] contentList = content.split("\n");
        LinkedHashSet<String> nameSet = new LinkedHashSet<>();
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        for (String line : contentList) {
            boolean flag = false;
            for (String rule : defendantPattern) {
                Pattern pattern = Pattern.compile(rule);
                Matcher matcherCase = pattern.matcher(line);
                if (matcherCase.find()) {
                    String target = matcherCase.group(1);
                    // 利用hanlp的词法分析提取人名
                    List<Term> termList = segment.seg(target);
                    for (Term term : termList) {
                        if (term.nature.toString().equals("nr")) {
                            nameSet.add(term.word);
                            flag = true;
                            break;
                        }
                    }
                    // 利用正则提取人名
                    if (!flag) {
                        String name = matcherCase.group(1);
                        if (name.length() >= 2 && name.length() <= 5) {
                            nameSet.add(name);
                        }
                    }
                }
                if (flag) break;
            }
        }
        nameSet = cleanDefendant(nameSet);
        return nameSet;
    }

    public static LinkedHashSet<String> cleanDefendant(LinkedHashSet<String> nameSet) {
        for (String name : nameSet) {
            if (name == null || name.length() < 2) {
                nameSet.remove(name);
            }
        }
        return nameSet;
    }
}
