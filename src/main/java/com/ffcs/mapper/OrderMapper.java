package com.ffcs.mapper;

import com.ffcs.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by urbo on 2018/8/18.
 */
@Mapper
public interface OrderMapper {

    /**
     * 1. list订单明细
     * 2. 订单数量及总金额
     * 3. 保存订单
     * 4. 退单 -> update
     */

    String QUERY_SQL = "<script>select o.*, a.sum from order_ o,( " +
            "select o.*,sum(oi.num * p.price) sum " +
            "from order_ o,order_item oi,product p " +
            "where oi.oid = o.id and oi.pid = p.id " +
            "group by o.id) a " +
            "where o.id = a.id" +
            "<if test='uid!=0'>and o.uid = #{uid}</if></script>";

    String AMOUNT_SQL = "select sum(t.sum) from ( " +
            "select o.*,sum(oi.num * p.price) sum " +
            "from order_ o,order_item oi,product p " +
            "where oi.oid = o.id " +
            "and oi.pid = p.id " +
            "group by o.id ) t";

    @Select(QUERY_SQL)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "orderItems", javaType = List.class, column = "id", many = @Many(select =
                    "com.ffcs.mapper.OrderItemMapper.listByOrder"))
    })
    List<Order> listAll(@Param("uid") int uid);

    @Select("select count(*) from order_")
    int count();

    @Select(AMOUNT_SQL)
    int amount();

    @Insert("insert into order_(uid,order_time) values(#{uid},now())")
    @Options(useGeneratedKeys=true)
    void saveOrder(Order order);

    @Update("update order_ set state = 1,refund_time = now() where id = #{id}")
    void updateOrder(int id);
}
