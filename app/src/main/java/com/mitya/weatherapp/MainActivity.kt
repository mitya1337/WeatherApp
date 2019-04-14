package com.mitya.weatherapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mitya.weatherapp.ui.main.MainFragment
import com.mitya.weatherapp.ui.search.SearchFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            replaceCurrentFragment(MainFragment.newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchItem -> {
                val fragment = supportFragmentManager.findFragmentByTag(SearchFragment::class.java.name)
                if (fragment == null || !fragment.isVisible) replaceCurrentFragment(
                    SearchFragment.newInstance(),
                    addToBackStack = true
                )
                true
            }
            R.id.mainItem -> {
                replaceCurrentFragment(MainFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun replaceCurrentFragment(newFragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, newFragment, newFragment.javaClass.name)
            .apply { if (addToBackStack) addToBackStack(null) }
            .commit()

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(MainFragment::class.java.name)
        if (fragment != null && fragment.isVisible) finish()
        else super.onBackPressed()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
