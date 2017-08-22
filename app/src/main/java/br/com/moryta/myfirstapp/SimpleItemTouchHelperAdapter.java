package br.com.moryta.myfirstapp;

/**
 * Created by moryta on 22/08/2017.
 */

public interface SimpleItemTouchHelperAdapter {
    boolean onItemMoved(int fromPosition, int toPosition);

    <T> T onItemSwiped(int position);
}
