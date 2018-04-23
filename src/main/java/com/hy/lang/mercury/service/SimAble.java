package com.hy.lang.mercury.service;

import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.pojo.SimBase;
import com.hy.lang.mercury.resource.req.SimBaseReq;
import com.hy.lang.mercury.resource.resp.SimBaseResp;
import com.hy.lang.mercury.resource.resp.SimMatrixResp;

import javax.validation.constraints.NotNull;

public interface SimAble {
    PageList<SimBase> list(SimBaseReq sim);

    void importCsv(String path, Long userId);

    SimMatrixResp matrix(Long userId);

    PageList<SimBase> indexFlowUse(SimBaseReq req);

    SimBaseResp indexCardInfo(@NotNull Long userId);

    void assign(String[] cards, String userId);
}
