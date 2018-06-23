package com.example.asus.android_hedef_aliskanlik_proje;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.android_hedef_aliskanlik_proje.HedefModelFragment.OnListFragmentInteractionListener;
import com.example.asus.android_hedef_aliskanlik_proje.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHedefModelRecyclerViewAdapter extends RecyclerView.Adapter<MyHedefModelRecyclerViewAdapter.ViewHolder> {
    private final List<HedefModel> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHedefModelRecyclerViewAdapter(List<HedefModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hedefmodel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.ID.setText(mValues.get(position).getID());
        holder.hedef.setText(mValues.get(position).getIsmi());
        holder.saat.setText(mValues.get(position).getSaat());
        holder.tarih.setText(mValues.get(position).getTarih());
        holder.aciklama.setText(mValues.get(position).getAciklama());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public final View mView;
        public final TextView ID;
        public final TextView hedef;
        public final TextView tarih;
        public final TextView saat;
        public final TextView aciklama;
        public HedefModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ID = (TextView) view.findViewById(R.id.hedefID);
            hedef = (TextView) view.findViewById(R.id.hedefisim);
            saat = (TextView) view.findViewById(R.id.hedefsaat);
            tarih = (TextView) view.findViewById(R.id.hedeftarih);
            aciklama = (TextView) view.findViewById(R.id.hedefaciklama);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + aciklama.getText() + "'";
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            HedefDBErisim.get(itemView.getContext().getApplicationContext()).KisiSil(mItem);
            mValues.remove(mItem);
            MyHedefModelRecyclerViewAdapter.super.notifyDataSetChanged();
            return true;
        }
        // uzerinde basili utuldugu zaman sil menusu acilir.
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem x = contextMenu.add("Sil..");
            x.setOnMenuItemClickListener(this);
        }
    }
}