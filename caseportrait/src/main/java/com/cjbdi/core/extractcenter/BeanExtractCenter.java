package com.cjbdi.core.extractcenter;

import com.cjbdi.core.extractcenter.extract.DefendantExtract;
import com.cjbdi.core.extractcenter.split.*;

public class BeanExtractCenter {
	public static FirstTrialSplit firstTrialSplit;
	public static IndicitmentSplit indicitmentSplit;
	public static IndictOpinionSplit indictOpinionSplit;
	public static NoprosSplit noprosSplit;
	public static TrialReportSplit trialReportSplit;
	public static DefendantExtract defendantExtract;

	public static void init() {
		firstTrialSplit = new FirstTrialSplit();
		indicitmentSplit = new IndicitmentSplit();
		indictOpinionSplit = new IndictOpinionSplit();
		noprosSplit = new NoprosSplit();
		trialReportSplit = new TrialReportSplit();
		defendantExtract = new DefendantExtract();
	}
}
