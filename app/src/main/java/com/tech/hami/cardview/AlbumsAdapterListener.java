package com.tech.hami.cardview;

import android.widget.ImageView;

interface AlbumsAdapterListener {
    void onAddToFavoriteSelected(int position);

    void onPlayNextSelected(int position);

    void onCardSelected(int position, ImageView thumbnail);
}
