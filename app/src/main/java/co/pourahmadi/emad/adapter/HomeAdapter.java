package co.pourahmadi.emad.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import co.pourahmadi.emad.Models.AlarmList;
import co.pourahmadi.emad.R;

public class HomeAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {

    private static final int VIEW_NORMAL = 0;
    String TAG = HomeAdapter.class.getSimpleName();
    private List <AlarmList> list = new ArrayList <>();
    private int aks = 1;


    private listener listener;

    public void setData (List <AlarmList> list) {
        try {
            this.list = new ArrayList <>(list);
        } catch (Exception ignored) {
            this.list = new ArrayList <>();
        }
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_NORMAL:
                return new ContactViewNormalHolder(LayoutInflater.
                        from(parent.getContext()).
                        inflate(R.layout.item_reminder, parent, false), listener);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ContactViewNormalHolder) {
            ((ContactViewNormalHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemViewType (int position) {
        return VIEW_NORMAL;
    }

    @Override
    public int getItemCount () {
        return list.size();
    }

    public void clear () {
        list.clear();
        notifyDataSetChanged();
    }

    public void setListener (listener listener) {
        this.listener = listener;
    }

    public void animateTo (List <AlarmList> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals (List <AlarmList> newModels) {
        for (int i = list.size() - 1; i >= 0; i--) {
            final AlarmList model = list.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions (List <AlarmList> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final AlarmList model = newModels.get(i);
            if (!list.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems (List <AlarmList> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final AlarmList model = newModels.get(toPosition);
            final int fromPosition = list.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }

        }
    }

    private void removeItem (int position) {
        final AlarmList model = list.remove(position);
        notifyItemRemoved(position);
    }

    private void addItem (int position, AlarmList model) {
        list.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem (int fromPosition, int toPosition) {
        final AlarmList model = list.remove(fromPosition);
        list.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface listener {
        void itemClick (AlarmList model);

    }

    class ContactViewNormalHolder extends RecyclerView.ViewHolder {

        private final listener listener;
        @BindView(R.id.layoutMain)
        ConstraintLayout layoutMain;
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtDesc)
        TextView txtDesc;

        ContactViewNormalHolder (View v, listener listener) {
            super(v);
            ButterKnife.bind(this, v);
            this.listener = listener;
        }

        @SuppressLint("SetTextI18n")
        void bind (AlarmList model) {
            txtTitle.setText(model.getName());
            txtDesc.setText(model.getPerDate() + " \n " + model.getHour() + " : " + model.getMinute());


            itemView.setOnClickListener(v ->
                    listener.itemClick(model));
            layoutMain.setBackgroundResource(myImageList[getAdapterPosition()]);
        }
    }

    private int[] myImageList = new int[] {
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
            R.color.bg1,
            R.color.bg2,
            R.color.bg3,
            R.color.bg4,
    };
}
