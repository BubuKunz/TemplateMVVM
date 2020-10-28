package yan.zubritskiy.templatemvvm.business.network

import yan.zubritskiy.templatemvvm.business.model.Post

interface PostsNetworkDataSource {

    /**
     * @param cursor - cursor to load next page
     */
    suspend fun loadPosts(
        cursor: String?,
        categoryId: String?,
        from: Long?,
        to: Long?
    ): Result<List<Post>>

    suspend fun loadPost(
        id: String
    ): Result<Post>
}