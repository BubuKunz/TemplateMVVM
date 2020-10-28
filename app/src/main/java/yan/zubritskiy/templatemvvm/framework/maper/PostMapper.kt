package yan.zubritskiy.templatemvvm.framework.maper

import yan.zubritskiy.templatemvvm.framework.model.Post
import yan.zubritskiy.templatemvvm.framework.model.Post as BusinessModel

class PostMapper {

    fun toDomain(model: Post): BusinessModel {
        return BusinessModel(
            id = model.id
        )
    }

    fun fromDomain(model: BusinessModel): Post {
        return Post(
            id = model.id
        )
    }
}