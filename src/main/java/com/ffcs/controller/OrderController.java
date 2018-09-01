package com.ffcs.controller;

import com.ffcs.mapper.OrderItemMapper;
import com.ffcs.mapper.OrderMapper;
import com.ffcs.mapper.ProductMapper;
import com.ffcs.mapper.UserMapper;
import com.ffcs.pojo.Order;
import com.ffcs.pojo.OrderItem;
import com.ffcs.pojo.Product;
import com.ffcs.pojo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.omg.IOP.ExceptionDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by urbo on 2018/8/18.
 */
@RestController
@RequestMapping("")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    UserMapper userMapper;

    static Logger logger = Logger.getLogger(OrderController.class);


    /**
     * 登陆注册
     */
    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response) {

        String flag = "1";
        User login = userMapper.isUser(user);
        if (login != null) {
            Cookie cookie = new Cookie("uid", login.getId() + "");
            cookie.setMaxAge(3 * 60 * 60);//设置为3小时
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            /*无此用户或密码错误*/
            flag = "0";
        }
        return flag;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userMapper.addUser(user);
        return "OK";
    }

    @GetMapping("/order/{uid}")
    public String listOrderByUid(@PathVariable(value="uid", required = false) int uid) {
        JSONArray array = new JSONArray();

        List<Order> orders = orderMapper.listAll(uid);

        for (Order order : orders) {
            array.add(JSONObject.fromObject(order));
        }

        return array.toString();
    }

    @GetMapping("/order")
    public String listOrder(@RequestParam(value = "uid") int uid) {

        JSONArray array = new JSONArray();

        List<Order> orders = orderMapper.listAll(uid);

        for (Order order : orders) {
            array.add(JSONObject.fromObject(order));
        }

        return array.toString();
    }

    /**
     * 提交订单
     */
    @PostMapping("/order")
    public String addOrder(@RequestBody Order order) {

        orderMapper.saveOrder(order);
        int oid = order.getId();
        for (OrderItem oi : order.getOrderItems()) {
            oi.setOid(oid);
            orderItemMapper.save(oi);
        }

        return "OK";
    }

    @PostMapping("/refund")
    public String updateOrder(@RequestBody Order order) {
        orderMapper.updateOrder(order.getId());
        return "OK";
    }


    @GetMapping("/product")
    public String ListProduct(@RequestParam(value = "start", defaultValue = "0") int start,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        JSONArray array = new JSONArray();
        List<Product> products = productMapper.listAll(start, size);
        if (products.isEmpty()) {
            return "null";
        }
        for (Product product : products) {
            array.add(JSONObject.fromObject(product));
        }
        return array.toString();
    }

}
