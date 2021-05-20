package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.common.ListItemClickListener;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyPlacesRecyclerView extends RecyclerView.Adapter<MyPlacesRecyclerView.ViewHolder> {

    private List<PlaceEntity> mValues;
    private Context ctx;
    final private ListItemClickListener mOnClickListener;
    final private DateFormat dateFormat;

    public MyPlacesRecyclerView(Context contexto, List<PlaceEntity> items, ListItemClickListener onClickListener) {
        this.ctx = contexto;
        this.mValues = items;
        this.mOnClickListener = onClickListener;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place, parent, false);
        return new ViewHolder(view,mOnClickListener) ;
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

            holder.place_title.setText(holder.mItem.title);

            ArrayList<String> img_paths = holder.mItem.getPhoto_paths();
            String path_principal = img_paths.get(0);

            Glide.with(holder.itemView.getContext())
                    .load(path_principal)
                    .centerCrop()
                    .into(holder.place_img);


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

        public ImageView place_img;
        public TextView place_title;
        public TextView place_address;
        public PlaceEntity mItem;

        private WeakReference<ListItemClickListener> listenerRef;

        ImageView edit_place;
        ImageView delete_place;

        public ViewHolder(View view,ListItemClickListener listener) {
            super(view);
            mView = view;

            listenerRef = new WeakReference<>(listener);


            this.place_img = view.findViewById(R.id.my_place_img);
            this.place_title = view.findViewById(R.id.my_place_title);

            this.edit_place = view.findViewById(R.id.btn_edit_place);
            this.edit_place.setOnClickListener(this);
            this.delete_place = view.findViewById(R.id.btn_delete_place);
            this.delete_place.setOnClickListener(this);




            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + place_title.getText() + "'";
        }

        @Override
        public void onClick(View v) {

            String action = "";

            switch (v.getId()){
                case R.id.btn_edit_place:
                    action = "EDIT";
                    break;

                case R.id.btn_delete_place:
                    action = "DELETE";
                    break;

                default:
                    action = "SHOW";

                    break;
            }

            listenerRef.get().onListItemClick(getAdapterPosition(), action);

        }
    }
}