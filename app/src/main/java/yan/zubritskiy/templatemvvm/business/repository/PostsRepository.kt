package yan.zubritskiy.templatemvvm.business.repository

import kotlinx.coroutines.flow.Flow
import yan.zubritskiy.templatemvvm.business.model.Post
import yan.zubritskiy.templatemvvm.business.network.Result

interface PostsRepository {

    /**
     * Will load posts and cache them.
     * @param cursor - cursor to load next page. If cursor null, then will be fetched first page.
     */
    suspend fun loadPosts(
        cursor: String?,
        categoryId: String?,
        from: Long?,
        to: Long?
    ): Result<Nothing>

    /**
     * @param categoryId - filter param. If null then will not be applied to filter
     * @param from - filter param. If null then will not be applied to filter
     * @param to - filter param. If null then will not be applied to filter
     * @return cached posts
     */
    fun getPosts(
        categoryId: String?,
        from: Long?,
        to: Long?
    ): Flow<List<Post>>

    suspend fun loadPost(postId: String): Result<Nothing>

    fun getPost(postId: String): Flow<Post?>
}