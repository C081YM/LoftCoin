package com.kpetrov.loftcoin.ui.wallets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.data.Transaction;
import com.kpetrov.loftcoin.databinding.LiTransactionBinding;
import com.kpetrov.loftcoin.util.PriceFormatter;
import com.kpetrov.loftcoin.util.TransactionFormatter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javax.inject.Inject;

public class TransactionsAdapter extends ListAdapter<Transaction, TransactionsAdapter.ViewHolder> {

    private final TransactionFormatter transactionFormatter;

    private final PriceFormatter priceFormatter;

    private LayoutInflater inflater;

    private int colorPositive = Color.GREEN;

    private int colorNegative = Color.RED;



    @Inject
    TransactionsAdapter(PriceFormatter priceFormatter, TransactionFormatter transactionFormatter) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
        this.transactionFormatter = transactionFormatter;
    }

    @NonNull
    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionsAdapter.ViewHolder(LiTransactionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.ViewHolder holder, int position) {
        final Transaction transaction = getItem(position);

        holder.binding.txtRateTop.setText(transactionFormatter.format(transaction));

        final double fiatAmount = transaction.amount() * transaction.coin().price();
        holder.binding.txtRateBottom.setText(priceFormatter.format(transaction.coin().currencyCode(), fiatAmount));

        @SuppressLint("SimpleDateFormat")
        Format dateFormat = new SimpleDateFormat("dd MMM yyyy");
        holder.binding.txtDate.setText(dateFormat.format(transaction.createdAt()).toUpperCase());

        if (transaction.amount() > 0) {
            holder.binding.txtRateBottom.setTextColor(colorPositive);
            holder.binding.icRateUp.setVisibility(View.VISIBLE);
            holder.binding.icRateDown.setVisibility(View.GONE);

        }
        else {
            holder.binding.txtRateBottom.setTextColor(colorNegative);
            holder.binding.icRateDown.setVisibility(View.VISIBLE);
            holder.binding.icRateUp.setVisibility(View.GONE);

        }
    }



    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());

        Context context = recyclerView.getContext();

        TypedValue typedValue = new TypedValue();

        context.getTheme().resolveAttribute(R.attr.textPositive, typedValue, true);
        colorPositive = typedValue.data;
        context.getTheme().resolveAttribute(R.attr.textNegative, typedValue, true);
        colorNegative = typedValue.data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiTransactionBinding binding;

        ViewHolder(@NonNull LiTransactionBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }

    }

}