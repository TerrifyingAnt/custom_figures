package xd.jg.custom_figures

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import xd.jg.custom_figures.data.local.dao.BasketDao
import xd.jg.custom_figures.data.local.entity.BasketItemEntity
import xd.jg.custom_figures.data.local.entity.FigureType
import xd.jg.custom_figures.data.repository.local.BasketRepository

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val dao = mockk<BasketDao>(relaxed = true)
    private val figure = BasketItemEntity(0, FigureType.CUSTOM_BY_PHOTO.ordinal)
    private val repository = BasketRepository(dao)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("xd.jg.custom_figures", appContext.packageName)
    }






}