package com.kpetrov.loftcoin.ui.rate;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.kpetrov.loftcoin.BuildConfig;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.data.Coin;
import com.kpetrov.loftcoin.util.ChangeFormatter;
import com.kpetrov.loftcoin.util.LoaderImages;
import com.kpetrov.loftcoin.widget.OutlineCircle;
import com.kpetrov.loftcoin.databinding.LiRateBinding;
import com.kpetrov.loftcoin.util.PriceFormatter;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

public class RateAdapter extends ListAdapter<Coin, RateAdapter.ViewHolder> {

    private PriceFormatter priceFormatter;
    private ChangeFormatter changeFormatter;
    private LoaderImages loaderImages;
    private LayoutInflater inflater;
    private int colorNegative = Color.RED;
    private int colorPositive = Color.GREEN;
    private int colorBackgroundDark = Color.DKGRAY;
    private int colorBackgroundGray = Color.GRAY;

    @Inject
    RateAdapter(PriceFormatter priceFormatter, ChangeFormatter changeFormatter, LoaderImages loaderImages) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public Object getChangePayload(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return newItem;
            }
        });
        this.priceFormatter = priceFormatter;
        this.changeFormatter = changeFormatter;
        this.loaderImages = loaderImages;
    }


    @NonNull
    @Override
    public RateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiRateBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Coin coin = (Coin) payloads.get(0);
            holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
            holder.binding.change.setText(changeFormatter.format(coin.change24h()));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RateAdapter.ViewHolder holder, int position) {

        final Coin coin = getItem(position);

        holder.binding.symbol.setText(coin.symbol());
        holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
        holder.binding.change.setText(changeFormatter.format(coin.change24h()));

        if (coin.change24h() > 0) {
            holder.binding.change.setTextColor(colorPositive);
        } else {
            holder.binding.change.setTextColor(colorNegative);
        }

        if (position %2 == 0) {
            holder.binding.getRoot().setBackgroundColor(colorBackgroundGray);
        } else {
            holder.binding.getRoot().setBackgroundColor(colorBackgroundDark);
        }

        loaderImages.load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logo);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Context context = recyclerView.getContext();
        inflater = LayoutInflater.from(context);

        TypedValue v = new TypedValue();

        context.getTheme().resolveAttribute(R.attr.textNegative, v, true);
        colorNegative = v.data;
        context.getTheme().resolveAttribute(R.attr.textPositive, v, true);
        colorPositive = v.data;

        context.getTheme().resolveAttribute(R.attr.rateBackgroundDark, v, true);
        colorBackgroundDark = v.data;
        context.getTheme().resolveAttribute(R.attr.rateBackgroundGray, v, true);
        colorBackgroundGray = v.data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LiRateBinding binding;

        ViewHolder(@NonNull LiRateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OutlineCircle.apply(binding.logo);
        }
    }
}