package com.hfad.bitsandpizzas;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ox on 30.07.18.
 */

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    // ViewHolder сообщает с какими данными должен работать адаптер
    // Своего рода ячейка отображаемых данных. Здесь указывается тип данных
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }
    //У RecycleView нет функциональности для установки слушателя событий.
    // Поэтому надо установить OnItemClickListener в нашем адаптере,
    // чтобы была реакция на выбранные элементы Holder в RecycleView.
    // Чтобы адаптер можно было повторно использовать, добавляют интерфейс
    // слушателя Listener (например) и реализацию его определяют вне адаптера
    // В реализации прописывают реакцию на Click выбранного элемента.
    public static interface Listener{
        public void onClick(int position);
    }

    private Listener listener;
    // переменные для хранения информации о пицце
    private String[] captions;
    private int[] imageIds;

    public CaptionedImagesAdapter(String[] captions, int[] imageIds){
        this.captions = captions;
        this.imageIds = imageIds;
    }
    // Установка слушателя, созданного из вне
    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // создание нового представления

        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //super.onBindViewHolder(holder, position, payloads);
        // заполнение заданного представления данными
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);

        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        // Установка слушателя
        // При щелчке на CardView вызвать метод onClick() интерфейса Listener
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // возвращает кол-во вариантов в наборе данных
        return captions.length;
    }
}
