package com.ffcs.mapper;

import com.ffcs.pojo.OrderItem;
import com.ffcs.pojo.TempData;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by urbo on 2018/8/18.
 */
@Mapper
public interface OrderItemMapper {

    /**
     * 1.提供order.listAll()一对多方法需要的listByOrder() 需要pid对应的productName
     * 2. 为Order.saveOrder() 实现级联 OrderItem.save()
     * 2.按商品列出订单 一对多 OrderItem->Product
     */

    @Select("select * from order_item where oid = #{oid}")
    @Results({
        @Result(property = "product",column = "pid", one=@One(select="com.ffcs.mapper.ProductMapper.get") )
    })
    List<OrderItem> listByOrder(int oid);

    @Insert("insert into order_item(pid,oid,num) values(#{pid},#{oid},#{num})")
    void save(OrderItem orderItem);



    @Select("select p.name name,count(*) count,sum(num) sum from order_item oi,product p where oi.pid = p.id group by pid")
    List<TempData> findByProduct();

}
