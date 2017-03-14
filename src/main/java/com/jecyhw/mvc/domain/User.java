package com.jecyhw.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by jecyhw on 16-11-21.
 */
@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    /**
     * 用户名
     */
    @Indexed(unique = true)
    @Field("user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    private Date registerDate;
}
