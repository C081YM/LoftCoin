package com.kpetrov.loftcoin.activity.ui.wallets;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.databinding.LiWalletBinding;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.ViewHolder> {

    private static final int [] WALLETS = {R.layout.li_wallet,R.layout.li_wallet,R.layout.li_wallet};

    private LayoutInflater layoutInflater;

    @Override
    public int getItemCount() {
        return WALLETS.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LiWalletBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        layoutInflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull LiWalletBinding binding) {
            super(binding.getRoot());

        }
    }
}
