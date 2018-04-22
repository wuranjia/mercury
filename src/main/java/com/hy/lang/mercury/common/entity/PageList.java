package com.hy.lang.mercury.common.entity;

import java.io.Serializable;
import java.util.List;

public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int current;//pageNo
    protected int pageSize;
    protected Long pageTotal;
    protected long total;
    protected int draw;
    protected List<T> items;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageTotal() {
        return this.pageSize <= 0 ? 0L : (this.total - 1L) / (long) this.pageSize + 1L;
    }

    public void setPageTotal(Long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
