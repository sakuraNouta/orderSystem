package com.ffcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffcs.mapper.UserMapper;
import com.ffcs.pojo.User;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * OrderController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>°ËÔÂ 19, 2018</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;
    @Mock
    private UserMapper userMapper;


    @Before
    public void before() throws Exception {

        //MockitoAnnotations.initMocks(this);

        OrderController controller = new OrderController();

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        //mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: ListOrder()
     */
    @Test
    public void testListOrder() throws Exception {

        mockMvc.perform(get("/order/0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testLogin() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"tel\":\"13400501160\",\"pwd\":\"123456\"}";
        User user = mapper.readValue(jsonString, User.class);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }


} 
