package com.kpetrov.loftcoin.activity.data;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
abstract class Listings {

    abstract List<AutoValue_Coin> data();
}
