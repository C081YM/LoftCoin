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
import androidx.recyclerview.widget.RecyclerView;
import com.kpetrov.loftcoin.BaseComponent;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.databinding.FragmentRateBinding;
import javax.inject.Inject;

public class RateFragment extends Fragment {

    private final RateComponent component;
    private FragmentRateBinding binding;
    private RateAdapter adapter;
    private RateViewModel viewModel;

    @Inject
    RateFragment(BaseComponent baseComponent) {
        component = DaggerRateComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, component.viewModelFactory())
                .get(RateViewModel.class);

        adapter = component.rateAdapter();

        adapter.registerAdapterDataObserver(dataObserver);
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

        if (item.getItemId() == R.id.currency_dialog) {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.currency_dialog);
            return true;
        } else if (item.getItemId() == R.id.sort) {
            viewModel.switchSortingOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.recycler.swapAdapter(null, false);
        adapter.unregisterAdapterDataObserver(dataObserver);
        super.onDestroyView();
    }

    private RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            binding.recycler.scrollToPosition(0);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            binding.recycler.scrollToPosition(0);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            binding.recycler.scrollToPosition(0);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            binding.recycler.scrollToPosition(0);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            binding.recycler.scrollToPosition(0);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            binding.recycler.scrollToPosition(0);
        }
    };
}