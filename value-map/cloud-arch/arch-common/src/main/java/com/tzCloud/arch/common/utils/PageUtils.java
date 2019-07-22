package com.tzCloud.arch.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 对list再分页
 *
 * @author : whisky_vip
 * @date : 2018/9/4 09:34
 */
public class PageUtils {
    public static class Page<T> implements Serializable {
        /**
         * 当前页
         */
        private Integer page;
        /**
         * 每页显示记录条数
         */
        private int     perPage;
        /**
         * 总页数
         */
        private int     total;
        /**
         * 总页数
         */
        private int     totalPage;
        /**
         * 每页显示的数据
         */
        private List<T> list;
        /**
         * 开始数据
         */
        private int     star;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }
    }

    public static <T> Page<T> fen(int page, int perPage, List<T> data) {
        Page<T> result = new Page<>();
        result.setPage(page);
        result.setPerPage(perPage);
        try {
            result.setPage(page);
            //设置每页数据为十条
            result.setPerPage(perPage);
            //每页的开始数
            result.setStar((page - 1) * perPage);
            //list的大小
            int count = data.size();
            //设置总页数
            result.setTotalPage(count % perPage == 0 ? count / perPage : count / perPage + 1);
            result.setTotal(count);
            //对list进行截取
            result.setList(data.subList(result.getStar(), count - result.getStar() > result.getPerPage() ? result.getStar() + result.getPerPage() : count));
            //设置作用域
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 56; i++) {
            list.add(i);
        }
        Page page = PageUtils.fen(3, 20, list);
        System.out.println(JsonUtil.toJSON(page));
    }
}
