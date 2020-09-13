package com.kpetrov.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.auto.value.AutoValue;
import java.util.List;

public interface CoinsRepo {

    @NonNull
    LiveData<List<Coin>> listings(@NonNull Query query);

    @AutoValue
    abstract class Query {

        @NonNull
        public static Builder builder() {
            return new AutoValue_CoinsRepo_Query.Builder()
                    .forceUpdate(true);
        }

        abstract String currency();
        abstract boolean forceUpdate();
        abstract Sorting sorting();

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder currency (String currency);
            public abstract Builder forceUpdate(boolean forceUpdate);
            public abstract Builder sorting(Sorting sorting);
            public abstract Query build();
        }
    }
}