package com.atom.tool.testbean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 员工信息
 *
 * @author Atom
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestEmployeesInfo {

    private String cn;
    private String staffName;
    private String mobile;
    private String staffNameEn;

}
