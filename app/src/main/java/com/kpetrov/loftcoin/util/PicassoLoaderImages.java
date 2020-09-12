package com.kpetrov.loftcoin.util;

import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class PicassoLoaderImages implements LoaderImages{

    @Override
    public void load (String url, ImageView view) {
        Picasso.get().load(url).into(view);
    }
}
