package com.funtl.itoken.service.posts.service.impl;

import com.funtl.itoken.common.domain.TbPostsPost;
import com.funtl.itoken.common.mapper.TbPostsPostMapper;
import com.funtl.itoken.common.service.impl.BaseServiceImpl;
import com.funtl.itoken.service.posts.service.PostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: BernardLowe
 * @Description:
 * @Date: Created in 13:53 2019/6/21
 * @Modified By: BernardLowe
 */
@Service
@Transactional(readOnly = true)
public class PostsServiceImpl extends BaseServiceImpl<TbPostsPost,TbPostsPostMapper> implements PostsService {

}
