package com.iwritebug.cleandemo

import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.iwritebug.cleandemo.adapter.MovieAdapter
import com.iwritebug.cleandemo.di.ApplicationComponent
import com.iwritebug.cleandemo.extension.failure
import com.iwritebug.cleandemo.extension.observe
import com.iwritebug.cleandemo.extension.viewModel
import com.iwritebug.domain.Failure
import com.iwritebug.viewmodel.MovieView
import com.iwritebug.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        moviesViewModel = viewModel(viewModelFactory) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }
        pb.visibility = View.VISIBLE
        recyclerView.layoutManager = GridLayoutManager(this,3)
        Thread(Runnable {
            Thread.sleep(2000)
            moviesViewModel.loadMovies()
        }).start()
    }

    private fun renderMoviesList(movies: List<MovieView>?) {
        pb.visibility = View.GONE
        val imageAdapter = MovieAdapter(this, movies)
        recyclerView.adapter  = imageAdapter
    }

    private fun handleFailure(failure: Failure?) {
        Log.e("sfsfsd","!!!!!!!!!!!!!!!!!!!")
    }
}
