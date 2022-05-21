# nsolm-blog

使用SpringBoot+MybatisPlus开发
其中blog-api 是博客的主要功能，包含发帖，评论，blog-admin 后台管理系统。
  每个帖子包含不同的标签。可以主页上显示最热文章，最新文章，最新标签。
  使用redis+JWT实现单点登录，对用户登录做了缓存，灵活控制用户的登录状态（续期，踢掉线）。
  使用ThreadLocal保存用户信息，请求线程之内，可以随时获取登录用户的信息，做了线程隔离。
  更新浏览量时，使用了CAS锁保证线程安全。
  查看文章时，使用线程池异步更新文章的浏览量，主线程直接返回文章信息。
  用redis缓存主页的最热文章，通过先修改数据库删除缓存的方式解决缓存一致性问题。
  使用AOP统一记录日志，统一缓存处理。
  