package com.newitd.oracle.util;

import java.util.Set;

public class CheckProblem {

    static public Double check(Set<Integer> trueAns, Set<Integer> ans, Integer score) {
        if (ans.isEmpty()) {
            return 0.0;
        }
        if (trueAns.equals(ans)) {
            return 1.0 * score;
        }
        for (Integer i : ans) {
            if (!trueAns.contains(i)) {
                return 0.0;
            }
        }
        return score / 2.0;
    }
}
