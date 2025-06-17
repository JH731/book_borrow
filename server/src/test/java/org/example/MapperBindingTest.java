package org.example;
import com.example.BookBorrowApplication;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = BookBorrowApplication.class)
public class MapperBindingTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void testPageQueryMapping() {
        Configuration config = sqlSessionFactory.getConfiguration();
        boolean exists = config.hasStatement("com.example.mapper.EmployeeMapper.pageQuery");
        System.out.println("pageQuery 映射是否存在: " + exists);

        // 打印所有已加载的 SQL 映射
        config.getMappedStatements().forEach(ms -> System.out.println("已加载映射: " + ms.getId()));
    }
}