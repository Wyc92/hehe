package com.wangyc.hehe.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe: JoinerSplitters
 * Date: 2021/1/21
 * Time: 4:25 下午
 * Author: wangyc
 */
public class JoinerSplitters {
    static Map<String, Joiner> cacheJoiners = new HashMap<>();
    static Map<String, Joiner> cacheBarelyJoiners = new HashMap<>();
    static Map<String, Splitter> cacheSplitters = new HashMap<>();

    public static Joiner getJoiner(String str) {
        if (!cacheJoiners.containsKey(str)) {
            synchronized (cacheJoiners) {
                if (!cacheJoiners.containsKey(str)) {
                    cacheJoiners.put(str, Joiner.on(str).skipNulls());
                }
            }
        }
        return cacheJoiners.get(str);
    }

    public static Joiner getBarelyJoiner(String str) {
        if (!cacheBarelyJoiners.containsKey(str)) {
            synchronized (cacheBarelyJoiners) {
                if (!cacheBarelyJoiners.containsKey(str)) {
                    cacheBarelyJoiners.put(str, Joiner.on(str));
                }
            }
        }
        return cacheBarelyJoiners.get(str);
    }

    public static Splitter getSplitter(String str) {
        if (!cacheSplitters.containsKey(str)) {
            synchronized (cacheSplitters) {
                if (!cacheSplitters.containsKey(str)) {
                    cacheSplitters.put(str, Splitter.on(str).trimResults().omitEmptyStrings());
                }
            }
        }
        return cacheSplitters.get(str);
    }
}
