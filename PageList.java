package com.logic.page.listlogicpage.util;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>描述</pre>
 * 逻辑分页实体类
 *
 * @version 1.0
 * @author: Kyle Wong
 * @date: 2020/1/13 11:33
 */
@Data
public class PageList<T> {

    /**
     * 当前页
     */
    private Integer current;
    /**
     * 总记录数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer sumPages;
    /**
     * 页面记录数
     */
    private Integer pageSize;
    /**
     * 判断是不是首页
     */
    private Boolean isFirstPage = false;
    /**
     * 判断是不是最后一页
     */
    private Boolean isLastPage = false;
    private List<T> list;
    int startIndex = 0;
    int endIndex = 0;
    public PageList(List<T> list, Integer current, Integer pageSize) {
        this.current = current;
        this.pageSize = pageSize;
        this.totalCount = list.size();
        // 计算总页数
        boolean isFullPages = totalCount % pageSize == 0;
        if (!isFullPages) {
            // 若当前数据总数与页面数据大小相除不为整数，则增加一页显示剩余的数据
            this.sumPages = totalCount / pageSize + 1;
        } else {
            this.sumPages = totalCount / pageSize;
        }
        startIndex = current * pageSize - pageSize;
        if (current <= 0) {
            // 若查询的页数是小于等于0的则直接取第一页
           this.startIndex = 0;
           this.current = 1;
           endIndex = this.current * pageSize;
            // throw new ArithmeticException("当前页无法查询");
        } else if (current > sumPages) {
            // 若查询的页数大于总页数，则置list为空
            list = new ArrayList<>();
        } else if (current.equals(sumPages)) {
            endIndex = totalCount;
        } else {
            endIndex = current * pageSize;
        }

        // 判断是否为首页
        if (this.current == 1) {
            isFirstPage = true;
        }

        // 判断是否为最后一页
        if (this.current.equals(sumPages)) {
            isLastPage = true;
        }

        if (CollectionUtils.isEmpty(list)) {
            this.list = new ArrayList<>();
        } else {
            this.list = list.subList(startIndex, endIndex);
        }
    }

}
