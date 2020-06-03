package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 根据标签ID查询最新问题列表
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem ,tb_pl where id=problemid and labelid =? ORDER BY replytime desc",nativeQuery = true )
    public Page<Problem> newList(String labelid, Pageable pageable);

    //最热评论
    @Query(value = "select * from tb_problem ,tb_pl where id=problemid and labelid =? ORDER BY reply desc",nativeQuery = true )
    public Page<Problem> hotList(String labelid, Pageable pageable);

    /**
     * 根据标签ID查询等待回答列表
     * @param labelid
     * @param pageable
     * @return
     */
    @Query(value = "select * from tb_problem ,tb_pl where id=problemid and labelid =? AND reply=0  ORDER BY createtime desc",nativeQuery = true )
    public Page<Problem> waitList(String labelid, Pageable pageable);
}
