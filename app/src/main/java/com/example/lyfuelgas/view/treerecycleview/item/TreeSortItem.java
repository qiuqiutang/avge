package com.example.lyfuelgas.view.treerecycleview.item;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public abstract class TreeSortItem<T> extends TreeItemGroup<T> {
    protected Object sortKey;

    public void setSortKey(Object sortKey) {
        this.sortKey = sortKey;
    }

    public Object getSortKey() {
        return sortKey;
    }

}
