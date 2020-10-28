package yan.zubritskiy.templatemvvm.ui.maper

import yan.zubritskiy.templatemvvm.ui.model.Post
import yan.zubritskiy.templatemvvm.ui.model.Post as BusinessModel

class PostMapper {

    fun to(model: Post): BusinessModel {
        return BusinessModel(
            id = model.id
        )
    }

    fun from(model: BusinessModel): Post {
        return Post(
            id = model.id
        )
    }
}