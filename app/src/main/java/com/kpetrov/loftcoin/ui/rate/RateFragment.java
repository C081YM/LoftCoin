package com.kpetrov.loftcoin.ui.rate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.util.ChangeFormatter;
import com.kpetrov.loftcoin.util.PicassoLoaderImages;
import com.kpetrov.loftcoin.util.PriceFormatter;
import com.kpetrov.loftcoin.databinding.FragmentRateBinding;
import timber.log.Timber;

public class RateFragment extends Fragment {

    private FragmentRateBinding binding;
    private RateAdapter adapter;
    private RateViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RateViewModel.class);
        adapter = new RateAdapter(new PriceFormatter(), new ChangeFormatter(), new PicassoLoaderImages());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding = FragmentRateBinding.bind(view);
        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recycler.swapAdapter(adapter, false);
        binding.recycler.setHasFixedSize(true);

        binding.refresher.setOnRefreshListener(() -> {
            viewModel.refresh();
        });

        viewModel.coins().observe(getViewLifecycleOwner(), adapter::submitList);
        viewModel.isRefreshing().observe(getViewLifecycleOwner(), binding.refresher::setRefreshing);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Timber.d("%s", item);

        if (item.getItemId() == R.id.currency_dialog) {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.currency_dialog);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.recycler.swapAdapter(null, false);
        super.onDestroyView();
    }

}