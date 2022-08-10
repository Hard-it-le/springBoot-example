package com.yjl.jpa.repository;

import com.yjl.jpa.entity.Users;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Author yujiale
 * @Date 2022/8/10 21:07
 * @Description PagingAndSortingRepository接口
 **/
public interface UsersRepositoryPagingAndSorting extends PagingAndSortingRepository<Users,Integer> {

}
