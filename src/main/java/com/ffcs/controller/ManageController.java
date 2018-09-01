package com.ffcs.controller;

import com.ffcs.mapper.OrderItemMapper;
import com.ffcs.mapper.OrderMapper;
import com.ffcs.mapper.ProductMapper;
import com.ffcs.pojo.OrderItem;
import com.ffcs.pojo.Product;
import com.ffcs.pojo.TempData;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by urbo on 2018/8/20.
 */
@RestController
public class ManageController {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductMapper productMapper;

    static Logger logger = Logger.getLogger(OrderController.class);

    /**
     * 增删改查接口
     */
    @PostMapping("/product")
    public String addProduct(@RequestBody  Product p) throws Exception {
        productMapper.save(p);
        return "OK";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam(value="id") int id) throws Exception {
        productMapper.delete(id);
        return "OK";
    }

    @GetMapping("/queryProduct")
    public String getProduct(@RequestParam(value="text") String text) throws Exception {
        JSONArray array = new JSONArray();
        List<Product> products = productMapper.getProducts(text);
        for(Product product : products) {
            array.add(JSONObject.fromObject(product));
        }

        return array.toString();
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestBody Product product) throws Exception {
        productMapper.update(product);
        return "OK";
    }

    /**
     * 按商品id列出订单
     */
    @GetMapping("/findOrderItem")
    public String findOrderItem() {
        JSONArray array = new JSONArray();
        List<TempData> data = orderItemMapper.findByProduct();
        for (TempData d : data) {
            array.add(JSONObject.fromObject(d));
        }
        return array.toString();
    }

    /**
     * 订单总数量和总金额
     */
    @GetMapping("/getCountAndAmount")
    public String getAmount() {
        JSONObject json = new JSONObject();
        int count = orderMapper.count();
        int amount = orderMapper.amount();

        json.put("count", count);
        json.put("amount", amount);

        return json.toString();
    }

    /**
     * 上传图片
     * */
    @PostMapping("/upload")
    public String upload(HttpServletRequest req,
                         @RequestParam("file") MultipartFile file, Model m) {

        JSONObject json = new JSONObject();
        String path = "img/default.jpg";

        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            File destFile = new File("E:\\Java\\Project\\orderSystem\\src\\main\\resources\\static\\upload/"+fileName);
            path = "upload/" + fileName;
            logger.debug(path);
            json.put("path", path);

            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败，" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败，" + e.getMessage();
        }
        return json.toString();
    }
}
