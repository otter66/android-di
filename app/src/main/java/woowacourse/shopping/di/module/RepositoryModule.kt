package woowacourse.shopping.di.module

import woowacourse.shopping.data.DefaultCartRepository
import woowacourse.shopping.data.DefaultProductRepository
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

object RepositoryModule : Module {
    fun provideProductRepository(): ProductRepository = DefaultProductRepository()
    fun provideCartRepository(): CartRepository = DefaultCartRepository()
}
