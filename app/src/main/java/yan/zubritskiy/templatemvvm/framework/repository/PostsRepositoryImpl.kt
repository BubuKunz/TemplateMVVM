package yan.zubritskiy.templatemvvm.framework.repository

import kotlinx.coroutines.flow.Flow
import yan.zubritskiy.templatemvvm.business.local.PostsLocalDataSource
import yan.zubritskiy.templatemvvm.business.model.Post
import yan.zubritskiy.templatemvvm.business.network.*
import yan.zubritskiy.templatemvvm.business.repository.PostsRepository

class PostsRepositoryImpl(
    private val localDataSource: PostsLocalDataSource,
    private val networkDataSource: PostsNetworkDataSource
) : PostsRepository {
    override suspend fun loadPosts(
        cursor: String?,
        categoryId: String?,
        from: Long?,
        to: Long?
    ): Result<Nothing> {
        return networkDataSource.loadPosts(
            cursor = cursor,
            categoryId = categoryId,
            from = from,
            to = to
        ).onSuccessNotNull {
            if (cursor == null) localDataSource.replaceAll(it)
            else localDataSource.add(it)
        }.mapNothing()
    }

    override fun getPosts(
        categoryId: String?,
        from: Long?,
        to: Long?
    ): Flow<List<Post>> = localDataSource.getPostsFlow(
        categoryId = categoryId,
        from = from,
        to = to
    )

    override suspend fun loadPost(postId: String): Result<Nothing> {
        return networkDataSource.loadPost(postId)
            .onSuccessNotNull {
                localDataSource.add(it)
            }
            .map {
                it ?: Result.Error(NetworkError.Unknown("Post was not loaded"))
            }
            .mapNothing()
    }

    override fun getPost(postId: String): Flow<Post?> = localDataSource.getPostFlow(postId)
}