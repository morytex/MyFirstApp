package br.com.moryta.myfirstapp;

import android.view.View;

/**
 * Created by moryta on 24/08/2017.
 */

public interface OnItemClickListener {
    <T> void onItemClick(T item, View view);
}
