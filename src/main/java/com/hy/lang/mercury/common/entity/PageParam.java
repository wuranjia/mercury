package com.hy.lang.mercury.common.entity;

import java.io.Serializable;

public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int limit = 10;
    protected int page = 1;
    protected int start;
    protected int draw;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return (this.page - 1) * this.limit;
    }


}
