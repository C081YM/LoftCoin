package com.kpetrov.loftcoin.util;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UtilModule {

    @Binds
    abstract LoaderImages loaderImages(PicassoLoaderImages impl);

    @Binds
    abstract RxSchedulers schedulers(RxSchedulersImpl impl);
}
