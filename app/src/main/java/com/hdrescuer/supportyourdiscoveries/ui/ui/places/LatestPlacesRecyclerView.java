package com.hdrescuer.supportyourdiscoveries.ui.ui.places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.ListItemClickListener;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.NewPlaceDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class LatestPlacesRecyclerView extends RecyclerView.Adapter<LatestPlacesRecyclerView.ViewHolder> {

    private List<PlaceEntity> mValues;
    private Context ctx;
    final private ListItemClickListener mOnClickListener;
    final private DateFormat dateFormat;

    public LatestPlacesRecyclerView(Context contexto, List<PlaceEntity> items, ListItemClickListener onClickListener) {
        this.ctx = contexto;
        this.mValues = items;
        this.mOnClickListener = onClickListener;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_latest_places_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Método que se llama para cada item de la lista, para "bindearlo" con la interfaz definiendo cada campo
     * @author Domingo Lopez
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);

           // holder.user_name.setText(holder.mItem.getUsername() + " " +holder.mItem.getLastname());

           /* if(holder.mItem.getTimestamp_ini() != null)
                holder.last_monitoring.setText(dateFormat.format(holder.mItem.getTimestamp_ini()));
            else
                holder.last_monitoring.setText("- -");

            if(holder.mItem.getTotal_time() != null)
                holder.sesion_duration.setText(SyncStateContract.Constants.getHMS(holder.mItem.getTotal_time()));
            else
                holder.sesion_duration.setText("- -");*/
        }
    }


    public void setData(List<PlaceEntity> placeEntities){
        this.mValues = placeEntities;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {

        if(this.mValues != null)
            return mValues.size();
        else
            return 0;

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;

        public  TextView title;
        public PlaceEntity mItem;
        //ViewPager
        ViewPager2 viewPager;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            this.title = view.findViewById(R.id.latest_places_item_title);
            this.viewPager = view.findViewById(R.id.viewPagerLatest);

            this.viewPager.setAdapter(new NewPlaceDialogFragment.ScreenSlidePagerAdapter(this.getActivity(),this.img_paths));

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString()/* + " '" + user_name.getText() + "'"*/;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position, "");
        }
    }
}