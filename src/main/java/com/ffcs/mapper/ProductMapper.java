package com.ffcs.mapper;

import com.ffcs.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by urbo on 2018/8/18.
 */
@Mapper
public interface ProductMapper {
    /**
     * 1.list所有商品
     * 2.增删改查
     */


    String INSERT_SQL = "<script>insert into product(name,price,img_src,info) " +
            "<choose><when test='imgSrc==null'>values(#{name},#{price},'img/default.jpg',#{info})</when>" +
            "<otherwise>values(#{name},#{price},#{imgSrc},#{info})</otherwise></choose></script>";

    String QUERY_SQL = "select * from product where name like '%${text}%' or info like '%${text}%'";

    @Select("select * from product limit #{start},#{size}")
    List<Product> listAll(@Param("start") int start, @Param("size") int size);

    @Select("select * from product where id = #{id}")
    Product get(int id);

    @Insert(INSERT_SQL)
    void save(Product product);

    @Delete("delete from product where id = #{id}")
    void delete(@Param(value="id") int id);

    @Select(QUERY_SQL)
    List<Product> getProducts(@Param("text") String text);

    @Update("update product set name=#{name},price=#{price},img_src=#{imgSrc},info=#{info} where id = #{id}")
    void update(Product product);

}
