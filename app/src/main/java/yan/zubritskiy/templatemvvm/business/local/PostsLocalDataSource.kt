package yan.zubritskiy.templatemvvm.business.local

import kotlinx.coroutines.flow.Flow
import yan.zubritskiy.templatemvvm.business.model.Post

interface PostsLocalDataSource {

    /**
     * @return cached data
     */
    fun getPostsFlow(
        categoryId: String?,
        from: Long?,
        to: Long?
    ): Flow<List<Post>>

    /**
     * @return cached data
     */
    fun getPostFlow(
        postId: String
    ): Flow<Post?>

    /**
     * plain adding
     */
    fun add(posts: List<Post>)

    /**
     * plain adding
     */
    fun add(post: Post)

    /**
     * clear all cached data and add
     */
    fun replaceAll(posts: List<Post>)
}