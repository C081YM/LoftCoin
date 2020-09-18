package com.kpetrov.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kpetrov.loftcoin.data.Wallet;
import com.kpetrov.loftcoin.databinding.LiWalletBinding;
import com.kpetrov.loftcoin.util.PriceFormatter;

import java.util.Objects;

import javax.inject.Inject;

class WalletsAdapter extends ListAdapter<Wallet, WalletsAdapter.ViewHolder> {

    private final PriceFormatter priceFormatter;

    private LayoutInflater inflater;

    @Inject
    WalletsAdapter(PriceFormatter priceFormatter) {
        super(new DiffUtil.ItemCallback<Wallet>() {
            @Override
            public boolean areItemsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiWalletBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wallet wallet = getItem(position);
        holder.binding.txtCardCurrency.setText(wallet.coin().symbol());
        holder.binding.txtCardMain.setText(priceFormatter.format(wallet.balance()));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LiWalletBinding binding;

        ViewHolder(@NonNull LiWalletBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }
    }
}