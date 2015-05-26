package com.chuckbjones.kittenweather;

import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PicassoKittenService implements KittenService {

//    private static final String URL = "http://placekitten.com/g/%d/%d";
    private static final String URL = "http://lorempixel.com/g/%d/%d/cats";

    @Override
    public void GetKitten(ImageView iv) {
        Picasso.with(iv.getContext())
                .load(String.format(URL, iv.getWidth(), iv.getHeight()))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .noPlaceholder()
                .into(iv);
    }
}
