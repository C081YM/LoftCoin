package com.kpetrov.loftcoin.util;

import androidx.annotation.NonNull;
import com.kpetrov.loftcoin.data.Transaction;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TransactionFormatter implements Formatter<Transaction> {

    @Inject
    TransactionFormatter(){
    }

    @NonNull
    @Override
    public String format(@NonNull Transaction value) {

        final DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("ru", "RU"));

        final DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();

        symbols.setCurrencySymbol(value.coin().symbol());
        format.setDecimalFormatSymbols(symbols);

        return format.format(value.amount());
    }

}
