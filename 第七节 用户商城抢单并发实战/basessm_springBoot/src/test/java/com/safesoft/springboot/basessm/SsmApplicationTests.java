package com.safesoft.springboot.basessm;

import com.safesoft.springboot.basessm.dao.ProductDao;
import com.safesoft.springboot.basessm.entity.ProductRecord;
import com.safesoft.springboot.basessm.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SsmApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;
    private static final String PRODUCT_NO = "No123321";
    @Test
    public void notNull() {
        Assert.assertNotNull(userService);
    }

    @Test
    public void testMethod() {
        Assert.assertEquals("jay", userService.selectByPrimaryKey(1L).getUsername());
        //Assert.assertTrue(userService.insert(new UserEntity("KK", 23)));
    }

    @Test
    public void testMethod2() {
        productDao.updateProduct(PRODUCT_NO);
        ProductRecord record = new ProductRecord(1L,"no",2);
        Assert.assertTrue(productDao.insertProductRecord(record)>0);
    }

}
