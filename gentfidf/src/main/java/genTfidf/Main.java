package genTfidf;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;

public class Main {
	public static void main(String [] args) {
		Segment segment = HanLP.newSegment().enableNameRecognize(true);
	}
}
