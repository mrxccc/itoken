package com.funtl.itoken.common.web.components.datatables;

import com.funtl.itoken.common.dto.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description:
 * @auther: BernardLowe
 * @date: 15:17 2019/6/22 
 * @param: 
 * @return: 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataTablesResult extends BaseResult implements Serializable {
    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private String error;
}
