package com.kpetrov.loftcoin.util;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class PicassoLoaderImages implements LoaderImages{

    private final Picasso picasso;

    @Inject
    PicassoLoaderImages(Picasso picasso) {
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ImageRequest load(String url) {
        return new PicassoImageRequest(picasso.load(url));
    }

    private static class PicassoImageRequest implements ImageRequest {

        private final RequestCreator request;

        PicassoImageRequest(RequestCreator request) {
            this.request = request;
        }

        @Override
        public void into(@NonNull ImageView view) {
            request.into(view);
        }
    }
}