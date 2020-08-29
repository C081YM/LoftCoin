package com.kpetrov.loftcoin.activity.ui.welcome;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kpetrov.loftcoin.R;
import com.kpetrov.loftcoin.databinding.WelcomePageBinding;

public class WelcomeAdapter extends RecyclerView.Adapter<WelcomeAdapter.ViewHolder> {

    private static final int[] IMAGES = {R.drawable.asset1, R.drawable.asset2, R.drawable.asset3,};

    private static final int[] TITLES = {R.string.welcome_page_1_title, R.string.welcome_page_2_title, R.string.welcome_page_3_title,};

    private static final int[] SUBTITLES = {R.string.welcome_page_1_subtitle, R.string.welcome_page_2_subtitle, R.string.welcome_page_3_subtitle,};

    private LayoutInflater inflater;

    @Override
    public int getItemCount () {
        return IMAGES.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(WelcomePageBinding.inflate(inflater,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.image.setImageResource(IMAGES[position]);
        holder.binding.title.setText(TITLES[position]);
        holder.binding.subtitle.setText(SUBTITLES[position]);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final WelcomePageBinding binding;
        ViewHolder(WelcomePageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
