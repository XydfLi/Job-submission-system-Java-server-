package com.test;

import com.api.SQLInjection.FilterSQLInjection;
import com.api.dao.*;
import com.api.model.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

public class SQLInjectionTest {

    public static void main(String[] args) {
        BaseCRUD<Account> accountBaseCRUD=new AccountDaoImpl();
        Account account=new Account();
        account.setIdentity(2);
        account.setAccountId("'';select * from Account where accountId='221801001'");
        account.setPassWord("");
        account.setAccountName("");
        Account account1=accountBaseCRUD.readByKey(new Object[]{account.getAccountId()});
        System.out.println(account1);
        String s="'';select * from Account where accountId='221801001'";
        System.out.println(FilterSQLInjection.sqlInjection(s));
        FilterSQLInjection.isSQLValid(s);


        Key key=MacProvider.generateKey(SignatureAlgorithm.HS512);
        SignatureAlgorithm signatureAlgorithm =SignatureAlgorithm.HS256;
        String jwtString = Jwts.builder()
                .setIssuer("Jersey-Security-Basic")//设置发行人
                .setSubject("tel")//设置抽象主题
                .setAudience("user")//设置角色
                .setExpiration(new Date())//过期时间
                .setIssuedAt(new Date())//设置现在时间
                .setId("1")//版本1
                .signWith(signatureAlgorithm,key)
                .compact();
        System.out.println(jwtString);

        Logger logger=Logger.getLogger(SQLInjectionTest.class.getName());
        logger.info("jdklsafdsa");
    }
}
