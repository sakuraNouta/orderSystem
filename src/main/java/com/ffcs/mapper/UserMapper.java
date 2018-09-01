package com.ffcs.mapper;

import com.ffcs.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by urbo on 2018/8/20.
 */
@Mapper
public interface UserMapper {

    /**
     * 登陆判断
     */
    @Select("select * from user_ where tel = #{tel} and pwd = #{pwd}")
    User isUser(User user);

    /**
     * 注册->增加
     * */
    @Insert("insert into user_(name,tel,pwd) values(#{name},#{tel},#{pwd})")
    void addUser(User user);
}
