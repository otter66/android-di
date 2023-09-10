package woowacourse.shopping.di.injector

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import woowacourse.shopping.di.module.Module
import woowacourse.shopping.di.viewModels
import woowacourse.shopping.provider.Fake
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

@RunWith(RobolectricTestRunner::class)
@Config(application = InjectorTestApplication::class)
class InjectorTest() {

    @Test
    fun `Inject annotation이 있는 프로퍼티에만 의존성을 주입한다`() {
        // given
        val activity = Robolectric
            .buildActivity(InjectorTestActivity::class.java)
            .create()
            .get()

        // then
        val viewModel = activity.viewModel
        TestCase.assertNotNull(viewModel.productRepository)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun `Inject annotation이 없는 프로퍼티는 초기화되지 않는다`() {
        // given
        val activity = Robolectric
            .buildActivity(InjectorTestActivity::class.java)
            .create()
            .get()

        // then
        val viewModel = activity.viewModel
        viewModel.cartRepository
    }
}

class InjectorTestViewModel() : ViewModel() {
    @Inject
    lateinit var productRepository: ProductRepository
    lateinit var cartRepository: CartRepository
}

class InjectorTestApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        injector = Injector(InjectorTestRepositoryModule)
    }

    companion object {
        lateinit var injector: Injector
    }
}

class InjectorTestActivity() : AppCompatActivity() {
    val viewModel: InjectorTestViewModel by viewModels(InjectorTestApplication.injector)
}

object InjectorTestRepositoryModule : Module {
    fun provideFakeProductRepository(): ProductRepository = Fake.ProductRepository()
    fun provideFakeCartRepository(): CartRepository = Fake.CartRepository()
}
