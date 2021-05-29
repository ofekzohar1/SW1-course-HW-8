package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.util.Comparator;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**
 * This class implements a comparator for RankedWord objects
 */
class RankedWordComparator implements Comparator<RankedWord> {
    rankType compType;

    public RankedWordComparator(rankType cType) {
        compType = cType;
    }

    /**
     * Compare 2 RankedWord objects according to a given compType
     * @param o1 First object to compare
     * @param o2 2nd object to compare
     * @return Negative number if o1 < o2, zero if equals, positive if o1 > o2
     */
    @Override
    public int compare(RankedWord o1, RankedWord o2) {
        return Integer.compare(o1.getRankByType(compType), o2.getRankByType(compType));
    }
}
