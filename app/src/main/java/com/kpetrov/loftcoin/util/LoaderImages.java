package com.kpetrov.loftcoin.util;

import android.widget.ImageView;
import androidx.annotation.NonNull;

public interface LoaderImages {

    @NonNull
    ImageRequest load(String url);

    interface ImageRequest {
        void into(@NonNull ImageView view);
    }
}
