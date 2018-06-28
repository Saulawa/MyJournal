package com.saulawa.anas.myjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";

    final private ItemClickListener mItemClickListener;
    private List<JournalEntryModel> mEntries;
    private Context context;
    private JournalEntryModel journalEntryModel;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public EntryAdapter(Context context, ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).
                inflate(R.layout.previewitemslayout, parent, false);

        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        journalEntryModel = mEntries.get(position);
        String title = journalEntryModel.getTitle();
        String description = journalEntryModel.getDescription();
        byte[] img = journalEntryModel.get_icon();
        String updatedAt = dateFormat.format(journalEntryModel.getUpdatedAt());


        holder.itemimg.setImageBitmap(converttobitma(img));
        holder.itemtitle.setText(title);
        holder.itemdescription.setText(description);
        holder.itemdate.setText(updatedAt);

    }

    @Override
    public int getItemCount() {

        if (mEntries == null) {
            return 0;
        }

        return mEntries.size();
    }

    private Bitmap converttobitma(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public void setEntries(List<JournalEntryModel> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    public List<JournalEntryModel> getEntries() {
        return mEntries;
    }


    public interface ItemClickListener {
        void onItemClickListener(String title, String description, byte[] icon, Date date);

        void onMenuAction(int p, MenuItem item);

    }

    class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {

        TextView itemtitle;
        TextView itemdescription;
        TextView itemdate;
        ImageView itemimg;

        public EntryViewHolder(View itemView) {
            super(itemView);

            itemtitle = itemView.findViewById(R.id.itemtitle);
            itemdescription = itemView.findViewById(R.id.itemdescription);
            itemimg = itemView.findViewById(R.id.icondisplay);
            itemdate = itemView.findViewById(R.id.datepreview);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {

//            int entryId = mEntries.get(getAdapterPosition()).getId();
            String title = mEntries.get(getAdapterPosition()).getTitle();
            String desctiption = mEntries.get(getAdapterPosition()).getDescription();
            byte[] icon = mEntries.get(getAdapterPosition()).get_icon();
           Date updatedAt = mEntries.get(getAdapterPosition()).getUpdatedAt();

            mItemClickListener.onItemClickListener(title, desctiption, icon, updatedAt);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (mItemClickListener != null) {
                int position = getAdapterPosition();
                mItemClickListener.onMenuAction(position, item);
            }
            return false;
        }
    }


}
