package weimin.tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void transfer(){
        jdbcTemplate.update("update t_account set balance=? where name=?",1500,"weimin");
        jdbcTemplate.update("update t_account set balance=? where name=?",1000,"xiaoming");

        //int a = 10/0;
    }
}
