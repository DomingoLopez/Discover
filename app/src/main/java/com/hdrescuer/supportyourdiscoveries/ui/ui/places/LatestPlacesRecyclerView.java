package com.hdrescuer.supportyourdiscoveries.ui.ui.places;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.ListItemClickListener;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.NewPlaceDialogFragment;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.ScreenSlidePageFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LatestPlacesRecyclerView extends RecyclerView.Adapter<LatestPlacesRecyclerView.ViewHolder> {

    private List<PlaceEntity> mValues;
    private Context ctx;
    final private ListItemClickListener mOnClickListener;
    final private DateFormat dateFormat;

    FragmentActivity fa;

    public LatestPlacesRecyclerView(Context contexto,FragmentActivity fa, List<PlaceEntity> items, ListItemClickListener onClickListener) {
        this.fa = fa;
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



            holder.viewPager.setAdapter(new ScreenSlidePagerAdapter(fa,holder.mItem.getPhoto_paths()));
            holder.title.setText(holder.mItem.getTitle());
            holder.address.setText(holder.mItem.getAddress());
            holder.score.setText(String.format("%.1f", holder.mItem.getRating()));
            holder.author_usename.setText(holder.mItem.getAuthor_name());
            holder.ratingBar.setRating(holder.mItem.getRating());
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

        public TextView title;
        public TextView address;
        public TextView score;
        public Button btn_place_details;
        public PlaceEntity mItem;
        public ImageView author_photo;
        public TextView author_usename;
        //ViewPager
        ViewPager2 viewPager;

        //Rating Bar
        RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            this.title = view.findViewById(R.id.latest_places_item_title);
            this.address = view.findViewById(R.id.latest_places_item_address);
            this.score = view.findViewById(R.id.tv_score);
            this.viewPager = view.findViewById(R.id.viewPagerLatest);
            this.author_photo = view.findViewById(R.id.author_phot);
            this.author_usename = view.findViewById(R.id.author_username);
            this.ratingBar = view.findViewById(R.id.ratingBarPlace);

            this.btn_place_details = view.findViewById(R.id.btn_go_place_details);
            this.btn_place_details.setOnClickListener(this);

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString()/* + " '" + user_name.getText() + "'"*/;
        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.btn_go_place_details){
                int position = getAdapterPosition();
                mOnClickListener.onListItemClick(position, "");
            }

        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        int num_img;
        ArrayList<String> paths_to_img;

        public ScreenSlidePagerAdapter(FragmentActivity fa, ArrayList<String> img_paths) {
            super(fa);
            this.paths_to_img = new ArrayList<>();
            if(img_paths.size() != 0) {
                this.num_img = img_paths.size();
            }else{
                this.num_img = 1;
            }

            this.paths_to_img = img_paths;

        }

        @Override
        public Fragment createFragment(int position) {

            String path = "";
            if(this.paths_to_img.size() == 0){
                path = "default";
            }else{
                path = this.paths_to_img.get(position);
            }

            return new ScreenSlidePageFragment(path);
        }

        @Override
        public int getItemCount() {
            return num_img;
        }
    }


}