package com.atom.tool.testbean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Atom
 */
@Data
@AllArgsConstructor
public class TestUserVO {

    private String name;
    private String password;
    private Integer age;
    private String gender;
}
