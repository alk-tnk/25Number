package com.Ichif1205.gridreflexes.ad;

import jp.beyond.sdk.Bead;

/**
 * Created by tanakatatsuya on 2014/07/14.
 */
public class BeadWrapper {

    private static final String BEAD_TAG = "47ec4bc31331a8719a9c803b124a0cbc8b2ba262875ea089";

    public static Bead createInstance() {
        return Bead.createOptionalInstance(BEAD_TAG, 1);
    }
}
