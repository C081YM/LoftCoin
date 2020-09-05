package com.kpetrov.loftcoin.ui.currency;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.data.CurrencyRepo;
import com.kpetrov.loftcoin.data.CurrencyRepoImpl;
import com.kpetrov.loftcoin.databinding.DialogCurrencyBinding;

public class CurrencyDialog extends AppCompatDialogFragment {

    private DialogCurrencyBinding binding;

    private CurrencyRepo currencyRepo;

    private CurrencyAdapter adapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencyRepo = new CurrencyRepoImpl(requireContext());
        adapter = new CurrencyAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogCurrencyBinding.inflate(requireActivity().getLayoutInflater());
        return new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.rate_menu_currency)
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.dialogCurrencyRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.dialogCurrencyRecycler.setAdapter(adapter);
        currencyRepo.availableCurrencies().observe(this, adapter::submitList);

    }

    @Override
    public void onDestroy() {
        binding.dialogCurrencyRecycler.setAdapter(null);
        super.onDestroy();
    }
}