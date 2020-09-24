package com.kpetrov.loftcoin.ui.currency;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kpetrov.loftcoin.BaseComponent;
import com.kpetrov.loftcoin.databinding.DialogCurrencyBinding;
import com.kpetrov.loftcoin.widget.OnItemClick;
import javax.inject.Inject;

public class CurrencyDialog extends AppCompatDialogFragment {

    private final CurrencyComponent component;
    private CurrencyViewModel viewModel;
    private DialogCurrencyBinding binding;
    private CurrencyAdapter adapter;
    private OnItemClick onItemClick;

    @Inject
    CurrencyDialog(BaseComponent baseComponent) {
        component = DaggerCurrencyComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, component.viewModelFactory())
                .get(CurrencyViewModel.class);
        adapter = new CurrencyAdapter();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogCurrencyBinding.inflate(requireActivity().getLayoutInflater());
        return new MaterialAlertDialogBuilder(requireActivity())
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recycler.setAdapter(adapter);
        viewModel.allCurrencies().observe(this, adapter::submitList);
        onItemClick = new OnItemClick((v) -> {
            final RecyclerView.ViewHolder viewHolder = binding.recycler.findContainingViewHolder(v);
            if (viewHolder != null) {
                viewModel.updateCurrency(adapter.getItem(viewHolder.getAdapterPosition()));
            }
            dismissAllowingStateLoss();
        });
        binding.recycler.addOnItemTouchListener(onItemClick);
    }

    @Override
    public void onDestroy() {
        binding.recycler.removeOnItemTouchListener(onItemClick);
        binding.recycler.setAdapter(null);
        super.onDestroy();
    }
}