package com.kpetrov.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.kpetrov.loftcoin.BaseComponent;
import com.kpetrov.loftcoin.BuildConfig;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.databinding.FragmentConverterBinding;
import com.kpetrov.loftcoin.util.LoaderImages;
import com.kpetrov.loftcoin.widget.OutlineCircle;

import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

public class ConverterFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final LoaderImages loaderImages;                                                            //

    private final ConverterComponent component;

    private FragmentConverterBinding binding;

    private ConverterViewModel viewModel;

    @Inject
    ConverterFragment(BaseComponent baseComponent, LoaderImages loaderImages) {                             //
        this.loaderImages = loaderImages;                                                                 //

        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment(), component.viewModelFactory())
                .get(ConverterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentConverterBinding.bind(view);

        final NavController navController = NavHostFragment.findNavController(this);

        OutlineCircle.apply(binding.icCurrencyFrom);                                                  //
        OutlineCircle.apply(binding.icCurrencyTo);                                                    //

        disposable.add(viewModel.topCoins().subscribe());

        disposable.add(RxView.clicks(binding.txtCurrencyFrom).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(CoinsSheet.KEY_MODE, CoinsSheet.MODE_FROM);
            navController.navigate(R.id.coins_sheet, args);
        }));

        disposable.add(RxView.clicks(binding.txtCurrencyTo).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(CoinsSheet.KEY_MODE, CoinsSheet.MODE_TO);
            navController.navigate(R.id.coins_sheet, args);
        }));

        disposable.add(viewModel.startCoin().subscribe(
                coin -> {
                    binding.txtCurrencyFrom.setText(coin.symbol());
                    loaderImages.load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                    .into(binding.icCurrencyFrom);
                }
        ));

        disposable.add(viewModel.endCoin().subscribe(
                coin -> {
                    binding.txtCurrencyTo.setText(coin.symbol());
                    loaderImages.load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                    .into(binding.icCurrencyTo);
                }
        ));

        disposable.add(RxTextView.textChanges(binding.txtValueFrom).subscribe(viewModel::startCoinValue));
        disposable.add(RxTextView.textChanges(binding.txtValueTo).subscribe(viewModel::endCoinValue));

        disposable.add(viewModel.startCoinValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.txtValueFrom.setText(text);
                    binding.txtValueFrom.setSelection(text.length());
                }));

        disposable.add(viewModel.endCoinValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.txtValueTo.setText(text);
                    binding.txtValueTo.setSelection(text.length());
                }));
    }

    @Override
    public void onDestroyView() {
        disposable.clear();
        super.onDestroyView();
    }
}