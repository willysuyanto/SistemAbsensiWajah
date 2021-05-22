package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.detection.database.PersonEntity;

import java.util.List;

public class AttendantAdapter extends RecyclerView.Adapter<AttendantAdapter.ViewHolder> {

    private static final String TAG = AttendantAdapter.class.getSimpleName();

    private Context context;
    private List<PersonEntity> list;
    private OnDeleteListener onDeleteListener;

    public AttendantAdapter(List<PersonEntity> list, OnDeleteListener onDeleteListener) {
        this.context = context;
        this.list = list;
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendant_item,
                parent, false);
        return new ViewHolder(view, onDeleteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonEntity item = list.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(item.getImgUrl());
        Log.d(TAG, "onBindViewHolder: "+item.getImgUrl());
        holder.tvNama.setText(item.getNama());
        holder.tvNip.setText(item.getNip());
        holder.imageView.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        int size = this.list.size();
        this.list.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvNip;
        ImageButton btnDelete;
        ImageView imageView;
        OnDeleteListener onDeleteListener;

        public ViewHolder(View itemView, OnDeleteListener onDeleteListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_profile);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNip = itemView.findViewById(R.id.tv_idNum);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            this.onDeleteListener = onDeleteListener;
            btnDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onDeleteListener.onDelete(getAdapterPosition());
        }
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }
}