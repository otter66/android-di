package woowacourse.shopping.di

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication

@MainThread
inline fun <reified VM : ViewModel> ViewModelStoreOwner.viewModels(): Lazy<VM> {
    val viewModelFactory = viewModelFactory {
        initializer {
            ShoppingApplication.injector.inject<VM>()
        }
    }
    return ViewModelLazy(
        VM::class,
        { viewModelStore },
        { viewModelFactory },
    )
}
