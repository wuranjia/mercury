package com.hy.lang.mercury.resource.rest;

import com.alibaba.fastjson.JSON;
import com.hy.lang.mercury.common.entity.PageList;
import com.hy.lang.mercury.common.entity.ResponseEntity;
import com.hy.lang.mercury.common.utils.DateTimeUtils;
import com.hy.lang.mercury.pojo.Store;
import com.hy.lang.mercury.pojo.StoreDetail;
import com.hy.lang.mercury.resource.req.StoreReq;
import com.hy.lang.mercury.service.StoreAble;
import com.hy.lang.mercury.service.impl.StoreService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * 库存管理相关
 */
@RestController
@RequestMapping(value = "/store")
public class StoreResource extends BaseResource {

    private final StoreAble storeService;

    public StoreResource(StoreAble storeService) {
        this.storeService = storeService;
    }

    @RequestMapping(path = "/in/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String buyList(
            @RequestBody StoreReq req) {
        try {
            PageList<Store> resp = storeService.inList(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/out/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sellList(
            @RequestBody StoreReq req) {
        try {
            PageList<Store> resp = storeService.outList(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(path = "/detail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String detail(
            @RequestBody StoreReq req) {
        try {
            List<StoreDetail> resp = storeService.detailList(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(resp));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    public String export( @RequestBody StoreReq req) {
        try {
            String url = storeService.export(req);
            return JSON.toJSONString(ResponseEntity.createBySuccess(url));
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.toJSONString(ResponseEntity.createByError());
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(
            @RequestParam("file") MultipartFile file) {
        String name = DateTimeUtils.dateToString(new Date(), DateTimeUtils.yyyy_MM_dd_HH_mm_ss);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }


}
