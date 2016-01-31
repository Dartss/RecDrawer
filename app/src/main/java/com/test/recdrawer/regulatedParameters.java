package com.test.recdrawer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gorih on 17.11.15.
 */
public class regulatedParameters implements Parcelable {
    float ratio;
    float size;
    int depth;

    public regulatedParameters(float ratioValue, float sizeValue, int recursionDepth) {
        ratio = ratioValue;
        size = sizeValue;
        depth = recursionDepth;
    }

    public regulatedParameters(Parcel in) {
        ratio = in.readFloat();
        size = in.readFloat();
        depth = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(ratio);
        dest.writeFloat(size);
        dest.writeInt(depth);
    }

    public static final Parcelable.Creator<regulatedParameters> CREATOR =
            new Parcelable.Creator<regulatedParameters>() {
        public regulatedParameters createFromParcel(Parcel in) {
            return new regulatedParameters(in);
        }

        public regulatedParameters[] newArray(int size) {
            return new regulatedParameters[size];
        }
    };


}
