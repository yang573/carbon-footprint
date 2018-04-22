package com.example.yangy.carbonfootprint;

import com.genymobile.mirror.annotation.Class;
import com.genymobile.mirror.annotation.Constructor;

/**
 * Created by yangy on 4/20/2018.
 */

@Class("com.android.internal.os.PowerProfile")
public interface PowerProfile {
    @Constructor
    void callConstructor(MainActivity.this);
}
